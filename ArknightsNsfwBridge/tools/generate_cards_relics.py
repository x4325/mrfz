#!/usr/bin/env python3
"""Generate extra arknsfw cards + relics."""
from pathlib import Path
import json

ROOT = Path(r"G:\SteamLibrary\steamapps\workshop\content\646570\ArknightsNsfwBridge")
CARD_DIR = ROOT / "src/main/java/arknsfw/cards"
RELIC_DIR = ROOT / "src/main/java/arknsfw/relics"
CARD_JSON = ROOT / "src/main/resources/arknsfwResources/localization/zhs/CardStrings.json"
RELIC_JSON = ROOT / "src/main/resources/arknsfwResources/localization/zhs/RelicStrings.json"


def write_card(char, name, cost, ctype, rarity, target, art, fields, use_body, upgrade, cname, desc, udesc):
    pkg = f"arknsfw.cards.{char}"
    color = "ColorEnum.Eyjafjalla_COLOR" if char == "eyja" else "Muelsyse.patches.ColorEnum.Muelsyse_COLOR"
    imports = [
        "import com.megacrit.cardcrawl.actions.AbstractGameAction;",
        "import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;",
        "import com.megacrit.cardcrawl.actions.common.DamageAction;",
        "import com.megacrit.cardcrawl.actions.common.GainBlockAction;",
        "import com.megacrit.cardcrawl.cards.AbstractCard;",
        "import com.megacrit.cardcrawl.cards.DamageInfo;",
        "import com.megacrit.cardcrawl.characters.AbstractPlayer;",
        "import com.megacrit.cardcrawl.dungeons.AbstractDungeon;",
        "import com.megacrit.cardcrawl.monsters.AbstractMonster;",
        "import com.megacrit.cardcrawl.powers.VulnerablePower;",
        "import com.megacrit.cardcrawl.powers.WeakPower;",
        "import liesecore.helpers.NsfwRunStats;",
        "import arknsfw.ArkNsfwMod;",
        "import arknsfw.cards.AbstractArkNsfwCard;",
    ]
    if char == "eyja":
        imports.append("import Eyjafjalla.modcore.ColorEnum;")
        if name == "CoreFever":
            imports += ["import arknsfw.powers.eyja.GeothermalPower;", "import arknsfw.powers.eyja.CoreFeverPower;"]
    else:
        imports.append("import Muelsyse.patches.ColorEnum;")
        if name == "MoistBarrier":
            imports.append("import arknsfw.powers.muel.HydrationPower;")
        if name == "CloneHaze":
            imports.append("import arknsfw.powers.muel.CloneHazePower;")

    src = f"""package {pkg};

{chr(10).join(imports)}

public class {name} extends AbstractArkNsfwCard {{
    public static final String ID = ArkNsfwMod.makeID("{name}");

    public {name}() {{
        super(ID, {cost}, CardType.{ctype}, {color}, CardRarity.{rarity}, CardTarget.{target}, "{art}");
        {fields}
    }}

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {{
        {use_body}
    }}

    @Override
    public void upgrade() {{
        if (!upgraded) {{
            upgradeName();
            {upgrade}
        }}
    }}

    @Override
    public AbstractCard makeCopy() {{
        return new {name}();
    }}
}}
"""
    (CARD_DIR / char / f"{name}.java").write_text(src, encoding="utf-8")
    entry = {"NAME": cname, "DESCRIPTION": desc}
    if udesc:
        entry["UPGRADE_DESCRIPTION"] = udesc
    return f"arknsfw:{name}", entry


def write_relic(char, cls, img, tier, sound, rname, rdesc, body, extra_imports=None):
    pkg = f"arknsfw.relics.{char}"
    imports = [
        "import com.megacrit.cardcrawl.relics.AbstractRelic;",
        "import liesecore.helpers.NsfwRunStats;",
        "import arknsfw.ArkNsfwMod;",
        "import arknsfw.relics.AbstractArkNsfwRelic;",
    ]
    if extra_imports:
        imports.extend(extra_imports)
    src = f"""package {pkg};

{chr(10).join(imports)}

public class {cls} extends AbstractArkNsfwRelic {{
    public static final String ID = ArkNsfwMod.makeID("{cls}");

    public {cls}() {{
        super(ID, "{img}", RelicTier.{tier}, LandingSound.{sound});
    }}

    {body}

    @Override
    public String getUpdatedDescription() {{
        return DESCRIPTIONS[0];
    }}

    @Override
    public AbstractRelic makeCopy() {{
        return new {cls}();
    }}
}}
"""
    (RELIC_DIR / char / f"{cls}.java").write_text(src, encoding="utf-8")
    return f"arknsfw:{cls}", {"NAME": rname, "FLAVOR": rname + "。", "DESCRIPTIONS": [rdesc]}


def main():
    cards_json = json.loads(CARD_JSON.read_text(encoding="utf-8"))
    relics_json = json.loads(RELIC_JSON.read_text(encoding="utf-8"))

    cards = [
        write_card("eyja", "AshKiss", 0, "SKILL", "UNCOMMON", "ENEMY", "card_ash_kiss.png",
                   "baseMagicNumber = magicNumber = 1;",
                   "addToBot(new ApplyPowerAction(m, p, new WeakPower(m, magicNumber, false), magicNumber));\n        NsfwRunStats.addExcitement(8);",
                   "upgradeMagicNumber(1); rawDescription = cardStrings.UPGRADE_DESCRIPTION; initializeDescription();",
                   "升温之吻", "给予 !M! 层 虚弱。兴奋 +8。", "给予 !M! 层 虚弱。兴奋 +12。"),
        write_card("eyja", "MagmaThrust", 1, "ATTACK", "COMMON", "ENEMY", "card_magma_thrust.png",
                   "baseDamage = 8;",
                   "addToBot(new DamageAction(m, new DamageInfo(p, damage, DamageInfo.DamageType.NORMAL), AbstractGameAction.AttackEffect.FIRE));\n        NsfwRunStats.addExcitement(10);\n        NsfwRunStats.addFertility(4, 0, false);",
                   "upgradeDamage(4);",
                   "熔流突刺", "造成 !D! 点伤害。兴奋 +10。受孕度 +4。", None),
        write_card("eyja", "LavaShield", 1, "SKILL", "COMMON", "SELF", "card_lava_shield.png",
                   "baseBlock = 10;",
                   "addToBot(new GainBlockAction(p, block));\n        NsfwRunStats.addExcitement(6);",
                   "upgradeBlock(4);",
                   "熔岩护体", "获得 !B! 点格挡。兴奋 +6。", None),
        write_card("eyja", "EruptionPeak", 2, "ATTACK", "UNCOMMON", "ALL_ENEMY", "card_eruption_peak.png",
                   "baseDamage = 9;\n        isMultiDamage = true;",
                   "for (AbstractMonster mo : AbstractDungeon.getMonsters().monsters) {\n            if (!mo.isDeadOrEscaped()) {\n                addToBot(new DamageAction(mo, new DamageInfo(p, damage, DamageInfo.DamageType.NORMAL), AbstractGameAction.AttackEffect.FIRE));\n            }\n        }\n        NsfwRunStats.addExcitement(15);\n        NsfwRunStats.addFertility(6, 4, true);",
                   "upgradeDamage(3);",
                   "喷发高潮", "对所有敌人造成 !D! 点伤害。兴奋 +15。中出：受孕 +6 / 妊娠 +4。", None),
        write_card("eyja", "BurnMark", 1, "SKILL", "UNCOMMON", "ENEMY", "card_burn_mark.png",
                   "baseMagicNumber = magicNumber = 2;",
                   "addToBot(new ApplyPowerAction(m, p, new VulnerablePower(m, magicNumber, false), magicNumber));\n        NsfwRunStats.addExcitement(8);\n        NsfwRunStats.addConception(3, false);",
                   "upgradeMagicNumber(1);",
                   "灼痕烙印", "给予 !M! 层 易伤。兴奋 +8。受孕度 +3。", None),
        write_card("eyja", "CoreFever", 2, "POWER", "RARE", "SELF", "card_core_fever.png",
                   "baseMagicNumber = magicNumber = 1;",
                   "addToBot(new ApplyPowerAction(p, p, new GeothermalPower(p, magicNumber), magicNumber));\n        addToBot(new ApplyPowerAction(p, p, new CoreFeverPower(p, magicNumber), magicNumber));",
                   "upgradeMagicNumber(1);",
                   "核心高热", "获得 !M! 层 地热 与 核心高热（受击后兴奋 +5）。", None),
        write_card("muel", "WetSlide", 0, "SKILL", "UNCOMMON", "SELF", "card_wet_slide.png",
                   "baseBlock = 5;",
                   "addToBot(new GainBlockAction(p, block));\n        NsfwRunStats.addExcitement(10);\n        NsfwRunStats.addFertility(3, 0, false);",
                   "upgradeBlock(3);",
                   "湿滑滑行", "获得 !B! 点格挡。兴奋 +10。受孕度 +3。", None),
        write_card("muel", "FluidSplash", 1, "ATTACK", "COMMON", "ENEMY", "card_fluid_splash.png",
                   "baseDamage = 9;",
                   "addToBot(new DamageAction(m, new DamageInfo(p, damage, DamageInfo.DamageType.NORMAL), AbstractGameAction.AttackEffect.SLASH_DIAGONAL));\n        NsfwRunStats.addExcitement(12);\n        NsfwRunStats.addConception(4, false);",
                   "upgradeDamage(4);",
                   "体液飞溅", "造成 !D! 点伤害。兴奋 +12。受孕度 +4。", None),
        write_card("muel", "MoistBarrier", 1, "SKILL", "COMMON", "SELF", "card_moist_barrier.png",
                   "baseBlock = 11;",
                   "addToBot(new GainBlockAction(p, block));\n        addToBot(new ApplyPowerAction(p, p, new HydrationPower(p, 1), 1));",
                   "upgradeBlock(4);\n            if (!upgraded) {\n                // second hydration on upgrade handled below\n            }",
                   "湿润屏障", "获得 !B! 点格挡。获得 1 层 水化。", "获得 !B! 点格挡。获得 2 层 水化。"),
        write_card("muel", "TwinPleasure", 2, "ATTACK", "RARE", "RANDOM", "card_twin_pleasure.png",
                   "baseDamage = 10;",
                   "addToBot(new DamageAction(m, new DamageInfo(p, damage, DamageInfo.DamageType.NORMAL), AbstractGameAction.AttackEffect.SLASH_HEAVY));\n        addToBot(new DamageAction(m, new DamageInfo(p, damage, DamageInfo.DamageType.NORMAL), AbstractGameAction.AttackEffect.SLASH_HEAVY));\n        NsfwRunStats.addExcitement(18);\n        NsfwRunStats.addFertility(8, 6, true);",
                   "upgradeDamage(4);",
                   "双份侍奉", "随机敌人受到两次 !D! 点伤害。兴奋 +18。中出：受孕 +8 / 妊娠 +6。", None),
        write_card("muel", "SeedSpray", 1, "SKILL", "UNCOMMON", "ALL_ENEMY", "card_seed_spray.png",
                   "baseMagicNumber = magicNumber = 1;",
                   "for (AbstractMonster mo : AbstractDungeon.getMonsters().monsters) {\n            if (!mo.isDeadOrEscaped()) {\n                addToBot(new ApplyPowerAction(mo, p, new WeakPower(mo, magicNumber, false), magicNumber));\n            }\n        }\n        NsfwRunStats.addExcitement(8);\n        NsfwRunStats.addFertility(5, 3, false);",
                   "upgradeMagicNumber(1);",
                   "播撒喷雾", "所有敌人获得 !M! 层 虚弱。兴奋 +8。受孕 +5 / 妊娠 +3。", None),
        write_card("muel", "CloneHaze", 2, "POWER", "RARE", "SELF", "card_clone_haze.png",
                   "baseMagicNumber = magicNumber = 1;",
                   "addToBot(new ApplyPowerAction(p, p, new CloneHazePower(p, magicNumber), magicNumber));",
                   "upgradeMagicNumber(1);",
                   "分身雾霭", "获得 !M! 层 分身雾（回合开始兴奋 +5，受孕 +2）。", None),
    ]
    for k, v in cards:
        cards_json[k] = v

    relics = [
        write_relic("eyja", "HeatStickerRelic", "heat_sticker.png", "UNCOMMON", "FLAT", "体温贴",
                    "每回合兴奋 +3。", "@Override\n    public void atTurnStart() {\n        NsfwRunStats.addExcitement(3);\n    }"),
        write_relic("eyja", "LavaPlugRelic", "lava_plug.png", "RARE", "SOLID", "熔核塞",
                    "打出 2 费及以上攻击牌时，妊娠进度 +2。",
                    "@Override\n    public void onPlayCard(com.megacrit.cardcrawl.cards.AbstractCard c, com.megacrit.cardcrawl.monsters.AbstractMonster m) {\n        if (c.type == com.megacrit.cardcrawl.cards.AbstractCard.CardType.ATTACK && c.costForTurn >= 2) {\n            NsfwRunStats.addFertility(0, 2, false);\n        }\n    }",
                    ["import com.megacrit.cardcrawl.cards.AbstractCard;", "import com.megacrit.cardcrawl.monsters.AbstractMonster;"]),
        write_relic("eyja", "AshCollarRelic", "ash_collar.png", "SHOP", "MAGICAL", "灰烬项圈",
                    "战斗开始兴奋 +10；受击后兴奋 +4。",
                    "@Override\n    public void atBattleStart() {\n        flash();\n        NsfwRunStats.addExcitement(10);\n    }\n\n    @Override\n    public void onLoseHp(int damageAmount) {\n        if (damageAmount > 0) {\n            NsfwRunStats.addExcitement(4);\n        }\n    }"),
        write_relic("eyja", "EmberSeedRelic", "ember_seed.png", "BOSS", "MAGICAL", "余烬之种",
                    "回合结束若兴奋≥50，受孕度 +5。",
                    "@Override\n    public void onPlayerEndTurn() {\n        if (NsfwRunStats.excitement >= 50) {\n            NsfwRunStats.addConception(5, false);\n        }\n    }"),
        write_relic("muel", "BubbleWandRelic", "bubble_wand.png", "UNCOMMON", "FLAT", "泡沫魔杖",
                    "获得格挡后兴奋 +2（每回合最多 6）。",
                    "private int gainThisTurn = 0;\n\n    @Override\n    public void onPlayerGainedBlock(float blockAmount) {\n        if (blockAmount > 0 && gainThisTurn < 6) {\n            NsfwRunStats.addExcitement(2);\n            gainThisTurn += 2;\n        }\n    }\n\n    @Override\n    public void atTurnStart() {\n        gainThisTurn = 0;\n    }"),
        write_relic("muel", "CloneTagRelic", "clone_tag.png", "RARE", "MAGICAL", "克隆工牌",
                    "战斗开始：兴奋 +8，受孕 +6。",
                    "@Override\n    public void atBattleStart() {\n        flash();\n        NsfwRunStats.addExcitement(8);\n        NsfwRunStats.addConception(6, false);\n    }"),
        write_relic("muel", "RootVineRelic", "root_vine.png", "SHOP", "SOLID", "根须藤蔓",
                    "回合结束受孕度 +3；兴奋≥40 时再 +3。",
                    "@Override\n    public void onPlayerEndTurn() {\n        NsfwRunStats.addConception(3, false);\n        if (NsfwRunStats.excitement >= 40) {\n            NsfwRunStats.addConception(3, false);\n        }\n    }"),
        write_relic("muel", "OverflowFlaskRelic", "overflow_flask.png", "BOSS", "MAGICAL", "溢流烧瓶",
                    "每打出 3 张技能牌，视为一次中出。",
                    "private int skillCount = 0;\n\n    @Override\n    public void onPlayCard(com.megacrit.cardcrawl.cards.AbstractCard c, com.megacrit.cardcrawl.monsters.AbstractMonster m) {\n        if (c.type == com.megacrit.cardcrawl.cards.AbstractCard.CardType.SKILL) {\n            skillCount++;\n            if (skillCount >= 3) {\n                skillCount = 0;\n                NsfwRunStats.addFertility(10, 8, true);\n                flash();\n            }\n        }\n    }",
                    ["import com.megacrit.cardcrawl.cards.AbstractCard;", "import com.megacrit.cardcrawl.monsters.AbstractMonster;"]),
    ]
    for k, v in relics:
        relics_json[k] = v

    CARD_JSON.write_text(json.dumps(cards_json, ensure_ascii=False, indent=2) + "\n", encoding="utf-8")
    RELIC_JSON.write_text(json.dumps(relics_json, ensure_ascii=False, indent=2) + "\n", encoding="utf-8")
    print("ok", len(cards), len(relics))


if __name__ == "__main__":
    main()
