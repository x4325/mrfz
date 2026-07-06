#!/usr/bin/env python3
"""Import ComfyUI arknsfw event images — scale to fit 768 canvas, no crop."""
from __future__ import annotations

import sys
from pathlib import Path

from PIL import Image

from image_util import fit_contain

COMFY = Path(sys.argv[1]) if len(sys.argv) > 1 else Path(r"E:\ComfyUI-aki-v3\ComfyUI\output")
OUT = Path(sys.argv[2]) if len(sys.argv) > 2 else Path(
    r"G:\SteamLibrary\steamapps\workshop\content\646570\ArknightsNsfwBridge\src\main\resources\arknsfwResources\images\events"
)
CANVAS = 768

PAIRS = [
    ("ComfyUI_03540_.png", "event_eyja_normal_act1.png"),
    ("ComfyUI_03541_.png", "event_eyja_normal_act2.png"),
    ("ComfyUI_03542_.png", "event_eyja_normal_act3.png"),
    ("ComfyUI_03543_.png", "event_eyja_shame_act1.png"),
    ("ComfyUI_03544_.png", "event_eyja_shame_act2.png"),
    ("ComfyUI_03545_.png", "event_eyja_shame_act3.png"),
    ("ComfyUI_03546_.png", "event_eyja_fall_act1.png"),
    ("ComfyUI_03547_.png", "event_eyja_fall_act2.png"),
    ("ComfyUI_03548_.png", "event_eyja_fall_act3.png"),
    ("ComfyUI_03549_.png", "event_eyja_debuff_seal.png"),
    ("ComfyUI_03550_.png", "event_muel_normal_act1.png"),
    ("ComfyUI_03551_.png", "event_muel_normal_act2.png"),
    ("ComfyUI_03552_.png", "event_muel_normal_act3.png"),
    ("ComfyUI_03553_.png", "event_muel_shame_act1.png"),
    ("ComfyUI_03554_.png", "event_muel_shame_act2.png"),
    ("ComfyUI_03555_.png", "event_muel_shame_act3.png"),
    ("ComfyUI_03556_.png", "event_muel_fall_act1.png"),
    ("ComfyUI_03557_.png", "event_muel_fall_act2.png"),
    ("ComfyUI_03558_.png", "event_muel_fall_act3.png"),
    ("ComfyUI_03559_.png", "event_muel_debuff_seal.png"),
]


def import_one(src_name: str, dst_name: str) -> None:
    src = COMFY / src_name
    dst = OUT / dst_name
    if not src.is_file():
        print("MISSING", src)
        return
    raw = Image.open(src)
    out = fit_contain(raw, CANVAS, CANVAS)
    dst.parent.mkdir(parents=True, exist_ok=True)
    out.save(dst, optimize=True)
    print("OK", src_name, "->", dst_name, f"src={raw.size} fit={out.size}")


def main() -> None:
    print("COMFY:", COMFY)
    print("OUT:", OUT, f"canvas={CANVAS} (contain, no crop)")
    for src, dst in PAIRS:
        import_one(src, dst)
    print("done", len(PAIRS), "events")


if __name__ == "__main__":
    main()
