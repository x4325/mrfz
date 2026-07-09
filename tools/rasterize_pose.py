#!/usr/bin/env python3
"""Rasterize a pose dump produced by DumpPose.java using a .atlas + page PNG.

Simulates the OLD (Slay the Spire) libgdx TextureAtlas semantics:
`rotate: true` = 90 degrees, any numeric value parses as false (unrotated),
so the game's exact texture-mapping behaviour can be previewed offline.
"""
import json
import math
import os
import sys

from PIL import Image, ImageChops, ImageDraw

sys.path.insert(0, os.path.dirname(os.path.abspath(__file__)))
from spine_atlas_util import parse_atlas  # noqa: E402


def old_loader_uv_rect(region, page_w, page_h):
    """Return (u,v,u2,v2, rotate90) as the old libgdx loader would."""
    x, y = region.xy
    w, h = region.size
    val = region.attrs.get("rotate", "false")
    rotate90 = val == "true"
    if rotate90:
        # old loader: new AtlasRegion(texture, x, y, packedW=h, packedH=w)... region swaps
        return x, y, x + h, y + w, True
    return x, y, x + w, y + h, False


def rasterize(pose_path, atlas_path, out_path, scale=1.0, canvas=(560, 560),
              bg=(46, 46, 52, 255), highlight=None):
    pose = json.load(open(pose_path))
    header, page, regions = parse_atlas(atlas_path)
    page_img = Image.open(os.path.join(os.path.dirname(atlas_path), page)).convert("RGBA")
    pw, ph = page_img.size
    rmap = {}
    for r in regions:
        rmap[r.name] = r

    W, H = canvas
    out = Image.new("RGBA", (W, H), bg)
    cx, cy = W / 2.0, H - 40.0

    for item in pose:
        r = rmap.get(item["path"])
        if r is None or item["alpha"] <= 0.01:
            continue
        x1, y1, x2, y2, rot90 = old_loader_uv_rect(r, pw, ph)
        world = item["world"]
        uvs = item["uvs"]
        tris = item["tris"]
        pts = [(cx + world[i] * scale, cy - world[i + 1] * scale)
               for i in range(0, len(world), 2)]
        # map normalized region uvs to page pixels, honoring rotate90 like
        # spine 3.6 MeshAttachment.updateUVs / RegionAttachment.setUVs
        tex = []
        for i in range(0, len(uvs), 2):
            u, v = uvs[i], uvs[i + 1]
            if rot90:
                # spine-libgdx 3.6 MeshAttachment.updateUVs, rotate branch:
                #   uvs[i]   = u + regionUVs[i+1] * width
                #   uvs[i+1] = v + height - regionUVs[i] * height
                px = x1 + v * (x2 - x1)
                py = y1 + (1.0 - u) * (y2 - y1)
            else:
                px = x1 + u * (x2 - x1)
                py = y1 + v * (y2 - y1)
            tex.append((px, py))
        for i in range(0, len(tris), 3):
            i0, i1, i2 = tris[i], tris[i + 1], tris[i + 2]
            _tri(out, page_img, [tex[i0], tex[i1], tex[i2]],
                 [pts[i0], pts[i1], pts[i2]])
        if highlight and highlight(item["slot"]):
            d = ImageDraw.Draw(out)
            xs = [p[0] for p in pts]; ys = [p[1] for p in pts]
            d.rectangle([min(xs), min(ys), max(xs), max(ys)], outline=(255, 0, 0, 255))
    out.save(out_path)
    return out_path


def _tri(canvas, tex, src, dst):
    xs = [p[0] for p in dst]; ys = [p[1] for p in dst]
    x0, x1 = int(math.floor(min(xs))), int(math.ceil(max(xs)))
    y0, y1 = int(math.floor(min(ys))), int(math.ceil(max(ys)))
    x0 = max(x0, 0); y0 = max(y0, 0)
    x1 = min(x1, canvas.width); y1 = min(y1, canvas.height)
    if x1 <= x0 or y1 <= y0:
        return
    (dx0, dy0), (dx1, dy1), (dx2, dy2) = dst
    (sx0, sy0), (sx1, sy1), (sx2, sy2) = src
    det = (dx1 - dx0) * (dy2 - dy0) - (dx2 - dx0) * (dy1 - dy0)
    if abs(det) < 1e-9:
        return
    a = ((sx1 - sx0) * (dy2 - dy0) - (sx2 - sx0) * (dy1 - dy0)) / det
    b = ((sx2 - sx0) * (dx1 - dx0) - (sx1 - sx0) * (dx2 - dx0)) / det
    c = sx0 - a * dx0 - b * dy0
    d = ((sy1 - sy0) * (dy2 - dy0) - (sy2 - sy0) * (dy1 - dy0)) / det
    e = ((sy2 - sy0) * (dx1 - dx0) - (sy1 - sy0) * (dx2 - dx0)) / det
    f = sy0 - d * dx0 - e * dy0
    w, h = x1 - x0, y1 - y0
    patch = tex.transform((w, h), Image.AFFINE,
                          (a, b, c + a * x0 + b * y0, d, e, f + d * x0 + e * y0),
                          resample=Image.BILINEAR)
    mask = Image.new("L", (w, h), 0)
    ImageDraw.Draw(mask).polygon([(p[0] - x0, p[1] - y0) for p in dst], fill=255)
    mask = ImageChops.multiply(mask, patch.getchannel("A"))
    canvas.paste(patch, (x0, y0), mask)


if __name__ == "__main__":
    pose, atlas, out = sys.argv[1:4]
    rasterize(pose, atlas, out)
    print("rasterized ->", out)
