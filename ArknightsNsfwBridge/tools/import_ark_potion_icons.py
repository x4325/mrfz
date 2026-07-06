#!/usr/bin/env python3
"""Replace arknsfw programmatic potion placeholders with OGA pixel icons."""
from __future__ import annotations

import io
import shutil
import zipfile
from pathlib import Path

from PIL import Image

PROJ = Path(r"G:\SteamLibrary\steamapps\workshop\content\646570\ArknightsNsfwBridge")
CORE_POTION = Path(
    r"G:\SteamLibrary\steamapps\workshop\content\646570\LieseCoreMod\tools\asset_cache\potion-7soul1_20201212.zip"
)
P496 = PROJ / "tools" / "asset_cache" / "496_RPG_icons.zip"
POTION_ZIP = PROJ / "tools" / "asset_cache" / "potion-7soul1_20201212.zip"
OUT = PROJ / "src" / "main" / "resources" / "arknsfwResources" / "images" / "potions"

# (zip_key, inner_path) — zip_key is "496" or "potion"
POTION_MAP: dict[str, tuple[str, str]] = {
    "eyja_cloud_warm.png": ("496", "I_Bottle02.png"),
    "eyja_pyro_aphro.png": ("496", "S_Fire04.png"),
    "eyja_volcanic_nectar.png": ("496", "I_Ruby.png"),
    "eyja_heat_linger.png": ("496", "I_Coal.png"),
    "eyja_ember_draught.png": ("496", "I_Crystal03.png"),
    "eyja_lava_bloom.png": ("496", "S_Fire03.png"),
    "eyja_curse_ash_dreg.png": ("496", "S_Shadow10.png"),
    "eyja_curse_fever_sediment.png": ("496", "S_Poison04.png"),
    "eyja_curse_ember_lock.png": ("496", "S_Shadow12.png"),
    "eyja_curse_collar_soot.png": ("496", "Ac_Necklace05.png"),
    "eyja_curse_contract_sediment.png": ("496", "I_Scroll.png"),
    "eyja_curse_magma_echo.png": ("496", "S_Thunder06.png"),
    "muel_bubble_serum.png": ("potion", "32x32/potion_01_blue.png"),
    "muel_clone_drip.png": ("potion", "32x32/vial_02.png"),
    "muel_root_dew.png": ("potion", "32x32/medicine_02.png"),
    "muel_mist_spray.png": ("potion", "32x32/potion_02_green.png"),
    "muel_twin_sap.png": ("potion", "32x32/bottle_02.png"),
    "muel_greenhouse_nectar.png": ("potion", "32x32/potion_01_green.png"),
    "muel_curse_flood_waste.png": ("496", "S_Water06.png"),
    "muel_curse_seed_sludge.png": ("496", "S_Poison05.png"),
    "muel_curse_mute_foam.png": ("496", "S_Water04.png"),
    "muel_curse_flood_mark.png": ("496", "I_Ink.png"),
    "muel_curse_bubble_mute.png": ("496", "S_Magic07.png"),
    "muel_curse_echo_sludge.png": ("496", "S_Shadow08.png"),
    "eyja_ash_shame_mist.png": ("496", "S_Shadow08.png"),
    "muel_bubble_shame_mist.png": ("potion", "32x32/potion_02_pink.png"),
}


def load496(zf: zipfile.ZipFile, name: str) -> Image.Image:
    return Image.open(io.BytesIO(zf.read(name))).convert("RGBA")


def load_potion(zf: zipfile.ZipFile, inner: str) -> Image.Image:
    return Image.open(io.BytesIO(zf.read(inner))).convert("RGBA")


def scale_potion(src: Image.Image) -> Image.Image:
    w, h = src.size
    scale = min(96 / w, 140 / h)
    nw = max(1, int(round(w * scale)))
    nh = max(1, int(round(h * scale)))
    up = src.resize((nw, nh), Image.NEAREST)
    canvas = Image.new("RGBA", (131, 181), (0, 0, 0, 0))
    canvas.paste(up, ((131 - nw) // 2, (181 - nh) // 2), up)
    return canvas


def main() -> None:
    POTION_ZIP.parent.mkdir(parents=True, exist_ok=True)
    if not POTION_ZIP.is_file() and CORE_POTION.is_file():
        shutil.copy2(CORE_POTION, POTION_ZIP)
    if not P496.is_file():
        core496 = Path(
            r"G:\SteamLibrary\steamapps\workshop\content\646570\LieseCoreMod\tools\asset_cache\496_RPG_icons.zip"
        )
        if core496.is_file():
            shutil.copy2(core496, P496)
    if not P496.is_file():
        raise SystemExit(f"missing {P496}")
    OUT.mkdir(parents=True, exist_ok=True)
    with zipfile.ZipFile(P496) as z496:
        pz = zipfile.ZipFile(POTION_ZIP) if POTION_ZIP.is_file() else None
        try:
            for out_name, (kind, src) in POTION_MAP.items():
                if kind == "496":
                    if src not in z496.namelist():
                        print(f"SKIP {out_name}: no {src}")
                        continue
                    img = scale_potion(load496(z496, src))
                else:
                    if pz is None or src not in pz.namelist():
                        print(f"SKIP {out_name}: no {src}")
                        continue
                    img = scale_potion(load_potion(pz, src))
                out_path = OUT / out_name
                img.save(out_path)
                print(f"potion {out_name}")
        finally:
            if pz is not None:
                pz.close()
    print(f"imported {len(POTION_MAP)} potion icons")


if __name__ == "__main__":
    main()
