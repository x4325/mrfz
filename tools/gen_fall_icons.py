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
    # 淫语墨书：皮肤色底 + 墨字涂鸦 + 毛笔
    img = canvas(); d = ImageDraw.Draw(img)
    d.rounded_rectangle([20, 30, 96, 104], radius=16, fill=(248, 214, 190), outline=(220, 170, 150), width=3)
    ink = (60, 30, 70)
    for i, y in enumerate((44, 62, 80)):
        d.line([(32, y), (60 - i * 4, y)], fill=ink, width=5)
        d.line([(38, y - 6), (38, y + 6)], fill=ink, width=4)
    heart(d, 74, 64, 10, PINK)
    d.line([(88, 20), (104, 60)], fill=(90, 60, 40), width=7)
    d.polygon([(100, 56), (112, 78), (104, 80)], fill=ink)
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
    # 调教乳环：一对金环 + 连链 + 小铃
    img = canvas(); d = ImageDraw.Draw(img)
    for cx in (40, 88):
        d.ellipse([cx - 16, 34, cx + 16, 66], outline=GOLD, width=8)
        d.ellipse([cx - 16, 34, cx + 16, 66], outline=(255, 230, 160), width=2)
        d.ellipse([cx - 4, 28, cx + 4, 38], fill=METAL)
    pts = [(40, 66), (52, 84), (64, 90), (76, 84), (88, 66)]
    for a, b in zip(pts, pts[1:]):
        d.line([a, b], fill=METAL, width=4)
    d.ellipse([57, 88, 71, 102], fill=GOLD, outline=(160, 120, 50))
    d.ellipse([61, 96, 67, 102], fill=(120, 90, 40))
    return img


def ic_remote_vibe():
    # 遥控震动棒：大头按摩棒 + 遥控器
    img = canvas(); d = ImageDraw.Draw(img)
    d.ellipse([22, 16, 62, 56], fill=PINK, outline=PINK_HI, width=3)
    d.rounded_rectangle([34, 48, 50, 104], radius=7, fill=(235, 235, 240), outline=METAL_DK, width=3)
    for i in range(3):
        d.arc([10 - i * 7, 6 - i * 6, 74 + i * 7, 66 + i * 6], 240, 330, fill=PINK_HI, width=3)
    d.rounded_rectangle([74, 46, 104, 100], radius=8, fill=(60, 56, 70), outline=METAL, width=3)
    d.ellipse([82, 54, 96, 68], fill=PINK_HI)
    d.rectangle([82, 76, 96, 84], fill=METAL_DK)
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
    # 淫纹阴环：贯穿软肉的小环 + 心形垂坠 + 淫纹微光
    img = canvas(); d = ImageDraw.Draw(img)
    d.ellipse([36, 18, 92, 52], fill=(248, 200, 190), outline=(230, 160, 150), width=3)
    d.ellipse([48, 40, 80, 84], outline=GOLD, width=9)
    d.ellipse([48, 40, 80, 84], outline=(255, 230, 160), width=2)
    d.ellipse([58, 36, 70, 48], fill=METAL)
    heart(d, 64, 96, 12, PINK)
    for ang in (150, 30):
        x = 64 + 34 * math.cos(math.radians(ang)); y = 88 + 18 * math.sin(math.radians(ang))
        d.line([(x - 4, y), (x + 4, y)], fill=PURPLE, width=3)
        d.line([(x, y - 4), (x, y + 4)], fill=PURPLE, width=3)
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
