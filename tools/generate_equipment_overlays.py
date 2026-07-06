#!/usr/bin/env python3
"""Restraint EQUIPMENT overlays (relic-bound), extending the base restraint set.

Items: cuffs / leash / belt / garter / belltag / clothgag / ringgag /
       laceblindfold / vibe / bodycrest
Anchors hand-calibrated per character on the 450x630 portrait canvas.
"""
import math
import os

from PIL import Image, ImageDraw, ImageFilter

W, H = 450, 630
OUT = "ArknightsNsfwBridge/src/main/resources/arknsfwResources/images/portraits/overlays"

from generate_restraint_overlays import ANCHORS, LEATHER, LEATHER_HI, METAL, METAL_DK, sag_points  # noqa: E402

EXTRA = {
    "highmore": {"wrists": [(302, 208)], "hip": (250, 395), "thigh": (235, 505)},
    "scene":    {"wrists": [(172, 232), (214, 228)], "hip": (190, 400), "thigh": (200, 505)},
    "archetto": {"wrists": [], "hip": (185, 400), "thigh": None},
    "haruka":   {"wrists": [(78, 345)], "hip": (165, 375), "thigh": (152, 478)},
    "nymph":    {"wrists": [(293, 455)], "hip": (235, 420), "thigh": None},
    "eyja":     {"wrists": [(172, 555), (205, 558)], "hip": (115, 440), "thigh": None},
    "muel":     {"wrists": [(126, 148)], "hip": (185, 430), "thigh": None},
}

PINK = (232, 84, 128, 255)
GOLD = (218, 178, 92, 255)


def canvas():
    return Image.new("RGBA", (W, H), (0, 0, 0, 0))


def soft(img, r=1.5):
    return img.filter(ImageFilter.GaussianBlur(r))


def chain(d, p0, p1, r=4):
    """Small-link chain between two points."""
    x0, y0 = p0
    x1, y1 = p1
    length = math.hypot(x1 - x0, y1 - y0)
    n = max(2, int(length / (r * 1.7)))
    for i in range(n + 1):
        t = i / n
        cx = x0 + (x1 - x0) * t
        cy = y0 + (y1 - y0) * t + 2 * math.sin(t * math.pi)
        d.ellipse([cx - r, cy - r, cx + r, cy + r], outline=METAL, width=2)
        d.ellipse([cx - r, cy - r, cx + r, cy + r], outline=METAL_DK, width=1)


def gen_cuffs(a, e):
    if not e["wrists"]:
        return None
    img = canvas()
    d = ImageDraw.Draw(img)
    for wx, wy in e["wrists"]:
        d.rounded_rectangle([wx - 13, wy - 7, wx + 13, wy + 7], radius=6,
                            fill=METAL_DK, outline=METAL, width=2)
        d.line([(wx - 13, wy - 2), (wx + 13, wy - 2)], fill=(235, 232, 240, 200), width=2)
        chain(d, (wx, wy + 7), (wx + 6, wy + 30), 3)
    if len(e["wrists"]) == 2:
        (x0, y0), (x1, y1) = e["wrists"]
        chain(d, (x0, y0 + 6), (x1, y1 + 6), 3)
    return img


def gen_leash(a, e):
    img = canvas()
    d = ImageDraw.Draw(img)
    cx, cy, w = a["collar"]
    ry = cy + 12
    pts = []
    for i in range(25):
        t = i / 24
        px = cx + 26 * math.sin(t * 2.6) * t
        py = ry + t * 130
        pts.append((px, py))
    for p0, p1 in zip(pts, pts[1:]):
        d.line([p0, p1], fill=LEATHER, width=6)
    for p0, p1 in zip(pts[::3], pts[1::3]):
        d.line([p0, p1], fill=LEATHER_HI, width=2)
    ex, ey = pts[-1]
    d.ellipse([ex - 8, ey - 4, ex + 8, ey + 12], outline=METAL, width=4)
    d.ellipse([cx - 6, ry - 3, cx + 6, ry + 9], outline=METAL, width=3)
    return img


def gen_belt(a, e):
    img = canvas()
    d = ImageDraw.Draw(img)
    hx, hy = e["hip"]
    hw = int(a["collar"][2] * 1.35)
    pts_t = sag_points(hx - hw, hx + hw, hy - 8, 5)
    pts_b = sag_points(hx - hw, hx + hw, hy + 8, 7)
    d.polygon(pts_t + pts_b[::-1], fill=(148, 142, 158, 235))
    for p0, p1 in zip(pts_t, pts_t[1:]):
        d.line([p0, p1], fill=(226, 222, 236, 255), width=2)
    for i in range(1, 6):
        t = i / 6
        px = hx - hw + 2 * hw * t
        py = hy + 6 * math.sin(math.pi * t) - 1
        d.ellipse([px - 2, py - 2, px + 2, py + 2], fill=METAL_DK)
    s = 9
    hy2 = hy + 14
    d.polygon([(hx, hy2 + s), (hx - s, hy2), (hx - s // 2, hy2 - s // 2),
               (hx, hy2), (hx + s // 2, hy2 - s // 2), (hx + s, hy2)], fill=GOLD)
    d.ellipse([hx - s, hy2 - s * 0.75, hx + 1, hy2 + s * 0.25], fill=GOLD)
    d.ellipse([hx - 1, hy2 - s * 0.75, hx + s, hy2 + s * 0.25], fill=GOLD)
    d.rectangle([hx - 2, hy2 - 1, hx + 2, hy2 + 4], fill=(120, 90, 40, 255))
    return img


def gen_garter(a, e):
    if not e["thigh"]:
        return None
    img = canvas()
    d = ImageDraw.Draw(img)
    tx, ty = e["thigh"]
    tw = 26
    pts_t = sag_points(tx - tw, tx + tw, ty - 5, 3)
    pts_b = sag_points(tx - tw, tx + tw, ty + 5, 5)
    d.polygon(pts_t + pts_b[::-1], fill=(30, 24, 32, 220))
    # 蕾丝波浪缘
    for pts, dy in ((pts_t, -3), (pts_b, 3)):
        for i, (px, py) in enumerate(pts[:-1]):
            d.ellipse([px - 2, py + dy - 2, px + 2, py + dy + 2], outline=PINK, width=1)
    # 中央小蝴蝶结
    d.polygon([(tx, ty), (tx - 8, ty - 5), (tx - 8, ty + 5)], fill=PINK)
    d.polygon([(tx, ty), (tx + 8, ty - 5), (tx + 8, ty + 5)], fill=PINK)
    d.ellipse([tx - 2, ty - 2, tx + 2, ty + 2], fill=(255, 200, 218, 255))
    return img


def gen_belltag(a, e):
    img = canvas()
    d = ImageDraw.Draw(img)
    cx, cy, w = a["collar"]
    bx = cx - w // 4
    by = cy + 14
    d.ellipse([bx - 7, by - 7, bx + 7, by + 7], fill=GOLD, outline=(150, 116, 48, 255), width=2)
    d.ellipse([bx - 2, by - 4, bx + 1, by - 1], fill=(255, 240, 200, 220))
    d.line([(bx, by + 2), (bx, by + 6)], fill=(120, 90, 40, 255), width=2)
    tx = cx + w // 4
    d.rounded_rectangle([tx - 9, by - 5, tx + 9, by + 8], radius=3,
                        fill=(232, 226, 214, 245), outline=(150, 140, 120, 255), width=1)
    for i, yy in enumerate((by - 1, by + 3)):
        d.line([(tx - 6, yy), (tx + 6, yy)], fill=(120, 60, 80, 200), width=1)
    return img


def gen_clothgag(a, e):
    img = canvas()
    d = ImageDraw.Draw(img)
    mx, my = a["mouth"]
    exL, _ = a["eyeL"]
    exR, _ = a["eyeR"]
    hw = max(24, int(abs(exR - exL) * 0.8))
    pts_t = sag_points(mx - hw, mx + hw, my - 7, -3)
    pts_b = sag_points(mx - hw, mx + hw, my + 7, 4)
    d.polygon(pts_t + pts_b[::-1], fill=(238, 234, 228, 245))
    for i in range(3):
        yy = my - 4 + i * 4
        d.line([(mx - hw + 6, yy), (mx + hw - 6, yy + (i - 1) * 2)], fill=(196, 188, 178, 255), width=1)
    # 中央咬痕凹陷
    d.arc([mx - 8, my - 6, mx + 8, my + 8], 20, 160, fill=(180, 170, 158, 255), width=2)
    return img


def gen_ringgag(a, e):
    img = canvas()
    d = ImageDraw.Draw(img)
    mx, my = a["mouth"]
    exL, _ = a["eyeL"]
    exR, _ = a["eyeR"]
    hw = max(24, int(abs(exR - exL) * 0.8))
    for sgn in (-1, 1):
        d.line([(mx + sgn * 9, my), (mx + sgn * hw, my - 7)], fill=LEATHER, width=6)
        d.line([(mx + sgn * 9, my), (mx + sgn * hw, my - 7)], fill=LEATHER_HI, width=1)
    d.ellipse([mx - 9, my - 9, mx + 9, my + 9], outline=METAL, width=5)
    d.ellipse([mx - 9, my - 9, mx + 9, my + 9], outline=METAL_DK, width=2)
    d.ellipse([mx - 9, my - 9, mx + 3, my + 3], outline=(240, 238, 246, 160), width=2)
    return img


def gen_laceblindfold(a, e):
    img = canvas()
    d = ImageDraw.Draw(img)
    exL, eyL = a["eyeL"]
    exR, eyR = a["eyeR"]
    cx, cy = (exL + exR) / 2, (eyL + eyR) / 2
    ang = math.atan2(eyR - eyL, exR - exL)
    hw = max(30, abs(exR - exL) * 0.95)
    hh = 14
    band = Image.new("RGBA", (int(hw * 2 + 28), hh * 2 + 20), (0, 0, 0, 0))
    bd = ImageDraw.Draw(band)
    bw, bh = band.size
    bd.rounded_rectangle([2, bh // 2 - hh, bw - 2, bh // 2 + hh], radius=9, fill=(24, 14, 26, 210))
    # 蕾丝花纹
    for i in range(6, bw - 6, 9):
        bd.ellipse([i - 3, bh // 2 - 5, i + 3, bh // 2 + 1], outline=(212, 120, 160, 190), width=1)
    # 上下波浪蕾丝缘
    for yy, dy in ((bh // 2 - hh, -3), (bh // 2 + hh, 3)):
        for i in range(4, bw - 4, 7):
            bd.ellipse([i - 3, yy + dy - 3, i + 3, yy + dy + 3], outline=(232, 150, 185, 200), width=1)
    band = band.rotate(-math.degrees(ang), expand=True, resample=Image.BICUBIC)
    img.alpha_composite(band, (int(cx - band.width / 2), int(cy - band.height / 2)))
    return img


def gen_vibe(a, e):
    img = canvas()
    d = ImageDraw.Draw(img)
    hx, hy = e["hip"]
    hw = int(a["collar"][2] * 1.1)
    rx = hx + hw
    # 细线：从裙内(hip中央下方)绕到腰侧遥控器
    pts = []
    for i in range(20):
        t = i / 19
        px = hx + (rx - hx) * t
        py = hy + 18 - 26 * math.sin(t * math.pi * 0.9) + 6 * t
        pts.append((px, py))
    for p0, p1 in zip(pts, pts[1:]):
        d.line([p0, p1], fill=(228, 226, 234, 235), width=2)
    d.rounded_rectangle([rx - 6, hy - 10, rx + 8, hy + 12], radius=4,
                        fill=(240, 238, 246, 250), outline=(160, 156, 170, 255), width=2)
    d.ellipse([rx - 1, hy - 5, rx + 4, hy], fill=PINK)
    d.rectangle([rx - 1, hy + 3, rx + 4, hy + 7], fill=(180, 176, 190, 255))
    return img


def _mini_heart(d, cx, cy, s, fill):
    d.polygon([(cx, cy + s), (cx - s, cy), (cx, cy - s * 0.2), (cx + s, cy)], fill=fill)
    d.ellipse([cx - s, cy - s * 0.7, cx + 1, cy + s * 0.2], fill=fill)
    d.ellipse([cx - 1, cy - s * 0.7, cx + s, cy + s * 0.2], fill=fill)


def gen_bodycrest(a, e):
    img = canvas()
    glow = canvas()
    dg = ImageDraw.Draw(glow)
    d = ImageDraw.Draw(img)
    spots = []
    cx, cy, w = a["collar"]
    spots.append((cx - w // 3, cy + 26, 7))  # 锁骨
    if e["thigh"]:
        tx, ty = e["thigh"]
        spots.append((tx + 4, ty - 22, 9))   # 大腿
    hx, hy = e["hip"]
    spots.append((hx + int(w * 0.9), hy - 6, 7))  # 侧腰
    for sx, sy, s in spots:
        _mini_heart(dg, sx, sy, s + 4, (255, 90, 140, 130))
        _mini_heart(d, sx, sy, s, (238, 62, 118, 215))
        d.ellipse([sx - 1, sy - 1, sx + 1, sy + 1], fill=(255, 200, 220, 240))
    return Image.alpha_composite(soft(glow, 3), img)


GENS = {"cuffs": gen_cuffs, "leash": gen_leash, "belt": gen_belt, "garter": gen_garter,
        "belltag": gen_belltag, "clothgag": gen_clothgag, "ringgag": gen_ringgag,
        "laceblindfold": gen_laceblindfold, "vibe": gen_vibe, "bodycrest": gen_bodycrest}


def main():
    os.makedirs(OUT, exist_ok=True)
    n = 0
    for ch, a in ANCHORS.items():
        e = EXTRA[ch]
        for item, fn in GENS.items():
            img = fn(a, e)
            if img is not None:
                img.save(f"{OUT}/{ch}_{item}.png")
                n += 1
    print(f"{n} equipment overlays written")


if __name__ == "__main__":
    main()
