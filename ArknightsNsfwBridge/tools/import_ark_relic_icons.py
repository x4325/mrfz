#!/usr/bin/env python3
"""Replace arknsfw programmatic relic placeholders with OGA #496 pixel icons."""
from __future__ import annotations

import io
import shutil
import zipfile
from pathlib import Path

from PIL import Image

PROJ = Path(r"G:\SteamLibrary\steamapps\workshop\content\646570\ArknightsNsfwBridge")
CORE_CACHE = Path(r"G:\SteamLibrary\steamapps\workshop\content\646570\LieseCoreMod\tools\asset_cache\496_RPG_icons.zip")
CACHE = PROJ / "tools" / "asset_cache" / "496_RPG_icons.zip"
OUT = PROJ / "src" / "main" / "resources" / "arknsfwResources" / "images" / "relics"

RELIC_MAP: dict[str, str] = {
    "thermometer_charm.png": "I_Crystal03.png",
    "wool_heat.png": "I_Fabric.png",
    "heat_sticker.png": "S_Fire04.png",
    "lava_plug.png": "I_Coal.png",
    "ash_collar.png": "Ac_Necklace05.png",
    "ember_seed.png": "I_Ruby.png",
    "rhine_gel.png": "I_Bottle02.png",
    "duplicate_mirror.png": "I_Mirror.png",
    "bubble_wand.png": "I_Torch01.png",
    "clone_tag.png": "S_Magic07.png",
    "root_vine.png": "I_FoxTail.png",
    "overflow_flask.png": "I_Bottle04.png",
    "curse_volcano_brand.png": "S_Fire03.png",
    "curse_ash_furnace.png": "S_Fire02.png",
    "curse_breeding_altar.png": "S_Holy07.png",
    "curse_clone_loop.png": "S_Magic01.png",
    "curse_rhine_filth.png": "S_Poison04.png",
    "curse_overflow_core.png": "S_Thunder06.png",
    "eyja_pregnancy_mark.png": "Ac_Medal02.png",
    "muel_pregnancy_mark.png": "Ac_Medal03.png",
}


def load_src(zf: zipfile.ZipFile, name: str) -> Image.Image:
    return Image.open(io.BytesIO(zf.read(name))).convert("RGBA")


def scale_center(src: Image.Image, size: int, max_inner: int) -> Image.Image:
    w, h = src.size
    scale = min(max_inner / w, max_inner / h)
    nw = max(1, int(round(w * scale)))
    nh = max(1, int(round(h * scale)))
    up = src.resize((nw, nh), Image.NEAREST)
    canvas = Image.new("RGBA", (size, size), (0, 0, 0, 0))
    canvas.paste(up, ((size - nw) // 2, (size - nh) // 2), up)
    return canvas


def make_outline(main: Image.Image) -> Image.Image:
    alpha = main.split()[3]
    out = Image.new("RGBA", main.size, (0, 0, 0, 0))
    sil = Image.new("RGBA", main.size, (42, 26, 58, 255))
    sil.putalpha(alpha)
    return sil


def main() -> None:
    CACHE.parent.mkdir(parents=True, exist_ok=True)
    if not CACHE.is_file() and CORE_CACHE.is_file():
        shutil.copy2(CORE_CACHE, CACHE)
    if not CACHE.is_file():
        raise SystemExit(f"missing {CACHE}")
    with zipfile.ZipFile(CACHE) as zf:
        names = set(zf.namelist())
        for out_name, src_name in RELIC_MAP.items():
            if src_name not in names:
                print(f"SKIP {out_name}: no {src_name}")
                continue
            main = scale_center(load_src(zf, src_name), 128, 96)
            outline = make_outline(main)
            main_path = OUT / out_name
            outline_path = OUT / "outline" / out_name
            main_path.parent.mkdir(parents=True, exist_ok=True)
            outline_path.parent.mkdir(parents=True, exist_ok=True)
            main.save(main_path)
            outline.save(outline_path)
            print(f"relic {out_name} <- {src_name}")
    print(f"imported {len(RELIC_MAP)} ark relic icons")


if __name__ == "__main__":
    main()
