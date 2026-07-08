#!/usr/bin/env python3
"""Process user-drawn full-body differential art (eyja / muel) into the
portrait state-machine + pregnancy belly patches.

Input:  art_input/<角色>/{基础,情欲0-3,小幅度破损,大幅度破损,极大幅度破损,全裸,手铐,装备合体,淫纹}.png (904x1264 RGBA)
Output: arknsfwResources/images/portraits/user/
        {tag}_e0..e3 / _d1.._d4 / _cuffs / _gear / _crest .png       (12 states)
        {tag}_<state>_p1..p3.png   pregnancy belly patches (full canvas, sparse)
        {tag}_meta.json            belly anchor + glow color
"""
import json
import os

import numpy as np
from PIL import Image, ImageFilter

W, H = 904, 1264
SRC = {"艾雅": "eyja", "缪尔赛思": "muel"}
STATES = {
    "e0": "情欲0", "e1": "情欲1", "e2": "情欲2", "e3": "情欲3",
    "d1": "小幅度破损", "d2": "大幅度破损", "d3": "极大幅度破损", "d4": "全裸",
    "cuffs": "手铐", "gear": "装备合体", "crest": "淫纹", "climax": "高潮",
}
OUT = "ArknightsNsfwBridge/src/main/resources/arknsfwResources/images/portraits/user"

# 孕肚形变强度（半径像素、最大外推比例）
PREG = {1: (165, 0.22), 2: (205, 0.40), 3: (240, 0.58)}


def belly_anchor(cn):
    """淫纹图 vs 基础图：高阈值差异+躯干范围收紧 → 纹章质心与主色。"""
    base = np.asarray(Image.open(f"art_input/{cn}/基础.png").convert("RGBA"), dtype=np.int16)
    crest_img = Image.open(f"art_input/{cn}/淫纹.png").convert("RGBA")
    crest = np.asarray(crest_img, dtype=np.int16)
    d = np.abs(crest - base).sum(axis=2)
    mask = d > 280
    mask[: int(H * 0.35)] = False
    mask[int(H * 0.75):] = False
    mask[:, : int(W * 0.3)] = False
    mask[:, int(W * 0.7):] = False
    m = Image.fromarray((mask * 255).astype(np.uint8)).filter(ImageFilter.MaxFilter(9)).filter(ImageFilter.MinFilter(5))
    lab = np.asarray(m) > 0
    ys, xs = np.where(lab)
    if len(xs) < 50:
        return W // 2, int(H * 0.52), (255, 120, 60)
    cx, cy = int(xs.mean()), int(ys.mean())
    sel = np.asarray(crest_img)[lab]
    mx = sel[:, :3].max(axis=1).astype(int)
    mn = sel[:, :3].min(axis=1).astype(int)
    sat = mx - mn
    top = sel[sat > np.percentile(sat, 70)]
    col = tuple(int(c) for c in top[:, :3].mean(axis=0))
    return cx, cy, col


def belly_warp(img_arr, cx, cy, radius, strength):
    """局部径向外推形变（孕肚），返回新数组。仅椭圆区域内有位移。"""
    h, w = img_arr.shape[:2]
    y0, y1 = max(0, cy - radius), min(h, cy + radius)
    x0, x1 = max(0, cx - radius), min(w, cx + radius)
    yy, xx = np.mgrid[y0:y1, x0:x1].astype(np.float32)
    dx = (xx - cx) / radius
    dy = (yy - cy) / (radius * 0.92)
    rr = np.sqrt(dx * dx + dy * dy)
    inside = rr < 1.0
    # 位移场：往外推（采样时往内取）
    k = strength * np.clip(np.cos(rr * np.pi / 2), 0, 1) ** 1.2
    sx = xx - (xx - cx) * k
    sy = yy - (yy - cy) * k * 0.75
    sx = np.clip(sx, 0, w - 1)
    sy = np.clip(sy, 0, h - 1)
    out = img_arr.copy()
    ix, iy = sx.astype(np.int32), sy.astype(np.int32)
    patch = img_arr[iy, ix]
    region = out[y0:y1, x0:x1]
    region[inside] = patch[inside]
    out[y0:y1, x0:x1] = region
    return out


def sparse_patch(orig, warped):
    """只保留变化区域的画布同尺寸稀疏补丁。"""
    diff = np.abs(warped.astype(np.int16) - orig.astype(np.int16)).sum(axis=2)
    mask = diff > 6
    # 膨胀几像素避免缝隙
    m = Image.fromarray((mask * 255).astype(np.uint8)).filter(ImageFilter.MaxFilter(7))
    mask = np.asarray(m) > 0
    patch = np.zeros_like(warped)
    patch[mask] = warped[mask]
    return patch


def main():
    os.makedirs(OUT, exist_ok=True)
    for cn, tag in SRC.items():
        cx, cy, glow = belly_anchor(cn)
        meta = {"belly": [cx, cy], "glow": list(glow)}
        json.dump(meta, open(f"{OUT}/{tag}_meta.json", "w"))
        print(f"{tag}: belly=({cx},{cy}) glow={glow}")
        for st, name in STATES.items():
            img = Image.open(f"art_input/{cn}/{name}.png").convert("RGBA")
            img.save(f"{OUT}/{tag}_{st}.png")
            arr = np.asarray(img)
            for stage, (radius, strength) in PREG.items():
                warped = belly_warp(arr, cx, cy, radius, strength)
                patch = sparse_patch(arr, warped)
                Image.fromarray(patch).save(f"{OUT}/{tag}_{st}_p{stage}.png")
        print(f"{tag}: 12 states + 36 preg patches done")


if __name__ == "__main__":
    main()
