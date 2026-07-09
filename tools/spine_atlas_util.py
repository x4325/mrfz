#!/usr/bin/env python3
"""Utilities for legacy (Spine 3.x / libgdx) .atlas files.

Purpose: Slay the Spire bundles an old libgdx whose TextureAtlas only
understands `rotate: true|false`.  Atlases packed with newer Spine texture
packers may contain numeric rotation values (`rotate: 90|180|270`) which the
old loader parses as `false`, producing garbled / wrongly rotated parts in
game.  This module can unpack such atlases and repack them without any
rotated regions so they load correctly in the old runtime.

Rotation convention (verified empirically, matches spine-libgdx):
  the value is the number of degrees the original image was rotated
  counter-clockwise when placed in the atlas page; `true` == 90.
  For 90/270 the packed rect has swapped width/height.
"""
from PIL import Image


class Region:
    __slots__ = ("name", "attrs", "order")

    def __init__(self, name, attrs, order):
        self.name = name
        self.attrs = attrs  # dict, values kept as raw strings
        self.order = order

    @property
    def degrees(self):
        v = self.attrs.get("rotate", "false")
        if v == "true":
            return 90
        if v == "false":
            return 0
        return int(v)

    @property
    def xy(self):
        x, y = self.attrs["xy"].split(",")
        return int(x), int(y)

    @property
    def size(self):
        w, h = self.attrs["size"].split(",")
        return int(w), int(h)


def parse_atlas(path):
    """Returns (page_header_lines, page_name, [Region...]). Single-page only."""
    lines = open(path, encoding="utf-8").read().splitlines()
    i = 0
    while i < len(lines) and not lines[i].strip():
        i += 1
    page_name = lines[i].strip()
    i += 1
    header = {}
    while i < len(lines) and ":" in lines[i] and not lines[i].startswith((" ", "\t")):
        k, v = lines[i].split(":", 1)
        if k.strip() not in ("size", "format", "filter", "repeat", "pma"):
            break
        header[k.strip()] = v.strip()
        i += 1
    regions = []
    order = 0
    while i < len(lines):
        line = lines[i]
        if not line.strip():
            i += 1
            continue
        if line.endswith(".png"):
            raise ValueError("multi-page atlas not supported: " + path)
        name = line.strip()
        i += 1
        attrs = {}
        while i < len(lines) and lines[i].startswith((" ", "\t")):
            k, v = lines[i].strip().split(":", 1)
            attrs[k.strip()] = v.strip()
            i += 1
        regions.append(Region(name, attrs, order))
        order += 1
    return header, page_name, regions


def extract_region(page_img, region, degrees=None):
    """Crop a region from the page and return it upright (unrotated)."""
    d = region.degrees if degrees is None else degrees
    x, y = region.xy
    w, h = region.size
    pw, ph = (h, w) if d in (90, 270) else (w, h)
    crop = page_img.crop((x, y, x + pw, y + ph))
    if d:
        # Packed image was rotated d degrees CCW; rotate d degrees CW to undo.
        # PIL transpose ROTATE_x rotates counter-clockwise, so use 360-d.
        undo = {90: Image.Transpose.ROTATE_270,
                180: Image.Transpose.ROTATE_180,
                270: Image.Transpose.ROTATE_90}[d]
        crop = crop.transpose(undo)
    return crop


def pack_shelf(items, max_width, padding=2):
    """items: list of (key, w, h). Returns (positions{key:(x,y)}, W, H)."""
    items = sorted(items, key=lambda t: (-t[2], -t[1], t[0]))
    positions = {}
    x = y = shelf_h = 0
    used_w = 0
    for key, w, h in items:
        if x + w + padding > max_width and x > 0:
            y += shelf_h + padding
            x = 0
            shelf_h = 0
        positions[key] = (x + padding, y + padding)
        x += w + padding
        shelf_h = max(shelf_h, h)
        used_w = max(used_w, x)
    total_h = y + shelf_h + 2 * padding
    total_w = used_w + 2 * padding
    return positions, total_w, total_h


def repack_atlas(atlas_path, out_atlas_path=None, out_png_path=None, max_width=1024):
    """Rewrite atlas + png so every region is stored unrotated (rotate: false)."""
    import os
    header, page_name, regions = parse_atlas(atlas_path)
    src_dir = os.path.dirname(atlas_path)
    page_img = Image.open(os.path.join(src_dir, page_name)).convert("RGBA")
    sprites = {}
    for r in regions:
        sprites[r.order] = extract_region(page_img, r)
    items = [(r.order, sprites[r.order].width, sprites[r.order].height) for r in regions]
    positions, W, H = pack_shelf(items, max_width)
    # round page size up a little so it looks like a normal packer output
    W = max(W, 64)
    H = max(H, 64)
    out = Image.new("RGBA", (W, H), (0, 0, 0, 0))
    for r in regions:
        img = sprites[r.order]
        x, y = positions[r.order]
        # 1px edge extrusion so Linear filtering does not bleed transparency
        w, h = img.size
        out.paste(img.crop((0, 0, w, 1)), (x, y - 1))
        out.paste(img.crop((0, h - 1, w, h)), (x, y + h))
        out.paste(img.crop((0, 0, 1, h)), (x - 1, y))
        out.paste(img.crop((w - 1, 0, w, h)), (x + w, y))
        out.paste(img, (x, y))

    out_atlas_path = out_atlas_path or atlas_path
    out_png_path = out_png_path or os.path.join(os.path.dirname(out_atlas_path), page_name)
    out.save(out_png_path)

    lines = ["", page_name,
             "size: %d,%d" % (W, H),
             "format: %s" % header.get("format", "RGBA8888"),
             "filter: %s" % header.get("filter", "Linear,Linear"),
             "repeat: %s" % header.get("repeat", "none")]
    for r in regions:  # keep original declaration order
        x, y = positions[r.order]
        w, h = r.size
        lines.append(r.name)
        lines.append("  rotate: false")
        lines.append("  xy: %d, %d" % (x, y))
        lines.append("  size: %d, %d" % (w, h))
        lines.append("  orig: %s" % r.attrs.get("orig", "%d, %d" % (w, h)))
        lines.append("  offset: %s" % r.attrs.get("offset", "0, 0"))
        lines.append("  index: %s" % r.attrs.get("index", "-1"))
    with open(out_atlas_path, "w", encoding="utf-8", newline="\n") as f:
        f.write("\n".join(lines) + "\n")
    return W, H


def verify_same(atlas_a, atlas_b):
    """Assert both atlases produce identical upright region images."""
    import os
    ha, pa, ra = parse_atlas(atlas_a)
    hb, pb, rb = parse_atlas(atlas_b)
    img_a = Image.open(os.path.join(os.path.dirname(atlas_a), pa)).convert("RGBA")
    img_b = Image.open(os.path.join(os.path.dirname(atlas_b), pb)).convert("RGBA")
    assert len(ra) == len(rb), "region count differs"
    bad = []
    for a, b in zip(ra, rb):
        assert a.name == b.name and a.attrs.get("index", "-1") == b.attrs.get("index", "-1")
        ia = extract_region(img_a, a)
        ib = extract_region(img_b, b)
        if ia.tobytes() != ib.tobytes():
            bad.append(a.name)
    return bad


if __name__ == "__main__":
    import sys
    for p in sys.argv[1:]:
        w, h = repack_atlas(p)
        print("repacked %s -> %dx%d" % (p, w, h))
