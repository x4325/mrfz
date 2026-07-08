#!/usr/bin/env python3
"""Generate placeholder grey PNGs for nymph (M1). User replaces card/event art later.
Image layout mirrors Eyjafjalla/Muelsyse: img/ (512,1024,char,charSelect,orbs,cards,relics)."""
from pathlib import Path
from PIL import Image, ImageDraw

ROOT = Path(r"G:\SteamLibrary\steamapps\workshop\content\646570\Nymph\src\main\resources\nymphResources\img")
GREY = (90, 90, 95)

def solid(rel, w, h, label=None):
    p = ROOT / rel
    p.parent.mkdir(parents=True, exist_ok=True)
    if p.exists() and p.stat().st_size > 2000:
        return
    img = Image.new("RGBA", (w, h), GREY + (255,))
    if label:
        ImageDraw.Draw(img).text((10, 10), label, fill=(220, 220, 220, 255))
    img.save(p)

def sync_small_orb():
    src = ROOT / "orbs/1.png"
    dst = ROOT / "char/small_orb.png"
    if not src.exists() or src.stat().st_size <= 2000:
        solid("char/small_orb.png", 64, 64, "orb")
        return
    img = Image.open(src).convert("RGBA")
    img.resize((64, 64), Image.Resampling.LANCZOS).save(dst)

if __name__ == "__main__":
    # 512 + 1024 card backgrounds + energy
    for bg in ("bg_attack", "bg_skill", "bg_power"):
        solid(f"512/{bg}.png", 512, 512, bg)
        solid(f"1024/{bg}.png", 1024, 1024, bg)
    solid("512/energy.png", 256, 256, "energy")
    solid("1024/energy.png", 256, 256, "energy")
    # char images (shoulder / corpse / orb)
    solid("char/char_shoulder.png", 1920, 1136, "妮芙")
    solid("char/char_shoulder2.png", 1920, 1136, "妮芙")
    solid("char/corpse.png", 512, 512, "妮芙")
    # charSelect
    solid("charSelect/button.png", 240, 240, "妮芙")
    solid("charSelect/portrait.png", 480, 600, "妮芙")
    # energy orb layers (orbs/1..6 like Eyja)
    for i in range(1, 7):
        solid(f"orbs/{i}.png", 128, 128, str(i))
    # card art (250x190)
    for c in ("card_attack", "card_skill", "card_power", "card_strike", "card_defend"):
        solid(f"cards/{c}.png", 250, 190, c)
    # relic + outline
    solid("relics/relic_starter.png", 128, 128, "starter")
    solid("relics/outline/relic_starter.png", 128, 128, "starter")
    sync_small_orb()
    print("nymph placeholder images ok")
