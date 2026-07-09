#!/usr/bin/env python3
"""堕落模式·本体SFW卡逐张定制色情附加效果表。
生成 ArkFallCardRider.java：154 张五角色本体卡的手工效果表 +
艾雅/缪尔本体卡的属性化启发式 + 卡牌描述追加逻辑。"""

# ---- 效果原语（java 片段 + 描述片段）----
FX = {
    "ex2":       ("ex(2);", "兴奋+2"),
    "ex3":       ("ex(3);", "兴奋+3"),
    "ex4":       ("ex(4);", "兴奋+4"),
    "ex6":       ("ex(6);", "兴奋+6"),
    "ex8":       ("ex(8);", "兴奋+8"),
    "calm4":     ("ex(-4);", "兴奋-4"),
    "conc3":     ("conc(3);", "受孕+3"),
    "conc4":     ("conc(4);", "受孕+4"),
    "fert3":     ("fert(3);", "受胎+3"),
    "blk30_2":   ("if (exAt(30)) { blk(2 + (has(\"arknsfw:RopeBindRelic\") ? 2 : 0)); }",
                  "兴奋≥30：+2格挡（绳缚再+2）"),
    "gearblk1":  ("blk(Math.min(8, arknsfw.helpers.ArkGearSetHelper.equipmentCount()));",
                  "每件拘束装备+1格挡（至多8）"),
    "mech50_1":  ("if (exAt(50)) { mechPlus(1); }", "兴奋≥50：本源机制+1"),
    "crest_ex4": ("if (has(\"arknsfw:BodyCrestRelic\")) { ex(4); }", "戴淫纹：兴奋+4"),
    "vibe_draw": ("if (has(\"arknsfw:VibeEggRelic\")) { ex(2); if (exAt(60)) { draw(1); } }",
                  "戴跳蛋：兴奋+2，兴奋≥60再抽1"),
    "blind_mech1": ("if (has(\"arknsfw:LaceBlindfoldRelic\")) { mechPlus(1); }",
                    "戴蕾丝眼罩：本源机制+1"),
    "gag_ex3":   ("if (has(\"arknsfw:ClothGagRelic\") || has(\"arknsfw:RingGagRelic\")) { ex(3); }",
                  "戴口枷：兴奋+3"),
    "edge_energy": ("if (nearClimax(10)) { energy(1); }", "濒临高潮（距阈值10内）：+1能量"),
}

# ---- 五角色 154 张本体卡逐张指定 ----
def bulk(prefix):
    return {
        f"{prefix}:BulkStrike01": ["ex2"],
        f"{prefix}:BulkGuard02": ["blk30_2"],
        f"{prefix}:BulkDraw03": ["vibe_draw"],
        f"{prefix}:BulkSweep04": ["ex3"],
        f"{prefix}:BulkFinale05": ["mech50_1", "crest_ex4"],
        f"{prefix}:BulkStrike06": ["ex2"],
        f"{prefix}:BulkGuard07": ["blk30_2"],
        f"{prefix}:BulkDraw08": ["vibe_draw"],
        f"{prefix}:BulkFinale30": ["mech50_1", "crest_ex4"],
    }

def basics(prefix):
    return {
        f"{prefix}:Strike": ["ex2"], f"{prefix}:Jab": ["ex2"], f"{prefix}:Cut": ["ex2"],
        f"{prefix}:Defend": ["blk30_2"], f"{prefix}:Ward": ["blk30_2"],
        f"{prefix}:Surge": ["conc3"],
    }

TABLE = {}
TABLE.update(basics("highmore")); TABLE.update(bulk("highmore"))
TABLE.update({
    "highmore:TideReap": ["mech50_1"],
    "highmore:ScytheSwing": ["ex2", "crest_ex4"],
    "highmore:SaltHarvest": ["mech50_1"],
    "highmore:BrineWall": ["blk30_2"],
    "highmore:BloodRush": ["ex4"],                # 痛觉转快感
    "highmore:WhirlReap": ["ex3"],
    "highmore:DeadDrift": ["conc4"],
    "highmore:AbyssPull": ["gearblk1"],
    "highmore:BloodScale": ["ex3", "fert3"],
    "highmore:RipTide": ["mech50_1"],
    "highmore:GreatTide": ["ex6"],                # 潮涌上头
    "highmore:AbyssGaze": ["conc4"],              # 以身饲渊
    "highmore:DrownEmbrace": ["ex8"],             # 清空收割的爆发=濒顶
    "highmore:SaltDraw": ["ex3"],
    "highmore:TidePool": ["blk30_2"],
})
TABLE.update(basics("scene")); TABLE.update(bulk("scene"))
TABLE.update({
    "scene:FocusShot": ["blind_mech1"],           # 感官剥夺反而更专注
    "scene:ShutterGuard": ["blk30_2"],
    "scene:Snapshot": ["ex3", "crest_ex4"],
    "scene:LensDraw": ["vibe_draw"],
    "scene:PanScan": ["blind_mech1"],
    "scene:LongExposure": ["mech50_1"],
    "scene:Develop": ["conc4"],                   # 暗房私密时间
    "scene:FreezeFrame": ["blk30_2"],
    "scene:DevelopRush": ["vibe_draw"],
    "scene:WideFrame": ["gearblk1"],
    "scene:BurstShutter": ["ex3"],
    "scene:Negative": ["ex6"],
    "scene:OverExposure": ["ex4"],
    "scene:TimerShutter": ["edge_energy"],
    "scene:DarkroomWash": ["conc4"],
    "scene:MasterLens": ["conc4"],
})
TABLE.update(basics("archetto")); TABLE.update(bulk("archetto"))
TABLE.update({
    "archetto:QuickShot": ["blind_mech1"],
    "archetto:MarkTarget": ["blind_mech1"],
    "archetto:BurstArrow": ["ex2"],
    "archetto:Reload": ["vibe_draw"],
    "archetto:FanVolley": ["ex3"],
    "archetto:WindPrep": ["blk30_2"],
    "archetto:Concerto": ["conc4"],
    "archetto:PierceShot": ["ex2", "crest_ex4"],
    "archetto:SteadyBreath": ["calm4"],           # 均匀呼吸压欲望
    "archetto:TrueShot": ["mech50_1"],
    "archetto:TripleArrow": ["ex3"],
    "archetto:HawkEye": ["conc4"],
    "archetto:SnapString": ["ex4", "crest_ex4"],
    "archetto:QuiverRefill": ["edge_energy"],
    "archetto:SwiftStep": ["blk30_2"],
    "archetto:ArrowStorm": ["ex3"],
})
TABLE.update(basics("haruka")); TABLE.update(bulk("haruka"))
TABLE.update({
    "haruka:SparkKick": ["ex2"],
    "haruka:FireworkPrep": ["blk30_2"],
    "haruka:FestivalStep": ["vibe_draw"],
    "haruka:DragonDance": ["ex3"],
    "haruka:AshGuard": ["blk30_2"],
    "haruka:FinaleMark": ["conc4"],
    "haruka:SparkWave": ["ex3"],
    "haruka:EncoreShout": ["edge_energy"],
    "haruka:EarlyBurst": ["ex8"],                 # 空中引爆=瞬间绝顶感
    "haruka:GatherFlame": ["blk30_2"],
    "haruka:FlameHeart": ["conc4"],
    "haruka:DuetSpark": ["ex3"],
    "haruka:FireTree": ["ex3"],
    "haruka:AddFuel": ["edge_energy"],
    "haruka:AfterHeat": ["gearblk1"],
    "haruka:FinalDance": ["ex4", "crest_ex4"],
})
TABLE.update(basics("nymph")); TABLE.update(bulk("nymph"))
TABLE.update({
    "nymph:HexBolt": ["mech50_1"],
    "nymph:FearWhisper": ["gag_ex3"],             # 被堵住嘴还要低语
    "nymph:HeartSeal": ["conc3"],
    "nymph:CurseWave": ["ex3"],
    "nymph:DreadGuard": ["blk30_2"],
    "nymph:SoulBind": ["mech50_1"],
    "nymph:Nightmare": ["gag_ex3"],
    "nymph:VoidCut": ["ex2"],
    "nymph:HexSpread": ["ex4"],
    "nymph:HeartBite": ["ex2", "crest_ex4"],
    "nymph:FearFeast": ["gag_ex3"],
    "nymph:HexLink": ["mech50_1"],
    "nymph:NightEcho": ["conc4"],
    "nymph:SpiritClaw": ["ex2"],
    "nymph:LockTighten": ["conc3"],
    "nymph:ShadowReap": ["ex8"],
})


# ---- 艾雅法拉本体卡（101张，按机制家族逐张指定）----
def _many(fx, *ids):
    return {i: [fx] for i in ids}

EYJA = {}
# 轻攻击：兴奋+2
EYJA.update(_many("ex2", "Strike_Eyjafjalla", "FloorCloud_Eyjafjalla", "Sunshine_Eyjafjalla",
    "InstantFireworks_Eyjafjalla", "CPS_Eyjafjalla", "JMCPS_Eyjafjalla", "XSS_Eyjafjalla",
    "VolcanoStone_Eyjafjalla"))
# 多段/激情攻击：兴奋+3
EYJA.update(_many("ex3", "HeartScar_Eyjafjalla", "UrgentPresto_Eyjafjalla", "Chorus_Eyjafjalla",
    "SilentSound_Eyjafjalla", "Duetto_Eyjafjalla", "ThunderCloud_Eyjafjalla", "CXXSS_Eyjafjalla"))
# 灼伤/炎息自热：痛热转快感 兴奋+4
EYJA.update(_many("ex4", "FlameSurround_Eyjafjalla", "FlameMountain_Eyjafjalla", "Ignition_Eyjafjalla",
    "ReIgnition_Eyjafjalla", "FireOfLove_Eyjafjalla", "HappinessAria_Eyjafjalla",
    "SadnessRecitative_Eyjafjalla", "DancingLava_Eyjafjalla", "FireHeart_Eyjafjalla",
    "SpurtOut_Eyjafjalla", "SayToSun_Eyjafjalla"))
# 旋律/咏唱：口枷联动
EYJA.update(_many("gag_ex3", "TreeAngle_Eyjafjalla", "InSunset_Eyjafjalla", "Sailing_Eyjafjalla",
    "TheEnd_Eyjafjalla", "WaveFlower_Eyjafjalla", "GoldAndRoses_Eyjafjalla", "BlazePrelude_Eyjafjalla",
    "AnswerInWind_Eyjafjalla", "TranceDream_Eyjafjalla", "Songs_Eyjafjalla", "SongMary_Eyjafjalla",
    "SongFarmer_Eyjafjalla", "SongBoat_Eyjafjalla"))
# 心音/检索抽牌：跳蛋联动
EYJA.update(_many("vibe_draw", "ResonanceDefend_Eyjafjalla", "HeartFeel_Eyjafjalla",
    "DreamCradle_Eyjafjalla", "LittleFeelings_Eyjafjalla", "Marshmallow_Eyjafjalla",
    "Shuttle_Eyjafjalla", "ClearDream_Eyjafjalla", "PracticalJoke_Eyjafjalla", "FKY_Eyjafjalla",
    "MTFKY_Eyjafjalla", "FXZ_Eyjafjalla", "LJFXZ_Eyjafjalla", "BurnGround_Eyjafjalla"))
# 云层/甜梦：受孕+3
EYJA.update(_many("conc3", "IntoDream_Eyjafjalla", "RiseUp_Eyjafjalla", "SevenColor_Eyjafjalla",
    "Morning_Eyjafjalla", "Afterglow_Eyjafjalla", "CloudSilk_Eyjafjalla", "Fairy_Eyjafjalla",
    "HeartInCandy_Eyjafjalla", "CloudCake_Eyjafjalla", "FollowHeart_Eyjafjalla",
    "SoLongAdele_Eyjafjalla", "PinkOcean_Eyjafjalla", "Cloud_Eyjafjalla", "UnknownCloud_Eyjafjalla",
    "HPY_Eyjafjalla", "DollyInvitation_Eyjafjalla", "TheLovedOne_Eyjafjalla", "TimeOfLove_Eyjafjalla",
    "BetweenUs_Eyjafjalla", "MissSound_Eyjafjalla", "ResearchOnNature_Eyjafjalla",
    "LetRainGo_Eyjafjalla", "CloudMelody_Eyjafjalla", "Inherit_Eyjafjalla", "SongOfLove_Eyjafjalla"))
# 守护/能力滋养：受胎+3
EYJA.update(_many("fert3", "DenseMist_Eyjafjalla", "VolcanicHealing_Eyjafjalla",
    "SilentWet_Eyjafjalla", "DGDGZ_Eyjafjalla"))
# 格挡类：兴奋≥30格挡联动
EYJA.update(_many("blk30_2", "Defend_Eyjafjalla", "FlutteringBreeze_Eyjafjalla",
    "CloudyBarrier_Eyjafjalla", "TQB_Eyjafjalla", "LTTQB_Eyjafjalla", "LLH_Eyjafjalla",
    "NSLLH_Eyjafjalla", "DGZ_Eyjafjalla", "LiveStone_Eyjafjalla", "PrepareBeforeCamp_Eyjafjalla",
    "CloudCrack_Eyjafjalla"))
# 按捺：压制欲望
EYJA["Push_Eyjafjalla"] = ["calm4"]
# 大招：兴奋≥50本源+1 + 淫纹联动
for i in ("Volcano_Eyjafjalla", "Darkside_Eyjafjalla", "FlameBurst_Eyjafjalla",
          "PulseOfTerra_Eyjafjalla", "Dreiton_Eyjafjalla"):
    EYJA[i] = ["mech50_1", "crest_ex4"]

# ---- 缪尔赛思本体卡（140张）----
MUEL = {}
# 协同轻攻击：兴奋+2
MUEL.update(_many("ex2", "WaterStrike_Muelsyse", "WaterStrikePro_Muelsyse", "Bright_Muelsyse",
    "Geometry_Muelsyse", "ScatterSpore_Muelsyse", "Starter_Muelsyse", "GoldenWander_Muelsyse",
    "StarMove_Muelsyse"))
# 大水/连击攻击：兴奋+3
MUEL.update(_many("ex3", "WaterStrikeEnd_Muelsyse", "FractalAttack_Muelsyse", "PoisonIvy_Muelsyse",
    "HugOfRegret_Muelsyse", "ApartWhenLoss_Muelsyse", "WaterWave_Muelsyse", "ExplosionMagic_Muelsyse",
    "CondensateWish_Muelsyse", "LiftStar_Muelsyse", "Suzuran_Muelsyse", "WhiteRose_Muelsyse",
    "MillenniumVine_Muelsyse", "VowAndEvolution_Muelsyse", "SourceTogether_Muelsyse",
    "GraduallyWater_Muelsyse", "FluidPower_Muelsyse"))
# 全身濡湿：兴奋+4
MUEL.update(_many("ex4", "WaterSilently_Muelsyse", "ShallowAdaption_Muelsyse", "WaterRecycle_Muelsyse"))
# 栽培/播种/花：受孕+3（授粉主题）
MUEL.update(_many("conc3", "SongOfRose_Muelsyse", "SpringWord_Muelsyse", "Seed_Muelsyse",
    "FullBlown_Muelsyse", "SoilSpread_Muelsyse", "SoilExhaust_Muelsyse", "BornShoots_Muelsyse",
    "LifeAndWind_Muelsyse", "EcologyCycle_Muelsyse", "Shoot_Muelsyse", "SpruceInCage_Muelsyse",
    "PlantAStar_Muelsyse", "EndTime_Muelsyse", "DeGold_Muelsyse", "EcologicalProject_Muelsyse",
    "ModeCultivate_Muelsyse", "FallenSeasons_Muelsyse", "StarLaurel_Muelsyse",
    "LingerFragrance_Muelsyse", "CrazyWord_Muelsyse", "SourceOfLife_Muelsyse", "MatrixWave_Muelsyse",
    "MyShadow_Muelsyse", "SeriousSpawn_Muelsyse"))
MUEL["LifeDeduction_Muelsyse"] = ["conc4"]
# 养料/塑形：受胎+3
MUEL.update(_many("fert3", "Orchid_Muelsyse", "FineBlend_Muelsyse", "CompositeNutrient_Muelsyse",
    "Reflux_Muelsyse", "LightFlow_Muelsyse", "TransRapid_Muelsyse", "TransCleanWater_Muelsyse",
    "SoilBack_Muelsyse", "UnknownFluid_Muelsyse", "DeepReflect_Muelsyse", "CurseOfSpirit_Muelsyse",
    "EcologicalCoupling_Muelsyse", "FlowerSpread_Muelsyse", "FlowerParasitism_Muelsyse",
    "FlowerCure_Muelsyse", "FlowerWater_Muelsyse"))
# 漫步/检索：跳蛋联动
MUEL.update(_many("vibe_draw", "TerraPatrol_Muelsyse", "Reconfiguration_Muelsyse",
    "InverseChange_Muelsyse", "DorothyVision_Muelsyse", "MeetInSnow_Muelsyse", "TimeRiver_Muelsyse",
    "UncertainForm_Muelsyse", "RemainDust_Muelsyse", "LivingEcho_Muelsyse", "LoneStar_Muelsyse",
    "CloudyBarrier_Muelsyse", "Mibo_Muelsyse"))
MUEL["ForSeek_Muelsyse"] = ["edge_energy"]
MUEL["Hypothermia_Muelsyse"] = ["calm4"]
# 格挡类
MUEL.update(_many("blk30_2", "WaterDefend_Muelsyse", "LossShield_Muelsyse", "Rest_Muelsyse",
    "Trail_Muelsyse", "Irrigate_Muelsyse", "DreamBubble_Muelsyse", "WaterShield_Muelsyse",
    "Revive_Muelsyse", "NoManMachine_Muelsyse", "BlueHyacinth_Muelsyse", "LifeRipple_Muelsyse"))
MUEL["ItIs_Muelsyse"] = ["gearblk1"]
# 大招
for i in ("WaterStrikeO_Muelsyse", "Ocean_Muelsyse", "RainWorld_Muelsyse", "Dandelion_Muelsyse",
          "ChorusFlower_Muelsyse"):
    MUEL[i] = ["mech50_1", "crest_ex4"]
# 复制卡：攻击型分身=兴奋+2，其余=被分身环绕 受孕+3
for i in ("Ifrit", "Typhon", "Greyy", "Kafka", "Dorothy", "Astgenne", "Amiya"):
    MUEL[f"{i}_Muelsyse"] = ["ex2"]
for i in ("Saria", "Mechanist", "Silence", "Ptilopsis", "Megallan", "Mayer", "Saileach", "Reed",
          "SilverAsh", "Astesia", "Tulip", "Pepe", "Narantuya", "Shu", "Eyjafjalla", "SilenceP",
          "Marcille", "TinMan", "Theresa", "Isharmla"):
    MUEL[f"{i}_Muelsyse"] = ["conc3"]

TABLE.update(EYJA)
TABLE.update(MUEL)

HEADER = '''package arknsfw.helpers;

import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.CardLibrary;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import liesecore.helpers.NsfwRunStats;

import java.util.HashMap;
import java.util.Map;

/**
 * 堕落模式·本体卡色情附加（由 gen_card_riders.py 生成）：
 * 五名自制角色的本体 SFW 卡逐张手工指定附加效果（与卡牌机制身份匹配，
 * 并与拘束/欲望装备联动）；艾雅/缪尔本体卡按卡牌属性定制。
 * 原效果完全保留，堕落模式下卡牌描述追加 [堕落] 行。
 */
public final class ArkFallCardRider {

    private static final Map<String, Runnable> TABLE = new HashMap<>();
    private static final Map<String, String> DESCS = new HashMap<>();
    private static boolean heuristicsBuilt = false;
    private static boolean descApplied = false;

    private ArkFallCardRider() {
    }

    // ---- 原语 ----
    private static void ex(int n) { ArkSafeStats.addExcitementDeferred(n); }
    private static void conc(int n) { ArkSafeStats.addConceptionDeferred(n, false); }
    private static void fert(int n) { ArkSafeStats.addFertilityDeferred(n, 0, false); }
    private static void blk(int n) {
        if (n > 0 && AbstractDungeon.player != null) {
            AbstractDungeon.actionManager.addToBottom(new GainBlockAction(AbstractDungeon.player, n));
        }
    }
    private static void draw(int n) {
        AbstractDungeon.actionManager.addToBottom(new DrawCardAction(AbstractDungeon.player, n));
    }
    private static void energy(int n) {
        AbstractDungeon.actionManager.addToBottom(new GainEnergyAction(n));
    }
    private static boolean has(String relicId) {
        return AbstractDungeon.player != null && AbstractDungeon.player.hasRelic(relicId);
    }
    private static boolean exAt(int t) { return NsfwRunStats.excitement >= t; }
    private static boolean nearClimax(int within) {
        int th = LieseCompat.climaxThreshold();
        return th > 0 && NsfwRunStats.excitement >= th - within && NsfwRunStats.excitement < th;
    }
    /** 本源机制+n：按当前角色分发（收割/取景/瞄准/花火/咒灵/云量/溯源）。 */
    private static void mechPlus(int n) {
        AbstractPlayer p = AbstractDungeon.player;
        if (p == null) { return; }
        if (ArkCharacterSetup.isHighmoreRun()) { ArkCharMechanicsHelper.applyReapPower(p, n); }
        else if (ArkCharacterSetup.isSceneRun()) { ArkCharMechanicsHelper.applyFocusPower(p, n); }
        else if (ArkCharacterSetup.isArchettoRun()) { ArkCharMechanicsHelper.applyAimPower(p, n); }
        else if (ArkCharacterSetup.isHarukaRun()) { ArkCharMechanicsHelper.applyPyroPower(p, n); }
        else if (ArkCharacterSetup.isNymphRun()) { ArkCharMechanicsHelper.applyHexPower(p, n); }
        else if (ArkCharacterSetup.isEyjaRun()) { ArkCharMechanicsHelper.gainCloudEnergy(n); }
        else if (ArkCharacterSetup.isMuelsyseRun()) { ArkCharMechanicsHelper.applyRootage(p, n); }
    }

    // ---- 入口 ----
    public static void onCardUsed(AbstractCard card) {
        if (card == null || !ArkFallMode.active() || AbstractDungeon.player == null) {
            return;
        }
        AbstractRoom room = AbstractDungeon.getCurrRoom();
        if (room == null || room.phase != AbstractRoom.RoomPhase.COMBAT) {
            return;
        }
        buildHeuristics();
        Runnable r = TABLE.get(card.cardID);
        if (r != null) {
            try { r.run(); } catch (Exception ignored) { }
        }
    }

    /** 堕落模式启用时：给表内卡牌的描述追加 [堕落] 行（重启后文本生效，效果实时）。 */
    public static void applyDescriptions() {
        if (descApplied || !ArkFallMode.enabled()) {
            return;
        }
        descApplied = true;
        buildHeuristics();
        for (Map.Entry<String, String> e : DESCS.entrySet()) {
            try {
                CardStrings cs = CardCrawlGame.languagePack.getCardStrings(e.getKey());
                if (cs == null || cs.DESCRIPTION == null || cs.DESCRIPTION.contains("[堕落]")) {
                    continue;
                }
                String line = " NL [堕落] " + e.getValue();
                cs.DESCRIPTION = cs.DESCRIPTION + line;
                if (cs.UPGRADE_DESCRIPTION != null && !cs.UPGRADE_DESCRIPTION.isEmpty()) {
                    cs.UPGRADE_DESCRIPTION = cs.UPGRADE_DESCRIPTION + line;
                }
                AbstractCard lib = CardLibrary.cards.get(e.getKey());
                if (lib != null) {
                    lib.rawDescription = lib.upgraded && cs.UPGRADE_DESCRIPTION != null
                            ? cs.UPGRADE_DESCRIPTION : cs.DESCRIPTION;
                    lib.initializeDescription();
                }
            } catch (Exception ignored) {
            }
        }
    }

    /** 艾雅/缪尔本体卡：按属性（类型/费用/AoE/消耗/稀有度）定制附加。 */
    private static void buildHeuristics() {
        if (heuristicsBuilt) {
            return;
        }
        heuristicsBuilt = true;
        AbstractCard.CardColor eyja;
        AbstractCard.CardColor muel;
        try {
            eyja = Eyjafjalla.modcore.ColorEnum.Eyjafjalla_COLOR;
            muel = Muelsyse.patches.ColorEnum.Muelsyse_COLOR;
        } catch (Throwable t) {
            return;
        }
        for (Map.Entry<String, AbstractCard> e : CardLibrary.cards.entrySet()) {
            AbstractCard c = e.getValue();
            if (c == null || (c.color != eyja && c.color != muel)) { continue; }
            String id = c.cardID;
            if (id.startsWith("arknsfw:") || TABLE.containsKey(id)) { continue; }
            if (c.type == AbstractCard.CardType.CURSE || c.type == AbstractCard.CardType.STATUS) { continue; }
            registerHeuristic(id, c);
        }
    }

    private static void registerHeuristic(String id, AbstractCard c) {
        final boolean aoe = c.target == AbstractCard.CardTarget.ALL_ENEMY
                || c.target == AbstractCard.CardTarget.ALL;
        final boolean big = c.cost >= 2;
        final boolean rare = c.rarity == AbstractCard.CardRarity.RARE;
        final boolean hasBlock = c.baseBlock > 0;
        if (c.type == AbstractCard.CardType.ATTACK) {
            if (rare) {
                TABLE.put(id, () -> { ex(big ? 4 : 3); if (exAt(50)) { mechPlus(1); }
                        if (has("arknsfw:BodyCrestRelic")) { ex(4); } });
                DESCS.put(id, "兴奋+" + (big ? 4 : 3) + "；兴奋≥50：本源机制+1；戴淫纹：兴奋+4。");
            } else if (aoe) {
                TABLE.put(id, () -> ex(3));
                DESCS.put(id, "兴奋+3。");
            } else if (big) {
                TABLE.put(id, () -> { ex(2); conc(3); });
                DESCS.put(id, "兴奋+2；受孕+3。");
            } else {
                TABLE.put(id, () -> ex(2));
                DESCS.put(id, "兴奋+2。");
            }
        } else if (c.type == AbstractCard.CardType.SKILL) {
            if (hasBlock) {
                TABLE.put(id, () -> { if (exAt(30)) { blk(2 + (has("arknsfw:RopeBindRelic") ? 2 : 0)); } });
                DESCS.put(id, "兴奋≥30：+2格挡（绳缚再+2）。");
            } else if (c.exhaust) {
                TABLE.put(id, () -> ex(4));
                DESCS.put(id, "兴奋+4。");
            } else {
                TABLE.put(id, () -> { if (has("arknsfw:VibeEggRelic")) { ex(2); if (exAt(60)) { draw(1); } } });
                DESCS.put(id, "戴跳蛋：兴奋+2，兴奋≥60再抽1。");
            }
        } else if (c.type == AbstractCard.CardType.POWER) {
            TABLE.put(id, () -> conc(4));
            DESCS.put(id, "受孕+4。");
        }
    }

    // ---- 五角色本体卡手工效果表 ----
    static {
%TABLE%
    }
}
'''


def main():
    lines = []
    for cid, keys in TABLE.items():
        java = " ".join(FX[k][0] for k in keys)
        desc = "；".join(FX[k][1] for k in keys) + "。"
        lines.append(f'        TABLE.put("{cid}", () -> {{ {java} }});')
        lines.append(f'        DESCS.put("{cid}", "{desc}");')
    src = HEADER.replace("%TABLE%", "\n".join(lines))
    out = "ArknightsNsfwBridge/src/main/java/arknsfw/helpers/ArkFallCardRider.java"
    open(out, "w", encoding="utf-8").write(src)
    print(f"rider table: {len(TABLE)} cards -> {out}")


if __name__ == "__main__":
    main()
