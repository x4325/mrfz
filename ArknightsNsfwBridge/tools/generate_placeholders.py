#!/usr/bin/env python3
"""Generate placeholder card/relic/event PNGs for arknsfw bridge."""
from pathlib import Path
from PIL import Image, ImageDraw

from image_util import apply_sts_card_mask

ROOT = Path(r"G:\SteamLibrary\steamapps\workshop\content\646570\ArknightsNsfwBridge\src\main\resources\arknsfwResources\images")

PALETTE = {
    "eyja": (200, 80, 60),
    "muel": (90, 180, 140),
    "scene": (180, 130, 190),
    "highmore": (90, 150, 190),
    "archetto": (210, 170, 80),
    "haruka": (200, 90, 110),
    "nymph": (150, 100, 190),
    "event": (60, 50, 70),
    "relic": (120, 90, 150),
}

ATTACK_CARDS = {
    "volcanic_embrace", "magma_thrust", "eruption_peak",
    "clone_service", "twin_pleasure", "fluid_splash",
}
POWER_CARDS = {
    "heat_resonance", "core_fever", "greenhouse_mist", "clone_haze",
}


def save_card(name, color):
    d = ROOT / "cards"
    d.mkdir(parents=True, exist_ok=True)
    out = d / f"card_{name}.png"
    if out.exists() and out.stat().st_size > 20000:
        return
    img = Image.new("RGBA", (250, 190), color + (255,))
    ImageDraw.Draw(img).text((10, 10), name, fill=(255, 255, 255, 255))
    kind = "attack" if name in ATTACK_CARDS else ("power" if name in POWER_CARDS else "skill")
    apply_sts_card_mask(img, kind).save(out)


def save_curse(filename, color):
    d = ROOT / "cards"
    d.mkdir(parents=True, exist_ok=True)
    out = d / filename
    if out.exists() and out.stat().st_size > 2000:
        return
    img = Image.new("RGBA", (250, 190), color + (255,))
    ImageDraw.Draw(img).text((20, 20), filename[:22], fill=(255, 255, 255, 255))
    apply_sts_card_mask(img, "curse").save(out)


def _char_color_from_name(name: str) -> tuple[int, int, int]:
    for key in ("scene", "highmore", "archetto", "haruka", "nymph", "eyja", "muel"):
        if key in name.lower():
            return PALETTE[key]
    return PALETTE["relic"]


def _card_kind(java_text: str) -> str:
    if "CardType.ATTACK" in java_text:
        return "attack"
    if "CardType.POWER" in java_text:
        return "power"
    return "skill"


def save_card_file(filename: str, color, kind: str = "skill"):
    """Write cards/{filename} if missing."""
    d = ROOT / "cards"
    d.mkdir(parents=True, exist_ok=True)
    out = d / filename
    if out.exists() and out.stat().st_size > 2000:
        return
    img = Image.new("RGBA", (250, 190), color + (255,))
    label = filename.replace("card_", "").replace(".png", "")[:24]
    ImageDraw.Draw(img).text((10, 10), label, fill=(255, 255, 255, 255))
    apply_sts_card_mask(img, kind).save(out)


def scan_java_assets():
    """Generate placeholders for any card/relic/event PNG referenced in Java but missing on disk."""
    import re

    java_root = ROOT.parent.parent.parent / "java" / "arknsfw"
    if not java_root.is_dir():
        return 0
    card_re = re.compile(r'"(card_[^"]+\.png)"')
    curse_re = re.compile(r'"(curse_[^"]+\.png)"')
    relic_re = re.compile(r'"(relic_[^"]+\.png)"')
    event_re = re.compile(r'events/event_[^"]+\.png')
    n = 0
    for java in java_root.rglob("*.java"):
        text = java.read_text(encoding="utf-8")
        for m in card_re.finditer(text):
            fn = m.group(1)
            save_card_file(fn, _char_color_from_name(fn), _card_kind(text))
            n += 1
        for m in curse_re.finditer(text):
            save_curse(m.group(1), (120, 40, 40))
            n += 1
        for m in relic_re.finditer(text):
            fn = m.group(1)
            save_relic(fn.replace(".png", ""), _char_color_from_name(fn))
            n += 1
        for m in event_re.finditer(text):
            name = m.group(0).replace("events/event_", "").replace(".png", "")
            save_event(name)
            n += 1
    return n


def save_relic(name, color):
    for sub in ("relics", "relics/outline"):
        d = ROOT / sub
        d.mkdir(parents=True, exist_ok=True)
        out = d / f"{name}.png"
        if out.exists():
            # 已有成品图标（含轮廓）一律不覆盖
            continue
        img = Image.new("RGBA", (128, 128), color + (255,))
        ImageDraw.Draw(img).ellipse((8, 8, 120, 120), fill=(240, 240, 240, 255))
        img.save(out)


def save_event(name):
    d = ROOT / "events"
    d.mkdir(parents=True, exist_ok=True)
    out = d / f"event_{name}.png"
    if out.exists() and out.stat().st_size > 20000:
        return  # keep imported ComfyUI art
    img = Image.new("RGBA", (1024, 1024), PALETTE["event"] + (255,))
    ImageDraw.Draw(img).text((40, 40), name, fill=(255, 220, 220, 255))
    img.save(out)


def save_potion(name, color):
    d = ROOT / "potions"
    d.mkdir(parents=True, exist_ok=True)
    out = d / name
    if out.exists() and out.stat().st_size > 8000:
        return
    img = Image.new("RGBA", (131, 181), color + (255,))
    ImageDraw.Draw(img).rounded_rectangle((10, 20, 121, 170), radius=12, fill=(240, 240, 250, 255))
    ImageDraw.Draw(img).text((16, 80), name[:12], fill=color + (255,))
    img.save(out)


PORTRAIT_W, PORTRAIT_H = 450, 630
CHAR_KEYS = ("eyja", "muel", "scene", "highmore", "archetto", "haruka", "nymph")
OVERLAY_ITEMS = (
    "cuffs", "belt", "garter", "vibe", "bodycrest", "collar", "leash", "belltag",
    "rope", "ringgag", "clothgag", "gag", "laceblindfold", "blindfold", "hearteyes",
)


def ensure_portrait_overlays():
    """Transparent 450x630 overlays so missing files never trigger purple fallback."""
    d = ROOT / "portraits" / "overlays"
    d.mkdir(parents=True, exist_ok=True)
    n = 0
    for key in CHAR_KEYS:
        for item in OVERLAY_ITEMS:
            out = d / f"{key}_{item}.png"
            if out.exists():
                continue
            Image.new("RGBA", (PORTRAIT_W, PORTRAIT_H), (0, 0, 0, 0)).save(out)
            n += 1
    return n


if __name__ == "__main__":
    eyja_cards = [
        "fever_caress", "volcanic_embrace", "heat_resonance",
        "ash_kiss", "magma_thrust", "lava_shield", "eruption_peak", "burn_mark", "core_fever",
    ]
    muel_cards = [
        "bubble_tease", "clone_service", "greenhouse_mist",
        "wet_slide", "fluid_splash", "moist_barrier", "twin_pleasure", "seed_spray", "clone_haze",
    ]
    eyja_relics = ["thermometer_charm", "wool_heat", "heat_sticker", "lava_plug", "ash_collar", "ember_seed", "eyja_pregnancy_mark"]
    muel_relics = ["rhine_gel", "duplicate_mirror", "bubble_wand", "clone_tag", "root_vine", "overflow_flask", "muel_pregnancy_mark"]
    events = [
        "eyja_normal_act1", "eyja_normal_act2", "eyja_normal_act3",
        "eyja_shame_act1", "eyja_shame_act2", "eyja_shame_act3",
        "eyja_fall_act1", "eyja_fall_act2", "eyja_fall_act3",
        "muel_normal_act1", "muel_normal_act2", "muel_normal_act3",
        "muel_shame_act1", "muel_shame_act2", "muel_shame_act3",
        "muel_fall_act1", "muel_fall_act2", "muel_fall_act3",
        "eyja_debuff_seal", "muel_debuff_seal",
    ]
    for n in eyja_cards + muel_cards:
        save_card(n, PALETTE["eyja"] if n in eyja_cards else PALETTE["muel"])
    eyja_curses = ["curse_womb_ember", "curse_fever_contract", "curse_ash_collar", "curse_magma_echo"]
    muel_curses = ["curse_clone_residue", "curse_flood_mark", "curse_bubble_mute", "curse_seed_womb"]
    for n in eyja_curses + muel_curses:
        save_curse(f"{n}.png", (120, 40, 40) if n in eyja_curses else (40, 100, 80))
    eyja_curse_relics = ["curse_volcano_brand", "curse_ash_furnace", "curse_breeding_altar"]
    muel_curse_relics = ["curse_clone_loop", "curse_rhine_filth", "curse_overflow_core"]
    for n in eyja_relics + muel_relics + eyja_curse_relics + muel_curse_relics:
        save_relic(n, PALETTE["relic"])
    for n in events:
        save_event(n)
    eyja_potions = [
        "eyja_cloud_warm.png", "eyja_pyro_aphro.png", "eyja_volcanic_nectar.png",
        "eyja_heat_linger.png", "eyja_ember_draught.png", "eyja_lava_bloom.png",
        "eyja_curse_ash_dreg.png", "eyja_curse_fever_sediment.png", "eyja_curse_ember_lock.png",
        "eyja_curse_collar_soot.png", "eyja_curse_contract_sediment.png", "eyja_curse_magma_echo.png",
    ]
    muel_potions = [
        "muel_bubble_serum.png", "muel_clone_drip.png", "muel_root_dew.png",
        "muel_mist_spray.png", "muel_twin_sap.png", "muel_greenhouse_nectar.png",
        "muel_curse_flood_waste.png", "muel_curse_seed_sludge.png", "muel_curse_mute_foam.png",
        "muel_curse_flood_mark.png", "muel_curse_bubble_mute.png", "muel_curse_echo_sludge.png",
    ]
    for n in eyja_potions:
        save_potion(n, PALETTE["eyja"])
    for n in muel_potions:
        save_potion(n, PALETTE["muel"])
    scanned = scan_java_assets() or 0
    overlays = ensure_portrait_overlays()
    print(f"placeholder images ok (scanned {scanned} java refs, {overlays} new overlays)")
