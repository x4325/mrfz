#!/usr/bin/env python3
"""Item-specific relic icons for equipment + curse relics (128px, transparent,
margin, soft shadow). Replaces the generic glyph versions."""
import math
import os

from PIL import Image, ImageDraw, ImageFilter

D = "ArknightsNsfwBridge/src/main/resources/arknsfwResources/images/relics"
S = 128

LEATHER = (52, 40, 50)
LEATHER_HI = (110, 90, 106)
METAL = (206, 198, 214)
METAL_DK = (128, 120, 138)
PINK = (232, 96, 138)
PINK_HI = (250, 168, 196)
GOLD = (222, 180, 96)
ROPE = (196, 56, 72)
ROPE_DK = (128, 24, 40)


def canvas():
    return Image.new("RGBA", (S, S), (0, 0, 0, 0))


def finish(img):
    a = img.split()[3].point(lambda v: 255 if v > 30 else 0)
    sh = Image.new("RGBA", (S, S), (0, 0, 0, 0))
    black = Image.new("RGBA", (S, S), (0, 0, 0, 110))
    sh.paste(black, (4, 5), a)
    sh = sh.filter(ImageFilter.GaussianBlur(3))
    return Image.alpha_composite(sh, img)


def outline_of(img):
    a = img.split()[3].point(lambda v: 255 if v > 40 else 0)
    out = Image.new("RGBA", (S, S), (0, 0, 0, 0))
    out.paste(Image.new("RGBA", (S, S), (255, 255, 255, 255)), (0, 0), a)
    return out


def heart(d, cx, cy, s, fill):
    d.polygon([(cx, cy + s), (cx - s, cy), (cx, cy - s * 0.2), (cx + s, cy)], fill=fill)
    d.ellipse([cx - s, cy - s * 0.72, cx + 2, cy + s * 0.25], fill=fill)
    d.ellipse([cx - 2, cy - s * 0.72, cx + s, cy + s * 0.25], fill=fill)


def ic_cuffs():
    img = canvas(); d = ImageDraw.Draw(img)
    for cx, cy in ((44, 46), (84, 82)):
        d.ellipse([cx - 22, cy - 18, cx + 22, cy + 18], outline=METAL, width=9)
        d.ellipse([cx - 22, cy - 18, cx + 22, cy + 18], outline=METAL_DK, width=3)
        d.rectangle([cx - 6, cy - 24, cx + 6, cy - 12], fill=METAL_DK)
    for i in range(3):
        x = 56 + i * 8; y = 60 + i * 5
        d.ellipse([x - 5, y - 5, x + 5, y + 5], outline=METAL, width=3)
    return img


def ic_leash():
    img = canvas(); d = ImageDraw.Draw(img)
    d.arc([34, 22, 94, 70], 200, 340 + 180, fill=LEATHER, width=11)
    d.arc([34, 22, 94, 70], 210, 330, fill=LEATHER_HI, width=3)
    d.ellipse([57, 62, 71, 78], outline=METAL, width=5)
    pts = [(64, 78)]
    for i in range(1, 9):
        t = i / 8
        pts.append((64 + 24 * math.sin(t * 2.6), 78 + t * 34))
    for p0, p1 in zip(pts, pts[1:]):
        d.line([p0, p1], fill=LEATHER, width=7)
    ex, ey = pts[-1]
    d.ellipse([ex - 7, ey - 3, ex + 7, ey + 11], outline=METAL, width=4)
    return img


def ic_belt():
    img = canvas(); d = ImageDraw.Draw(img)
    d.arc([24, 26, 104, 116], 160, 380, fill=METAL, width=12)
    d.arc([24, 26, 104, 116], 165, 375, fill=METAL_DK, width=3)
    d.rounded_rectangle([50, 54, 78, 86], radius=6, fill=GOLD)
    d.ellipse([59, 62, 69, 72], fill=(90, 62, 26, 255))
    d.rectangle([62, 68, 66, 80], fill=(90, 62, 26, 255))
    return img


def ic_garter():
    img = canvas(); d = ImageDraw.Draw(img)
    d.rounded_rectangle([26, 52, 102, 78], radius=13, fill=(30, 24, 32, 235))
    for x in range(30, 100, 10):
        d.ellipse([x - 4, 46, x + 4, 54], outline=PINK, width=2)
        d.ellipse([x - 4, 74, x + 4, 82], outline=PINK, width=2)
    d.polygon([(64, 64), (48, 54), (48, 74)], fill=PINK)
    d.polygon([(64, 64), (80, 54), (80, 74)], fill=PINK)
    d.ellipse([60, 60, 68, 68], fill=PINK_HI)
    return img


def ic_belltag():
    img = canvas(); d = ImageDraw.Draw(img)
    d.pieslice([36, 28, 92, 92], 180, 360, fill=GOLD)
    d.polygon([(36, 60), (92, 60), (98, 76), (30, 76)], fill=GOLD)
    d.ellipse([58, 80, 70, 92], fill=(150, 116, 48, 255))
    d.ellipse([46, 36, 58, 50], fill=(255, 240, 200, 190))
    d.rectangle([58, 20, 70, 30], fill=(150, 116, 48, 255))
    return img


def ic_clothgag():
    img = canvas(); d = ImageDraw.Draw(img)
    d.polygon([(20, 56), (108, 48), (108, 74), (20, 84)], fill=(238, 234, 228, 250))
    for i, y in enumerate((58, 66, 74)):
        d.line([(24, y + 2), (104, y - 4)], fill=(196, 188, 178, 255), width=2)
    d.arc([52, 52, 78, 82], 20, 160, fill=(170, 160, 148, 255), width=3)
    return img


def ic_ringgag():
    img = canvas(); d = ImageDraw.Draw(img)
    for sgn in (-1, 1):
        d.line([(64 + sgn * 20, 64), (64 + sgn * 52, 52)], fill=LEATHER, width=9)
    d.ellipse([40, 40, 88, 88], outline=METAL, width=11)
    d.ellipse([40, 40, 88, 88], outline=METAL_DK, width=4)
    d.arc([40, 40, 88, 88], 200, 300, fill=(244, 240, 250, 200), width=4)
    return img


def ic_blindfold():
    img = canvas(); d = ImageDraw.Draw(img)
    d.rounded_rectangle([18, 50, 110, 82], radius=15, fill=(24, 16, 28, 250))
    for x in range(26, 104, 12):
        d.ellipse([x - 4, 60, x + 4, 72], outline=(212, 120, 160, 210), width=2)
    for x in range(22, 108, 9):
        d.ellipse([x - 3, 45, x + 3, 51], outline=(232, 150, 185, 190), width=1)
        d.ellipse([x - 3, 81, x + 3, 87], outline=(232, 150, 185, 190), width=1)
    d.line([(110, 62), (122, 54)], fill=(24, 16, 28, 250), width=6)
    d.line([(110, 70), (122, 78)], fill=(24, 16, 28, 250), width=6)
    return img


def ic_vibe():
    img = canvas(); d = ImageDraw.Draw(img)
    d.ellipse([38, 30, 74, 78], fill=PINK)
    d.ellipse([44, 36, 58, 52], fill=PINK_HI)
    pts = [(56, 78)]
    for i in range(1, 8):
        t = i / 7
        pts.append((56 + 26 * math.sin(t * 3.0), 78 + t * 26))
    for p0, p1 in zip(pts, pts[1:]):
        d.line([p0, p1], fill=(230, 228, 236, 240), width=3)
    ex, ey = pts[-1]
    d.rounded_rectangle([ex - 8, ey - 4, ex + 10, ey + 20], radius=4,
                        fill=(242, 240, 248, 250), outline=(160, 156, 170, 255), width=2)
    d.ellipse([ex - 2, ey + 1, ex + 4, ey + 7], fill=PINK)
    return img


def ic_bodycrest():
    img = canvas(); d = ImageDraw.Draw(img)
    glow = canvas(); dg = ImageDraw.Draw(glow)
    heart(dg, 64, 62, 34, (255, 90, 140, 120))
    heart(d, 64, 62, 26, PINK)
    d.arc([46, 44, 82, 80], 0, 360, fill=PINK_HI, width=3)
    for ang in range(0, 360, 45):
        x = 64 + 40 * math.cos(math.radians(ang))
        y = 62 + 40 * math.sin(math.radians(ang))
        d.line([(64 + 32 * math.cos(math.radians(ang)), 62 + 32 * math.sin(math.radians(ang))), (x, y)],
               fill=PINK, width=3)
    return Image.alpha_composite(glow.filter(ImageFilter.GaussianBlur(4)), img)


def ic_rope():
    img = canvas(); d = ImageDraw.Draw(img)
    pts = [(64, 22), (34, 64), (64, 106), (94, 64), (64, 22)]
    d.line(pts, fill=ROPE_DK, width=11, joint="curve")
    d.line(pts, fill=ROPE, width=7, joint="curve")
    for (x0, y0), (x1, y1) in zip(pts, pts[1:]):
        n = 6
        for i in range(n):
            t = (i + 0.5) / n
            cx, cy = x0 + (x1 - x0) * t, y0 + (y1 - y0) * t
            nx, ny = -(y1 - y0), (x1 - x0)
            ln = math.hypot(nx, ny); nx, ny = nx / ln * 4, ny / ln * 4
            d.line([(cx - nx, cy - ny), (cx + nx, cy + ny)],
                   fill=(236, 120, 132, 255) if i % 2 == 0 else ROPE_DK, width=2)
    d.ellipse([56, 56, 72, 72], fill=ROPE)
    return img


EQUIP = {
    "equipment_cuffs": ic_cuffs, "equipment_leash": ic_leash, "equipment_belt": ic_belt,
    "equipment_garter": ic_garter, "equipment_belltag": ic_belltag, "equipment_clothgag": ic_clothgag,
    "equipment_ringgag": ic_ringgag, "equipment_laceblindfold": ic_blindfold,
    "equipment_vibe": ic_vibe, "equipment_bodycrest": ic_bodycrest, "equipment_rope": ic_rope,
}

# ---- 诅咒：按名字给专属母题（统一暗色调+左上小锁标记诅咒） ----
CURSE_TINT = {
    "ash": (150, 78, 58), "volcano": (150, 78, 58),
    "clone": (86, 142, 112), "overflow": (86, 142, 112), "rhine": (86, 142, 112),
    "breeding": (150, 96, 120), "calamity": (128, 100, 140),
}


def curse_motif(name, d):
    if "furnace" in name:
        d.rounded_rectangle([36, 44, 92, 96], radius=8, outline=(220, 200, 190, 255), width=6)
        d.polygon([(52, 84), (64, 56), (76, 84)], fill=(255, 150, 70, 255))
        d.polygon([(58, 84), (64, 68), (70, 84)], fill=(255, 220, 130, 255))
    elif "womb" in name or "breeding" in name:
        d.ellipse([40, 40, 88, 92], outline=(230, 200, 214, 255), width=6)
        heart(d, 64, 64, 14, (240, 120, 150, 255))
        d.ellipse([60, 84, 68, 92], fill=(240, 120, 150, 255))
    elif "thermometer" in name or "calamity" in name:
        d.rounded_rectangle([56, 26, 72, 86], radius=8, outline=(230, 224, 236, 255), width=5)
        d.ellipse([50, 80, 78, 106], fill=(236, 90, 90, 255))
        d.rectangle([60, 44, 68, 86], fill=(236, 90, 90, 255))
    elif "loop" in name:
        for r in (34, 22, 10):
            d.arc([64 - r, 64 - r, 64 + r, 64 + r], 20 + r * 3, 320 + r * 3,
                  fill=(210, 230, 220, 255), width=5)
    elif "spore" in name or "clone" in name:
        for cx, cy, r in ((48, 52, 14), (78, 44, 10), (66, 78, 12), (88, 72, 8)):
            d.ellipse([cx - r, cy - r, cx + r, cy + r], outline=(200, 235, 215, 255), width=4)
            d.ellipse([cx - 3, cy - 3, cx + 3, cy + 3], fill=(200, 235, 215, 255))
    elif "overflow" in name or "leak" in name or "filth" in name:
        d.ellipse([44, 30, 84, 74], outline=(210, 236, 225, 255), width=6)
        for i, (dx, dy) in enumerate(((-10, 0), (0, 6), (10, 0))):
            d.polygon([(64 + dx, 74 + dy), (58 + dx, 92 + dy), (70 + dx, 92 + dy)],
                      fill=(160, 220, 195, 255))
            d.ellipse([58 + dx, 86 + dy, 70 + dx, 100 + dy], fill=(160, 220, 195, 255))
    elif "brand" in name or "volcano" in name:
        d.polygon([(64, 24), (44, 62), (56, 62), (40, 100), (78, 56), (64, 56), (84, 24)],
                  fill=(255, 150, 70, 255))
    elif "collar" in name or "soot" in name:
        d.arc([38, 34, 90, 86], 300, 240, fill=(80, 62, 74, 255), width=10)
        d.ellipse([58, 80, 70, 94], outline=(206, 198, 214, 255), width=4)
    else:
        d.ellipse([42, 42, 86, 86], outline=(220, 210, 224, 255), width=6)
        d.line([(50, 50), (78, 78)], fill=(220, 210, 224, 255), width=6)


def curse_icon(fname):
    name = fname.lower()
    img = canvas(); d = ImageDraw.Draw(img)
    tint = (110, 90, 120)
    for k, v in CURSE_TINT.items():
        if k in name:
            tint = v
            break
    d.rounded_rectangle([22, 22, 106, 106], radius=18, fill=(*[int(c * 0.35) for c in tint], 210),
                        outline=(*tint, 255), width=3)
    curse_motif(name, d)
    # 左上小锁 = 诅咒标记
    d.rounded_rectangle([26, 30, 44, 44], radius=4, fill=(40, 34, 44, 255), outline=(180, 170, 188, 255), width=2)
    d.arc([29, 22, 41, 36], 180, 360, fill=(180, 170, 188, 255), width=3)
    return img


def main():
    import glob
    for name, fn in EQUIP.items():
        img = finish(fn())
        img.save(f"{D}/{name}.png")
        outline_of(img).save(f"{D}/outline/{name}.png")
    n = 0
    for f in glob.glob(f"{D}/curse_*.png"):
        base = os.path.basename(f)[:-4]
        img = finish(curse_icon(base))
        img.save(f)
        outline_of(img).save(f"{D}/outline/{base}.png")
        n += 1
    print(f"{len(EQUIP)} equipment + {n} curse icons redrawn")


if __name__ == "__main__":
    main()
