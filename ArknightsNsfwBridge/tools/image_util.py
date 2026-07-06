"""Shared image import helpers: scale to fit, never crop; STS card window mask."""
from __future__ import annotations

from functools import lru_cache
from pathlib import Path

from PIL import Image

MASK_DIR = Path(__file__).resolve().parent / "_sts_masks"
LIESE_MASK_DIR = Path(
    r"G:\SteamLibrary\steamapps\workshop\content\646570\LieseDragonMod\tools\_basemod_ref\img\test\test\cards"
)
CARD_W, CARD_H = 250, 190


def fit_contain(img: Image.Image, canvas_w: int, canvas_h: int) -> Image.Image:
    """Uniform scale down/up so entire image fits inside canvas; centered on transparent bg."""
    img = img.convert("RGBA")
    w, h = img.size
    if w == 0 or h == 0:
        return img
    scale = min(canvas_w / w, canvas_h / h)
    if scale > 1.0:
        scale = 1.0
    new_w = max(1, int(round(w * scale)))
    new_h = max(1, int(round(h * scale)))
    resized = img.resize((new_w, new_h), Image.Resampling.LANCZOS)
    canvas = Image.new("RGBA", (canvas_w, canvas_h), (0, 0, 0, 0))
    ox = (canvas_w - new_w) // 2
    oy = (canvas_h - new_h) // 2
    canvas.paste(resized, (ox, oy), resized)
    return canvas


@lru_cache(maxsize=4)
def _load_mask_file(kind: str, width: int, height: int) -> Image.Image:
    """Return STS portrait-window alpha mask resized to width x height."""
    name = "strike_purple.png" if kind == "attack" else "defend_purple.png"
    for base in (MASK_DIR, LIESE_MASK_DIR):
        path = base / name
        if path.is_file():
            mask = Image.open(path).convert("RGBA")
            if mask.size != (width, height):
                mask = mask.resize((width, height), Image.Resampling.LANCZOS)
            return mask
    raise FileNotFoundError(
        f"Missing STS card mask {name}; expected under {MASK_DIR} or {LIESE_MASK_DIR}"
    )


def apply_sts_card_mask(img: Image.Image, kind: str = "skill") -> Image.Image:
    """Clip card art to the vanilla STS portrait window (rounded rect + transparent corners)."""
    if kind not in ("attack", "skill", "power", "curse"):
        kind = "skill"
    mask_kind = "attack" if kind == "attack" else "skill"
    w, h = img.size
    mask = _load_mask_file(mask_kind, w, h)
    out = img.convert("RGBA").copy()
    out.putalpha(mask.split()[3])
    return out


def import_card_art(
    raw: Image.Image,
    *,
    kind: str = "skill",
    canvas_w: int = CARD_W,
    canvas_h: int = CARD_H,
) -> Image.Image:
    """Fit ComfyUI source into 250x190 and apply STS window mask."""
    fitted = fit_contain(raw, canvas_w, canvas_h)
    return apply_sts_card_mask(fitted, kind)
