#!/usr/bin/env python3
"""Minimal software renderer for Spine 3.8 JSON setup pose.

Renders region + (weighted) mesh attachments of the default skin using an
.atlas + page PNG, so atlas repacks can be verified without the game.
Not a general runtime: no animations, constraints, or clipping.
"""
import json
import math
import os
import sys

from PIL import Image

sys.path.insert(0, os.path.dirname(os.path.abspath(__file__)))
from spine_atlas_util import parse_atlas, extract_region  # noqa: E402


def bone_world_transforms(data):
    world = {}
    for b in data["bones"]:
        rot = b.get("rotation", 0.0)
        x, y = b.get("x", 0.0), b.get("y", 0.0)
        sx, sy = b.get("scaleX", 1.0), b.get("scaleY", 1.0)
        shx, shy = b.get("shearX", 0.0), b.get("shearY", 0.0)
        la = math.cos(math.radians(rot + shx)) * sx
        lb = math.cos(math.radians(rot + 90 + shy)) * sy
        lc = math.sin(math.radians(rot + shx)) * sx
        ld = math.sin(math.radians(rot + 90 + shy)) * sy
        if b.get("parent") is None:
            world[b["name"]] = (la, lb, lc, ld, x, y)
        else:
            pa, pb, pc, pd, px, py = world[b["parent"]]
            wx, wy = pa * x + pb * y + px, pc * x + pd * y + py
            world[b["name"]] = (pa * la + pb * lc, pa * lb + pb * ld,
                                pc * la + pd * lc, pc * lb + pd * ld, wx, wy)
    return world


def apply(m, x, y):
    a, b, c, d, tx, ty = m
    return a * x + b * y + tx, c * x + d * y + ty


def attachment_world_verts(att, slot_bone, data, world):
    t = att.get("type", "region")
    if t == "region":
        w, h = att["width"], att["height"]
        sx, sy = att.get("scaleX", 1.0), att.get("scaleY", 1.0)
        rot = math.radians(att.get("rotation", 0.0))
        ax, ay = att.get("x", 0.0), att.get("y", 0.0)
        cosr, sinr = math.cos(rot), math.sin(rot)
        verts, uvs = [], []
        for lx, ly, u, v in ((-w / 2, -h / 2, 0, 1), (-w / 2, h / 2, 0, 0),
                             (w / 2, h / 2, 1, 0), (w / 2, -h / 2, 1, 1)):
            lx, ly = lx * sx, ly * sy
            px = lx * cosr - ly * sinr + ax
            py = lx * sinr + ly * cosr + ay
            verts.append(apply(world[slot_bone], px, py))
            uvs.append((u, v))
        return verts, uvs, [0, 1, 2, 2, 3, 0]
    if t in ("mesh",):
        uvflat = att["uvs"]
        uvs = [(uvflat[i], uvflat[i + 1]) for i in range(0, len(uvflat), 2)]
        vraw = att["vertices"]
        verts = []
        if len(vraw) == len(uvflat):  # unweighted, in slot bone space
            for i in range(0, len(vraw), 2):
                verts.append(apply(world[slot_bone], vraw[i], vraw[i + 1]))
        else:  # weighted
            bones = data["bones"]
            i = 0
            while i < len(vraw):
                n = int(vraw[i]); i += 1
                wx = wy = 0.0
                for _ in range(n):
                    bi, bx, by, bw = int(vraw[i]), vraw[i + 1], vraw[i + 2], vraw[i + 3]
                    i += 4
                    m = world[bones[bi]["name"]]
                    px, py = apply(m, bx, by)
                    wx += px * bw
                    wy += py * bw
                verts.append((wx, wy))
        return verts, uvs, att["triangles"]
    return None, None, None


def render(skel_json, atlas_path, out_path, degrees_override=None, scale=1.0,
           canvas=(560, 560)):
    """degrees_override: optional fn(region)->degrees to test conventions."""
    data = json.load(open(skel_json, encoding="utf-8"))
    world = bone_world_transforms(data)
    _, page, regions = parse_atlas(atlas_path)
    page_img = Image.open(os.path.join(os.path.dirname(atlas_path), page)).convert("RGBA")
    sprites = {}
    for r in regions:
        d = degrees_override(r) if degrees_override else None
        key = (r.name, r.attrs.get("index", "-1"))
        sprites[key] = extract_region(page_img, r, degrees=d)
        sprites[r.name] = sprites[key]

    slot_bone = {s["name"]: s["bone"] for s in data["slots"]}
    slot_att = {s["name"]: s.get("attachment") for s in data["slots"]}
    skins = data["skins"]
    default = skins["default"] if isinstance(skins, dict) else skins[0]["attachments"]

    W, H = canvas
    out = Image.new("RGBA", (W, H), (46, 46, 52, 255))
    cx, cy = W / 2.0, H - 40.0

    def to_canvas(p):
        return cx + p[0] * scale, cy - p[1] * scale

    for slot in data["slots"]:  # draw in slot order
        sname = slot["name"]
        aname = slot_att.get(sname)
        if not aname or sname not in default:
            continue
        att = default[sname].get(aname)
        if att is None or att.get("type", "region") not in ("region", "mesh"):
            continue
        tex_name = att.get("path", aname)
        img = sprites.get(tex_name)
        if img is None:
            continue
        verts, uvs, tris = attachment_world_verts(att, slot_bone[sname], data, world)
        if not verts:
            continue
        cverts = [to_canvas(v) for v in verts]
        tw, th = img.size
        for i in range(0, len(tris), 3):
            i0, i1, i2 = tris[i], tris[i + 1], tris[i + 2]
            dst = [cverts[i0], cverts[i1], cverts[i2]]
            src = [(uvs[k][0] * tw, uvs[k][1] * th) for k in (i0, i1, i2)]
            _draw_triangle(out, img, src, dst)
    out.save(out_path)
    return out_path


def _draw_triangle(canvas, tex, src, dst):
    """Affine-map triangle src(tex coords px) -> dst(canvas px)."""
    xs = [p[0] for p in dst]; ys = [p[1] for p in dst]
    x0, x1 = int(math.floor(min(xs))), int(math.ceil(max(xs)))
    y0, y1 = int(math.floor(min(ys))), int(math.ceil(max(ys)))
    if x1 <= x0 or y1 <= y0:
        return
    x0 = max(x0, 0); y0 = max(y0, 0)
    x1 = min(x1, canvas.width); y1 = min(y1, canvas.height)
    if x1 <= x0 or y1 <= y0:
        return
    # solve affine T: dst -> src   (PIL transform wants inverse mapping)
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
    from PIL import ImageDraw
    mask = Image.new("L", (w, h), 0)
    ImageDraw.Draw(mask).polygon([(p[0] - x0, p[1] - y0) for p in dst], fill=255)
    # respect texture alpha too
    alpha = patch.getchannel("A").point(lambda v: v)
    from PIL import ImageChops
    mask = ImageChops.multiply(mask, alpha)
    canvas.paste(patch, (x0, y0), mask)


if __name__ == "__main__":
    skel, atlas, out = sys.argv[1:4]
    render(skel, atlas, out)
    print("rendered", out)
