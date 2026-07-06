#!/usr/bin/env python3
"""Generate restraint/heart-eye overlay layers for battle portraits (450x630).

Per-character anchors were hand-calibrated against the official art crops.
Outputs: arknsfwResources/images/portraits/overlays/{char}_{item}.png
"""
import math
import os

from PIL import Image, ImageDraw, ImageFilter

W, H = 450, 630
OUT = "ArknightsNsfwBridge/src/main/resources/arknsfwResources/images/portraits/overlays"

# eyeL, eyeR, mouth, collar(cx, cy, w)
ANCHORS = {
    "highmore": {"eyeL": (316, 152), "eyeR": (356, 150), "mouth": (330, 178), "collar": (333, 207, 58)},
    "scene":    {"eyeL": (112, 120), "eyeR": (205, 110), "mouth": (150, 148), "collar": (168, 172, 50)},
    "archetto": {"eyeL": (62, 142),  "eyeR": (108, 140), "mouth": (95, 168),  "collar": (100, 198, 46)},
    "haruka":   {"eyeL": (107, 105), "eyeR": (160, 110), "mouth": (128, 130), "collar": (140, 160, 62)},
    "nymph":    {"eyeL": (163, 172), "eyeR": (203, 170), "mouth": (190, 200), "collar": (195, 218, 50)},
    "eyja":     {"eyeL": (28, 198),  "eyeR": (95, 197),  "mouth": (60, 228),  "collar": (65, 252, 52)},
    "muel":     {"eyeL": (150, 140), "eyeR": (218, 128), "mouth": (185, 168), "collar": (185, 208, 66)},
}

ROPE = (178, 34, 52, 255)
ROPE_DARK = (120, 16, 32, 255)
ROPE_HI = (228, 92, 108, 255)
LEATHER = (38, 30, 36, 255)
LEATHER_HI = (92, 76, 88, 255)
METAL = (208, 200, 214, 255)
METAL_DK = (120, 114, 128, 255)


def canvas():
    return Image.new("RGBA", (W, H), (0, 0, 0, 0))


def soft(img, r=1.2):
    return img.filter(ImageFilter.GaussianBlur(r))


def rope_line(d, p0, p1, width=5):
    """Twisted rope: base line + alternating twist dashes + highlight."""
    x0, y0 = p0
    x1, y1 = p1
    d.line([p0, p1], fill=ROPE_DARK, width=width + 2)
    d.line([p0, p1], fill=ROPE, width=width)
    length = math.hypot(x1 - x0, y1 - y0)
    if length < 1:
        return
    ux, uy = (x1 - x0) / length, (y1 - y0) / length
    nx, ny = -uy, ux
    step = 7
    n = int(length / step)
    for i in range(n):
        t0 = i * step
        cx = x0 + ux * (t0 + 1.5)
        cy = y0 + uy * (t0 + 1.5)
        off = width * 0.28 if i % 2 == 0 else -width * 0.28
        d.line([(cx + nx * off - ux * 2, cy + ny * off - uy * 2),
                (cx + nx * off + ux * 2, cy + ny * off + uy * 2)],
               fill=ROPE_HI if i % 2 == 0 else ROPE_DARK, width=2)


def sag_points(x0, x1, y, sag, steps=14):
    pts = []
    for i in range(steps + 1):
        t = i / steps
        px = x0 + (x1 - x0) * t
        py = y + sag * math.sin(math.pi * t)
        pts.append((px, py))
    return pts


def rope_curve(d, x0, x1, y, sag, width=5):
    pts = sag_points(x0, x1, y, sag)
    for a, b in zip(pts, pts[1:]):
        rope_line(d, a, b, width)


def gen_collar(a):
    img = canvas()
    d = ImageDraw.Draw(img)
    cx, cy, w = a["collar"]
    hw = w // 2
    band_h = 13
    # 皮质颈环（轻微下弧）
    pts_top = sag_points(cx - hw, cx + hw, cy - band_h // 2, 3)
    pts_bot = sag_points(cx - hw, cx + hw, cy + band_h // 2, 4)
    d.polygon(pts_top + pts_bot[::-1], fill=LEATHER)
    for p0, p1 in zip(pts_top, pts_top[1:]):
        d.line([p0, p1], fill=LEATHER_HI, width=2)
    # 铆钉
    for i in range(1, 5):
        t = i / 5
        px = cx - hw + w * t
        py = cy + 3 * math.sin(math.pi * t)
        d.ellipse([px - 2, py - 2, px + 2, py + 2], fill=METAL)
    # 中央 D 环 + 心形锁
    ry = cy + band_h // 2 + 2
    d.ellipse([cx - 7, ry - 4, cx + 7, ry + 8], outline=METAL, width=3)
    d.ellipse([cx - 7, ry - 4, cx + 7, ry + 8], outline=METAL_DK, width=1)
    hy = ry + 13
    hs = 7
    d.polygon([(cx, hy + hs), (cx - hs, hy), (cx - hs // 2, hy - hs // 2),
               (cx, hy), (cx + hs // 2, hy - hs // 2), (cx + hs, hy)],
              fill=(212, 60, 92, 255))
    d.ellipse([cx - hs, hy - hs * 0.75, cx, hy + hs * 0.25], fill=(212, 60, 92, 255))
    d.ellipse([cx, hy - hs * 0.75, cx + hs, hy + hs * 0.25], fill=(212, 60, 92, 255))
    d.ellipse([cx - 2, hy - 2, cx + 1, hy + 1], fill=(255, 190, 205, 255))
    return img


def gen_rope(a):
    img = canvas()
    d = ImageDraw.Draw(img)
    cx, cy, w = a["collar"]
    span = w * 2.5
    x0, x1 = cx - span / 2, cx + span / 2
    y1 = cy + 52
    y2 = cy + 96
    rope_curve(d, x0, x1, y1, 7)
    rope_curve(d, x0, x1, y2, 9)
    # 中央菱形结
    mid = cx
    my = (y1 + y2) / 2 + 4
    dw, dh = 20, 26
    for pts in ([(mid, my - dh / 2), (mid - dw / 2, my), (mid, my + dh / 2), (mid + dw / 2, my)],):
        d.line(pts + [pts[0]], fill=ROPE_DARK, width=7)
        d.line(pts + [pts[0]], fill=ROPE, width=5)
    # 竖绳连接项圈位置与两道横绳
    rope_line(d, (mid, cy + 12), (mid, my - dh / 2), 4)
    rope_line(d, (mid, my + dh / 2), (mid, y2 + 26), 4)
    # 两侧斜绳
    rope_line(d, (x0 + 6, y1 + 2), (mid - dw / 2, my), 4)
    rope_line(d, (x1 - 6, y1 + 2), (mid + dw / 2, my), 4)
    # 勒痕阴影
    sh = canvas()
    ds = ImageDraw.Draw(sh)
    for yy, sag in ((y1, 7), (y2, 9)):
        pts = sag_points(x0, x1, yy + 4, sag)
        for p0, p1 in zip(pts, pts[1:]):
            ds.line([p0, p1], fill=(90, 20, 30, 90), width=8)
    img = Image.alpha_composite(soft(sh, 2.5), img)
    return img


def gen_gag(a):
    img = canvas()
    d = ImageDraw.Draw(img)
    mx, my = a["mouth"]
    exL, eyL = a["eyeL"]
    exR, eyR = a["eyeR"]
    face_hw = max(26, int(abs(exR - exL) * 0.85))
    # 皮带（口部向两侧脸颊延伸）
    for sgn in (-1, 1):
        bx = mx + sgn * face_hw
        by = my - 6 * 1
        d.line([(mx, my), (bx, by - 4)], fill=LEATHER, width=8)
        d.line([(mx, my), (bx, by - 4)], fill=LEATHER_HI, width=2)
    # 球体
    r = 11
    d.ellipse([mx - r, my - r, mx + r, my + r], fill=(196, 40, 60, 255), outline=(120, 16, 32, 255), width=2)
    d.ellipse([mx - r * 0.5, my - r * 0.65, mx + r * 0.1, my - r * 0.1], fill=(255, 150, 165, 210))
    # 球面绑带孔
    d.ellipse([mx - 3, my - 3, mx + 3, my + 3], fill=(120, 16, 32, 255))
    return img


def gen_blindfold(a):
    img = canvas()
    d = ImageDraw.Draw(img)
    exL, eyL = a["eyeL"]
    exR, eyR = a["eyeR"]
    cx, cy = (exL + exR) / 2, (eyL + eyR) / 2
    ang = math.atan2(eyR - eyL, exR - exL)
    hw = max(30, abs(exR - exL) * 0.95)
    hh = 15
    # 眼带（旋转矩形）
    band = Image.new("RGBA", (int(hw * 2 + 24), hh * 2 + 12), (0, 0, 0, 0))
    bd = ImageDraw.Draw(band)
    bw, bh = band.size
    bd.rounded_rectangle([2, bh // 2 - hh, bw - 2, bh // 2 + hh], radius=10, fill=(20, 16, 24, 255))
    # 布褶
    for i in range(1, 4):
        yy = bh // 2 - hh + i * (2 * hh) // 4
        bd.line([(8, yy), (bw - 8, yy - 3)], fill=(56, 48, 66, 255), width=2)
    bd.rounded_rectangle([2, bh // 2 - hh, bw - 2, bh // 2 + hh], radius=10, outline=(70, 60, 82, 255), width=2)
    band = band.rotate(-math.degrees(ang), expand=True, resample=Image.BICUBIC)
    img.alpha_composite(band, (int(cx - band.width / 2), int(cy - band.height / 2)))
    # 侧结与垂带
    kx = int(cx + math.cos(ang) * (hw + 6))
    ky = int(cy + math.sin(ang) * (hw + 6))
    d.ellipse([kx - 5, ky - 5, kx + 5, ky + 5], fill=(20, 16, 24, 255), outline=(70, 60, 82, 255))
    d.line([(kx, ky), (kx + 9, ky + 22)], fill=(20, 16, 24, 255), width=5)
    d.line([(kx, ky), (kx - 4, ky + 26)], fill=(20, 16, 24, 255), width=4)
    return img


def draw_heart(d, cx, cy, s, fill, outline=None):
    d.polygon([(cx, cy + s), (cx - s, cy), (cx - s * 0.55, cy - s * 0.45),
               (cx, cy - s * 0.05), (cx + s * 0.55, cy - s * 0.45), (cx + s, cy)], fill=fill)
    d.ellipse([cx - s, cy - s * 0.75, cx + 1, cy + s * 0.3], fill=fill)
    d.ellipse([cx - 1, cy - s * 0.75, cx + s, cy + s * 0.3], fill=fill)


def gen_hearteyes(a):
    img = canvas()
    glow = canvas()
    dg = ImageDraw.Draw(glow)
    d = ImageDraw.Draw(img)
    for key in ("eyeL", "eyeR"):
        ex, ey = a[key]
        draw_heart(dg, ex, ey, 13, (255, 96, 150, 160))
        draw_heart(d, ex, ey, 9, (255, 70, 130, 235))
        draw_heart(d, ex - 2, ey - 2, 3, (255, 210, 228, 255))
    return Image.alpha_composite(soft(glow, 3.5), img)


def main():
    os.makedirs(OUT, exist_ok=True)
    gens = {"collar": gen_collar, "rope": gen_rope, "gag": gen_gag,
            "blindfold": gen_blindfold, "hearteyes": gen_hearteyes}
    for ch, a in ANCHORS.items():
        for item, fn in gens.items():
            fn(a).save(f"{OUT}/{ch}_{item}.png")
    print(f"{len(ANCHORS) * len(gens)} overlays written to {OUT}")


if __name__ == "__main__":
    main()
