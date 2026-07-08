#!/usr/bin/env python3
"""堕落模式色情卡生成器：28 个机制模板 × 7 名角色 = 196 张卡。
生成 Java 卡牌类、注册器 ArkFallCards、CardStrings 本地化。"""
import json
import os
import re
import glob

ROOT = "ArknightsNsfwBridge/src/main"
JAVA = f"{ROOT}/java/arknsfw"
LOC = f"{ROOT}/resources/arknsfwResources/localization/zhs/CardStrings.json"

CHARS = {
    "highmore": dict(prefix="Highmore", color_import="highmore.core.ColorEnum",
                     color="ColorEnum.HIGHMORE_COLOR", theme="幽潮",
                     mech="ArkCharMechanicsHelper.applyReapPower(p, {n});",
                     mechd="获得 {n} 层收割"),
    "scene": dict(prefix="Scene", color_import="scene.core.ColorEnum",
                  color="ColorEnum.SCENE_COLOR", theme="留影",
                  mech="ArkCharMechanicsHelper.applyFocusPower(p, {n});",
                  mechd="获得 {n} 层聚焦"),
    "archetto": dict(prefix="Archetto", color_import="archetto.core.ColorEnum",
                     color="ColorEnum.ARCHETTO_COLOR", theme="鸣弦",
                     mech="ArkCharMechanicsHelper.applyAimPower(p, {n});",
                     mechd="获得 {n} 层瞄准"),
    "haruka": dict(prefix="Haruka", color_import="haruka.core.ColorEnum",
                   color="ColorEnum.HARUKA_COLOR", theme="烬焰",
                   mech="ArkCharMechanicsHelper.applyPyroPower(p, {n});",
                   mechd="获得 {n} 层灼热"),
    "nymph": dict(prefix="Nymph", color_import="nymph.core.ColorEnum",
                  color="ColorEnum.NYMPH_COLOR", theme="迷雾",
                  mech="ArkCharMechanicsHelper.applyHexPower(p, {n});",
                  mechd="对随机敌人施加 {n} 层咒灵"),
    "eyja": dict(prefix="Eyja", color_import="Eyjafjalla.modcore.ColorEnum",
                 color="ColorEnum.Eyjafjalla_COLOR", theme="熔心",
                 mech="ArkCharMechanicsHelper.gainCloudEnergy(1); ArkCharMechanicsHelper.markPyrobreath(this);",
                 mechd="获得 1 云量并附炎息"),
    "muel": dict(prefix="Muel", color_import="Muelsyse.patches.ColorEnum",
                 color="ColorEnum.Muelsyse_COLOR", theme="清漪",
                 mech="ArkCharMechanicsHelper.applyRootage(p, {n});",
                 mechd="获得 {n} 层溯源"),
}

HEADER = """package arknsfw.cards.{pkg};

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.EnergizedPower;
import com.megacrit.cardcrawl.powers.VulnerablePower;
import com.megacrit.cardcrawl.powers.WeakPower;
import com.megacrit.cardcrawl.ui.panels.EnergyPanel;
import liesecore.helpers.NsfwRunStats;
import arknsfw.ArkNsfwMod;
import arknsfw.cards.AbstractArkNsfwCard;
import arknsfw.helpers.ArkCharMechanicsHelper;
import arknsfw.helpers.ArkExposureHelper;
import arknsfw.helpers.ArkGearSetHelper;
import arknsfw.helpers.ArkSafeStats;
import arknsfw.helpers.LieseCompat;
import {color_import};

/** {comment} */
public class {cls} extends AbstractArkNsfwCard {{
    public static final String ID = ArkNsfwMod.makeID("{cls}");

    public {cls}() {{
        super(ID, {cost}, CardType.{ctype}, {color}, CardRarity.{rarity}, CardTarget.{target}, "{art}");
{init}
    }}

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {{
{use}
    }}

    @Override
    public void upgrade() {{
        if (!upgraded) {{
            upgradeName();
{up}
            applyUpgradeDescription();
        }}
    }}

    @Override
    public AbstractCard makeCopy() {{ return new {cls}(); }}
}}
"""

# 28 模板：cls/名/费用/类型/稀有度/目标/初始化/use体/升级体/描述/升级描述
# {MECH1}/{MECH2}=机制注入 {MECHD1}/{MECHD2}=机制描述
T = []
def t(**kw):
    T.append(kw)

t(cls="TeasingTouch", name="挑逗指尖", cost=1, ctype="ATTACK", rarity="COMMON", target="ENEMY",
  init="        baseDamage = 7;",
  use="""        addToBot(new DamageAction(m, new DamageInfo(p, damage, damageTypeForTurn), AbstractGameAction.AttackEffect.SLASH_HORIZONTAL));
        NsfwRunStats.addExcitement(3);
        {MECH1}""",
  up="            upgradeDamage(3);",
  desc="造成 !D! 点伤害。兴奋 +3。 NL {MECHD1}。",
  udesc="造成 !D! 点伤害。兴奋 +3。 NL {MECHD1}。")

t(cls="WetKissCard", name="濡湿之吻", cost=1, ctype="SKILL", rarity="COMMON", target="SELF",
  init="        baseBlock = 8;",
  use="""        addToBot(new GainBlockAction(p, block));
        NsfwRunStats.addExcitement(4);""",
  up="            upgradeBlock(3);",
  desc="获得 !B! 点格挡。兴奋 +4。",
  udesc="获得 !B! 点格挡。兴奋 +4。")

t(cls="CrestDetonate", name="纹章引爆", cost=2, ctype="ATTACK", rarity="UNCOMMON", target="ENEMY",
  init="        baseDamage = 8;\n        baseMagicNumber = magicNumber = 4;",
  use="""        int curses = 0;
        for (AbstractCard c : p.masterDeck.group) { if (c.type == CardType.CURSE) curses++; }
        addToBot(new DamageAction(m, new DamageInfo(p, damage + magicNumber * curses, damageTypeForTurn), AbstractGameAction.AttackEffect.FIRE));
        NsfwRunStats.addExcitement(4);""",
  up="            upgradeMagicNumber(2);",
  desc="造成 !D! 点伤害，牌组中每张诅咒额外 +!M!。兴奋 +4。",
  udesc="造成 !D! 点伤害，牌组中每张诅咒额外 +!M!。兴奋 +4。")

t(cls="SelfRelief", name="自渎泄压", cost=0, ctype="SKILL", rarity="COMMON", target="SELF",
  init="        baseMagicNumber = magicNumber = 15;\n        this.exhaust = true;",
  use="""        NsfwRunStats.addExcitement(-magicNumber);
        addToBot(new DrawCardAction(p, 1));""",
  up="            upgradeMagicNumber(5);",
  desc="兴奋 -!M!。抽 1 张牌。消耗。",
  udesc="兴奋 -!M!。抽 1 张牌。消耗。")

t(cls="HeatCharge", name="发情冲锋", cost=1, ctype="ATTACK", rarity="UNCOMMON", target="ENEMY",
  init="        baseDamage = 9;",
  use="""        int dmg = damage;
        if (NsfwRunStats.excitement >= 50) { dmg *= 2; }
        addToBot(new DamageAction(m, new DamageInfo(p, dmg, damageTypeForTurn), AbstractGameAction.AttackEffect.BLUNT_HEAVY));
        NsfwRunStats.addExcitement(5);""",
  up="            upgradeDamage(3);",
  desc="造成 !D! 点伤害；兴奋≥50 时伤害翻倍。兴奋 +5。",
  udesc="造成 !D! 点伤害；兴奋≥50 时伤害翻倍。兴奋 +5。")

t(cls="SweetSpotShot", name="敏点狙击", cost=1, ctype="ATTACK", rarity="COMMON", target="ENEMY",
  init="        baseDamage = 6;",
  use="""        addToBot(new DamageAction(m, new DamageInfo(p, damage, damageTypeForTurn), AbstractGameAction.AttackEffect.SLASH_DIAGONAL));
        addToBot(new ApplyPowerAction(m, p, new WeakPower(m, 1, false), 1));
        {MECH1}""",
  up="            upgradeDamage(2);",
  desc="造成 !D! 点伤害，给予 1 层虚弱。 NL {MECHD1}。",
  udesc="造成 !D! 点伤害，给予 1 层虚弱。 NL {MECHD1}。")

t(cls="WombUrge", name="孕欲涌动", cost=1, ctype="SKILL", rarity="UNCOMMON", target="SELF",
  init="        baseMagicNumber = magicNumber = 2;",
  use="""        NsfwRunStats.addConception(10, false);
        NsfwRunStats.addFertility(0, 5, false);
        addToBot(new DrawCardAction(p, magicNumber));""",
  up="            upgradeMagicNumber(1);",
  desc="受孕 +10、受胎质量 +5。抽 !M! 张牌。",
  udesc="受孕 +10、受胎质量 +5。抽 !M! 张牌。")

t(cls="PleasureShield", name="快感护壁", cost=1, ctype="SKILL", rarity="COMMON", target="SELF",
  init="        baseBlock = 5;",
  use="""        int bonus = Math.min(10, NsfwRunStats.excitement / 10);
        addToBot(new GainBlockAction(p, block + bonus));""",
  up="            upgradeBlock(3);",
  desc="获得 !B! 点格挡，兴奋每 10 点额外 +1（至多 +10）。",
  udesc="获得 !B! 点格挡，兴奋每 10 点额外 +1（至多 +10）。")

t(cls="CrestChant", name="淫纹咏唱", cost=1, ctype="SKILL", rarity="UNCOMMON", target="SELF",
  init="        baseMagicNumber = magicNumber = 2;",
  use="""        addToBot(new ApplyPowerAction(p, p, new EnergizedPower(p, magicNumber), magicNumber));
        NsfwRunStats.addExcitement(8);""",
  up="            upgradeMagicNumber(1);",
  desc="下回合获得 !M! 点额外能量。兴奋 +8。",
  udesc="下回合获得 !M! 点额外能量。兴奋 +8。")

t(cls="EdgeControl", name="绝顶边缘", cost=0, ctype="SKILL", rarity="UNCOMMON", target="SELF",
  init="        baseMagicNumber = magicNumber = 2;\n        this.exhaust = true;",
  use="""        int th = LieseCompat.climaxThreshold();
        if (th > 0) {
            int target = Math.min(NsfwRunStats.excitement + 20, th - 5);
            if (target > NsfwRunStats.excitement) { NsfwRunStats.addExcitement(target - NsfwRunStats.excitement); }
        } else {
            NsfwRunStats.addExcitement(20);
        }
        addToBot(new DrawCardAction(p, magicNumber));""",
  up="            upgradeMagicNumber(1);",
  desc="兴奋提升 20（不会越过高潮阈值）。抽 !M! 张牌。消耗。",
  udesc="兴奋提升 20（不会越过高潮阈值）。抽 !M! 张牌。消耗。")

t(cls="LustCounter", name="欲火反击", cost=1, ctype="ATTACK", rarity="COMMON", target="ENEMY",
  init="        baseDamage = 8;\n        baseMagicNumber = magicNumber = 5;",
  use="""        boolean debuffed = false;
        for (com.megacrit.cardcrawl.powers.AbstractPower pw : p.powers) {
            if (pw.type == com.megacrit.cardcrawl.powers.AbstractPower.PowerType.DEBUFF) { debuffed = true; break; }
        }
        addToBot(new DamageAction(m, new DamageInfo(p, damage + (debuffed ? magicNumber : 0), damageTypeForTurn), AbstractGameAction.AttackEffect.SLASH_HEAVY));""",
  up="            upgradeDamage(3);\n            upgradeMagicNumber(2);",
  desc="造成 !D! 点伤害；自身有减益时额外 +!M!。",
  udesc="造成 !D! 点伤害；自身有减益时额外 +!M!。")

t(cls="LewdBreath", name="媚热吐息", cost=1, ctype="ATTACK", rarity="UNCOMMON", target="ALL_ENEMY",
  init="        baseDamage = 5;\n        this.isMultiDamage = true;",
  use="""        addToBot(new DamageAllEnemiesAction(p, multiDamage, damageTypeForTurn, AbstractGameAction.AttackEffect.FIRE));
        NsfwRunStats.addExcitement(4);
        {MECH1}""",
  up="            upgradeDamage(3);",
  desc="对所有敌人造成 !D! 点伤害。兴奋 +4。 NL {MECHD1}。",
  udesc="对所有敌人造成 !D! 点伤害。兴奋 +4。 NL {MECHD1}。")

t(cls="BondageBliss", name="束缚之悦", cost=1, ctype="SKILL", rarity="UNCOMMON", target="SELF",
  init="        baseMagicNumber = magicNumber = 4;",
  use="""        int n = Math.max(1, ArkGearSetHelper.equipmentCount());
        addToBot(new GainBlockAction(p, magicNumber * n));""",
  up="            upgradeMagicNumber(1);",
  desc="每持有一件拘束装备获得 !M! 点格挡（至少按 1 件计）。",
  udesc="每持有一件拘束装备获得 !M! 点格挡（至少按 1 件计）。")

t(cls="LustRush", name="淫奔加速", cost=0, ctype="SKILL", rarity="UNCOMMON", target="SELF",
  init="        baseMagicNumber = magicNumber = 1;\n        this.exhaust = true;",
  use="""        addToBot(new GainEnergyAction(magicNumber));
        NsfwRunStats.addExcitement(6);""",
  up="            upgradeMagicNumber(1);",
  desc="获得 !M! 点能量。兴奋 +6。消耗。",
  udesc="获得 !M! 点能量。兴奋 +6。消耗。")

t(cls="FallPactCard", name="堕落契约", cost=1, ctype="SKILL", rarity="RARE", target="SELF",
  init="        this.exhaust = true;",
  use="""        addToBot(new GainEnergyAction(2));
        addToBot(new DrawCardAction(p, 2));
        NsfwRunStats.addExcitement(10);
        AbstractCard curse = com.megacrit.cardcrawl.helpers.CardLibrary.getCurse();
        if (curse != null) {
            addToBot(new MakeTempCardInDiscardAction(curse.makeCopy(), 1));
        }""",
  up="            upgradeBaseCost(0);",
  desc="获得 2 点能量，抽 2 张牌，兴奋 +10。将一张随机诅咒置入弃牌堆。消耗。",
  udesc="获得 2 点能量，抽 2 张牌，兴奋 +10。将一张随机诅咒置入弃牌堆。消耗。")

t(cls="IndulgeCard", name="沉溺", cost=2, ctype="SKILL", rarity="UNCOMMON", target="SELF",
  init="        baseMagicNumber = magicNumber = 12;",
  use="""        int amount = Math.min(magicNumber, NsfwRunStats.excitement / 8);
        if (amount > 0) { ArkCharMechanicsHelper.healOrBlock(p, amount); }
        NsfwRunStats.addExcitement(-10);""",
  up="            upgradeMagicNumber(4);",
  desc="回复生命，数值=兴奋/8（至多 !M!，海沫改为格挡）。兴奋 -10。",
  udesc="回复生命，数值=兴奋/8（至多 !M!，海沫改为格挡）。兴奋 -10。")

t(cls="MoanEcho", name="娇声回响", cost=1, ctype="ATTACK", rarity="COMMON", target="ENEMY",
  init="        baseDamage = 6;\n        baseMagicNumber = magicNumber = 6;",
  use="""        int dmg = damage + (NsfwRunStats.pregnant ? magicNumber : 0);
        addToBot(new DamageAction(m, new DamageInfo(p, dmg, damageTypeForTurn), AbstractGameAction.AttackEffect.SLASH_HORIZONTAL));""",
  up="            upgradeDamage(3);",
  desc="造成 !D! 点伤害；怀孕时额外 +!M!。",
  udesc="造成 !D! 点伤害；怀孕时额外 +!M!。")

t(cls="PhantomUnion", name="交合幻影", cost=2, ctype="ATTACK", rarity="RARE", target="ENEMY",
  init="        baseDamage = 14;",
  use="""        addToBot(new DamageAction(m, new DamageInfo(p, damage, damageTypeForTurn), AbstractGameAction.AttackEffect.BLUNT_HEAVY));
        ArkSafeStats.addConceptionDeferred(8, false);
        {MECH2}""",
  up="            upgradeDamage(4);",
  desc="造成 !D! 点伤害。受孕 +8。 NL {MECHD2}。",
  udesc="造成 !D! 点伤害。受孕 +8。 NL {MECHD2}。")

t(cls="LustDominate", name="淫欲支配", cost=1, ctype="SKILL", rarity="RARE", target="ALL_ENEMY",
  init="        baseMagicNumber = magicNumber = 2;",
  use="""        for (AbstractMonster mo : AbstractDungeon.getMonsters().monsters) {
            if (!mo.isDeadOrEscaped()) {
                addToBot(new ApplyPowerAction(mo, p, new WeakPower(mo, magicNumber, false), magicNumber));
                addToBot(new ApplyPowerAction(mo, p, new VulnerablePower(mo, 1, false), 1));
            }
        }
        NsfwRunStats.addExcitement(6);""",
  up="            upgradeMagicNumber(1);",
  desc="所有敌人获得 !M! 层虚弱与 1 层易伤。兴奋 +6。",
  udesc="所有敌人获得 !M! 层虚弱与 1 层易伤。兴奋 +6。")

t(cls="ClimaxTransfer", name="高潮转移", cost=1, ctype="SKILL", rarity="RARE", target="ALL_ENEMY",
  init="        baseMagicNumber = magicNumber = 20;",
  use="""        int x = NsfwRunStats.excitement;
        if (x > 0) {
            NsfwRunStats.addExcitement(-x);
            int dmg = Math.min(magicNumber, x / 4);
            if (dmg > 0) {
                addToBot(new DamageAllEnemiesAction(p, DamageInfo.createDamageMatrix(dmg, true), DamageInfo.DamageType.THORNS, AbstractGameAction.AttackEffect.FIRE));
            }
        }""",
  up="            upgradeMagicNumber(10);",
  desc="清空兴奋，每 4 点兴奋对所有敌人造成 1 点伤害（至多 !M!）。",
  udesc="清空兴奋，每 4 点兴奋对所有敌人造成 1 点伤害（至多 !M!）。")

t(cls="SecretCaress", name="秘处爱抚", cost=0, ctype="SKILL", rarity="COMMON", target="SELF",
  init="        baseBlock = 3;",
  use="""        addToBot(new GainBlockAction(p, block));
        NsfwRunStats.addExcitement(8);
        addToBot(new DrawCardAction(p, 1));""",
  up="            upgradeBlock(3);",
  desc="获得 !B! 点格挡。兴奋 +8。抽 1 张牌。",
  udesc="获得 !B! 点格挡。兴奋 +8。抽 1 张牌。")

t(cls="WombBrandCard", name="子宫烙印", cost=1, ctype="ATTACK", rarity="UNCOMMON", target="ENEMY",
  init="        baseDamage = 10;",
  use="""        addToBot(new DamageAction(m, new DamageInfo(p, damage, damageTypeForTurn), AbstractGameAction.AttackEffect.FIRE));
        ArkSafeStats.addConceptionDeferred(8, false);
        ArkSafeStats.addFertilityDeferred(4, 4, false);""",
  up="            upgradeDamage(4);",
  desc="造成 !D! 点伤害。受孕 +8、受胎 +4。",
  udesc="造成 !D! 点伤害。受孕 +8、受胎 +4。")

t(cls="ExposeDeclare", name="露出宣言", cost=1, ctype="SKILL", rarity="UNCOMMON", target="SELF",
  init="        this.exhaust = true;",
  use="""        ArkExposureHelper.tear();
        addToBot(new GainEnergyAction(1));
        addToBot(new DrawCardAction(p, 2));
        NsfwRunStats.addExcitement(8);""",
  up="            upgradeBaseCost(0);",
  desc="衣装破损一档。获得 1 点能量，抽 2 张牌，兴奋 +8。消耗。",
  udesc="衣装破损一档。获得 1 点能量，抽 2 张牌，兴奋 +8。消耗。")

t(cls="CrestOverload", name="淫纹过载", cost=-1, ctype="ATTACK", rarity="RARE", target="ENEMY",
  init="        baseMagicNumber = magicNumber = 9;",
  use="""        int effect = this.energyOnUse;
        if (p.hasRelic("Chemical X")) { effect += 2; }
        if (effect > 0) {
            addToBot(new DamageAction(m, new DamageInfo(p, magicNumber * effect, damageTypeForTurn), AbstractGameAction.AttackEffect.FIRE));
            NsfwRunStats.addExcitement(4 * effect);
        }
        if (!this.freeToPlayOnce) { p.energy.use(EnergyPanel.totalCount); }""",
  up="            upgradeMagicNumber(3);",
  desc="消耗所有能量，每点造成 !M! 点伤害并兴奋 +4。",
  udesc="消耗所有能量，每点造成 !M! 点伤害并兴奋 +4。")

t(cls="SageMeditate", name="贤者冥想", cost=1, ctype="SKILL", rarity="COMMON", target="SELF",
  init="        baseBlock = 6;\n        baseMagicNumber = magicNumber = 20;",
  use="""        addToBot(new GainBlockAction(p, block));
        NsfwRunStats.addExcitement(-magicNumber);""",
  up="            upgradeBlock(3);\n            upgradeMagicNumber(5);",
  desc="获得 !B! 点格挡。兴奋 -!M!。",
  udesc="获得 !B! 点格挡。兴奋 -!M!。")

t(cls="LewdMist", name="媚雾弥漫", cost=1, ctype="SKILL", rarity="COMMON", target="ALL_ENEMY",
  init="        baseMagicNumber = magicNumber = 1;",
  use="""        for (AbstractMonster mo : AbstractDungeon.getMonsters().monsters) {
            if (!mo.isDeadOrEscaped()) {
                addToBot(new ApplyPowerAction(mo, p, new WeakPower(mo, magicNumber, false), magicNumber));
            }
        }
        NsfwRunStats.addExcitement(4);
        {MECH1}""",
  up="            upgradeMagicNumber(1);",
  desc="所有敌人获得 !M! 层虚弱。兴奋 +4。 NL {MECHD1}。",
  udesc="所有敌人获得 !M! 层虚弱。兴奋 +4。 NL {MECHD1}。")

t(cls="SweetReward", name="甘美犒赏", cost=1, ctype="SKILL", rarity="COMMON", target="SELF",
  init="        baseMagicNumber = magicNumber = 8;",
  use="""        ArkCharMechanicsHelper.healOrBlock(p, magicNumber);
        ArkSafeStats.addConceptionDeferred(3, false);""",
  up="            upgradeMagicNumber(3);",
  desc="回复 !M! 点生命（海沫改为格挡）。受孕 +3。",
  udesc="回复 !M! 点生命（海沫改为格挡）。受孕 +3。")

t(cls="DeepTraining", name="深度调教", cost=2, ctype="SKILL", rarity="RARE", target="SELF",
  init="        this.exhaust = true;",
  use="""        if (ArkGearSetHelper.equipmentCount() >= 3) {
            addToBot(new GainEnergyAction(2));
            addToBot(new DrawCardAction(p, 2));
        } else {
            NsfwRunStats.addExcitement(15);
        }""",
  up="            upgradeBaseCost(1);",
  desc="拘束装备≥3件：获得 2 点能量并抽 2 张牌；否则兴奋 +15。消耗。",
  udesc="拘束装备≥3件：获得 2 点能量并抽 2 张牌；否则兴奋 +15。消耗。")

assert len(T) == 28, len(T)


def art_files(key):
    """从现有卡牌 Java 源码中提取该角色可用的卡面文件名。"""
    arts = []
    for f in sorted(glob.glob(f"{JAVA}/cards/{key}/*.java")):
        src = open(f, encoding="utf-8").read()
        m = re.search(r'"(card_[a-z0-9_]+\.png)"', src)
        if m and m.group(1) not in arts:
            arts.append(m.group(1))
    return arts or ["card_bubble_tease.png"]


def main():
    strings = json.load(open(LOC, encoding="utf-8"))
    reg_lines = []
    total = 0
    for key, cfg in CHARS.items():
        arts = art_files(key)
        os.makedirs(f"{JAVA}/cards/{key}", exist_ok=True)
        for i, tpl in enumerate(T):
            cls = cfg["prefix"] + tpl["cls"]
            art = arts[i % len(arts)]
            mech1 = cfg["mech"].replace("{n}", "1")
            mech2 = cfg["mech"].replace("{n}", "2")
            mechd1 = cfg["mechd"].replace("{n}", "1")
            mechd2 = cfg["mechd"].replace("{n}", "2")
            use = tpl["use"].replace("{MECH1}", mech1).replace("{MECH2}", mech2)
            src = HEADER.format(
                pkg=key, color_import=cfg["color_import"], color=cfg["color"],
                comment=cfg["theme"] + "·" + tpl["name"],
                cls=cls, cost=tpl["cost"], ctype=tpl["ctype"], rarity=tpl["rarity"],
                target=tpl["target"], art=art, init=tpl["init"], use=use, up=tpl["up"])
            open(f"{JAVA}/cards/{key}/{cls}.java", "w", encoding="utf-8").write(src)
            cid = f"arknsfw:{cls}"
            strings[cid] = {
                "NAME": cfg["theme"] + "·" + tpl["name"],
                "DESCRIPTION": tpl["desc"].replace("{MECHD1}", mechd1).replace("{MECHD2}", mechd2),
                "UPGRADE_DESCRIPTION": tpl["udesc"].replace("{MECHD1}", mechd1).replace("{MECHD2}", mechd2),
            }
            reg_lines.append(f"        BaseMod.addCard(new arknsfw.cards.{key}.{cls}());")
            total += 1

    registrar = """package arknsfw.cards;

import basemod.BaseMod;

/** 堕落模式色情卡注册器（28 模板 × 7 角色，由 gen_fall_cards.py 生成）。 */
public final class ArkFallCards {

    private ArkFallCards() {
    }

    public static void registerAll() {
%s
    }
}
""" % "\n".join(reg_lines)
    open(f"{JAVA}/cards/ArkFallCards.java", "w", encoding="utf-8").write(registrar)
    json.dump(strings, open(LOC, "w", encoding="utf-8"), ensure_ascii=False, indent=2)
    print(f"generated {total} cards + registrar + strings")


if __name__ == "__main__":
    main()
