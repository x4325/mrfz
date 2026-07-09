#!/usr/bin/env python3
"""Regenerate all arknsfw bridge relic icons: transparent glyph style with margins
(replaces old placeholder icons that had solid backing squares)."""
import glob
import math
import os

from PIL import Image, ImageDraw, ImageFilter

DIR = "ArknightsNsfwBridge/src/main/resources/arknsfwResources/images/relics"
S = 128
PAD = 18  # content margin => icon fills ~72%

PALETTES = {
    "highmore": ((90, 150, 190), (140, 200, 235)),
    "scene": ((180, 130, 190), (215, 175, 220)),
    "archetto": ((210, 170, 80), (240, 210, 130)),
    "haruka": ((205, 90, 110), (240, 145, 160)),
    "nymph": ((150, 100, 190), (195, 150, 225)),
    "eyja": ((200, 80, 60), (235, 135, 100)),
    "ash": ((200, 80, 60), (235, 135, 100)),
    "volcano": ((200, 80, 60), (235, 135, 100)),
    "magma": ((200, 80, 60), (235, 135, 100)),
    "ember": ((200, 80, 60), (235, 135, 100)),
    "muel": ((90, 180, 140), (150, 220, 185)),
    "bubble": ((90, 180, 140), (150, 220, 185)),
    "clone": ((90, 180, 140), (150, 220, 185)),
    "flood": ((90, 180, 140), (150, 220, 185)),
}
DEFAULT_PAL = ((190, 120, 150), (225, 170, 195))


def pal_for(name):
    for key, pal in PALETTES.items():
        if key in name:
            return pal
    return DEFAULT_PAL


def glyph_for(name):
    n = name
    if "cuffs" in n or "chain" in n or "rope" in n:
        return "chain"
    if "leash" in n or "collar" in n:
        return "collar"
    if "belt" in n:
        return "heart"
    if "garter" in n:
        return "orb"
    if "belltag" in n or "bell" in n:
        return "star"
    if "gag" in n:
        return "orb"
    if "blindfold" in n:
        return "mirror"
    if "vibe" in n:
        return "drop"
    if "bodycrest" in n:
        return "heart"
    if "mark" in n or "womb" in n or "breed" in n or "seed" in n:
        return "heart"
    if "delivery" in n or "cradle" in n:
        return "cradle"
    if "flask" in n or "serum" in n or "draught" in n or "dew" in n or "nectar" in n or "sap" in n:
        return "flask"
    if "mirror" in n:
        return "mirror"
    if "charm" in n or "sticker" in n:
        return "star"
    if "curse" in n or "brand" in n or "loop" in n or "altar" in n or "furnace" in n:
        return "chain"
    if "plug" in n or "core" in n or "pulse" in n:
        return "orb"
    if "wand" in n or "tag" in n:
        return "wand"
    return "drop"


def base_canvas():
    return Image.new("RGBA", (S, S), (0, 0, 0, 0))


def with_style(draw_fn, pal):
    main, hi = pal
    # 阴影层
    shadow = base_canvas()
    ds = ImageDraw.Draw(shadow)
    draw_fn(ds, (0, 0, 0, 110), 4, 3)
    shadow = shadow.filter(ImageFilter.GaussianBlur(3))
    # 主体 + 高光
    img = base_canvas()
    d = ImageDraw.Draw(img)
    draw_fn(d, (*main, 255), 0, 0)
    draw_fn(d, (*hi, 130), -2, -2)
    return Image.alpha_composite(shadow, img)


def _heart(d, fill, ox, oy):
    cx, cy = S / 2 + ox, S / 2 + 6 + oy
    s = (S - 2 * PAD) * 0.42
    d.polygon([(cx, cy + s), (cx - s, cy - s * 0.1), (cx - s * 0.5, cy - s * 0.62),
               (cx, cy - s * 0.15), (cx + s * 0.5, cy - s * 0.62), (cx + s, cy - s * 0.1)], fill=fill)
    d.ellipse([cx - s, cy - s * 0.85, cx + 2, cy + s * 0.15], fill=fill)
    d.ellipse([cx - 2, cy - s * 0.85, cx + s, cy + s * 0.15], fill=fill)


def _collar(d, fill, ox, oy):
    cx, cy = S / 2 + ox, S / 2 + oy
    r = (S - 2 * PAD) * 0.40
    d.arc([cx - r, cy - r, cx + r, cy + r], 300, 240, fill=fill, width=14)
    d.ellipse([cx - 7, cy + r - 9, cx + 7, cy + r + 5], outline=fill, width=6)


def _flask(d, fill, ox, oy):
    cx = S / 2 + ox
    top = PAD + 8 + oy
    bot = S - PAD - 4 + oy
    w = (S - 2 * PAD) * 0.62
    d.polygon([(cx - 8, top), (cx + 8, top), (cx + 8, top + 22),
               (cx + w / 2, bot - 12), (cx + w / 2 - 6, bot),
               (cx - w / 2 + 6, bot), (cx - w / 2, bot - 12), (cx - 8, top + 22)], fill=fill)
    d.rectangle([cx - 11, top - 4, cx + 11, top + 4], fill=fill)


def _mirror(d, fill, ox, oy):
    cx, cy = S / 2 + ox, S / 2 - 6 + oy
    rx, ry = (S - 2 * PAD) * 0.34, (S - 2 * PAD) * 0.42
    d.ellipse([cx - rx, cy - ry, cx + rx, cy + ry], outline=fill, width=10)
    d.rectangle([cx - 6, cy + ry, cx + 6, cy + ry + 18], fill=fill)


def _star(d, fill, ox, oy):
    cx, cy = S / 2 + ox, S / 2 + oy
    R = (S - 2 * PAD) * 0.46
    pts = []
    for i in range(10):
        r = R if i % 2 == 0 else R * 0.45
        a = -math.pi / 2 + i * math.pi / 5
        pts.append((cx + r * math.cos(a), cy + r * math.sin(a)))
    d.polygon(pts, fill=fill)


def _chain(d, fill, ox, oy):
    cx, cy = S / 2 + ox, S / 2 + oy
    r = 15
    for i, (dx, dy) in enumerate(((-26, -26), (0, 0), (26, 26))):
        d.ellipse([cx + dx - r, cy + dy - r, cx + dx + r, cy + dy + r], outline=fill, width=9)


def _orb(d, fill, ox, oy):
    cx, cy = S / 2 + ox, S / 2 + oy
    r = (S - 2 * PAD) * 0.40
    d.ellipse([cx - r, cy - r, cx + r, cy + r], fill=fill)
    d.ellipse([cx - r * 0.35 - r * 0.25, cy - r * 0.55, cx - r * 0.25 + r * 0.2, cy - r * 0.1],
              fill=(255, 255, 255, 120))


def _wand(d, fill, ox, oy):
    x0, y0 = PAD + 10 + ox, S - PAD - 10 + oy
    x1, y1 = S - PAD - 14 + ox, PAD + 14 + oy
    d.line([(x0, y0), (x1, y1)], fill=fill, width=11)
    r = 13
    d.ellipse([x1 - r, y1 - r, x1 + r, y1 + r], fill=fill)


def _cradle(d, fill, ox, oy):
    cx, cy = S / 2 + ox, S / 2 + oy
    r = (S - 2 * PAD) * 0.42
    d.arc([cx - r, cy - r * 0.7, cx + r, cy + r], 0, 180, fill=fill, width=12)
    d.ellipse([cx - 10, cy - r * 0.55, cx + 10, cy - r * 0.55 + 20], fill=fill)


def _drop(d, fill, ox, oy):
    cx, cy = S / 2 + ox, S / 2 + 8 + oy
    r = (S - 2 * PAD) * 0.34
    d.ellipse([cx - r, cy - r * 0.6, cx + r, cy + r], fill=fill)
    d.polygon([(cx, cy - r * 1.5), (cx - r * 0.72, cy - r * 0.2), (cx + r * 0.72, cy - r * 0.2)], fill=fill)


GLYPHS = {"heart": _heart, "collar": _collar, "flask": _flask, "mirror": _mirror,
          "star": _star, "chain": _chain, "orb": _orb, "wand": _wand,
          "cradle": _cradle, "drop": _drop}


def outline_of(img):
    a = img.split()[3].point(lambda v: 255 if v > 40 else 0)
    white = Image.new("RGBA", img.size, (255, 255, 255, 255))
    out = Image.new("RGBA", img.size, (0, 0, 0, 0))
    out.paste(white, (0, 0), a)
    return out


def main():
    files = sorted(glob.glob(f"{DIR}/*.png"))
    os.makedirs(f"{DIR}/outline", exist_ok=True)
    for f in files:
        name = os.path.basename(f)[:-4].lower()
        pal = pal_for(name)
        if "curse" in name:
            pal = (tuple(int(c * 0.55) for c in pal[0]), tuple(int(c * 0.7) for c in pal[1]))
        img = with_style(GLYPHS[glyph_for(name)], pal)
        if "curse" in name:
            d = ImageDraw.Draw(img)
            d.line([(PAD + 8, S - PAD - 8), (S - PAD - 8, PAD + 8)], fill=(120, 20, 30, 220), width=7)
        img.save(f)
        outline_of(img).save(f"{DIR}/outline/{os.path.basename(f)}")
    print(f"{len(files)} relic icons regenerated (transparent glyph style)")


if __name__ == "__main__":
    main()
