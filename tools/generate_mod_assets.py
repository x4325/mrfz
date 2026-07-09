#!/usr/bin/env python3
"""Procedural UI asset generator for the five character mods + NSFW bridge.

Generates a consistent flat-style set per character:
  relic icons (+white outlines), energy orb layers, card frames,
  character-select button, power/debuff icons.
Character art (cards, portraits, events) is intentionally left to the user.
"""
import math
import os
import sys

from PIL import Image, ImageDraw, ImageFilter

ROOT = os.path.dirname(os.path.dirname(os.path.abspath(__file__)))

# ---------------------------------------------------------------- palettes
PAL = {
    "highmore": dict(main=(64, 150, 168), light=(150, 220, 230), dark=(20, 52, 72),
                     accent=(120, 110, 220), glow=(140, 230, 240)),
    "scene":    dict(main=(150, 160, 70), light=(220, 225, 150), dark=(50, 52, 26),
                     accent=(220, 120, 90), glow=(235, 240, 170)),
    "archetto": dict(main=(200, 140, 60), light=(245, 210, 140), dark=(70, 40, 20),
                     accent=(180, 60, 70), glow=(255, 225, 160)),
    "haruka":   dict(main=(210, 90, 100), light=(250, 180, 170), dark=(70, 24, 34),
                     accent=(250, 190, 90), glow=(255, 200, 180)),
    "nymph":    dict(main=(190, 90, 160), light=(240, 170, 220), dark=(56, 24, 56),
                     accent=(120, 200, 190), glow=(250, 190, 235)),
}

S = 4  # supersampling factor


def canvas(size):
    return Image.new("RGBA", (size * S, size * S), (0, 0, 0, 0))


def finish(img, size):
    return img.resize((size, size), Image.LANCZOS)


def rgba(c, a=255):
    return (c[0], c[1], c[2], a)


def mix(a, b, t):
    return tuple(int(a[i] + (b[i] - a[i]) * t) for i in range(3))


# ---------------------------------------------------------------- glyphs
# Each glyph draws in a normalized 100x100 box on the supplied draw at (ox, oy, scale).

def P(pts, ox, oy, sc):
    return [(ox + x * sc, oy + y * sc) for x, y in pts]


def g_scythe(d, ox, oy, sc, c):
    d.arc([ox + 5 * sc, oy + 0 * sc, ox + 95 * sc, oy + 78 * sc], 190, 355, fill=c, width=int(11 * sc))
    d.line(P([(30, 30), (68, 96)], ox, oy, sc), fill=c, width=int(9 * sc))
    d.line(P([(45, 55), (62, 47)], ox, oy, sc), fill=c, width=int(6 * sc))


def g_wave(d, ox, oy, sc, c):
    for k, yy in enumerate((38, 62)):
        pts = []
        for i in range(0, 101, 4):
            pts.append((i, yy + 10 * math.sin((i / 100.0) * 2 * math.pi + k)))
        d.line(P(pts, ox, oy, sc), fill=c, width=int(9 * sc))


def g_anchor(d, ox, oy, sc, c):
    d.ellipse(P([(42, 6), (58, 22)], ox, oy, sc)[0] + P([(42, 6), (58, 22)], ox, oy, sc)[1], outline=c, width=int(6 * sc))
    d.line(P([(50, 22), (50, 78)], ox, oy, sc), fill=c, width=int(8 * sc))
    d.line(P([(30, 38), (70, 38)], ox, oy, sc), fill=c, width=int(7 * sc))
    d.arc([ox + 16 * sc, oy + 42 * sc, ox + 84 * sc, oy + 96 * sc], 20, 160, fill=c, width=int(8 * sc))


def g_shell(d, ox, oy, sc, c):
    d.pieslice([ox + 14 * sc, oy + 10 * sc, ox + 86 * sc, oy + 96 * sc], 200, 340, fill=c)
    bg = (0, 0, 0, 0)
    for ang in (230, 270, 310):
        x2 = 50 + 46 * math.cos(math.radians(ang))
        y2 = 53 + 46 * math.sin(math.radians(ang)) + 20
        d.line(P([(50, 72), (x2, y2)], ox, oy, sc), fill=bg, width=int(4 * sc))


def g_droplet(d, ox, oy, sc, c):
    d.polygon(P([(50, 4), (74, 48), (50, 60), (26, 48)], ox, oy, sc), fill=c)
    d.ellipse([ox + 24 * sc, oy + 36 * sc, ox + 76 * sc, oy + 88 * sc], fill=c)


def g_coral(d, ox, oy, sc, c):
    for x0, tilt in ((50, 0), (34, -22), (66, 22)):
        d.line(P([(x0, 90), (x0 + tilt * 0.4, 55), (x0 + tilt, 22)], ox, oy, sc), fill=c, width=int(8 * sc))
    d.line(P([(30, 92), (70, 92)], ox, oy, sc), fill=c, width=int(7 * sc))


def g_aperture(d, ox, oy, sc, c):
    d.ellipse([ox + 8 * sc, oy + 8 * sc, ox + 92 * sc, oy + 92 * sc], outline=c, width=int(8 * sc))
    for k in range(6):
        a = math.radians(k * 60)
        x1, y1 = 50 + 16 * math.cos(a), 50 + 16 * math.sin(a)
        x2, y2 = 50 + 36 * math.cos(a + 0.7), 50 + 36 * math.sin(a + 0.7)
        d.line(P([(x1, y1), (x2, y2)], ox, oy, sc), fill=c, width=int(6 * sc))


def g_camera(d, ox, oy, sc, c):
    d.rounded_rectangle([ox + 10 * sc, oy + 28 * sc, ox + 90 * sc, oy + 82 * sc], radius=8 * sc, outline=c, width=int(7 * sc))
    d.rectangle([ox + 34 * sc, oy + 18 * sc, ox + 62 * sc, oy + 28 * sc], fill=c)
    d.ellipse([ox + 34 * sc, oy + 38 * sc, ox + 66 * sc, oy + 70 * sc], outline=c, width=int(7 * sc))


def g_film(d, ox, oy, sc, c):
    d.rounded_rectangle([ox + 8 * sc, oy + 30 * sc, ox + 92 * sc, oy + 70 * sc], radius=6 * sc, fill=c)
    hole = (0, 0, 0, 0)
    for x in range(16, 92, 16):
        d.rectangle([ox + x * sc, oy + 36 * sc, ox + (x + 8) * sc, oy + 44 * sc], fill=hole)
        d.rectangle([ox + x * sc, oy + 56 * sc, ox + (x + 8) * sc, oy + 64 * sc], fill=hole)


def g_tripod(d, ox, oy, sc, c):
    d.rectangle([ox + 38 * sc, oy + 8 * sc, ox + 62 * sc, oy + 30 * sc], fill=c)
    d.line(P([(50, 30), (50, 92)], ox, oy, sc), fill=c, width=int(7 * sc))
    d.line(P([(46, 40), (20, 92)], ox, oy, sc), fill=c, width=int(7 * sc))
    d.line(P([(54, 40), (80, 92)], ox, oy, sc), fill=c, width=int(7 * sc))


def g_lens(d, ox, oy, sc, c):
    d.ellipse([ox + 12 * sc, oy + 12 * sc, ox + 88 * sc, oy + 88 * sc], outline=c, width=int(9 * sc))
    d.ellipse([ox + 32 * sc, oy + 32 * sc, ox + 68 * sc, oy + 68 * sc], fill=c)
    d.ellipse([ox + 38 * sc, oy + 36 * sc, ox + 50 * sc, oy + 48 * sc], fill=(255, 255, 255, 200))


def g_arrow(d, ox, oy, sc, c):
    d.line(P([(20, 80), (74, 26)], ox, oy, sc), fill=c, width=int(8 * sc))
    d.polygon(P([(66, 10), (92, 8), (88, 34)], ox, oy, sc), fill=c)
    d.line(P([(20, 80), (34, 78)], ox, oy, sc), fill=c, width=int(6 * sc))
    d.line(P([(20, 80), (22, 66)], ox, oy, sc), fill=c, width=int(6 * sc))


def g_bow(d, ox, oy, sc, c):
    d.arc([ox + 14 * sc, oy + 4 * sc, ox + 118 * sc, oy + 96 * sc], 120, 240, fill=c, width=int(9 * sc))
    d.line(P([(40, 12), (40, 88)], ox, oy, sc), fill=c, width=int(5 * sc))
    d.line(P([(40, 50), (88, 50)], ox, oy, sc), fill=c, width=int(6 * sc))
    d.polygon(P([(82, 42), (98, 50), (82, 58)], ox, oy, sc), fill=c)


def g_quiver(d, ox, oy, sc, c):
    d.rounded_rectangle([ox + 34 * sc, oy + 30 * sc, ox + 70 * sc, oy + 94 * sc], radius=10 * sc, outline=c, width=int(7 * sc))
    for dx in (-8, 4, 16):
        d.line(P([(44 + dx, 34), (56 + dx, 6)], ox, oy, sc), fill=c, width=int(6 * sc))


def g_fork(d, ox, oy, sc, c):
    d.line(P([(50, 40), (50, 92)], ox, oy, sc), fill=c, width=int(8 * sc))
    d.line(P([(38, 8), (38, 40)], ox, oy, sc), fill=c, width=int(7 * sc))
    d.line(P([(62, 8), (62, 40)], ox, oy, sc), fill=c, width=int(7 * sc))
    d.arc([ox + 38 * sc, oy + 28 * sc, ox + 62 * sc, oy + 52 * sc], 0, 180, fill=c, width=int(7 * sc))


def g_target(d, ox, oy, sc, c):
    for r in (40, 26, 12):
        d.ellipse([ox + (50 - r) * sc, oy + (50 - r) * sc, ox + (50 + r) * sc, oy + (50 + r) * sc],
                  outline=c, width=int(7 * sc))
    d.ellipse([ox + 46 * sc, oy + 46 * sc, ox + 54 * sc, oy + 54 * sc], fill=c)


def g_burst(d, ox, oy, sc, c):
    for k in range(12):
        a = math.radians(k * 30)
        r1 = 14 if k % 2 == 0 else 22
        r2 = 46 if k % 2 == 0 else 34
        d.line(P([(50 + r1 * math.cos(a), 50 + r1 * math.sin(a)),
                  (50 + r2 * math.cos(a), 50 + r2 * math.sin(a))], ox, oy, sc), fill=c, width=int(6 * sc))
    d.ellipse([ox + 44 * sc, oy + 44 * sc, ox + 56 * sc, oy + 56 * sc], fill=c)


def g_sparkler(d, ox, oy, sc, c):
    d.line(P([(30, 94), (62, 40)], ox, oy, sc), fill=c, width=int(6 * sc))
    for k in range(8):
        a = math.radians(k * 45 + 20)
        d.line(P([(62 + 8 * math.cos(a), 40 + 8 * math.sin(a)),
                  (62 + 26 * math.cos(a), 40 + 26 * math.sin(a))], ox, oy, sc), fill=c, width=int(5 * sc))


def g_mask(d, ox, oy, sc, c):
    d.rounded_rectangle([ox + 18 * sc, oy + 22 * sc, ox + 82 * sc, oy + 78 * sc], radius=26 * sc, fill=c)
    hole = (0, 0, 0, 0)
    d.ellipse([ox + 30 * sc, oy + 40 * sc, ox + 44 * sc, oy + 54 * sc], fill=hole)
    d.ellipse([ox + 56 * sc, oy + 40 * sc, ox + 70 * sc, oy + 54 * sc], fill=hole)
    d.line(P([(18, 30), (8, 20)], ox, oy, sc), fill=c, width=int(6 * sc))
    d.line(P([(82, 30), (92, 20)], ox, oy, sc), fill=c, width=int(6 * sc))


def g_drum(d, ox, oy, sc, c):
    d.rounded_rectangle([ox + 20 * sc, oy + 36 * sc, ox + 80 * sc, oy + 80 * sc], radius=8 * sc, outline=c, width=int(7 * sc))
    d.ellipse([ox + 20 * sc, oy + 26 * sc, ox + 80 * sc, oy + 48 * sc], outline=c, width=int(6 * sc))
    d.line(P([(30, 10), (44, 30)], ox, oy, sc), fill=c, width=int(5 * sc))
    d.line(P([(70, 10), (56, 30)], ox, oy, sc), fill=c, width=int(5 * sc))


def g_lantern(d, ox, oy, sc, c):
    d.rounded_rectangle([ox + 30 * sc, oy + 24 * sc, ox + 70 * sc, oy + 82 * sc], radius=16 * sc, outline=c, width=int(7 * sc))
    d.line(P([(50, 24), (50, 82)], ox, oy, sc), fill=c, width=int(4 * sc))
    d.rectangle([ox + 40 * sc, oy + 14 * sc, ox + 60 * sc, oy + 24 * sc], fill=c)
    d.rectangle([ox + 42 * sc, oy + 82 * sc, ox + 58 * sc, oy + 92 * sc], fill=c)


def g_flame(d, ox, oy, sc, c):
    d.polygon(P([(50, 4), (70, 36), (62, 44), (78, 62), (50, 96), (22, 62), (38, 44), (30, 36)], ox, oy, sc), fill=c)


def g_ghost(d, ox, oy, sc, c):
    d.pieslice([ox + 22 * sc, oy + 10 * sc, ox + 78 * sc, oy + 66 * sc], 180, 360, fill=c)
    d.rectangle([ox + 22 * sc, oy + 38 * sc, ox + 78 * sc, oy + 78 * sc], fill=c)
    hole = (0, 0, 0, 0)
    for x in (30, 46, 62):
        d.pieslice([ox + x * sc, oy + 70 * sc, ox + (x + 16) * sc, oy + 88 * sc], 0, 180, fill=c)
    d.ellipse([ox + 34 * sc, oy + 32 * sc, ox + 44 * sc, oy + 44 * sc], fill=hole)
    d.ellipse([ox + 56 * sc, oy + 32 * sc, ox + 66 * sc, oy + 44 * sc], fill=hole)


def g_bell(d, ox, oy, sc, c):
    d.pieslice([ox + 22 * sc, oy + 14 * sc, ox + 78 * sc, oy + 86 * sc], 180, 360, fill=c)
    d.rectangle([ox + 22 * sc, oy + 50 * sc, ox + 78 * sc, oy + 66 * sc], fill=c)
    d.ellipse([ox + 44 * sc, oy + 68 * sc, ox + 56 * sc, oy + 80 * sc], fill=c)
    d.line(P([(50, 6), (50, 16)], ox, oy, sc), fill=c, width=int(6 * sc))


def g_key(d, ox, oy, sc, c):
    d.ellipse([ox + 18 * sc, oy + 14 * sc, ox + 54 * sc, oy + 50 * sc], outline=c, width=int(8 * sc))
    d.line(P([(48, 44), (84, 80)], ox, oy, sc), fill=c, width=int(8 * sc))
    d.line(P([(70, 66), (80, 56)], ox, oy, sc), fill=c, width=int(7 * sc))
    d.line(P([(80, 76), (90, 66)], ox, oy, sc), fill=c, width=int(7 * sc))


def g_seal(d, ox, oy, sc, c):
    d.ellipse([ox + 18 * sc, oy + 18 * sc, ox + 82 * sc, oy + 82 * sc], fill=c)
    for k in range(10):
        a = math.radians(k * 36)
        d.ellipse([ox + (50 + 32 * math.cos(a) - 7) * sc, oy + (50 + 32 * math.sin(a) - 7) * sc,
                   ox + (50 + 32 * math.cos(a) + 7) * sc, oy + (50 + 32 * math.sin(a) + 7) * sc], fill=c)
    d.ellipse([ox + 36 * sc, oy + 36 * sc, ox + 64 * sc, oy + 64 * sc], outline=(255, 255, 255, 160), width=int(5 * sc))


def g_eye(d, ox, oy, sc, c):
    d.polygon(P([(6, 50), (50, 20), (94, 50), (50, 80)], ox, oy, sc), fill=c)
    d.ellipse([ox + 36 * sc, oy + 36 * sc, ox + 64 * sc, oy + 64 * sc], fill=(0, 0, 0, 0))
    d.ellipse([ox + 42 * sc, oy + 42 * sc, ox + 58 * sc, oy + 58 * sc], fill=c)


def g_heartlock(d, ox, oy, sc, c):
    d.pieslice([ox + 20 * sc, oy + 18 * sc, ox + 54 * sc, oy + 52 * sc], 180, 360, fill=c)
    d.pieslice([ox + 46 * sc, oy + 18 * sc, ox + 80 * sc, oy + 52 * sc], 180, 360, fill=c)
    d.polygon(P([(21, 40), (79, 40), (50, 88)], ox, oy, sc), fill=c)
    hole = (0, 0, 0, 0)
    d.ellipse([ox + 44 * sc, oy + 40 * sc, ox + 56 * sc, oy + 52 * sc], fill=hole)
    d.rectangle([ox + 47 * sc, oy + 48 * sc, ox + 53 * sc, oy + 62 * sc], fill=hole)


def g_charm(d, ox, oy, sc, c):
    d.polygon(P([(50, 8), (78, 34), (66, 84), (34, 84), (22, 34)], ox, oy, sc), fill=c)
    d.line(P([(50, 8), (50, 84)], ox, oy, sc), fill=(255, 255, 255, 120), width=int(4 * sc))
    d.line(P([(22, 34), (78, 34)], ox, oy, sc), fill=(255, 255, 255, 120), width=int(4 * sc))


def g_sticker(d, ox, oy, sc, c):
    d.pieslice([ox + 14 * sc, oy + 14 * sc, ox + 86 * sc, oy + 86 * sc], 180, 360, fill=c)
    d.pieslice([ox + 14 * sc, oy + 14 * sc, ox + 86 * sc, oy + 86 * sc], 0, 180, fill=c)
    d.polygon(P([(50, 86), (86, 50), (86, 86)], ox, oy, sc), fill=(255, 255, 255, 150))


def g_collar(d, ox, oy, sc, c):
    d.ellipse([ox + 16 * sc, oy + 22 * sc, ox + 84 * sc, oy + 78 * sc], outline=c, width=int(10 * sc))
    d.ellipse([ox + 42 * sc, oy + 64 * sc, ox + 58 * sc, oy + 80 * sc], fill=c)


def g_mirror(d, ox, oy, sc, c):
    d.ellipse([ox + 24 * sc, oy + 8 * sc, ox + 76 * sc, oy + 66 * sc], outline=c, width=int(8 * sc))
    d.ellipse([ox + 32 * sc, oy + 16 * sc, ox + 68 * sc, oy + 58 * sc], fill=rgba(c, 90))
    d.line(P([(50, 66), (50, 90)], ox, oy, sc), fill=c, width=int(8 * sc))
    d.line(P([(38, 92), (62, 92)], ox, oy, sc), fill=c, width=int(7 * sc))


def g_flask(d, ox, oy, sc, c):
    d.line(P([(42, 8), (58, 8)], ox, oy, sc), fill=c, width=int(6 * sc))
    d.polygon(P([(44, 10), (56, 10), (56, 36), (78, 82), (72, 92), (28, 92), (22, 82), (44, 36)], ox, oy, sc),
              outline=c, width=int(6 * sc))
    d.polygon(P([(34, 66), (66, 66), (72, 84), (66, 90), (34, 90), (28, 84)], ox, oy, sc), fill=c)


def g_plug(d, ox, oy, sc, c):
    d.ellipse([ox + 34 * sc, oy + 8 * sc, ox + 66 * sc, oy + 40 * sc], fill=c)
    d.polygon(P([(38, 34), (62, 34), (54, 66), (46, 66)], ox, oy, sc), fill=c)
    d.ellipse([ox + 36 * sc, oy + 62 * sc, ox + 64 * sc, oy + 90 * sc], outline=c, width=int(7 * sc))


def g_core(d, ox, oy, sc, c):
    d.ellipse([ox + 28 * sc, oy + 28 * sc, ox + 72 * sc, oy + 72 * sc], fill=c)
    for k in range(6):
        a = math.radians(k * 60 + 15)
        d.arc([ox + 12 * sc, oy + 12 * sc, ox + 88 * sc, oy + 88 * sc],
              k * 60 + 5, k * 60 + 40, fill=c, width=int(6 * sc))


def g_brand(d, ox, oy, sc, c):
    d.ellipse([ox + 18 * sc, oy + 18 * sc, ox + 82 * sc, oy + 82 * sc], outline=c, width=int(8 * sc))
    d.polygon(P([(50, 26), (64, 50), (50, 74), (36, 50)], ox, oy, sc), fill=c)
    d.line(P([(50, 4), (50, 18)], ox, oy, sc), fill=c, width=int(6 * sc))
    d.line(P([(50, 82), (50, 96)], ox, oy, sc), fill=c, width=int(6 * sc))


def g_altar(d, ox, oy, sc, c):
    d.polygon(P([(50, 10), (88, 78), (12, 78)], ox, oy, sc), outline=c, width=int(7 * sc))
    d.ellipse([ox + 42 * sc, oy + 44 * sc, ox + 58 * sc, oy + 60 * sc], fill=c)
    d.rectangle([ox + 22 * sc, oy + 82 * sc, ox + 78 * sc, oy + 92 * sc], fill=c)


def g_loop(d, ox, oy, sc, c):
    d.arc([ox + 16 * sc, oy + 24 * sc, ox + 64 * sc, oy + 76 * sc], 90, 330, fill=c, width=int(9 * sc))
    d.arc([ox + 36 * sc, oy + 24 * sc, ox + 84 * sc, oy + 76 * sc], 270, 150, fill=c, width=int(9 * sc))


def g_heart(d, ox, oy, sc, c):
    d.pieslice([ox + 18 * sc, oy + 18 * sc, ox + 54 * sc, oy + 54 * sc], 180, 360, fill=c)
    d.pieslice([ox + 46 * sc, oy + 18 * sc, ox + 80 * sc, oy + 52 * sc], 180, 360, fill=c)
    d.polygon(P([(19, 40), (81, 40), (50, 88)], ox, oy, sc), fill=c)


def g_focusframe(d, ox, oy, sc, c):
    for cx, cy, dx, dy in ((14, 14, 1, 1), (86, 14, -1, 1), (14, 86, 1, -1), (86, 86, -1, -1)):
        d.line(P([(cx, cy), (cx + 24 * dx, cy)], ox, oy, sc), fill=c, width=int(8 * sc))
        d.line(P([(cx, cy), (cx, cy + 24 * dy)], ox, oy, sc), fill=c, width=int(8 * sc))
    d.ellipse([ox + 40 * sc, oy + 40 * sc, ox + 60 * sc, oy + 60 * sc], fill=c)


GLYPHS = dict(scythe=g_scythe, wave=g_wave, anchor=g_anchor, shell=g_shell, droplet=g_droplet,
              coral=g_coral, aperture=g_aperture, camera=g_camera, film=g_film, tripod=g_tripod,
              lens=g_lens, arrow=g_arrow, bow=g_bow, quiver=g_quiver, fork=g_fork, target=g_target,
              burst=g_burst, sparkler=g_sparkler, mask=g_mask, drum=g_drum, lantern=g_lantern,
              flame=g_flame, ghost=g_ghost, bell=g_bell, key=g_key, seal=g_seal, eye=g_eye,
              heartlock=g_heartlock, charm=g_charm, sticker=g_sticker, collar=g_collar,
              mirror=g_mirror, flask=g_flask, plug=g_plug, core=g_core, brand=g_brand,
              altar=g_altar, loop=g_loop, heart=g_heart, focusframe=g_focusframe)


# ---------------------------------------------------------------- builders

def radial(img, cx, cy, r, inner, outer):
    d = ImageDraw.Draw(img)
    steps = 48
    for i in range(steps, 0, -1):
        t = i / steps
        d.ellipse([cx - r * t, cy - r * t, cx + r * t, cy + r * t], fill=rgba(mix(inner, outer, t), 255))


def relic_icon(pal, glyph, size=128, curse=False):
    img = canvas(size)
    d = ImageDraw.Draw(img)
    w = size * S
    m = w * 0.06
    dark, main, light = pal["dark"], pal["main"], pal["light"]
    if curse:
        dark = mix(dark, (20, 6, 26), 0.6)
        main = mix(main, (90, 30, 110), 0.5)
        light = mix(light, (190, 120, 200), 0.5)
    # badge
    grad = Image.new("RGBA", (w, w), (0, 0, 0, 0))
    radial(grad, w / 2, w / 2, w / 2 - m, mix(main, (255, 255, 255), 0.15), dark)
    maskc = Image.new("L", (w, w), 0)
    ImageDraw.Draw(maskc).ellipse([m, m, w - m, w - m], fill=255)
    img.paste(grad, (0, 0), maskc)
    d.ellipse([m, m, w - m, w - m], outline=rgba(light), width=int(w * 0.028))
    d.ellipse([m + w * 0.045, m + w * 0.045, w - m - w * 0.045, w - m - w * 0.045],
              outline=rgba(dark, 200), width=int(w * 0.014))
    # glyph
    gl = Image.new("RGBA", (w, w), (0, 0, 0, 0))
    gd = ImageDraw.Draw(gl)
    box = w * 0.52
    GLYPHS[glyph](gd, (w - box) / 2, (w - box) / 2, box / 100.0, rgba((255, 255, 255)))
    sh = gl.filter(ImageFilter.GaussianBlur(w * 0.02))
    black = Image.new("RGBA", (w, w), rgba(dark))
    img.paste(black, (0, 0), sh.getchannel("A"))
    tint = Image.new("RGBA", (w, w), rgba(mix(light, (255, 255, 255), 0.5)))
    img.paste(tint, (0, 0), gl.getchannel("A"))
    # top highlight
    hl = Image.new("RGBA", (w, w), (0, 0, 0, 0))
    ImageDraw.Draw(hl).pieslice([m + w * 0.06, m + w * 0.04, w - m - w * 0.06, w * 0.62], 200, 340,
                                fill=(255, 255, 255, 34))
    img = Image.alpha_composite(img, hl)
    return finish(img, size)


def outline_of(icon):
    a = icon.getchannel("A").point(lambda v: 255 if v > 40 else 0)
    white = Image.new("RGBA", icon.size, (255, 255, 255, 255))
    out = Image.new("RGBA", icon.size, (0, 0, 0, 0))
    out.paste(white, (0, 0), a)
    return out


def orb_layer(pal, idx, size=128):
    """idx 1 = filled core; 2..6 rotating ring layers."""
    img = canvas(size)
    d = ImageDraw.Draw(img)
    w = size * S
    cx = cy = w / 2
    if idx == 1:
        grad = Image.new("RGBA", (w, w), (0, 0, 0, 0))
        radial(grad, cx, cy, w * 0.30, mix(pal["glow"], (255, 255, 255), 0.5), pal["main"])
        maskc = Image.new("L", (w, w), 0)
        ImageDraw.Draw(maskc).ellipse([cx - w * 0.30, cy - w * 0.30, cx + w * 0.30, cy + w * 0.30], fill=255)
        img.paste(grad, (0, 0), maskc)
        d.ellipse([cx - w * 0.30, cy - w * 0.30, cx + w * 0.30, cy + w * 0.30],
                  outline=rgba(pal["light"], 230), width=int(w * 0.02))
    else:
        r = w * (0.30 + 0.035 * idx)
        col = rgba(pal["light"] if idx % 2 == 0 else pal["accent"], 210 - idx * 12)
        n = 2 + idx % 3
        span = 360 / n * 0.62
        for k in range(n):
            a0 = k * 360 / n + idx * 17
            d.arc([cx - r, cy - r, cx + r, cy + r], a0, a0 + span, fill=col, width=int(w * (0.030 - idx * 0.002)))
        for k in range(n):
            a = math.radians(k * 360 / n + idx * 17 + span)
            d.ellipse([cx + r * math.cos(a) - w * 0.018, cy + r * math.sin(a) - w * 0.018,
                       cx + r * math.cos(a) + w * 0.018, cy + r * math.sin(a) + w * 0.018], fill=col)
    return finish(img, size)


def energy_icon(pal, motif, size=256):
    img = canvas(size)
    w = size * S
    cx = cy = w / 2
    grad = Image.new("RGBA", (w, w), (0, 0, 0, 0))
    radial(grad, cx, cy, w * 0.42, mix(pal["glow"], (255, 255, 255), 0.45), pal["main"])
    maskc = Image.new("L", (w, w), 0)
    ImageDraw.Draw(maskc).ellipse([cx - w * 0.42, cy - w * 0.42, cx + w * 0.42, cy + w * 0.42], fill=255)
    img.paste(grad, (0, 0), maskc)
    d = ImageDraw.Draw(img)
    d.ellipse([cx - w * 0.42, cy - w * 0.42, cx + w * 0.42, cy + w * 0.42],
              outline=rgba(pal["light"]), width=int(w * 0.022))
    gl = Image.new("RGBA", (w, w), (0, 0, 0, 0))
    box = w * 0.46
    GLYPHS[motif](ImageDraw.Draw(gl), (w - box) / 2, (w - box) / 2, box / 100.0, rgba(pal["dark"], 210))
    img = Image.alpha_composite(img, gl)
    return finish(img, size)


def card_frame(pal, kind, size, motif):
    """kind: attack / skill / power. Full-canvas ornate frame."""
    img = canvas(size)
    d = ImageDraw.Draw(img)
    w = size * S
    kindmix = {"attack": pal["accent"], "skill": pal["main"], "power": mix(pal["main"], pal["accent"], 0.5)}[kind]
    base_out = mix(kindmix, (10, 10, 14), 0.55)
    base_in = mix(kindmix, (24, 26, 32), 0.35)
    m = w * 0.012
    d.rounded_rectangle([m, m, w - m, w - m], radius=w * 0.075, fill=rgba(base_out))
    d.rounded_rectangle([m, m, w - m, w - m], radius=w * 0.075, outline=rgba(mix(pal["light"], kindmix, 0.35)),
                        width=int(w * 0.012))
    m2 = w * 0.055
    d.rounded_rectangle([m2, m2, w - m2, w - m2], radius=w * 0.05, fill=rgba(base_in))
    d.rounded_rectangle([m2, m2, w - m2, w - m2], radius=w * 0.05,
                        outline=rgba(mix(pal["light"], (255, 255, 255), 0.2), 170), width=int(w * 0.006))
    # art window (slightly darker) upper half
    aw = w * 0.10
    d.rounded_rectangle([aw, w * 0.10, w - aw, w * 0.55], radius=w * 0.03,
                        fill=rgba(mix(base_in, (0, 0, 0), 0.35)))
    # text panel lower
    d.rounded_rectangle([aw, w * 0.60, w - aw, w * 0.92], radius=w * 0.03,
                        fill=rgba(mix(base_in, (0, 0, 0), 0.25)))
    # banner behind title
    d.rounded_rectangle([w * 0.16, w * 0.555, w * 0.84, w * 0.625], radius=w * 0.03,
                        fill=rgba(mix(kindmix, (0, 0, 0), 0.15)),
                        outline=rgba(pal["light"], 200), width=int(w * 0.005))
    # corner ornaments
    for cx, cy in ((m2, m2), (w - m2, m2), (m2, w - m2), (w - m2, w - m2)):
        d.ellipse([cx - w * 0.02, cy - w * 0.02, cx + w * 0.02, cy + w * 0.02], fill=rgba(pal["light"]))
    # faint motif watermark at art window center
    gl = Image.new("RGBA", (w, w), (0, 0, 0, 0))
    box = w * 0.28
    GLYPHS[motif](ImageDraw.Draw(gl), (w - box) / 2, w * 0.18, box / 100.0, rgba(pal["light"], 46))
    img = Image.alpha_composite(img, gl)
    return finish(img, size)


def char_button(pal, motif, size=240):
    img = canvas(size)
    d = ImageDraw.Draw(img)
    w = size * S
    cx = cy = w / 2
    grad = Image.new("RGBA", (w, w), (0, 0, 0, 0))
    radial(grad, cx, cy, w * 0.46, mix(pal["main"], (255, 255, 255), 0.1), pal["dark"])
    maskc = Image.new("L", (w, w), 0)
    ImageDraw.Draw(maskc).ellipse([cx - w * 0.46, cy - w * 0.46, cx + w * 0.46, cy + w * 0.46], fill=255)
    img.paste(grad, (0, 0), maskc)
    d.ellipse([cx - w * 0.46, cy - w * 0.46, cx + w * 0.46, cy + w * 0.46],
              outline=rgba(pal["light"]), width=int(w * 0.025))
    d.ellipse([cx - w * 0.40, cy - w * 0.40, cx + w * 0.40, cy + w * 0.40],
              outline=rgba(pal["accent"], 180), width=int(w * 0.010))
    gl = Image.new("RGBA", (w, w), (0, 0, 0, 0))
    box = w * 0.5
    GLYPHS[motif](ImageDraw.Draw(gl), (w - box) / 2, (w - box) / 2, box / 100.0,
                  rgba(mix(pal["light"], (255, 255, 255), 0.55)))
    img = Image.alpha_composite(img, gl)
    return finish(img, size)


def power_icon(pal, glyph, size=48, debuff=False):
    img = canvas(size)
    d = ImageDraw.Draw(img)
    w = size * S
    col = pal["accent"] if debuff else pal["light"]
    ring = mix(col, (255, 255, 255), 0.35)
    d.ellipse([w * 0.04, w * 0.04, w * 0.96, w * 0.96], fill=rgba(mix(pal["dark"], (0, 0, 0), 0.2), 235))
    d.ellipse([w * 0.04, w * 0.04, w * 0.96, w * 0.96], outline=rgba(ring), width=int(w * 0.055))
    gl = Image.new("RGBA", (w, w), (0, 0, 0, 0))
    box = w * 0.62
    GLYPHS[glyph](ImageDraw.Draw(gl), (w - box) / 2, (w - box) / 2, box / 100.0, rgba(ring))
    img = Image.alpha_composite(img, gl)
    return finish(img, size)


def save(img, *rel):
    path = os.path.join(ROOT, *rel)
    os.makedirs(os.path.dirname(path), exist_ok=True)
    img.save(path)
    # keep >2KB so placeholder generators never overwrite
    if os.path.getsize(path) < 2048:
        img.convert("RGBA").save(path, pnginfo=None)
    return path


# ---------------------------------------------------------------- per-mod specs
MODS = {
    "highmore": dict(dir="Highmore", res="highmoreResources", motif="scythe",
                     relics={"relic_starter": "scythe", "HighmoreTideCharm": "droplet",
                             "HighmoreCoralScythe": "coral", "HighmoreAnchor": "anchor",
                             "HighmoreShell": "shell"},
                     powers={"reap_power": "scythe", "drift_power": "wave"}),
    "scene": dict(dir="Scene", res="sceneResources", motif="aperture",
                  relics={"relic_starter": "aperture", "SceneTripod": "tripod",
                          "SceneFilmRoll": "film", "SceneWideLens": "lens", "SceneOldCamera": "camera"},
                  powers={"focus_power": "focusframe"}),
    "archetto": dict(dir="Archetto", res="archettoResources", motif="bow",
                     relics={"relic_starter": "bow", "ArchettoQuiver": "quiver",
                             "ArchettoTuningFork": "fork", "ArchettoSalePoster": "target",
                             "ArchettoEncoreBow": "arrow"},
                     powers={"aim_power": "target", "concerto_power": "fork"}),
    "haruka": dict(dir="Haruka", res="harukaResources", motif="burst",
                   relics={"relic_starter": "burst", "HarukaSparkler": "sparkler",
                           "HarukaFestivalMask": "mask", "HarukaDrumBeat": "drum",
                           "HarukaLantern": "lantern"},
                   powers={"pyro_power": "flame", "finale_echo_power": "burst"}),
    "nymph": dict(dir="Nymph", res="nymphResources", motif="heartlock",
                  relics={"relic_starter": "heartlock", "NymphHexCharm": "eye",
                          "NymphFearBell": "bell", "NymphHeartKey": "key", "NymphWaxSeal": "seal"},
                  powers={"hex_power": "eye", "fear_power": "ghost", "heart_lock_power": "heartlock"}),
}

BRIDGE_RELICS = {  # suffix -> glyph (shared across chars), curse?
    "warmcharmrelic": ("charm", False), "blushstickerrelic": ("sticker", False),
    "softcollarrelic": ("collar", False), "pulseplugrelic": ("plug", False),
    "twinmirrorrelic": ("mirror", False), "moistflaskrelic": ("flask", False),
    "overflowcorerelic": ("core", False),
    "brandcurserelic": ("brand", True), "altarcurserelic": ("altar", True),
    "loopcurserelic": ("loop", True),
}

BRIDGE_DEBUFFS = {  # icon file -> (char, glyph)
    "tide_brand": ("highmore", "droplet"), "exposed_lens": ("scene", "lens"),
    "trembling_grip": ("archetto", "bow"), "lingering_heat": ("haruka", "flame"),
    "heart_gnaw": ("nymph", "heart"), "core_need": ("nymph", "core"),
}


def main():
    count = 0
    for key, spec in MODS.items():
        pal = PAL[key]
        img_root = (spec["dir"], "src", "main", "resources", spec["res"], "img")
        # relic icons + outlines
        for fname, glyph in spec["relics"].items():
            icon = relic_icon(pal, glyph)
            save(icon, *img_root, "relics", fname + ".png")
            save(outline_of(icon), *img_root, "relics", "outline", fname + ".png")
            count += 2
        # orbs
        for i in range(1, 7):
            save(orb_layer(pal, i), *img_root, "orbs", f"{i}.png"); count += 1
        save(orb_layer(pal, 1, 64), *img_root, "char", "small_orb.png"); count += 1
        # card frames + energy
        for size, folder in ((512, "512"), (1024, "1024")):
            for kind in ("attack", "skill", "power"):
                save(card_frame(pal, kind, size, spec["motif"]), *img_root, folder, f"bg_{kind}.png"); count += 1
            save(energy_icon(pal, spec["motif"]), *img_root, folder, "energy.png"); count += 1
        # char select button
        save(char_button(pal, spec["motif"]), *img_root, "charSelect", "button.png"); count += 1
        # power icons (48px, loaded by PowerIcons helper)
        for fname, glyph in spec["powers"].items():
            save(power_icon(pal, glyph), *img_root, "powers", fname + ".png"); count += 1
    # bridge relics
    for key, spec in MODS.items():
        pal = PAL[key]
        for suffix, (glyph, curse) in BRIDGE_RELICS.items():
            if curse:
                fname = f"relic_curse_{key}_{key}{suffix}.png"
            else:
                fname = f"relic_{key}_{key}{suffix}.png"
            icon = relic_icon(pal, glyph, curse=curse)
            save(icon, "ArknightsNsfwBridge", "src", "main", "resources", "arknsfwResources",
                 "images", "relics", fname)
            save(outline_of(icon), "ArknightsNsfwBridge", "src", "main", "resources", "arknsfwResources",
                 "images", "relics", "outline", fname)
            count += 2
    # bridge debuff icons (32px, loadDebuffIcon requires exactly 32x32)
    for fname, (char, glyph) in BRIDGE_DEBUFFS.items():
        save(power_icon(PAL[char], glyph, size=32, debuff=True),
             "ArknightsNsfwBridge", "src", "main", "resources", "arknsfwResources",
             "images", "powers", fname + ".png")
        count += 1
    print("generated", count, "assets")


if __name__ == "__main__":
    main()
