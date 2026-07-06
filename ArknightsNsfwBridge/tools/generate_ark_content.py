#!/usr/bin/env python3
"""Generate route events + placeholder art list for arknsfw bridge."""
from __future__ import annotations

import json
import textwrap
from pathlib import Path

ROOT = Path(r"G:\SteamLibrary\steamapps\workshop\content\646570\ArknightsNsfwBridge")
JAVA_EVENTS = ROOT / "src/main/java/arknsfw/events"
RES = ROOT / "src/main/resources/arknsfwResources/localization/zhs/EventStrings.json"
TAGS = ROOT / "tools/event_tags.json"

# char prefix, route, act -> (class suffix, image key, tags)
ROUTES = ("Normal", "Shame", "Fall")
ACTS = ("Act1", "Act2", "Act3")
ACT_DUNGEON = {"Act1": "Exordium", "Act2": "TheCity", "Act3": "TheBeyond"}

EVENT_META = {
    "eyja": {
        "Normal": [
            ("VolcanoRest", "eyja_normal_act1", "eyjafjalla, nsfw, normal, volcano spring, modest towel, sitting, calm, blush, solo, 1girl, horns, red hair, outdoor onsen, steam, soft sunset"),
            ("FieldCamp", "eyja_normal_act2", "eyjafjalla, nsfw, normal, field camp, night, blanket, stargazing, close sitting, shy smile, solo, 1girl, horns, red hair, warm campfire"),
            ("QuietEmbrace", "eyja_normal_act3", "eyjafjalla, nsfw, normal, bedroom, gentle hug, fully clothed, tender, tears of relief, solo, 1girl, horns, red hair, moonlight"),
        ],
        "Shame": [
            ("PublicSample", "eyja_shame_act1", "eyjafjalla, nsfw, shame, public lab, crowd silhouette, exposed shoulders, measuring, blush, tears, solo focus, 1girl, horns, red hair, humiliation"),
            ("OpenSeminar", "eyja_shame_act2", "eyjafjalla, nsfw, shame, seminar stage, projector, disheveled lab coat, audience, covering chest, embarrassed, 1girl, horns, red hair"),
            ("AuditHall", "eyja_shame_act3", "eyjafjalla, nsfw, shame, tribunal hall, kneeling, torn clothes, head down, public scorn, solo, 1girl, horns, red hair, dramatic lighting"),
        ],
        "Fall": [
            ("HeatNeed", "eyja_fall_act1", "eyjafjalla, nsfw, fall, corrupted, fever, messy hair, addicted expression, heated room, suggestive pose, solo, 1girl, horns, red hair, red glow"),
            ("BreedingRite", "eyja_fall_act2", "eyjafjalla, nsfw, fall, ritual circle, lava altar, submissive, collar, pregnant suggest, solo, 1girl, horns, red hair, dark fantasy"),
            ("AshWomb", "eyja_fall_act3", "eyjafjalla, nsfw, fall, corrupted womb glow, kneeling altar, ecstasy, broken mind, solo, 1girl, horns, red hair, intense red black theme"),
        ],
    },
    "muel": {
        "Normal": [
            ("BubbleBreak", "muel_normal_act1", "muelsyse, nsfw, normal, bubble bath, playful, smiling, foam, green hair, elf ears, solo, 1girl, relaxed, bathroom"),
            ("GardenTea", "muel_normal_act2", "muelsyse, nsfw, normal, greenhouse tea party, sundress, sitting, gentle, green hair, elf ears, solo, 1girl, flowers"),
            ("StarPool", "muel_normal_act3", "muelsyse, nsfw, normal, rooftop pool, night sky, holding hands implied offscreen, shy, green hair, elf ears, solo, 1girl"),
        ],
        "Shame": [
            ("LabAudit", "muel_shame_act1", "muelsyse, nsfw, shame, laboratory inspection, clipboard, spilled reagent, wet clothes, embarrassed, green hair, elf ears, 1girl"),
            ("LiveDemo", "muel_shame_act2", "muelsyse, nsfw, shame, live demonstration, audience, transparent coat, covering, blush, green hair, elf ears, stage"),
            ("Broadcast", "muel_shame_act3", "muelsyse, nsfw, shame, monitor broadcast, kneeling, messy, public exposure, green hair, elf ears, 1girl, cyber screen glow"),
        ],
        "Fall": [
            ("CloneNeed", "muel_fall_act1", "muelsyse, nsfw, fall, clone, pleasure, laboratory, duplicated self, green hair, elf ears, suggestive, 2girls same person"),
            ("SeedGreenhouse", "muel_fall_act2", "muelsyse, nsfw, fall, greenhouse, vines, breeding, submissive, swollen belly suggest, green hair, elf ears, 1girl"),
            ("FluidOverflow", "muel_fall_act3", "muelsyse, nsfw, fall, flooded lab, ecstasy, corrupted smile, soaked, green hair, elf ears, 1girl, excess fluid theme suggest"),
        ],
    },
}

EVENT_STRINGS = {}
EVENT_TAGS = {"events": {}}


def java_class(char: str, route: str, act: str, suffix: str) -> str:
    pkg = f"arknsfw.events.{char}"
    cls = f"{char.capitalize()}{route}{act}Event" if False else f"{'Eyja' if char=='eyja' else 'Muel'}{route}{suffix}Event"
    # suffix like VolcanoRest -> EyjaNormalVolcanoRestEvent
    cls = f"{'Eyja' if char=='eyja' else 'Muel'}{route}{suffix}Event"
    route_enum = route.upper()
    img_const = f"{'EYJA' if char=='eyja' else 'MUEL'}_{route.upper()}_{act.upper()}"
    id_name = cls.replace("Event", "")
    event_id = f"arknsfw:{id_name}"

    # Outcome templates per route
    if route == "Normal":
        outcomes = [
            "heal(15); excite(15); fert(6,0,false); lock();",
            "excite(-8); fert(0,0,false); lock();",
            "gold(30); excite(8); lock();",
        ]
    elif route == "Shame":
        outcomes = [
            "excite(25); fert(8,0,false); curse(); lock();",
            "hp(-8); excite(35); lock();",
            "excite(20); fert(12,0,true); lock();",
        ]
    else:
        outcomes = [
            "excite(40); fert(10,8,true); lock();",
            "excite(55); fert(0,15,true); lock();",
            "hp(-5); excite(65); fert(15,20,true); lock();",
        ]

    body = f"""package {pkg};

import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import liesecore.helpers.CurseHelper;
import liesecore.helpers.NsfwRunStats;
import arknsfw.ArkNsfwMod;
import arknsfw.events.AbstractArkRouteEvent;
import arknsfw.helpers.ArkEventImages;
import arknsfw.helpers.ArkRunProgress;

public class {cls} extends AbstractArkRouteEvent {{
    public static final String ID = ArkNsfwMod.makeID("{id_name}");

    public {cls}() {{
        super(ID, ArkEventImages.path(ArkEventImages.{img_const}), ArkRunProgress.Route.{route_enum}, 3);
    }}

    @Override
    protected void onFirstChoice(int buttonUsed) {{
        lockThisRoute();
"""
    for i, code in enumerate(outcomes):
        body += f"        if (buttonUsed == {i}) {{\n"
        for stmt in code.split(";"):
            stmt = stmt.strip()
            if not stmt or stmt == "lock()":
                continue
            if stmt.startswith("heal"):
                pct = stmt[5:-1]
                body += f"            AbstractDungeon.player.heal(Math.max(1, AbstractDungeon.player.maxHealth * {pct} / 100));\n"  # pct e.g. 15
            elif stmt.startswith("excite"):
                n = stmt[7:-1]
                body += f"            NsfwRunStats.addExcitement({n});\n"
            elif stmt.startswith("fert"):
                args = stmt[5:-1].split(",")
                body += f"            NsfwRunStats.addFertility({args[0]}, {args[1]}, {'true' if args[2].strip()=='true' else 'false'});\n"
            elif stmt.startswith("gold"):
                n = stmt[5:-1]
                body += f"            AbstractDungeon.player.gainGold({n});\n"
            elif stmt.startswith("hp"):
                n = stmt[3:-1]
                body += f"            AbstractDungeon.player.damage(new com.megacrit.cardcrawl.cards.DamageInfo(null, {n.lstrip('-')}));\n"
            elif stmt == "curse()":
                body += "            CurseHelper.addRandomCurseToDeck();\n"
        body += f"            showResult({i + 1});\n            return;\n        }}\n"
    body += "    }\n}\n"
    return cls, body, event_id, img_const


def main():
    event_strings = {}
    image_consts = []
    setup_lines = []
    register_lines = []

    for char in ("eyja", "muel"):
        pkg_dir = JAVA_EVENTS / char
        pkg_dir.mkdir(parents=True, exist_ok=True)
        for route in ROUTES:
            for act_idx, act in enumerate(ACTS):
                suffix, img_key, tags = EVENT_META[char][route][act_idx]
                cls, src, eid, img_const = java_class(char, route, act, suffix)
                (pkg_dir / f"{cls}.java").write_text(src, encoding="utf-8")
                image_consts.append((img_const, f"events/event_{img_key}.png"))
                event_strings[eid] = {
                    "NAME": _title(char, route, act, suffix),
                    "DESCRIPTIONS": [
                        _desc_open(char, route, act),
                        _desc_res(char, route, 0),
                        _desc_res(char, route, 1),
                        _desc_res(char, route, 2),
                    ],
                    "OPTIONS": _opts(route),
                }
                EVENT_TAGS["events"][img_key] = {
                    "event_id": eid,
                    "image_file": f"event_{img_key}.png",
                    "route": route.lower(),
                    "act": act.lower(),
                    "character": char,
                    "comfyui_tags": tags,
                }
                register_lines.append(
                    f'        BaseMod.addEvent({cls}.ID, {cls}.class, {ACT_DUNGEON[act]}.ID);'
                )
                setup_lines.append(f"            {cls}.ID,")

    # ArkEventImages constants
    img_java = ROOT / "src/main/java/arknsfw/helpers/ArkEventImages.java"
    lines = ["package arknsfw.helpers;\n", "import arknsfw.ArkNsfwMod;\n", "public final class ArkEventImages {\n"]
    for const, path in image_consts:
        lines.append(f'    public static final String {const} = "{path}";\n')
    lines.append("\n    private ArkEventImages() {}\n\n    public static String path(String file) {\n        return ArkNsfwMod.makeImagePath(file);\n    }\n}\n")
    img_java.write_text("".join(lines), encoding="utf-8")

    RES.write_text(json.dumps(event_strings, ensure_ascii=False, indent=2) + "\n", encoding="utf-8")
    TAGS.write_text(json.dumps(EVENT_TAGS, ensure_ascii=False, indent=2) + "\n", encoding="utf-8")

    print("Generated", len(event_strings), "events")
    print("Register lines:", len(register_lines))


def _title(char, route, act, suffix):
    names = {
        "eyja": "艾雅法拉", "muel": "缪尔赛思",
        "Normal": "温存", "Shame": "羞耻", "Fall": "堕落",
        "Act1": "一", "Act2": "二", "Act3": "三",
    }
    return f"{names[char]}·{names[route]}线·第{names[act]}幕"


def _desc_open(char, route, act):
    return f"{'火山' if char=='eyja' else '莱茵'}侧的{'私密' if route=='Normal' else '公开' if route=='Shame' else '失控'}遭遇（{'一' if act=='Act1' else '二' if act=='Act2' else '三'}层）。"


def _desc_res(char, route, idx):
    return ["你选择了较温和的处理。", "你咬牙承受。", "你彻底放任。"][idx]


def _opts(route):
    if route == "Normal":
        return ["温柔接受", "保持距离", "换取安抚", "离开"]
    if route == "Shame":
        return ["忍耐曝光", "试图遮掩", "破罐破摔", "离开"]
    return ["渴求更多", "献上身体", "完全堕落", "离开"]


if __name__ == "__main__":
    main()
