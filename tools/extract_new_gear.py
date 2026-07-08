#!/usr/bin/env python3
"""新一批单件装备图 + buff 状态差分 → 独立叠加层（软 alpha + 连通域滤噪 + 孕肚变体）。
输出 portraits/user/{tag}_it_*.png 与 {tag}_fxb_*.png。"""
import sys, os
sys.path.insert(0, os.path.dirname(os.path.abspath(__file__)))
import numpy as np
from PIL import Image, ImageFilter
from scipy import ndimage
from process_user_art import belly_warp, PREG, BELLY_MANUAL

OUT = "ArknightsNsfwBridge/src/main/resources/arknsfwResources/images/portraits/user"

FILES = {
    "eyja": ("艾雅", {
        "it_wand": "震动棒", "it_nipplering": "乳环", "it_pubring": "阴环",
        "it_inkwrit": "淫语墨书", "it_converter": "快感转换器", "it_watch": "淫堕怀表",
        "fxb_lustsurge": "淫气高涨", "fxb_aphrotoxin": "催淫毒素",
        "fxb_afterglow": "绝顶余韵", "fxb_dependence": "快感依存"}),
    "muel": ("缪尔赛思", {
        "it_wand": "震动棒", "it_nipplering": "乳环", "it_pubring": "阴环",
        "it_inkwrit": "墨书", "it_converter": "转换器", "it_watch": "怀表",
        "fxb_lustsurge": "淫器高涨", "fxb_aphrotoxin": "媚毒",
        "fxb_afterglow": "高潮余韵", "fxb_dependence": "快感依存"}),
}


def extract(base, img):
    """软 alpha：差异越大越不透明；连通域过滤丢弃头发微移鬼影。"""
    v = np.asarray(img, dtype=np.int16)
    d = np.abs(v - base).sum(axis=2).astype(np.float32)
    alpha = np.clip((d - 30.0) / 70.0, 0.0, 1.0)
    solid = alpha > 0.6
    solid = np.asarray(Image.fromarray((solid * 255).astype(np.uint8)).filter(ImageFilter.MaxFilter(5))) > 0
    labels, n = ndimage.label(solid)
    gate = np.zeros(alpha.shape, dtype=np.float32)
    if n:
        sizes = ndimage.sum(solid, labels, range(1, n + 1))
        keep = np.zeros(n + 1, dtype=bool)
        keep[1:] = sizes >= 700
        keepmask = keep[labels]
        gate = np.asarray(Image.fromarray((keepmask * 255).astype(np.uint8))
                          .filter(ImageFilter.MaxFilter(9))
                          .filter(ImageFilter.GaussianBlur(3.0)), dtype=np.float32) / 255.0
    ga = np.asarray(img)
    layer = ga.copy()
    layer[:, :, 3] = np.minimum(ga[:, :, 3], (alpha * gate * 255).astype(np.uint8))
    return layer


def main():
    for tag, (cn, items) in FILES.items():
        base = np.asarray(Image.open(f"art_input/{cn}/基础.png").convert("RGBA"), dtype=np.int16)
        cx, cy = BELLY_MANUAL[tag]
        for key, fn in items.items():
            img = Image.open(f"art_input/{cn}/{fn}.png").convert("RGBA")
            layer = extract(base, img)
            Image.fromarray(layer).save(f"{OUT}/{tag}_{key}.png")
            for stage, (radius, strength) in PREG.items():
                warped = belly_warp(layer, cx, cy, radius, strength)
                Image.fromarray(warped).save(f"{OUT}/{tag}_{key}_p{stage}.png")
        print(tag, len(items), "layers (+preg)")


if __name__ == "__main__":
    main()
