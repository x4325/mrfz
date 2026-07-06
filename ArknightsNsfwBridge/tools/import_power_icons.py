#!/usr/bin/env python3
"""Crop CC0 RPG icons (OpenGameArt #496) into 32x32 STS power HUD icons."""
from __future__ import annotations

import io
import zipfile
from pathlib import Path

from PIL import Image

PROJ = Path(r"G:\SteamLibrary\steamapps\workshop\content\646570\ArknightsNsfwBridge")
CACHE = PROJ / "tools" / "asset_cache" / "496_RPG_icons.zip"
OUT = PROJ / "src" / "main" / "resources" / "arknsfwResources" / "images" / "powers"

# CC0 / public domain — Henrique Lazarini, OpenGameArt.org
MAPPING = {
    "ash_shame": "Ac_Necklace05.png",
    "volcanic_flush": "S_Fire04.png",
    "core_strain": "S_Thunder06.png",
    "bubble_gag": "S_Water06.png",
    "leak": "S_Poison04.png",
    "clone_echo": "S_Magic07.png",
}


def crop32(raw: Image.Image) -> Image.Image:
    im = raw.convert("RGBA")
    w, h = im.size
    size = min(w, h, 32)
    left = max(0, (w - size) // 2)
    top = max(0, (h - size) // 2)
    im = im.crop((left, top, left + size, top + size))
    if im.size != (32, 32):
        im = im.resize((32, 32), Image.NEAREST)
    return im


def main() -> None:
    if not CACHE.is_file():
        raise SystemExit(f"missing icon pack: {CACHE}\nrun: curl -L -o {CACHE} "
                         "https://opengameart.org/sites/default/files/496_RPG_icons.zip")
    OUT.mkdir(parents=True, exist_ok=True)
    with zipfile.ZipFile(CACHE) as zf:
        for out_name, src_name in MAPPING.items():
            out_path = OUT / f"{out_name}.png"
            try:
                data = zf.read(src_name)
            except KeyError:
                print(f"SKIP {out_name}: missing {src_name}")
                continue
            icon = crop32(Image.open(io.BytesIO(data)))
            icon.save(out_path)
            print(f"OK {src_name} -> {out_path.name}")
    print("power icons done")


if __name__ == "__main__":
    main()
