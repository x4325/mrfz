#!/usr/bin/env python3
"""重绘占位感遗物图标：五角色7件套（按角色配色）、艾雅/缪尔专属、孕印、淫堕怀表。"""
import math
from PIL import Image, ImageDraw, ImageFilter

R = "ArknightsNsfwBridge/src/main/resources/arknsfwResources/images/relics"
S = 128
GOLD = (222, 180, 96)
METAL = (206, 198, 214)
PINK = (232, 96, 138)
PINK_HI = (250, 168, 196)

CHAR_TINT = {
    "highmore": (96, 156, 198), "scene": (186, 132, 196), "archetto": (214, 176, 92),
    "haruka": (206, 96, 116), "nymph": (156, 104, 196),
    "eyja": (204, 88, 66), "muel": (96, 184, 146),
}


def canvas():
    return Image.new("RGBA", (S, S), (0, 0, 0, 0))


def finish(img):
    a = img.split()[3].point(lambda v: 255 if v > 30 else 0)
    sh = Image.new("RGBA", (S, S), (0, 0, 0, 0))
    sh.paste(Image.new("RGBA", (S, S), (0, 0, 0, 110)), (4, 5), a)
    sh = sh.filter(ImageFilter.GaussianBlur(3))
    return Image.alpha_composite(sh, img)


def save(name, img):
    img = finish(img)
    img.save(f"{R}/{name}")
    a = img.split()[3].point(lambda v: 255 if v > 40 else 0)
    out = Image.new("RGBA", (S, S), (0, 0, 0, 0))
    out.paste(Image.new("RGBA", (S, S), (255, 255, 255, 255)), (0, 0), a)
    out.save(f"{R}/outline/{name}")


def heart(d, cx, cy, s, fill):
    d.polygon([(cx, cy + s), (cx - s, cy), (cx, cy - s * 0.2), (cx + s, cy)], fill=fill)
    d.ellipse([cx - s, cy - s * 0.72, cx + 2, cy + s * 0.25], fill=fill)
    d.ellipse([cx - 2, cy - s * 0.72, cx + s, cy + s * 0.25], fill=fill)


def darker(c, f=0.55):
    return tuple(int(v * f) for v in c)


def lighter(c, f=0.5):
    return tuple(min(255, int(v + (255 - v) * f)) for v in c)


# ---- 七件套母版（tint = 角色色）----
def warm_charm(t):
    img = canvas(); d = ImageDraw.Draw(img)
    d.line([(64, 10), (44, 34)], fill=GOLD, width=5)
    d.line([(64, 10), (84, 34)], fill=GOLD, width=5)
    d.ellipse([28, 30, 100, 102], fill=darker(t), outline=GOLD, width=6)
    heart(d, 64, 64, 20, PINK)
    heart(d, 64, 62, 10, PINK_HI)
    for ang in range(0, 360, 45):
        x = 64 + 46 * math.cos(math.radians(ang)); y = 66 + 46 * math.sin(math.radians(ang))
        d.line([(64 + 40 * math.cos(math.radians(ang)), 66 + 40 * math.sin(math.radians(ang))), (x, y)],
               fill=lighter(t), width=3)
    return img


def blush_sticker(t):
    img = canvas(); d = ImageDraw.Draw(img)
    d.rounded_rectangle([24, 30, 104, 102], radius=14, fill=lighter(t, 0.65), outline=darker(t), width=4)
    d.ellipse([38, 52, 62, 74], fill=(250, 140, 160))
    d.ellipse([70, 52, 94, 74], fill=(250, 140, 160))
    heart(d, 64, 88, 9, PINK)
    d.polygon([(96, 30), (104, 30), (104, 40)], fill=(255, 255, 255, 180))
    return img


def soft_collar(t):
    img = canvas(); d = ImageDraw.Draw(img)
    d.ellipse([24, 40, 104, 96], outline=lighter(t, 0.35), width=16)
    d.ellipse([24, 40, 104, 96], outline=darker(t), width=4)
    heart(d, 64, 96, 11, PINK)
    d.ellipse([58, 88, 70, 100], outline=GOLD, width=3)
    return img


def pulse_plug(t):
    img = canvas(); d = ImageDraw.Draw(img)
    d.ellipse([44, 26, 84, 78], fill=t, outline=darker(t), width=3)
    d.polygon([(50, 70), (78, 70), (70, 92), (58, 92)], fill=darker(t, 0.75))
    d.rounded_rectangle([46, 90, 82, 104], radius=7, fill=GOLD)
    heart(d, 64, 100, 6, PINK)
    for i in range(3):
        d.arc([28 - i * 8, 14 - i * 7, 100 + i * 8, 90 + i * 7], 250, 290, fill=PINK_HI, width=3)
    return img


def twin_mirror(t):
    img = canvas(); d = ImageDraw.Draw(img)
    for cx, cy, hx, hy in ((46, 46, 30, 104), (82, 52, 98, 108)):
        d.line([(cx, cy + 22), (hx, hy)], fill=GOLD, width=6)
        d.ellipse([cx - 20, cy - 26, cx + 20, cy + 26], fill=(226, 238, 248), outline=darker(t), width=5)
        d.ellipse([cx - 12, cy - 17, cx + 4, cy + 3], fill=(255, 255, 255, 190))
    heart(d, 64, 108, 8, PINK)
    return img


def moist_flask(t):
    img = canvas(); d = ImageDraw.Draw(img)
    d.polygon([(54, 30), (74, 30), (78, 58), (96, 96), (32, 96), (50, 58)], fill=(230, 236, 244, 200), outline=darker(t))
    d.polygon([(46, 72), (82, 72), (92, 94), (36, 94)], fill=t)
    d.rectangle([52, 20, 76, 32], fill=darker(t))
    d.ellipse([60, 4, 72, 18], fill=lighter(t))
    return img


def overflow_core(t):
    img = canvas(); d = ImageDraw.Draw(img)
    d.ellipse([32, 40, 96, 104], fill=darker(t), outline=t, width=5)
    d.ellipse([44, 52, 68, 76], fill=lighter(t, 0.35))
    for x, y in ((40, 34), (64, 26), (88, 34)):
        d.ellipse([x - 7, y - 7, x + 7, y + 7], fill=t)
        d.line([(x, y), (x, y + 18)], fill=t, width=6)
    heart(d, 64, 78, 9, PINK)
    return img


SET7 = {
    "warmcharmrelic": warm_charm, "blushstickerrelic": blush_sticker,
    "softcollarrelic": soft_collar, "pulseplugrelic": pulse_plug,
    "twinmirrorrelic": twin_mirror, "moistflaskrelic": moist_flask,
    "overflowcorerelic": overflow_core,
}


# ---- 孕印 / 分娩纪念 ----
def pregnancy_mark(t):
    img = canvas(); d = ImageDraw.Draw(img)
    d.ellipse([30, 26, 98, 106], fill=(248, 210, 188), outline=(226, 168, 150), width=4)
    d.ellipse([40, 44, 88, 100], outline=lighter(t, 0.2), width=3)
    heart(d, 64, 70, 16, PINK)
    for ang in (200, 270, 340):
        x = 64 + 30 * math.cos(math.radians(ang)); y = 72 + 30 * math.sin(math.radians(ang))
        d.line([(x - 4, y), (x + 4, y)], fill=t, width=3)
        d.line([(x, y - 4), (x, y + 4)], fill=t, width=3)
    return img


# ---- 艾雅 / 缪尔专属 ----
def thermometer_charm(t):
    img = canvas(); d = ImageDraw.Draw(img)
    d.line([(64, 8), (50, 26)], fill=GOLD, width=4)
    d.line([(64, 8), (78, 26)], fill=GOLD, width=4)
    d.rounded_rectangle([56, 24, 72, 88], radius=8, fill=(236, 240, 246), outline=(150, 150, 165), width=3)
    d.rectangle([60, 46, 68, 86], fill=(230, 70, 60))
    d.ellipse([50, 82, 78, 110], fill=(230, 70, 60), outline=(160, 40, 36), width=3)
    for y in (34, 46, 58, 70):
        d.line([(72, y), (80, y)], fill=(150, 150, 165), width=2)
    return img


def wool_heat(t):
    img = canvas(); d = ImageDraw.Draw(img)
    for i, y in enumerate((38, 58, 78)):
        d.rounded_rectangle([26, y, 102, y + 24], radius=12, fill=lighter(t, 0.5 - i * 0.12), outline=darker(t), width=3)
    for x in range(34, 100, 12):
        d.arc([x - 6, 92, x + 6, 106], 0, 180, fill=darker(t), width=3)
    heart(d, 64, 50, 8, PINK)
    return img


def heat_sticker(t):
    img = canvas(); d = ImageDraw.Draw(img)
    d.rounded_rectangle([28, 32, 100, 100], radius=10, fill=(250, 226, 210), outline=(226, 168, 150), width=4)
    d.polygon([(64, 42), (78, 66), (70, 66), (80, 90), (56, 64), (66, 64)], fill=(238, 110, 70))
    d.ellipse([44, 76, 56, 88], fill=PINK_HI)
    return img


def lava_plug(t):
    img = canvas(); d = ImageDraw.Draw(img)
    d.ellipse([44, 24, 84, 74], fill=(70, 40, 44), outline=(238, 110, 70), width=4)
    for ang in range(0, 360, 60):
        x = 64 + 16 * math.cos(math.radians(ang)); y = 48 + 20 * math.sin(math.radians(ang))
        d.line([(64, 48), (x, y)], fill=(250, 150, 70), width=3)
    d.polygon([(50, 68), (78, 68), (70, 90), (58, 90)], fill=(50, 30, 34))
    d.rounded_rectangle([46, 88, 82, 102], radius=7, fill=(238, 110, 70))
    return img


def ash_collar(t):
    img = canvas(); d = ImageDraw.Draw(img)
    d.ellipse([24, 40, 104, 96], outline=(58, 50, 56), width=15)
    d.ellipse([24, 40, 104, 96], outline=(110, 96, 104), width=4)
    for ang in range(0, 360, 45):
        x = 64 + 38 * math.cos(math.radians(ang)); y = 68 + 27 * math.sin(math.radians(ang))
        d.ellipse([x - 4, y - 4, x + 4, y + 4], fill=(238, 110, 70))
    d.ellipse([56, 88, 72, 104], fill=(238, 110, 70), outline=(160, 60, 40), width=3)
    return img


def ember_seed(t):
    img = canvas(); d = ImageDraw.Draw(img)
    d.ellipse([40, 34, 88, 98], fill=(88, 52, 44), outline=(238, 110, 70), width=4)
    for ang in (250, 290, 210, 330):
        x = 64 + 26 * math.cos(math.radians(ang)); y = 66 + 34 * math.sin(math.radians(ang))
        d.line([(64, 66), (x, y)], fill=(250, 150, 70), width=3)
    d.ellipse([56, 56, 72, 74], fill=(250, 190, 90))
    d.polygon([(64, 16), (72, 34), (56, 34)], fill=(120, 170, 90))
    return img


def rhine_gel(t):
    img = canvas(); d = ImageDraw.Draw(img)
    d.rounded_rectangle([44, 34, 84, 102], radius=12, fill=lighter(t, 0.55), outline=darker(t), width=4)
    d.rectangle([50, 22, 78, 38], fill=darker(t))
    d.ellipse([52, 54, 76, 90], fill=(255, 255, 255, 130))
    d.ellipse([58, 106, 70, 118], fill=lighter(t, 0.3))
    return img


def duplicate_mirror(t):
    img = canvas(); d = ImageDraw.Draw(img)
    d.line([(64, 76), (64, 112)], fill=GOLD, width=7)
    d.ellipse([34, 16, 94, 80], fill=(226, 244, 238), outline=darker(t), width=6)
    d.ellipse([44, 28, 64, 50], fill=(255, 255, 255, 200))
    d.ellipse([66, 42, 82, 62], fill=lighter(t, 0.3))
    d.ellipse([70, 46, 78, 58], fill=t)
    return img


def root_vine(t):
    img = canvas(); d = ImageDraw.Draw(img)
    for i in range(3):
        r = 40 - i * 11
        d.arc([64 - r, 62 - r, 64 + r, 62 + r], 30 + i * 40, 320 + i * 30, fill=darker(t, 0.8 - i * 0.15), width=8 - i * 2)
    for x, y in ((94, 40), (30, 66), (80, 96)):
        d.polygon([(x, y), (x + 12, y - 6), (x + 4, y + 8)], fill=t)
    return img


def corrupt_watch(t):
    img = canvas(); d = ImageDraw.Draw(img)
    pts = [(64, 6), (52, 14), (62, 22), (50, 30)]
    for a, b in zip(pts, pts[1:]):
        d.line([a, b], fill=GOLD, width=4)
    d.rectangle([56, 26, 72, 38], fill=GOLD)
    d.ellipse([26, 34, 102, 110], fill=(56, 40, 54), outline=GOLD, width=6)
    d.ellipse([36, 44, 92, 100], fill=(244, 232, 240))
    heart(d, 64, 70, 9, PINK)
    d.line([(64, 72), (64, 52)], fill=(90, 60, 84), width=4)
    d.line([(64, 72), (78, 80)], fill=(90, 60, 84), width=4)
    for ang in range(0, 360, 30):
        x = 64 + 24 * math.cos(math.radians(ang)); y = 72 + 24 * math.sin(math.radians(ang))
        d.ellipse([x - 2, y - 2, x + 2, y + 2], fill=(120, 90, 110))
    return img


# ---- 三种诅咒遗物（角色色点缀）----
def curse_altar(t):
    img = canvas(); d = ImageDraw.Draw(img)
    d.polygon([(30, 100), (98, 100), (90, 78), (38, 78)], fill=(48, 38, 46), outline=(96, 76, 92))
    d.rectangle([46, 56, 82, 80], fill=(62, 48, 60))
    heart(d, 64, 46, 14, PINK)
    for ang in (240, 270, 300):
        x = 64 + 26 * math.cos(math.radians(ang)); y = 50 + 26 * math.sin(math.radians(ang))
        d.line([(64, 44), (x, y)], fill=lighter(t, 0.2), width=3)
    d.ellipse([40, 88, 48, 96], fill=t); d.ellipse([80, 88, 88, 96], fill=t)
    return img


def curse_brand(t):
    img = canvas(); d = ImageDraw.Draw(img)
    d.line([(84, 16), (60, 52)], fill=(90, 84, 96), width=8)
    d.rectangle([80, 8, 96, 24], fill=(60, 56, 66))
    d.ellipse([34, 48, 90, 104], outline=(238, 110, 70), width=7)
    heart(d, 62, 74, 16, (238, 110, 70))
    for ang in range(0, 360, 60):
        x = 62 + 34 * math.cos(math.radians(ang)); y = 76 + 34 * math.sin(math.radians(ang))
        d.line([(62 + 28 * math.cos(math.radians(ang)), 76 + 28 * math.sin(math.radians(ang))), (x, y)],
               fill=lighter(t, 0.25), width=3)
    return img


def curse_loop(t):
    img = canvas(); d = ImageDraw.Draw(img)
    d.arc([28, 28, 100, 100], 30, 330, fill=(150, 60, 110), width=10)
    d.polygon([(92, 30), (106, 40), (88, 46)], fill=(150, 60, 110))
    heart(d, 64, 66, 12, PINK)
    for ang in (90, 210, 330):
        x = 64 + 36 * math.cos(math.radians(ang)); y = 64 + 36 * math.sin(math.radians(ang))
        d.ellipse([x - 4, y - 4, x + 4, y + 4], fill=lighter(t, 0.25))
    return img


def main():
    n = 0
    for char in ("highmore", "scene", "archetto", "haruka", "nymph"):
        t = CHAR_TINT[char]
        for suffix, fn in SET7.items():
            save(f"relic_{char}_{char}{suffix}.png", fn(t)); n += 1
        save(f"relic_curse_{char}_{char}altarcurserelic.png", curse_altar(t)); n += 1
        save(f"relic_curse_{char}_{char}brandcurserelic.png", curse_brand(t)); n += 1
        save(f"relic_curse_{char}_{char}loopcurserelic.png", curse_loop(t)); n += 1
        save(f"{char}_pregnancy_mark.png", pregnancy_mark(t)); n += 1
    for char in ("eyja", "muel"):
        save(f"{char}_pregnancy_mark.png", pregnancy_mark(CHAR_TINT[char])); n += 1
    t = CHAR_TINT["eyja"]
    save("thermometer_charm.png", thermometer_charm(t)); n += 1
    save("wool_heat.png", wool_heat(t)); n += 1
    save("heat_sticker.png", heat_sticker(t)); n += 1
    save("lava_plug.png", lava_plug(t)); n += 1
    save("ash_collar.png", ash_collar(t)); n += 1
    save("ember_seed.png", ember_seed(t)); n += 1
    t = CHAR_TINT["muel"]
    save("rhine_gel.png", rhine_gel(t)); n += 1
    save("duplicate_mirror.png", duplicate_mirror(t)); n += 1
    save("root_vine.png", root_vine(t)); n += 1
    save("fall_corrupt_hourglass.png", corrupt_watch(None)); n += 1
    print("redrawn:", n)


if __name__ == "__main__":
    main()
