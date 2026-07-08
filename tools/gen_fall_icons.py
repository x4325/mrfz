#!/usr/bin/env python3
"""堕落模式新增遗物图标（128px + 白色轮廓）、power 图标（32px）、mod 徽章。"""
import math
from PIL import Image, ImageDraw, ImageFilter

REL = "ArknightsNsfwBridge/src/main/resources/arknsfwResources/images/relics"
POW = "ArknightsNsfwBridge/src/main/resources/arknsfwResources/images/powers"
IMG = "ArknightsNsfwBridge/src/main/resources/arknsfwResources/images"
S = 128

PINK = (232, 96, 138)
PINK_HI = (250, 168, 196)
GOLD = (222, 180, 96)
METAL = (206, 198, 214)
METAL_DK = (128, 120, 138)
LEATHER = (52, 40, 50)
PURPLE = (150, 90, 200)
CLOTH = (120, 60, 90)


def canvas(s=S):
    return Image.new("RGBA", (s, s), (0, 0, 0, 0))


def finish(img):
    a = img.split()[3].point(lambda v: 255 if v > 30 else 0)
    sh = Image.new("RGBA", (S, S), (0, 0, 0, 0))
    sh.paste(Image.new("RGBA", (S, S), (0, 0, 0, 110)), (4, 5), a)
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


def save(name, img):
    img = finish(img)
    img.save(f"{REL}/{name}")
    outline_of(img).save(f"{REL}/outline/{name}")


# ---- 祝福 ----
def ic_lustsurge_charm():
    img = canvas(); d = ImageDraw.Draw(img)
    heart(d, 64, 62, 34, PINK)
    for ang in range(0, 360, 45):
        x = 64 + 52 * math.cos(math.radians(ang)); y = 62 + 52 * math.sin(math.radians(ang))
        d.line([(64 + 40 * math.cos(math.radians(ang)), 62 + 40 * math.sin(math.radians(ang))), (x, y)], fill=GOLD, width=5)
    heart(d, 64, 60, 16, PINK_HI)
    return img


def ic_heatadapt_badge():
    img = canvas(); d = ImageDraw.Draw(img)
    d.regular_polygon((64, 66, 44), 6, fill=(90, 40, 60), outline=GOLD)
    d.regular_polygon((64, 66, 36), 6, outline=PINK_HI)
    for i in range(3):
        x = 46 + i * 18
        d.arc([x - 8, 40, x + 8, 92], 200, 340, fill=PINK_HI, width=5)
    return img


def ic_sagetime_watch():
    img = canvas(); d = ImageDraw.Draw(img)
    d.ellipse([24, 24, 104, 104], fill=(240, 236, 228), outline=GOLD, width=7)
    d.rectangle([56, 8, 72, 26], fill=GOLD)
    d.line([(64, 64), (64, 38)], fill=(60, 60, 80), width=5)
    d.line([(64, 64), (84, 72)], fill=(60, 60, 80), width=5)
    d.ellipse([60, 60, 68, 68], fill=PINK)
    return img


def ic_crest_pendant():
    img = canvas(); d = ImageDraw.Draw(img)
    d.line([(64, 8), (44, 34)], fill=GOLD, width=5)
    d.line([(64, 8), (84, 34)], fill=GOLD, width=5)
    d.polygon([(64, 30), (96, 60), (64, 108), (32, 60)], fill=PURPLE, outline=PINK_HI)
    heart(d, 64, 62, 16, PINK_HI)
    d.line([(64, 78), (64, 94)], fill=PINK_HI, width=4)
    return img


def ic_mother_brooch():
    img = canvas(); d = ImageDraw.Draw(img)
    d.ellipse([28, 28, 100, 100], fill=(250, 220, 180), outline=GOLD, width=6)
    d.ellipse([46, 44, 82, 86], fill=PINK_HI)
    d.ellipse([56, 56, 72, 74], fill=PINK)
    for ang in range(0, 360, 30):
        x = 64 + 44 * math.cos(math.radians(ang)); y = 64 + 44 * math.sin(math.radians(ang))
        d.ellipse([x - 3, y - 3, x + 3, y + 3], fill=GOLD)
    return img


# ---- 欲望装备 ----
def ic_exposure_cloak():
    img = canvas(); d = ImageDraw.Draw(img)
    d.polygon([(64, 14), (24, 44), (32, 108), (96, 108), (104, 44)], fill=CLOTH, outline=(80, 36, 58))
    d.polygon([(64, 14), (46, 108), (82, 108)], fill=(28, 20, 26))
    heart(d, 64, 70, 12, PINK)
    d.ellipse([56, 20, 72, 34], fill=GOLD)
    return img


def ic_pleasure_converter():
    img = canvas(); d = ImageDraw.Draw(img)
    d.rounded_rectangle([34, 40, 94, 96], radius=10, fill=(60, 56, 70), outline=METAL, width=4)
    heart(d, 64, 62, 15, PINK)
    for i, ang in enumerate((210, 270, 330)):
        x = 64 + 44 * math.cos(math.radians(ang)); y = 68 + 44 * math.sin(math.radians(ang))
        d.line([(64, 68), (x, y)], fill=(255, 200, 80), width=5)
    d.rectangle([44, 30, 52, 42], fill=METAL); d.rectangle([76, 30, 84, 42], fill=METAL)
    return img


def ic_training_collar():
    img = canvas(); d = ImageDraw.Draw(img)
    d.ellipse([26, 36, 102, 94], outline=LEATHER, width=14)
    d.ellipse([26, 36, 102, 94], outline=(110, 90, 106), width=4)
    for ang in range(0, 360, 40):
        x = 64 + 38 * math.cos(math.radians(ang)); y = 65 + 29 * math.sin(math.radians(ang))
        d.ellipse([x - 4, y - 4, x + 4, y + 4], fill=GOLD)
    d.rectangle([58, 88, 70, 104], fill=METAL)
    d.text((59, 90), "+", fill=(40, 30, 40))
    return img


def ic_remote_vibe():
    img = canvas(); d = ImageDraw.Draw(img)
    d.ellipse([30, 52, 66, 98], fill=PINK, outline=PINK_HI, width=3)
    d.rounded_rectangle([72, 30, 102, 86], radius=8, fill=(60, 56, 70), outline=METAL, width=3)
    d.ellipse([80, 40, 94, 54], fill=PINK_HI)
    d.rectangle([80, 62, 94, 70], fill=METAL_DK)
    for i in range(3):
        d.arc([50 - i * 8, 30 - i * 6, 90 + i * 8, 70 + i * 6], 220, 320, fill=PINK_HI, width=3)
    return img


def ic_corrupt_hourglass():
    img = canvas(); d = ImageDraw.Draw(img)
    d.rectangle([36, 18, 92, 28], fill=GOLD); d.rectangle([36, 100, 92, 110], fill=GOLD)
    d.polygon([(40, 28), (88, 28), (66, 64), (62, 64)], fill=(220, 210, 230, 160), outline=METAL)
    d.polygon([(62, 64), (66, 64), (88, 100), (40, 100)], fill=(220, 210, 230, 160), outline=METAL)
    d.polygon([(48, 32), (80, 32), (64, 58)], fill=PINK)
    d.polygon([(64, 70), (78, 96), (50, 96)], fill=PINK)
    return img


def ic_crest_ring():
    img = canvas(); d = ImageDraw.Draw(img)
    d.ellipse([30, 34, 98, 102], outline=GOLD, width=11)
    d.ellipse([30, 34, 98, 102], outline=(255, 230, 160), width=3)
    d.polygon([(64, 14), (76, 34), (52, 34)], fill=PURPLE)
    heart(d, 64, 32, 10, PINK)
    return img


ICONS = {
    "fall_lustsurge_charm.png": ic_lustsurge_charm,
    "fall_heatadapt_badge.png": ic_heatadapt_badge,
    "fall_sagetime_watch.png": ic_sagetime_watch,
    "fall_crest_pendant.png": ic_crest_pendant,
    "fall_mother_brooch.png": ic_mother_brooch,
    "fall_exposure_cloak.png": ic_exposure_cloak,
    "fall_pleasure_converter.png": ic_pleasure_converter,
    "fall_training_collar.png": ic_training_collar,
    "fall_remote_vibe.png": ic_remote_vibe,
    "fall_corrupt_hourglass.png": ic_corrupt_hourglass,
    "fall_crest_ring.png": ic_crest_ring,
}


def power_icon(name, draw_fn):
    img = canvas(32)
    d = ImageDraw.Draw(img)
    draw_fn(d)
    img.save(f"{POW}/{name}.png")


def main():
    for name, fn in ICONS.items():
        save(name, fn())
    print("relic icons:", len(ICONS))

    power_icon("power_lustsurge", lambda d: (heart(d, 16, 15, 9, PINK), d.line([(16, 4), (16, 0)], fill=PINK)))
    power_icon("power_heatadapt", lambda d: (d.arc([4, 4, 28, 28], 0, 360, fill=PINK_HI, width=3),
                                             d.polygon([(16, 6), (20, 14), (12, 14)], fill=PINK)))
    power_icon("power_sagetime", lambda d: (d.ellipse([5, 5, 27, 27], outline=GOLD, width=3),
                                            d.line([(16, 16), (16, 9)], fill=GOLD, width=2),
                                            d.line([(16, 16), (21, 18)], fill=GOLD, width=2)))
    power_icon("power_sensitive", lambda d: (d.ellipse([8, 8, 24, 24], fill=PINK),
                                             d.ellipse([12, 12, 20, 20], fill=PINK_HI)))
    power_icon("power_aphrotoxin", lambda d: (d.polygon([(16, 2), (26, 22), (6, 22)], fill=PURPLE),
                                              d.ellipse([12, 22, 20, 30], fill=PINK)))
    power_icon("power_afterglow", lambda d: (heart(d, 16, 14, 8, (150, 150, 170)),
                                             d.line([(6, 26), (26, 26)], fill=(150, 150, 170), width=3)))
    power_icon("power_dependence", lambda d: (d.ellipse([6, 6, 26, 26], outline=PINK, width=3),
                                              heart(d, 16, 15, 6, PINK)))
    print("power icons: 7")

    # mod 徽章 32×32
    badge = canvas(32)
    d = ImageDraw.Draw(badge)
    d.rounded_rectangle([1, 1, 31, 31], radius=7, fill=(60, 30, 50), outline=PINK, width=2)
    heart(d, 16, 15, 9, PINK)
    badge.save(f"{IMG}/badge.png")
    print("badge done")


if __name__ == "__main__":
    main()
