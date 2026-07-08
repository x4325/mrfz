#!/usr/bin/env python3
"""Split the pixel-locked 装备合体 diff layer into per-relic overlay PNGs,
and bake the climax expression state. Outputs to portraits/user/."""
import sys, os
sys.path.insert(0, os.path.dirname(os.path.abspath(__file__)))
import numpy as np
from PIL import Image, ImageFilter
from scipy import ndimage
from process_user_art import belly_warp, sparse_patch, PREG, belly_anchor

W, H = 904, 1264
OUT = "ArknightsNsfwBridge/src/main/resources/arknsfwResources/images/portraits/user"

# 每件装备的裁剪盒 (x0,y0,x1,y1)，来自网格标定
# 有序装备格：先到先得认领像素（小而精确的部件在前，绳缚兜底在后）
BOXES = {
    "eyja": [
        ("blindfold", (300, 160, 590, 262)),
        ("gag",       (348, 258, 545, 312)),
        ("collar",    (330, 300, 560, 396)),
        ("garter",    (325, 660, 478, 795)),
        ("vibe",      (462, 630, 585, 782)),
        ("chastity",  (325, 525, 575, 668)),
        ("rope",      (325, 388, 575, 525)),
    ],
    "muel": [
        ("blindfold", (300, 170, 590, 270)),
        ("gag",       (345, 262, 550, 318)),
        ("collar",    (330, 300, 565, 375)),
        ("garter",    (320, 640, 470, 790)),
        ("vibe",      (455, 615, 530, 700)),
        ("chastity",  (390, 555, 545, 710)),
        ("leash",     (440, 415, 595, 650)),
        ("rope",      (325, 370, 595, 665)),
    ],
}
SRC = {"eyja": "艾雅", "muel": "缪尔赛思"}


def extract_alpha(cn):
    """软 alpha 提取：差异越大越不透明，无硬边；再用连通域剔除
    AI 生成图中头发/衣褶微移造成的碎小“黑线”鬼影。返回 float [0,1]。"""
    base = np.asarray(Image.open(f"art_input/{cn}/基础.png").convert("RGBA"), dtype=np.int16)
    gear = np.asarray(Image.open(f"art_input/{cn}/装备合体.png").convert("RGBA"), dtype=np.int16)
    d = np.abs(gear - base).sum(axis=2).astype(np.float32)
    alpha = np.clip((d - 30.0) / 70.0, 0.0, 1.0)
    # 连通域过滤：只保留面积足够大的实体装备区域
    solid = alpha > 0.6
    solid = np.asarray(Image.fromarray((solid * 255).astype(np.uint8)).filter(ImageFilter.MaxFilter(5))) > 0
    labels, n = ndimage.label(solid)
    keepmask = np.zeros_like(solid)
    if n:
        sizes = ndimage.sum(solid, labels, range(1, n + 1))
        keep = np.zeros(n + 1, dtype=bool)
        keep[1:] = sizes >= 800
        keepmask = keep[labels]
    # 保留区外扩一圈并羽化，作为 alpha 的准入门
    gate = np.asarray(Image.fromarray((keepmask * 255).astype(np.uint8))
                      .filter(ImageFilter.MaxFilter(9))
                      .filter(ImageFilter.GaussianBlur(3.0)), dtype=np.float32) / 255.0
    return alpha * gate


def box_gate(box):
    """羽化矩形准入门，避免格子边缘出现直切硬边。"""
    from PIL import ImageDraw
    x0, y0, x1, y1 = box
    m = Image.new("L", (W, H), 0)
    ImageDraw.Draw(m).rectangle([x0, y0, x1, y1], fill=255)
    m = m.filter(ImageFilter.GaussianBlur(4.0))
    return np.asarray(m, dtype=np.float32) / 255.0


def main():
    for tag, cn in SRC.items():
        gear_img = Image.open(f"art_input/{cn}/装备合体.png").convert("RGBA")
        ga = np.asarray(gear_img)
        alpha = extract_alpha(cn)
        from process_user_art import BELLY_MANUAL
        cx, cy = BELLY_MANUAL[tag]
        claimed = np.zeros((H, W), dtype=np.float32)
        for item, box in BOXES[tag]:
            m = alpha * box_gate(box)
            m = m * (1.0 - claimed)          # 先到先得，避免同一像素被两件装备重复绘制
            claimed = np.clip(claimed + m, 0.0, 1.0)
            piece = ga.copy()
            piece[:, :, 3] = np.minimum(ga[:, :, 3], (m * 255).astype(np.uint8))
            Image.fromarray(piece).save(f"{OUT}/{tag}_it_{item}.png")
            # 孕肚形变版（装备贴着肚子走）
            for stage, (radius, strength) in PREG.items():
                warped = belly_warp(piece, cx, cy, radius, strength)
                Image.fromarray(warped).save(f"{OUT}/{tag}_it_{item}_p{stage}.png")
        print(tag, f"{len(BOXES[tag])} items split (+preg variants)")
        # 高潮表情：整图状态 + 孕肚补丁
        img = Image.open(f"art_input/{cn}/高潮.png").convert("RGBA")
        img.save(f"{OUT}/{tag}_climax.png")
    print("done (climax preg patches via process_user_art step)")


if __name__ == "__main__":
    main()
