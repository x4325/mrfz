#!/usr/bin/env python3
"""Split the pixel-locked 装备合体 diff layer into per-relic overlay PNGs,
and bake the climax expression state. Outputs to portraits/user/."""
import sys, os
sys.path.insert(0, os.path.dirname(os.path.abspath(__file__)))
import numpy as np
from PIL import Image, ImageFilter
from process_user_art import belly_warp, sparse_patch, PREG, belly_anchor

W, H = 904, 1264
OUT = "ArknightsNsfwBridge/src/main/resources/arknsfwResources/images/portraits/user"

# 每件装备的裁剪盒 (x0,y0,x1,y1)，来自网格标定
BOXES = {
    "eyja": {
        "blindfold": (300, 160, 590, 262),
        "gag":       (348, 258, 545, 312),
        "collar":    (330, 300, 560, 396),
        "rope":      (325, 388, 575, 525),
        "chastity":  (325, 525, 575, 668),
        "garter":    (325, 660, 478, 795),
        "vibe":      (462, 630, 585, 782),
    },
    "muel": {
        "blindfold": (300, 170, 590, 270),
        "gag":       (345, 262, 550, 318),
        "collar":    (330, 300, 565, 400),
        "rope":      (330, 375, 580, 520),
        "chastity":  (330, 520, 580, 700),
        "garter":    (320, 640, 470, 790),
        "vibe":      (455, 610, 585, 760),
    },
}
SRC = {"eyja": "艾雅", "muel": "缪尔赛思"}


def extract_layer(cn):
    base = np.asarray(Image.open(f"art_input/{cn}/基础.png").convert("RGBA"), dtype=np.int16)
    gear_img = Image.open(f"art_input/{cn}/装备合体.png").convert("RGBA")
    gear = np.asarray(gear_img, dtype=np.int16)
    d = np.abs(gear - base).sum(axis=2)
    mask = (d > 70).astype(np.uint8) * 255
    m = Image.fromarray(mask).filter(ImageFilter.MinFilter(3)).filter(ImageFilter.MaxFilter(9)).filter(ImageFilter.MaxFilter(5))
    mm = np.asarray(m) > 0
    ga = np.asarray(gear_img)
    layer = np.zeros_like(ga)
    layer[mm] = ga[mm]
    feather = np.asarray(Image.fromarray((mm * 255).astype(np.uint8)).filter(ImageFilter.GaussianBlur(1.2)))
    layer[:, :, 3] = np.minimum(feather, ga[:, :, 3])
    return layer


def main():
    for tag, cn in SRC.items():
        layer = extract_layer(cn)
        cx, cy, _ = belly_anchor(cn)
        for item, (x0, y0, x1, y1) in BOXES[tag].items():
            piece = np.zeros_like(layer)
            piece[y0:y1, x0:x1] = layer[y0:y1, x0:x1]
            Image.fromarray(piece).save(f"{OUT}/{tag}_it_{item}.png")
            # 孕肚形变版（装备贴着肚子走）
            for stage, (radius, strength) in PREG.items():
                warped = belly_warp(piece, cx, cy, radius, strength)
                Image.fromarray(warped).save(f"{OUT}/{tag}_it_{item}_p{stage}.png")
        print(tag, "7 items split (+preg variants)")
        # 高潮表情：整图状态 + 孕肚补丁
        img = Image.open(f"art_input/{cn}/高潮.png").convert("RGBA")
        img.save(f"{OUT}/{tag}_climax.png")
    print("done (climax preg patches via process_user_art step)")


if __name__ == "__main__":
    main()
