#!/usr/bin/env python3
"""Import Eyja + Muelsyse card art from ComfyUI.

Eyja  03589-03597: 9 NSFW cards, 03598-03601: 4 curse cards
Muel  03602-03610: 9 NSFW cards, 03611-03614: 4 curse cards
Source 1000×760 → fit inside 250×190, then clip with STS card-window mask."""
from __future__ import annotations

import sys
from pathlib import Path

from PIL import Image

from image_util import CARD_H, CARD_W, import_card_art

COMFY = Path(sys.argv[1]) if len(sys.argv) > 1 else Path(r"E:\ComfyUI-aki-v3\ComfyUI\output")
OUT = Path(sys.argv[2]) if len(sys.argv) > 2 else Path(
    r"G:\SteamLibrary\steamapps\workshop\content\646570\ArknightsNsfwBridge\src\main\resources\arknsfwResources\images\cards"
)

# dst filename -> STS mask kind (attack / skill / power / curse)
CARD_KIND = {
    "card_fever_caress.png": "skill",
    "card_volcanic_embrace.png": "attack",
    "card_heat_resonance.png": "power",
    "card_ash_kiss.png": "skill",
    "card_magma_thrust.png": "attack",
    "card_lava_shield.png": "skill",
    "card_eruption_peak.png": "attack",
    "card_burn_mark.png": "skill",
    "card_core_fever.png": "power",
    "card_bubble_tease.png": "skill",
    "card_clone_service.png": "attack",
    "card_greenhouse_mist.png": "power",
    "card_wet_slide.png": "skill",
    "card_fluid_splash.png": "attack",
    "card_moist_barrier.png": "skill",
    "card_twin_pleasure.png": "attack",
    "card_seed_spray.png": "skill",
    "card_clone_haze.png": "power",
}

# Order = ArkNsfwMod.receiveEditCards
PAIRS = [
    ("ComfyUI_03589_.png", "card_fever_caress.png"),
    ("ComfyUI_03590_.png", "card_volcanic_embrace.png"),
    ("ComfyUI_03591_.png", "card_heat_resonance.png"),
    ("ComfyUI_03592_.png", "card_ash_kiss.png"),
    ("ComfyUI_03593_.png", "card_magma_thrust.png"),
    ("ComfyUI_03594_.png", "card_lava_shield.png"),
    ("ComfyUI_03595_.png", "card_eruption_peak.png"),
    ("ComfyUI_03596_.png", "card_burn_mark.png"),
    ("ComfyUI_03597_.png", "card_core_fever.png"),
    ("ComfyUI_03598_.png", "curse_womb_ember.png"),
    ("ComfyUI_03599_.png", "curse_fever_contract.png"),
    ("ComfyUI_03600_.png", "curse_ash_collar.png"),
    ("ComfyUI_03601_.png", "curse_magma_echo.png"),
    ("ComfyUI_03602_.png", "card_bubble_tease.png"),
    ("ComfyUI_03603_.png", "card_clone_service.png"),
    ("ComfyUI_03604_.png", "card_greenhouse_mist.png"),
    ("ComfyUI_03605_.png", "card_wet_slide.png"),
    ("ComfyUI_03606_.png", "card_fluid_splash.png"),
    ("ComfyUI_03607_.png", "card_moist_barrier.png"),
    ("ComfyUI_03608_.png", "card_twin_pleasure.png"),
    ("ComfyUI_03609_.png", "card_seed_spray.png"),
    ("ComfyUI_03610_.png", "card_clone_haze.png"),
    ("ComfyUI_03611_.png", "curse_clone_residue.png"),
    ("ComfyUI_03612_.png", "curse_flood_mark.png"),
    ("ComfyUI_03613_.png", "curse_bubble_mute.png"),
    ("ComfyUI_03614_.png", "curse_seed_womb.png"),
]


def mask_kind_for(dst_name: str) -> str:
    if dst_name.startswith("curse_"):
        return "curse"
    return CARD_KIND.get(dst_name, "skill")


def import_one(src_name: str, dst_name: str) -> None:
    src = COMFY / src_name
    dst = OUT / dst_name
    if not src.is_file():
        print("MISSING", src)
        return
    raw = Image.open(src)
    kind = mask_kind_for(dst_name)
    out = import_card_art(raw, kind=kind, canvas_w=CARD_W, canvas_h=CARD_H)
    dst.parent.mkdir(parents=True, exist_ok=True)
    out.save(dst, optimize=True)
    print("OK", src_name, "->", dst_name, f"src={raw.size} mask={kind}")


def main() -> None:
    print("COMFY:", COMFY)
    print("OUT:", OUT, f"canvas={CARD_W}x{CARD_H} (contain + STS window mask)")
    for src, dst in PAIRS:
        import_one(src, dst)
    print("done", len(PAIRS), "cards")


if __name__ == "__main__":
    main()
