package arknsfw.helpers;

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
        TABLE.put("highmore:Strike", () -> { ex(2); });
        DESCS.put("highmore:Strike", "兴奋+2。");
        TABLE.put("highmore:Jab", () -> { ex(2); });
        DESCS.put("highmore:Jab", "兴奋+2。");
        TABLE.put("highmore:Cut", () -> { ex(2); });
        DESCS.put("highmore:Cut", "兴奋+2。");
        TABLE.put("highmore:Defend", () -> { if (exAt(30)) { blk(2 + (has("arknsfw:RopeBindRelic") ? 2 : 0)); } });
        DESCS.put("highmore:Defend", "兴奋≥30：+2格挡（绳缚再+2）。");
        TABLE.put("highmore:Ward", () -> { if (exAt(30)) { blk(2 + (has("arknsfw:RopeBindRelic") ? 2 : 0)); } });
        DESCS.put("highmore:Ward", "兴奋≥30：+2格挡（绳缚再+2）。");
        TABLE.put("highmore:Surge", () -> { conc(3); });
        DESCS.put("highmore:Surge", "受孕+3。");
        TABLE.put("highmore:BulkStrike01", () -> { ex(2); });
        DESCS.put("highmore:BulkStrike01", "兴奋+2。");
        TABLE.put("highmore:BulkGuard02", () -> { if (exAt(30)) { blk(2 + (has("arknsfw:RopeBindRelic") ? 2 : 0)); } });
        DESCS.put("highmore:BulkGuard02", "兴奋≥30：+2格挡（绳缚再+2）。");
        TABLE.put("highmore:BulkDraw03", () -> { if (has("arknsfw:VibeEggRelic")) { ex(2); if (exAt(60)) { draw(1); } } });
        DESCS.put("highmore:BulkDraw03", "戴跳蛋：兴奋+2，兴奋≥60再抽1。");
        TABLE.put("highmore:BulkSweep04", () -> { ex(3); });
        DESCS.put("highmore:BulkSweep04", "兴奋+3。");
        TABLE.put("highmore:BulkFinale05", () -> { if (exAt(50)) { mechPlus(1); } if (has("arknsfw:BodyCrestRelic")) { ex(4); } });
        DESCS.put("highmore:BulkFinale05", "兴奋≥50：本源机制+1；戴淫纹：兴奋+4。");
        TABLE.put("highmore:BulkStrike06", () -> { ex(2); });
        DESCS.put("highmore:BulkStrike06", "兴奋+2。");
        TABLE.put("highmore:BulkGuard07", () -> { if (exAt(30)) { blk(2 + (has("arknsfw:RopeBindRelic") ? 2 : 0)); } });
        DESCS.put("highmore:BulkGuard07", "兴奋≥30：+2格挡（绳缚再+2）。");
        TABLE.put("highmore:BulkDraw08", () -> { if (has("arknsfw:VibeEggRelic")) { ex(2); if (exAt(60)) { draw(1); } } });
        DESCS.put("highmore:BulkDraw08", "戴跳蛋：兴奋+2，兴奋≥60再抽1。");
        TABLE.put("highmore:BulkFinale30", () -> { if (exAt(50)) { mechPlus(1); } if (has("arknsfw:BodyCrestRelic")) { ex(4); } });
        DESCS.put("highmore:BulkFinale30", "兴奋≥50：本源机制+1；戴淫纹：兴奋+4。");
        TABLE.put("highmore:TideReap", () -> { if (exAt(50)) { mechPlus(1); } });
        DESCS.put("highmore:TideReap", "兴奋≥50：本源机制+1。");
        TABLE.put("highmore:ScytheSwing", () -> { ex(2); if (has("arknsfw:BodyCrestRelic")) { ex(4); } });
        DESCS.put("highmore:ScytheSwing", "兴奋+2；戴淫纹：兴奋+4。");
        TABLE.put("highmore:SaltHarvest", () -> { if (exAt(50)) { mechPlus(1); } });
        DESCS.put("highmore:SaltHarvest", "兴奋≥50：本源机制+1。");
        TABLE.put("highmore:BrineWall", () -> { if (exAt(30)) { blk(2 + (has("arknsfw:RopeBindRelic") ? 2 : 0)); } });
        DESCS.put("highmore:BrineWall", "兴奋≥30：+2格挡（绳缚再+2）。");
        TABLE.put("highmore:BloodRush", () -> { ex(4); });
        DESCS.put("highmore:BloodRush", "兴奋+4。");
        TABLE.put("highmore:WhirlReap", () -> { ex(3); });
        DESCS.put("highmore:WhirlReap", "兴奋+3。");
        TABLE.put("highmore:DeadDrift", () -> { conc(4); });
        DESCS.put("highmore:DeadDrift", "受孕+4。");
        TABLE.put("highmore:AbyssPull", () -> { blk(Math.min(8, arknsfw.helpers.ArkGearSetHelper.equipmentCount())); });
        DESCS.put("highmore:AbyssPull", "每件拘束装备+1格挡（至多8）。");
        TABLE.put("highmore:BloodScale", () -> { ex(3); fert(3); });
        DESCS.put("highmore:BloodScale", "兴奋+3；受胎+3。");
        TABLE.put("highmore:RipTide", () -> { if (exAt(50)) { mechPlus(1); } });
        DESCS.put("highmore:RipTide", "兴奋≥50：本源机制+1。");
        TABLE.put("highmore:GreatTide", () -> { ex(6); });
        DESCS.put("highmore:GreatTide", "兴奋+6。");
        TABLE.put("highmore:AbyssGaze", () -> { conc(4); });
        DESCS.put("highmore:AbyssGaze", "受孕+4。");
        TABLE.put("highmore:DrownEmbrace", () -> { ex(8); });
        DESCS.put("highmore:DrownEmbrace", "兴奋+8。");
        TABLE.put("highmore:SaltDraw", () -> { ex(3); });
        DESCS.put("highmore:SaltDraw", "兴奋+3。");
        TABLE.put("highmore:TidePool", () -> { if (exAt(30)) { blk(2 + (has("arknsfw:RopeBindRelic") ? 2 : 0)); } });
        DESCS.put("highmore:TidePool", "兴奋≥30：+2格挡（绳缚再+2）。");
        TABLE.put("scene:Strike", () -> { ex(2); });
        DESCS.put("scene:Strike", "兴奋+2。");
        TABLE.put("scene:Jab", () -> { ex(2); });
        DESCS.put("scene:Jab", "兴奋+2。");
        TABLE.put("scene:Cut", () -> { ex(2); });
        DESCS.put("scene:Cut", "兴奋+2。");
        TABLE.put("scene:Defend", () -> { if (exAt(30)) { blk(2 + (has("arknsfw:RopeBindRelic") ? 2 : 0)); } });
        DESCS.put("scene:Defend", "兴奋≥30：+2格挡（绳缚再+2）。");
        TABLE.put("scene:Ward", () -> { if (exAt(30)) { blk(2 + (has("arknsfw:RopeBindRelic") ? 2 : 0)); } });
        DESCS.put("scene:Ward", "兴奋≥30：+2格挡（绳缚再+2）。");
        TABLE.put("scene:Surge", () -> { conc(3); });
        DESCS.put("scene:Surge", "受孕+3。");
        TABLE.put("scene:BulkStrike01", () -> { ex(2); });
        DESCS.put("scene:BulkStrike01", "兴奋+2。");
        TABLE.put("scene:BulkGuard02", () -> { if (exAt(30)) { blk(2 + (has("arknsfw:RopeBindRelic") ? 2 : 0)); } });
        DESCS.put("scene:BulkGuard02", "兴奋≥30：+2格挡（绳缚再+2）。");
        TABLE.put("scene:BulkDraw03", () -> { if (has("arknsfw:VibeEggRelic")) { ex(2); if (exAt(60)) { draw(1); } } });
        DESCS.put("scene:BulkDraw03", "戴跳蛋：兴奋+2，兴奋≥60再抽1。");
        TABLE.put("scene:BulkSweep04", () -> { ex(3); });
        DESCS.put("scene:BulkSweep04", "兴奋+3。");
        TABLE.put("scene:BulkFinale05", () -> { if (exAt(50)) { mechPlus(1); } if (has("arknsfw:BodyCrestRelic")) { ex(4); } });
        DESCS.put("scene:BulkFinale05", "兴奋≥50：本源机制+1；戴淫纹：兴奋+4。");
        TABLE.put("scene:BulkStrike06", () -> { ex(2); });
        DESCS.put("scene:BulkStrike06", "兴奋+2。");
        TABLE.put("scene:BulkGuard07", () -> { if (exAt(30)) { blk(2 + (has("arknsfw:RopeBindRelic") ? 2 : 0)); } });
        DESCS.put("scene:BulkGuard07", "兴奋≥30：+2格挡（绳缚再+2）。");
        TABLE.put("scene:BulkDraw08", () -> { if (has("arknsfw:VibeEggRelic")) { ex(2); if (exAt(60)) { draw(1); } } });
        DESCS.put("scene:BulkDraw08", "戴跳蛋：兴奋+2，兴奋≥60再抽1。");
        TABLE.put("scene:BulkFinale30", () -> { if (exAt(50)) { mechPlus(1); } if (has("arknsfw:BodyCrestRelic")) { ex(4); } });
        DESCS.put("scene:BulkFinale30", "兴奋≥50：本源机制+1；戴淫纹：兴奋+4。");
        TABLE.put("scene:FocusShot", () -> { if (has("arknsfw:LaceBlindfoldRelic")) { mechPlus(1); } });
        DESCS.put("scene:FocusShot", "戴蕾丝眼罩：本源机制+1。");
        TABLE.put("scene:ShutterGuard", () -> { if (exAt(30)) { blk(2 + (has("arknsfw:RopeBindRelic") ? 2 : 0)); } });
        DESCS.put("scene:ShutterGuard", "兴奋≥30：+2格挡（绳缚再+2）。");
        TABLE.put("scene:Snapshot", () -> { ex(3); if (has("arknsfw:BodyCrestRelic")) { ex(4); } });
        DESCS.put("scene:Snapshot", "兴奋+3；戴淫纹：兴奋+4。");
        TABLE.put("scene:LensDraw", () -> { if (has("arknsfw:VibeEggRelic")) { ex(2); if (exAt(60)) { draw(1); } } });
        DESCS.put("scene:LensDraw", "戴跳蛋：兴奋+2，兴奋≥60再抽1。");
        TABLE.put("scene:PanScan", () -> { if (has("arknsfw:LaceBlindfoldRelic")) { mechPlus(1); } });
        DESCS.put("scene:PanScan", "戴蕾丝眼罩：本源机制+1。");
        TABLE.put("scene:LongExposure", () -> { if (exAt(50)) { mechPlus(1); } });
        DESCS.put("scene:LongExposure", "兴奋≥50：本源机制+1。");
        TABLE.put("scene:Develop", () -> { conc(4); });
        DESCS.put("scene:Develop", "受孕+4。");
        TABLE.put("scene:FreezeFrame", () -> { if (exAt(30)) { blk(2 + (has("arknsfw:RopeBindRelic") ? 2 : 0)); } });
        DESCS.put("scene:FreezeFrame", "兴奋≥30：+2格挡（绳缚再+2）。");
        TABLE.put("scene:DevelopRush", () -> { if (has("arknsfw:VibeEggRelic")) { ex(2); if (exAt(60)) { draw(1); } } });
        DESCS.put("scene:DevelopRush", "戴跳蛋：兴奋+2，兴奋≥60再抽1。");
        TABLE.put("scene:WideFrame", () -> { blk(Math.min(8, arknsfw.helpers.ArkGearSetHelper.equipmentCount())); });
        DESCS.put("scene:WideFrame", "每件拘束装备+1格挡（至多8）。");
        TABLE.put("scene:BurstShutter", () -> { ex(3); });
        DESCS.put("scene:BurstShutter", "兴奋+3。");
        TABLE.put("scene:Negative", () -> { ex(6); });
        DESCS.put("scene:Negative", "兴奋+6。");
        TABLE.put("scene:OverExposure", () -> { ex(4); });
        DESCS.put("scene:OverExposure", "兴奋+4。");
        TABLE.put("scene:TimerShutter", () -> { if (nearClimax(10)) { energy(1); } });
        DESCS.put("scene:TimerShutter", "濒临高潮（距阈值10内）：+1能量。");
        TABLE.put("scene:DarkroomWash", () -> { conc(4); });
        DESCS.put("scene:DarkroomWash", "受孕+4。");
        TABLE.put("scene:MasterLens", () -> { conc(4); });
        DESCS.put("scene:MasterLens", "受孕+4。");
        TABLE.put("archetto:Strike", () -> { ex(2); });
        DESCS.put("archetto:Strike", "兴奋+2。");
        TABLE.put("archetto:Jab", () -> { ex(2); });
        DESCS.put("archetto:Jab", "兴奋+2。");
        TABLE.put("archetto:Cut", () -> { ex(2); });
        DESCS.put("archetto:Cut", "兴奋+2。");
        TABLE.put("archetto:Defend", () -> { if (exAt(30)) { blk(2 + (has("arknsfw:RopeBindRelic") ? 2 : 0)); } });
        DESCS.put("archetto:Defend", "兴奋≥30：+2格挡（绳缚再+2）。");
        TABLE.put("archetto:Ward", () -> { if (exAt(30)) { blk(2 + (has("arknsfw:RopeBindRelic") ? 2 : 0)); } });
        DESCS.put("archetto:Ward", "兴奋≥30：+2格挡（绳缚再+2）。");
        TABLE.put("archetto:Surge", () -> { conc(3); });
        DESCS.put("archetto:Surge", "受孕+3。");
        TABLE.put("archetto:BulkStrike01", () -> { ex(2); });
        DESCS.put("archetto:BulkStrike01", "兴奋+2。");
        TABLE.put("archetto:BulkGuard02", () -> { if (exAt(30)) { blk(2 + (has("arknsfw:RopeBindRelic") ? 2 : 0)); } });
        DESCS.put("archetto:BulkGuard02", "兴奋≥30：+2格挡（绳缚再+2）。");
        TABLE.put("archetto:BulkDraw03", () -> { if (has("arknsfw:VibeEggRelic")) { ex(2); if (exAt(60)) { draw(1); } } });
        DESCS.put("archetto:BulkDraw03", "戴跳蛋：兴奋+2，兴奋≥60再抽1。");
        TABLE.put("archetto:BulkSweep04", () -> { ex(3); });
        DESCS.put("archetto:BulkSweep04", "兴奋+3。");
        TABLE.put("archetto:BulkFinale05", () -> { if (exAt(50)) { mechPlus(1); } if (has("arknsfw:BodyCrestRelic")) { ex(4); } });
        DESCS.put("archetto:BulkFinale05", "兴奋≥50：本源机制+1；戴淫纹：兴奋+4。");
        TABLE.put("archetto:BulkStrike06", () -> { ex(2); });
        DESCS.put("archetto:BulkStrike06", "兴奋+2。");
        TABLE.put("archetto:BulkGuard07", () -> { if (exAt(30)) { blk(2 + (has("arknsfw:RopeBindRelic") ? 2 : 0)); } });
        DESCS.put("archetto:BulkGuard07", "兴奋≥30：+2格挡（绳缚再+2）。");
        TABLE.put("archetto:BulkDraw08", () -> { if (has("arknsfw:VibeEggRelic")) { ex(2); if (exAt(60)) { draw(1); } } });
        DESCS.put("archetto:BulkDraw08", "戴跳蛋：兴奋+2，兴奋≥60再抽1。");
        TABLE.put("archetto:BulkFinale30", () -> { if (exAt(50)) { mechPlus(1); } if (has("arknsfw:BodyCrestRelic")) { ex(4); } });
        DESCS.put("archetto:BulkFinale30", "兴奋≥50：本源机制+1；戴淫纹：兴奋+4。");
        TABLE.put("archetto:QuickShot", () -> { if (has("arknsfw:LaceBlindfoldRelic")) { mechPlus(1); } });
        DESCS.put("archetto:QuickShot", "戴蕾丝眼罩：本源机制+1。");
        TABLE.put("archetto:MarkTarget", () -> { if (has("arknsfw:LaceBlindfoldRelic")) { mechPlus(1); } });
        DESCS.put("archetto:MarkTarget", "戴蕾丝眼罩：本源机制+1。");
        TABLE.put("archetto:BurstArrow", () -> { ex(2); });
        DESCS.put("archetto:BurstArrow", "兴奋+2。");
        TABLE.put("archetto:Reload", () -> { if (has("arknsfw:VibeEggRelic")) { ex(2); if (exAt(60)) { draw(1); } } });
        DESCS.put("archetto:Reload", "戴跳蛋：兴奋+2，兴奋≥60再抽1。");
        TABLE.put("archetto:FanVolley", () -> { ex(3); });
        DESCS.put("archetto:FanVolley", "兴奋+3。");
        TABLE.put("archetto:WindPrep", () -> { if (exAt(30)) { blk(2 + (has("arknsfw:RopeBindRelic") ? 2 : 0)); } });
        DESCS.put("archetto:WindPrep", "兴奋≥30：+2格挡（绳缚再+2）。");
        TABLE.put("archetto:Concerto", () -> { conc(4); });
        DESCS.put("archetto:Concerto", "受孕+4。");
        TABLE.put("archetto:PierceShot", () -> { ex(2); if (has("arknsfw:BodyCrestRelic")) { ex(4); } });
        DESCS.put("archetto:PierceShot", "兴奋+2；戴淫纹：兴奋+4。");
        TABLE.put("archetto:SteadyBreath", () -> { ex(-4); });
        DESCS.put("archetto:SteadyBreath", "兴奋-4。");
        TABLE.put("archetto:TrueShot", () -> { if (exAt(50)) { mechPlus(1); } });
        DESCS.put("archetto:TrueShot", "兴奋≥50：本源机制+1。");
        TABLE.put("archetto:TripleArrow", () -> { ex(3); });
        DESCS.put("archetto:TripleArrow", "兴奋+3。");
        TABLE.put("archetto:HawkEye", () -> { conc(4); });
        DESCS.put("archetto:HawkEye", "受孕+4。");
        TABLE.put("archetto:SnapString", () -> { ex(4); if (has("arknsfw:BodyCrestRelic")) { ex(4); } });
        DESCS.put("archetto:SnapString", "兴奋+4；戴淫纹：兴奋+4。");
        TABLE.put("archetto:QuiverRefill", () -> { if (nearClimax(10)) { energy(1); } });
        DESCS.put("archetto:QuiverRefill", "濒临高潮（距阈值10内）：+1能量。");
        TABLE.put("archetto:SwiftStep", () -> { if (exAt(30)) { blk(2 + (has("arknsfw:RopeBindRelic") ? 2 : 0)); } });
        DESCS.put("archetto:SwiftStep", "兴奋≥30：+2格挡（绳缚再+2）。");
        TABLE.put("archetto:ArrowStorm", () -> { ex(3); });
        DESCS.put("archetto:ArrowStorm", "兴奋+3。");
        TABLE.put("haruka:Strike", () -> { ex(2); });
        DESCS.put("haruka:Strike", "兴奋+2。");
        TABLE.put("haruka:Jab", () -> { ex(2); });
        DESCS.put("haruka:Jab", "兴奋+2。");
        TABLE.put("haruka:Cut", () -> { ex(2); });
        DESCS.put("haruka:Cut", "兴奋+2。");
        TABLE.put("haruka:Defend", () -> { if (exAt(30)) { blk(2 + (has("arknsfw:RopeBindRelic") ? 2 : 0)); } });
        DESCS.put("haruka:Defend", "兴奋≥30：+2格挡（绳缚再+2）。");
        TABLE.put("haruka:Ward", () -> { if (exAt(30)) { blk(2 + (has("arknsfw:RopeBindRelic") ? 2 : 0)); } });
        DESCS.put("haruka:Ward", "兴奋≥30：+2格挡（绳缚再+2）。");
        TABLE.put("haruka:Surge", () -> { conc(3); });
        DESCS.put("haruka:Surge", "受孕+3。");
        TABLE.put("haruka:BulkStrike01", () -> { ex(2); });
        DESCS.put("haruka:BulkStrike01", "兴奋+2。");
        TABLE.put("haruka:BulkGuard02", () -> { if (exAt(30)) { blk(2 + (has("arknsfw:RopeBindRelic") ? 2 : 0)); } });
        DESCS.put("haruka:BulkGuard02", "兴奋≥30：+2格挡（绳缚再+2）。");
        TABLE.put("haruka:BulkDraw03", () -> { if (has("arknsfw:VibeEggRelic")) { ex(2); if (exAt(60)) { draw(1); } } });
        DESCS.put("haruka:BulkDraw03", "戴跳蛋：兴奋+2，兴奋≥60再抽1。");
        TABLE.put("haruka:BulkSweep04", () -> { ex(3); });
        DESCS.put("haruka:BulkSweep04", "兴奋+3。");
        TABLE.put("haruka:BulkFinale05", () -> { if (exAt(50)) { mechPlus(1); } if (has("arknsfw:BodyCrestRelic")) { ex(4); } });
        DESCS.put("haruka:BulkFinale05", "兴奋≥50：本源机制+1；戴淫纹：兴奋+4。");
        TABLE.put("haruka:BulkStrike06", () -> { ex(2); });
        DESCS.put("haruka:BulkStrike06", "兴奋+2。");
        TABLE.put("haruka:BulkGuard07", () -> { if (exAt(30)) { blk(2 + (has("arknsfw:RopeBindRelic") ? 2 : 0)); } });
        DESCS.put("haruka:BulkGuard07", "兴奋≥30：+2格挡（绳缚再+2）。");
        TABLE.put("haruka:BulkDraw08", () -> { if (has("arknsfw:VibeEggRelic")) { ex(2); if (exAt(60)) { draw(1); } } });
        DESCS.put("haruka:BulkDraw08", "戴跳蛋：兴奋+2，兴奋≥60再抽1。");
        TABLE.put("haruka:BulkFinale30", () -> { if (exAt(50)) { mechPlus(1); } if (has("arknsfw:BodyCrestRelic")) { ex(4); } });
        DESCS.put("haruka:BulkFinale30", "兴奋≥50：本源机制+1；戴淫纹：兴奋+4。");
        TABLE.put("haruka:SparkKick", () -> { ex(2); });
        DESCS.put("haruka:SparkKick", "兴奋+2。");
        TABLE.put("haruka:FireworkPrep", () -> { if (exAt(30)) { blk(2 + (has("arknsfw:RopeBindRelic") ? 2 : 0)); } });
        DESCS.put("haruka:FireworkPrep", "兴奋≥30：+2格挡（绳缚再+2）。");
        TABLE.put("haruka:FestivalStep", () -> { if (has("arknsfw:VibeEggRelic")) { ex(2); if (exAt(60)) { draw(1); } } });
        DESCS.put("haruka:FestivalStep", "戴跳蛋：兴奋+2，兴奋≥60再抽1。");
        TABLE.put("haruka:DragonDance", () -> { ex(3); });
        DESCS.put("haruka:DragonDance", "兴奋+3。");
        TABLE.put("haruka:AshGuard", () -> { if (exAt(30)) { blk(2 + (has("arknsfw:RopeBindRelic") ? 2 : 0)); } });
        DESCS.put("haruka:AshGuard", "兴奋≥30：+2格挡（绳缚再+2）。");
        TABLE.put("haruka:FinaleMark", () -> { conc(4); });
        DESCS.put("haruka:FinaleMark", "受孕+4。");
        TABLE.put("haruka:SparkWave", () -> { ex(3); });
        DESCS.put("haruka:SparkWave", "兴奋+3。");
        TABLE.put("haruka:EncoreShout", () -> { if (nearClimax(10)) { energy(1); } });
        DESCS.put("haruka:EncoreShout", "濒临高潮（距阈值10内）：+1能量。");
        TABLE.put("haruka:EarlyBurst", () -> { ex(8); });
        DESCS.put("haruka:EarlyBurst", "兴奋+8。");
        TABLE.put("haruka:GatherFlame", () -> { if (exAt(30)) { blk(2 + (has("arknsfw:RopeBindRelic") ? 2 : 0)); } });
        DESCS.put("haruka:GatherFlame", "兴奋≥30：+2格挡（绳缚再+2）。");
        TABLE.put("haruka:FlameHeart", () -> { conc(4); });
        DESCS.put("haruka:FlameHeart", "受孕+4。");
        TABLE.put("haruka:DuetSpark", () -> { ex(3); });
        DESCS.put("haruka:DuetSpark", "兴奋+3。");
        TABLE.put("haruka:FireTree", () -> { ex(3); });
        DESCS.put("haruka:FireTree", "兴奋+3。");
        TABLE.put("haruka:AddFuel", () -> { if (nearClimax(10)) { energy(1); } });
        DESCS.put("haruka:AddFuel", "濒临高潮（距阈值10内）：+1能量。");
        TABLE.put("haruka:AfterHeat", () -> { blk(Math.min(8, arknsfw.helpers.ArkGearSetHelper.equipmentCount())); });
        DESCS.put("haruka:AfterHeat", "每件拘束装备+1格挡（至多8）。");
        TABLE.put("haruka:FinalDance", () -> { ex(4); if (has("arknsfw:BodyCrestRelic")) { ex(4); } });
        DESCS.put("haruka:FinalDance", "兴奋+4；戴淫纹：兴奋+4。");
        TABLE.put("nymph:Strike", () -> { ex(2); });
        DESCS.put("nymph:Strike", "兴奋+2。");
        TABLE.put("nymph:Jab", () -> { ex(2); });
        DESCS.put("nymph:Jab", "兴奋+2。");
        TABLE.put("nymph:Cut", () -> { ex(2); });
        DESCS.put("nymph:Cut", "兴奋+2。");
        TABLE.put("nymph:Defend", () -> { if (exAt(30)) { blk(2 + (has("arknsfw:RopeBindRelic") ? 2 : 0)); } });
        DESCS.put("nymph:Defend", "兴奋≥30：+2格挡（绳缚再+2）。");
        TABLE.put("nymph:Ward", () -> { if (exAt(30)) { blk(2 + (has("arknsfw:RopeBindRelic") ? 2 : 0)); } });
        DESCS.put("nymph:Ward", "兴奋≥30：+2格挡（绳缚再+2）。");
        TABLE.put("nymph:Surge", () -> { conc(3); });
        DESCS.put("nymph:Surge", "受孕+3。");
        TABLE.put("nymph:BulkStrike01", () -> { ex(2); });
        DESCS.put("nymph:BulkStrike01", "兴奋+2。");
        TABLE.put("nymph:BulkGuard02", () -> { if (exAt(30)) { blk(2 + (has("arknsfw:RopeBindRelic") ? 2 : 0)); } });
        DESCS.put("nymph:BulkGuard02", "兴奋≥30：+2格挡（绳缚再+2）。");
        TABLE.put("nymph:BulkDraw03", () -> { if (has("arknsfw:VibeEggRelic")) { ex(2); if (exAt(60)) { draw(1); } } });
        DESCS.put("nymph:BulkDraw03", "戴跳蛋：兴奋+2，兴奋≥60再抽1。");
        TABLE.put("nymph:BulkSweep04", () -> { ex(3); });
        DESCS.put("nymph:BulkSweep04", "兴奋+3。");
        TABLE.put("nymph:BulkFinale05", () -> { if (exAt(50)) { mechPlus(1); } if (has("arknsfw:BodyCrestRelic")) { ex(4); } });
        DESCS.put("nymph:BulkFinale05", "兴奋≥50：本源机制+1；戴淫纹：兴奋+4。");
        TABLE.put("nymph:BulkStrike06", () -> { ex(2); });
        DESCS.put("nymph:BulkStrike06", "兴奋+2。");
        TABLE.put("nymph:BulkGuard07", () -> { if (exAt(30)) { blk(2 + (has("arknsfw:RopeBindRelic") ? 2 : 0)); } });
        DESCS.put("nymph:BulkGuard07", "兴奋≥30：+2格挡（绳缚再+2）。");
        TABLE.put("nymph:BulkDraw08", () -> { if (has("arknsfw:VibeEggRelic")) { ex(2); if (exAt(60)) { draw(1); } } });
        DESCS.put("nymph:BulkDraw08", "戴跳蛋：兴奋+2，兴奋≥60再抽1。");
        TABLE.put("nymph:BulkFinale30", () -> { if (exAt(50)) { mechPlus(1); } if (has("arknsfw:BodyCrestRelic")) { ex(4); } });
        DESCS.put("nymph:BulkFinale30", "兴奋≥50：本源机制+1；戴淫纹：兴奋+4。");
        TABLE.put("nymph:HexBolt", () -> { if (exAt(50)) { mechPlus(1); } });
        DESCS.put("nymph:HexBolt", "兴奋≥50：本源机制+1。");
        TABLE.put("nymph:FearWhisper", () -> { if (has("arknsfw:ClothGagRelic") || has("arknsfw:RingGagRelic")) { ex(3); } });
        DESCS.put("nymph:FearWhisper", "戴口枷：兴奋+3。");
        TABLE.put("nymph:HeartSeal", () -> { conc(3); });
        DESCS.put("nymph:HeartSeal", "受孕+3。");
        TABLE.put("nymph:CurseWave", () -> { ex(3); });
        DESCS.put("nymph:CurseWave", "兴奋+3。");
        TABLE.put("nymph:DreadGuard", () -> { if (exAt(30)) { blk(2 + (has("arknsfw:RopeBindRelic") ? 2 : 0)); } });
        DESCS.put("nymph:DreadGuard", "兴奋≥30：+2格挡（绳缚再+2）。");
        TABLE.put("nymph:SoulBind", () -> { if (exAt(50)) { mechPlus(1); } });
        DESCS.put("nymph:SoulBind", "兴奋≥50：本源机制+1。");
        TABLE.put("nymph:Nightmare", () -> { if (has("arknsfw:ClothGagRelic") || has("arknsfw:RingGagRelic")) { ex(3); } });
        DESCS.put("nymph:Nightmare", "戴口枷：兴奋+3。");
        TABLE.put("nymph:VoidCut", () -> { ex(2); });
        DESCS.put("nymph:VoidCut", "兴奋+2。");
        TABLE.put("nymph:HexSpread", () -> { ex(4); });
        DESCS.put("nymph:HexSpread", "兴奋+4。");
        TABLE.put("nymph:HeartBite", () -> { ex(2); if (has("arknsfw:BodyCrestRelic")) { ex(4); } });
        DESCS.put("nymph:HeartBite", "兴奋+2；戴淫纹：兴奋+4。");
        TABLE.put("nymph:FearFeast", () -> { if (has("arknsfw:ClothGagRelic") || has("arknsfw:RingGagRelic")) { ex(3); } });
        DESCS.put("nymph:FearFeast", "戴口枷：兴奋+3。");
        TABLE.put("nymph:HexLink", () -> { if (exAt(50)) { mechPlus(1); } });
        DESCS.put("nymph:HexLink", "兴奋≥50：本源机制+1。");
        TABLE.put("nymph:NightEcho", () -> { conc(4); });
        DESCS.put("nymph:NightEcho", "受孕+4。");
        TABLE.put("nymph:SpiritClaw", () -> { ex(2); });
        DESCS.put("nymph:SpiritClaw", "兴奋+2。");
        TABLE.put("nymph:LockTighten", () -> { conc(3); });
        DESCS.put("nymph:LockTighten", "受孕+3。");
        TABLE.put("nymph:ShadowReap", () -> { ex(8); });
        DESCS.put("nymph:ShadowReap", "兴奋+8。");
        TABLE.put("Strike_Eyjafjalla", () -> { ex(2); });
        DESCS.put("Strike_Eyjafjalla", "兴奋+2。");
        TABLE.put("FloorCloud_Eyjafjalla", () -> { ex(2); });
        DESCS.put("FloorCloud_Eyjafjalla", "兴奋+2。");
        TABLE.put("Sunshine_Eyjafjalla", () -> { ex(2); });
        DESCS.put("Sunshine_Eyjafjalla", "兴奋+2。");
        TABLE.put("InstantFireworks_Eyjafjalla", () -> { ex(2); });
        DESCS.put("InstantFireworks_Eyjafjalla", "兴奋+2。");
        TABLE.put("CPS_Eyjafjalla", () -> { ex(2); });
        DESCS.put("CPS_Eyjafjalla", "兴奋+2。");
        TABLE.put("JMCPS_Eyjafjalla", () -> { ex(2); });
        DESCS.put("JMCPS_Eyjafjalla", "兴奋+2。");
        TABLE.put("XSS_Eyjafjalla", () -> { ex(2); });
        DESCS.put("XSS_Eyjafjalla", "兴奋+2。");
        TABLE.put("VolcanoStone_Eyjafjalla", () -> { ex(2); });
        DESCS.put("VolcanoStone_Eyjafjalla", "兴奋+2。");
        TABLE.put("HeartScar_Eyjafjalla", () -> { ex(3); });
        DESCS.put("HeartScar_Eyjafjalla", "兴奋+3。");
        TABLE.put("UrgentPresto_Eyjafjalla", () -> { ex(3); });
        DESCS.put("UrgentPresto_Eyjafjalla", "兴奋+3。");
        TABLE.put("Chorus_Eyjafjalla", () -> { ex(3); });
        DESCS.put("Chorus_Eyjafjalla", "兴奋+3。");
        TABLE.put("SilentSound_Eyjafjalla", () -> { ex(3); });
        DESCS.put("SilentSound_Eyjafjalla", "兴奋+3。");
        TABLE.put("Duetto_Eyjafjalla", () -> { ex(3); });
        DESCS.put("Duetto_Eyjafjalla", "兴奋+3。");
        TABLE.put("ThunderCloud_Eyjafjalla", () -> { ex(3); });
        DESCS.put("ThunderCloud_Eyjafjalla", "兴奋+3。");
        TABLE.put("CXXSS_Eyjafjalla", () -> { ex(3); });
        DESCS.put("CXXSS_Eyjafjalla", "兴奋+3。");
        TABLE.put("FlameSurround_Eyjafjalla", () -> { ex(4); });
        DESCS.put("FlameSurround_Eyjafjalla", "兴奋+4。");
        TABLE.put("FlameMountain_Eyjafjalla", () -> { ex(4); });
        DESCS.put("FlameMountain_Eyjafjalla", "兴奋+4。");
        TABLE.put("Ignition_Eyjafjalla", () -> { ex(4); });
        DESCS.put("Ignition_Eyjafjalla", "兴奋+4。");
        TABLE.put("ReIgnition_Eyjafjalla", () -> { ex(4); });
        DESCS.put("ReIgnition_Eyjafjalla", "兴奋+4。");
        TABLE.put("FireOfLove_Eyjafjalla", () -> { ex(4); });
        DESCS.put("FireOfLove_Eyjafjalla", "兴奋+4。");
        TABLE.put("HappinessAria_Eyjafjalla", () -> { ex(4); });
        DESCS.put("HappinessAria_Eyjafjalla", "兴奋+4。");
        TABLE.put("SadnessRecitative_Eyjafjalla", () -> { ex(4); });
        DESCS.put("SadnessRecitative_Eyjafjalla", "兴奋+4。");
        TABLE.put("DancingLava_Eyjafjalla", () -> { ex(4); });
        DESCS.put("DancingLava_Eyjafjalla", "兴奋+4。");
        TABLE.put("FireHeart_Eyjafjalla", () -> { ex(4); });
        DESCS.put("FireHeart_Eyjafjalla", "兴奋+4。");
        TABLE.put("SpurtOut_Eyjafjalla", () -> { ex(4); });
        DESCS.put("SpurtOut_Eyjafjalla", "兴奋+4。");
        TABLE.put("SayToSun_Eyjafjalla", () -> { ex(4); });
        DESCS.put("SayToSun_Eyjafjalla", "兴奋+4。");
        TABLE.put("TreeAngle_Eyjafjalla", () -> { if (has("arknsfw:ClothGagRelic") || has("arknsfw:RingGagRelic")) { ex(3); } });
        DESCS.put("TreeAngle_Eyjafjalla", "戴口枷：兴奋+3。");
        TABLE.put("InSunset_Eyjafjalla", () -> { if (has("arknsfw:ClothGagRelic") || has("arknsfw:RingGagRelic")) { ex(3); } });
        DESCS.put("InSunset_Eyjafjalla", "戴口枷：兴奋+3。");
        TABLE.put("Sailing_Eyjafjalla", () -> { if (has("arknsfw:ClothGagRelic") || has("arknsfw:RingGagRelic")) { ex(3); } });
        DESCS.put("Sailing_Eyjafjalla", "戴口枷：兴奋+3。");
        TABLE.put("TheEnd_Eyjafjalla", () -> { if (has("arknsfw:ClothGagRelic") || has("arknsfw:RingGagRelic")) { ex(3); } });
        DESCS.put("TheEnd_Eyjafjalla", "戴口枷：兴奋+3。");
        TABLE.put("WaveFlower_Eyjafjalla", () -> { if (has("arknsfw:ClothGagRelic") || has("arknsfw:RingGagRelic")) { ex(3); } });
        DESCS.put("WaveFlower_Eyjafjalla", "戴口枷：兴奋+3。");
        TABLE.put("GoldAndRoses_Eyjafjalla", () -> { if (has("arknsfw:ClothGagRelic") || has("arknsfw:RingGagRelic")) { ex(3); } });
        DESCS.put("GoldAndRoses_Eyjafjalla", "戴口枷：兴奋+3。");
        TABLE.put("BlazePrelude_Eyjafjalla", () -> { if (has("arknsfw:ClothGagRelic") || has("arknsfw:RingGagRelic")) { ex(3); } });
        DESCS.put("BlazePrelude_Eyjafjalla", "戴口枷：兴奋+3。");
        TABLE.put("AnswerInWind_Eyjafjalla", () -> { if (has("arknsfw:ClothGagRelic") || has("arknsfw:RingGagRelic")) { ex(3); } });
        DESCS.put("AnswerInWind_Eyjafjalla", "戴口枷：兴奋+3。");
        TABLE.put("TranceDream_Eyjafjalla", () -> { if (has("arknsfw:ClothGagRelic") || has("arknsfw:RingGagRelic")) { ex(3); } });
        DESCS.put("TranceDream_Eyjafjalla", "戴口枷：兴奋+3。");
        TABLE.put("Songs_Eyjafjalla", () -> { if (has("arknsfw:ClothGagRelic") || has("arknsfw:RingGagRelic")) { ex(3); } });
        DESCS.put("Songs_Eyjafjalla", "戴口枷：兴奋+3。");
        TABLE.put("SongMary_Eyjafjalla", () -> { if (has("arknsfw:ClothGagRelic") || has("arknsfw:RingGagRelic")) { ex(3); } });
        DESCS.put("SongMary_Eyjafjalla", "戴口枷：兴奋+3。");
        TABLE.put("SongFarmer_Eyjafjalla", () -> { if (has("arknsfw:ClothGagRelic") || has("arknsfw:RingGagRelic")) { ex(3); } });
        DESCS.put("SongFarmer_Eyjafjalla", "戴口枷：兴奋+3。");
        TABLE.put("SongBoat_Eyjafjalla", () -> { if (has("arknsfw:ClothGagRelic") || has("arknsfw:RingGagRelic")) { ex(3); } });
        DESCS.put("SongBoat_Eyjafjalla", "戴口枷：兴奋+3。");
        TABLE.put("ResonanceDefend_Eyjafjalla", () -> { if (has("arknsfw:VibeEggRelic")) { ex(2); if (exAt(60)) { draw(1); } } });
        DESCS.put("ResonanceDefend_Eyjafjalla", "戴跳蛋：兴奋+2，兴奋≥60再抽1。");
        TABLE.put("HeartFeel_Eyjafjalla", () -> { if (has("arknsfw:VibeEggRelic")) { ex(2); if (exAt(60)) { draw(1); } } });
        DESCS.put("HeartFeel_Eyjafjalla", "戴跳蛋：兴奋+2，兴奋≥60再抽1。");
        TABLE.put("DreamCradle_Eyjafjalla", () -> { if (has("arknsfw:VibeEggRelic")) { ex(2); if (exAt(60)) { draw(1); } } });
        DESCS.put("DreamCradle_Eyjafjalla", "戴跳蛋：兴奋+2，兴奋≥60再抽1。");
        TABLE.put("LittleFeelings_Eyjafjalla", () -> { if (has("arknsfw:VibeEggRelic")) { ex(2); if (exAt(60)) { draw(1); } } });
        DESCS.put("LittleFeelings_Eyjafjalla", "戴跳蛋：兴奋+2，兴奋≥60再抽1。");
        TABLE.put("Marshmallow_Eyjafjalla", () -> { if (has("arknsfw:VibeEggRelic")) { ex(2); if (exAt(60)) { draw(1); } } });
        DESCS.put("Marshmallow_Eyjafjalla", "戴跳蛋：兴奋+2，兴奋≥60再抽1。");
        TABLE.put("Shuttle_Eyjafjalla", () -> { if (has("arknsfw:VibeEggRelic")) { ex(2); if (exAt(60)) { draw(1); } } });
        DESCS.put("Shuttle_Eyjafjalla", "戴跳蛋：兴奋+2，兴奋≥60再抽1。");
        TABLE.put("ClearDream_Eyjafjalla", () -> { if (has("arknsfw:VibeEggRelic")) { ex(2); if (exAt(60)) { draw(1); } } });
        DESCS.put("ClearDream_Eyjafjalla", "戴跳蛋：兴奋+2，兴奋≥60再抽1。");
        TABLE.put("PracticalJoke_Eyjafjalla", () -> { if (has("arknsfw:VibeEggRelic")) { ex(2); if (exAt(60)) { draw(1); } } });
        DESCS.put("PracticalJoke_Eyjafjalla", "戴跳蛋：兴奋+2，兴奋≥60再抽1。");
        TABLE.put("FKY_Eyjafjalla", () -> { if (has("arknsfw:VibeEggRelic")) { ex(2); if (exAt(60)) { draw(1); } } });
        DESCS.put("FKY_Eyjafjalla", "戴跳蛋：兴奋+2，兴奋≥60再抽1。");
        TABLE.put("MTFKY_Eyjafjalla", () -> { if (has("arknsfw:VibeEggRelic")) { ex(2); if (exAt(60)) { draw(1); } } });
        DESCS.put("MTFKY_Eyjafjalla", "戴跳蛋：兴奋+2，兴奋≥60再抽1。");
        TABLE.put("FXZ_Eyjafjalla", () -> { if (has("arknsfw:VibeEggRelic")) { ex(2); if (exAt(60)) { draw(1); } } });
        DESCS.put("FXZ_Eyjafjalla", "戴跳蛋：兴奋+2，兴奋≥60再抽1。");
        TABLE.put("LJFXZ_Eyjafjalla", () -> { if (has("arknsfw:VibeEggRelic")) { ex(2); if (exAt(60)) { draw(1); } } });
        DESCS.put("LJFXZ_Eyjafjalla", "戴跳蛋：兴奋+2，兴奋≥60再抽1。");
        TABLE.put("BurnGround_Eyjafjalla", () -> { if (has("arknsfw:VibeEggRelic")) { ex(2); if (exAt(60)) { draw(1); } } });
        DESCS.put("BurnGround_Eyjafjalla", "戴跳蛋：兴奋+2，兴奋≥60再抽1。");
        TABLE.put("IntoDream_Eyjafjalla", () -> { conc(3); });
        DESCS.put("IntoDream_Eyjafjalla", "受孕+3。");
        TABLE.put("RiseUp_Eyjafjalla", () -> { conc(3); });
        DESCS.put("RiseUp_Eyjafjalla", "受孕+3。");
        TABLE.put("SevenColor_Eyjafjalla", () -> { conc(3); });
        DESCS.put("SevenColor_Eyjafjalla", "受孕+3。");
        TABLE.put("Morning_Eyjafjalla", () -> { conc(3); });
        DESCS.put("Morning_Eyjafjalla", "受孕+3。");
        TABLE.put("Afterglow_Eyjafjalla", () -> { conc(3); });
        DESCS.put("Afterglow_Eyjafjalla", "受孕+3。");
        TABLE.put("CloudSilk_Eyjafjalla", () -> { conc(3); });
        DESCS.put("CloudSilk_Eyjafjalla", "受孕+3。");
        TABLE.put("Fairy_Eyjafjalla", () -> { conc(3); });
        DESCS.put("Fairy_Eyjafjalla", "受孕+3。");
        TABLE.put("HeartInCandy_Eyjafjalla", () -> { conc(3); });
        DESCS.put("HeartInCandy_Eyjafjalla", "受孕+3。");
        TABLE.put("CloudCake_Eyjafjalla", () -> { conc(3); });
        DESCS.put("CloudCake_Eyjafjalla", "受孕+3。");
        TABLE.put("FollowHeart_Eyjafjalla", () -> { conc(3); });
        DESCS.put("FollowHeart_Eyjafjalla", "受孕+3。");
        TABLE.put("SoLongAdele_Eyjafjalla", () -> { conc(3); });
        DESCS.put("SoLongAdele_Eyjafjalla", "受孕+3。");
        TABLE.put("PinkOcean_Eyjafjalla", () -> { conc(3); });
        DESCS.put("PinkOcean_Eyjafjalla", "受孕+3。");
        TABLE.put("Cloud_Eyjafjalla", () -> { conc(3); });
        DESCS.put("Cloud_Eyjafjalla", "受孕+3。");
        TABLE.put("UnknownCloud_Eyjafjalla", () -> { conc(3); });
        DESCS.put("UnknownCloud_Eyjafjalla", "受孕+3。");
        TABLE.put("HPY_Eyjafjalla", () -> { conc(3); });
        DESCS.put("HPY_Eyjafjalla", "受孕+3。");
        TABLE.put("DollyInvitation_Eyjafjalla", () -> { conc(3); });
        DESCS.put("DollyInvitation_Eyjafjalla", "受孕+3。");
        TABLE.put("TheLovedOne_Eyjafjalla", () -> { conc(3); });
        DESCS.put("TheLovedOne_Eyjafjalla", "受孕+3。");
        TABLE.put("TimeOfLove_Eyjafjalla", () -> { conc(3); });
        DESCS.put("TimeOfLove_Eyjafjalla", "受孕+3。");
        TABLE.put("BetweenUs_Eyjafjalla", () -> { conc(3); });
        DESCS.put("BetweenUs_Eyjafjalla", "受孕+3。");
        TABLE.put("MissSound_Eyjafjalla", () -> { conc(3); });
        DESCS.put("MissSound_Eyjafjalla", "受孕+3。");
        TABLE.put("ResearchOnNature_Eyjafjalla", () -> { conc(3); });
        DESCS.put("ResearchOnNature_Eyjafjalla", "受孕+3。");
        TABLE.put("LetRainGo_Eyjafjalla", () -> { conc(3); });
        DESCS.put("LetRainGo_Eyjafjalla", "受孕+3。");
        TABLE.put("CloudMelody_Eyjafjalla", () -> { conc(3); });
        DESCS.put("CloudMelody_Eyjafjalla", "受孕+3。");
        TABLE.put("Inherit_Eyjafjalla", () -> { conc(3); });
        DESCS.put("Inherit_Eyjafjalla", "受孕+3。");
        TABLE.put("SongOfLove_Eyjafjalla", () -> { conc(3); });
        DESCS.put("SongOfLove_Eyjafjalla", "受孕+3。");
        TABLE.put("DenseMist_Eyjafjalla", () -> { fert(3); });
        DESCS.put("DenseMist_Eyjafjalla", "受胎+3。");
        TABLE.put("VolcanicHealing_Eyjafjalla", () -> { fert(3); });
        DESCS.put("VolcanicHealing_Eyjafjalla", "受胎+3。");
        TABLE.put("SilentWet_Eyjafjalla", () -> { fert(3); });
        DESCS.put("SilentWet_Eyjafjalla", "受胎+3。");
        TABLE.put("DGDGZ_Eyjafjalla", () -> { fert(3); });
        DESCS.put("DGDGZ_Eyjafjalla", "受胎+3。");
        TABLE.put("Defend_Eyjafjalla", () -> { if (exAt(30)) { blk(2 + (has("arknsfw:RopeBindRelic") ? 2 : 0)); } });
        DESCS.put("Defend_Eyjafjalla", "兴奋≥30：+2格挡（绳缚再+2）。");
        TABLE.put("FlutteringBreeze_Eyjafjalla", () -> { if (exAt(30)) { blk(2 + (has("arknsfw:RopeBindRelic") ? 2 : 0)); } });
        DESCS.put("FlutteringBreeze_Eyjafjalla", "兴奋≥30：+2格挡（绳缚再+2）。");
        TABLE.put("CloudyBarrier_Eyjafjalla", () -> { if (exAt(30)) { blk(2 + (has("arknsfw:RopeBindRelic") ? 2 : 0)); } });
        DESCS.put("CloudyBarrier_Eyjafjalla", "兴奋≥30：+2格挡（绳缚再+2）。");
        TABLE.put("TQB_Eyjafjalla", () -> { if (exAt(30)) { blk(2 + (has("arknsfw:RopeBindRelic") ? 2 : 0)); } });
        DESCS.put("TQB_Eyjafjalla", "兴奋≥30：+2格挡（绳缚再+2）。");
        TABLE.put("LTTQB_Eyjafjalla", () -> { if (exAt(30)) { blk(2 + (has("arknsfw:RopeBindRelic") ? 2 : 0)); } });
        DESCS.put("LTTQB_Eyjafjalla", "兴奋≥30：+2格挡（绳缚再+2）。");
        TABLE.put("LLH_Eyjafjalla", () -> { if (exAt(30)) { blk(2 + (has("arknsfw:RopeBindRelic") ? 2 : 0)); } });
        DESCS.put("LLH_Eyjafjalla", "兴奋≥30：+2格挡（绳缚再+2）。");
        TABLE.put("NSLLH_Eyjafjalla", () -> { if (exAt(30)) { blk(2 + (has("arknsfw:RopeBindRelic") ? 2 : 0)); } });
        DESCS.put("NSLLH_Eyjafjalla", "兴奋≥30：+2格挡（绳缚再+2）。");
        TABLE.put("DGZ_Eyjafjalla", () -> { if (exAt(30)) { blk(2 + (has("arknsfw:RopeBindRelic") ? 2 : 0)); } });
        DESCS.put("DGZ_Eyjafjalla", "兴奋≥30：+2格挡（绳缚再+2）。");
        TABLE.put("LiveStone_Eyjafjalla", () -> { if (exAt(30)) { blk(2 + (has("arknsfw:RopeBindRelic") ? 2 : 0)); } });
        DESCS.put("LiveStone_Eyjafjalla", "兴奋≥30：+2格挡（绳缚再+2）。");
        TABLE.put("PrepareBeforeCamp_Eyjafjalla", () -> { if (exAt(30)) { blk(2 + (has("arknsfw:RopeBindRelic") ? 2 : 0)); } });
        DESCS.put("PrepareBeforeCamp_Eyjafjalla", "兴奋≥30：+2格挡（绳缚再+2）。");
        TABLE.put("CloudCrack_Eyjafjalla", () -> { if (exAt(30)) { blk(2 + (has("arknsfw:RopeBindRelic") ? 2 : 0)); } });
        DESCS.put("CloudCrack_Eyjafjalla", "兴奋≥30：+2格挡（绳缚再+2）。");
        TABLE.put("Push_Eyjafjalla", () -> { ex(-4); });
        DESCS.put("Push_Eyjafjalla", "兴奋-4。");
        TABLE.put("Volcano_Eyjafjalla", () -> { if (exAt(50)) { mechPlus(1); } if (has("arknsfw:BodyCrestRelic")) { ex(4); } });
        DESCS.put("Volcano_Eyjafjalla", "兴奋≥50：本源机制+1；戴淫纹：兴奋+4。");
        TABLE.put("Darkside_Eyjafjalla", () -> { if (exAt(50)) { mechPlus(1); } if (has("arknsfw:BodyCrestRelic")) { ex(4); } });
        DESCS.put("Darkside_Eyjafjalla", "兴奋≥50：本源机制+1；戴淫纹：兴奋+4。");
        TABLE.put("FlameBurst_Eyjafjalla", () -> { if (exAt(50)) { mechPlus(1); } if (has("arknsfw:BodyCrestRelic")) { ex(4); } });
        DESCS.put("FlameBurst_Eyjafjalla", "兴奋≥50：本源机制+1；戴淫纹：兴奋+4。");
        TABLE.put("PulseOfTerra_Eyjafjalla", () -> { if (exAt(50)) { mechPlus(1); } if (has("arknsfw:BodyCrestRelic")) { ex(4); } });
        DESCS.put("PulseOfTerra_Eyjafjalla", "兴奋≥50：本源机制+1；戴淫纹：兴奋+4。");
        TABLE.put("Dreiton_Eyjafjalla", () -> { if (exAt(50)) { mechPlus(1); } if (has("arknsfw:BodyCrestRelic")) { ex(4); } });
        DESCS.put("Dreiton_Eyjafjalla", "兴奋≥50：本源机制+1；戴淫纹：兴奋+4。");
        TABLE.put("WaterStrike_Muelsyse", () -> { ex(2); });
        DESCS.put("WaterStrike_Muelsyse", "兴奋+2。");
        TABLE.put("WaterStrikePro_Muelsyse", () -> { ex(2); });
        DESCS.put("WaterStrikePro_Muelsyse", "兴奋+2。");
        TABLE.put("Bright_Muelsyse", () -> { ex(2); });
        DESCS.put("Bright_Muelsyse", "兴奋+2。");
        TABLE.put("Geometry_Muelsyse", () -> { ex(2); });
        DESCS.put("Geometry_Muelsyse", "兴奋+2。");
        TABLE.put("ScatterSpore_Muelsyse", () -> { ex(2); });
        DESCS.put("ScatterSpore_Muelsyse", "兴奋+2。");
        TABLE.put("Starter_Muelsyse", () -> { ex(2); });
        DESCS.put("Starter_Muelsyse", "兴奋+2。");
        TABLE.put("GoldenWander_Muelsyse", () -> { ex(2); });
        DESCS.put("GoldenWander_Muelsyse", "兴奋+2。");
        TABLE.put("StarMove_Muelsyse", () -> { ex(2); });
        DESCS.put("StarMove_Muelsyse", "兴奋+2。");
        TABLE.put("WaterStrikeEnd_Muelsyse", () -> { ex(3); });
        DESCS.put("WaterStrikeEnd_Muelsyse", "兴奋+3。");
        TABLE.put("FractalAttack_Muelsyse", () -> { ex(3); });
        DESCS.put("FractalAttack_Muelsyse", "兴奋+3。");
        TABLE.put("PoisonIvy_Muelsyse", () -> { ex(3); });
        DESCS.put("PoisonIvy_Muelsyse", "兴奋+3。");
        TABLE.put("HugOfRegret_Muelsyse", () -> { ex(3); });
        DESCS.put("HugOfRegret_Muelsyse", "兴奋+3。");
        TABLE.put("ApartWhenLoss_Muelsyse", () -> { ex(3); });
        DESCS.put("ApartWhenLoss_Muelsyse", "兴奋+3。");
        TABLE.put("WaterWave_Muelsyse", () -> { ex(3); });
        DESCS.put("WaterWave_Muelsyse", "兴奋+3。");
        TABLE.put("ExplosionMagic_Muelsyse", () -> { ex(3); });
        DESCS.put("ExplosionMagic_Muelsyse", "兴奋+3。");
        TABLE.put("CondensateWish_Muelsyse", () -> { ex(3); });
        DESCS.put("CondensateWish_Muelsyse", "兴奋+3。");
        TABLE.put("LiftStar_Muelsyse", () -> { ex(3); });
        DESCS.put("LiftStar_Muelsyse", "兴奋+3。");
        TABLE.put("Suzuran_Muelsyse", () -> { ex(3); });
        DESCS.put("Suzuran_Muelsyse", "兴奋+3。");
        TABLE.put("WhiteRose_Muelsyse", () -> { ex(3); });
        DESCS.put("WhiteRose_Muelsyse", "兴奋+3。");
        TABLE.put("MillenniumVine_Muelsyse", () -> { ex(3); });
        DESCS.put("MillenniumVine_Muelsyse", "兴奋+3。");
        TABLE.put("VowAndEvolution_Muelsyse", () -> { ex(3); });
        DESCS.put("VowAndEvolution_Muelsyse", "兴奋+3。");
        TABLE.put("SourceTogether_Muelsyse", () -> { ex(3); });
        DESCS.put("SourceTogether_Muelsyse", "兴奋+3。");
        TABLE.put("GraduallyWater_Muelsyse", () -> { ex(3); });
        DESCS.put("GraduallyWater_Muelsyse", "兴奋+3。");
        TABLE.put("FluidPower_Muelsyse", () -> { ex(3); });
        DESCS.put("FluidPower_Muelsyse", "兴奋+3。");
        TABLE.put("WaterSilently_Muelsyse", () -> { ex(4); });
        DESCS.put("WaterSilently_Muelsyse", "兴奋+4。");
        TABLE.put("ShallowAdaption_Muelsyse", () -> { ex(4); });
        DESCS.put("ShallowAdaption_Muelsyse", "兴奋+4。");
        TABLE.put("WaterRecycle_Muelsyse", () -> { ex(4); });
        DESCS.put("WaterRecycle_Muelsyse", "兴奋+4。");
        TABLE.put("SongOfRose_Muelsyse", () -> { conc(3); });
        DESCS.put("SongOfRose_Muelsyse", "受孕+3。");
        TABLE.put("SpringWord_Muelsyse", () -> { conc(3); });
        DESCS.put("SpringWord_Muelsyse", "受孕+3。");
        TABLE.put("Seed_Muelsyse", () -> { conc(3); });
        DESCS.put("Seed_Muelsyse", "受孕+3。");
        TABLE.put("FullBlown_Muelsyse", () -> { conc(3); });
        DESCS.put("FullBlown_Muelsyse", "受孕+3。");
        TABLE.put("SoilSpread_Muelsyse", () -> { conc(3); });
        DESCS.put("SoilSpread_Muelsyse", "受孕+3。");
        TABLE.put("SoilExhaust_Muelsyse", () -> { conc(3); });
        DESCS.put("SoilExhaust_Muelsyse", "受孕+3。");
        TABLE.put("BornShoots_Muelsyse", () -> { conc(3); });
        DESCS.put("BornShoots_Muelsyse", "受孕+3。");
        TABLE.put("LifeAndWind_Muelsyse", () -> { conc(3); });
        DESCS.put("LifeAndWind_Muelsyse", "受孕+3。");
        TABLE.put("EcologyCycle_Muelsyse", () -> { conc(3); });
        DESCS.put("EcologyCycle_Muelsyse", "受孕+3。");
        TABLE.put("Shoot_Muelsyse", () -> { conc(3); });
        DESCS.put("Shoot_Muelsyse", "受孕+3。");
        TABLE.put("SpruceInCage_Muelsyse", () -> { conc(3); });
        DESCS.put("SpruceInCage_Muelsyse", "受孕+3。");
        TABLE.put("PlantAStar_Muelsyse", () -> { conc(3); });
        DESCS.put("PlantAStar_Muelsyse", "受孕+3。");
        TABLE.put("EndTime_Muelsyse", () -> { conc(3); });
        DESCS.put("EndTime_Muelsyse", "受孕+3。");
        TABLE.put("DeGold_Muelsyse", () -> { conc(3); });
        DESCS.put("DeGold_Muelsyse", "受孕+3。");
        TABLE.put("EcologicalProject_Muelsyse", () -> { conc(3); });
        DESCS.put("EcologicalProject_Muelsyse", "受孕+3。");
        TABLE.put("ModeCultivate_Muelsyse", () -> { conc(3); });
        DESCS.put("ModeCultivate_Muelsyse", "受孕+3。");
        TABLE.put("FallenSeasons_Muelsyse", () -> { conc(3); });
        DESCS.put("FallenSeasons_Muelsyse", "受孕+3。");
        TABLE.put("StarLaurel_Muelsyse", () -> { conc(3); });
        DESCS.put("StarLaurel_Muelsyse", "受孕+3。");
        TABLE.put("LingerFragrance_Muelsyse", () -> { conc(3); });
        DESCS.put("LingerFragrance_Muelsyse", "受孕+3。");
        TABLE.put("CrazyWord_Muelsyse", () -> { conc(3); });
        DESCS.put("CrazyWord_Muelsyse", "受孕+3。");
        TABLE.put("SourceOfLife_Muelsyse", () -> { conc(3); });
        DESCS.put("SourceOfLife_Muelsyse", "受孕+3。");
        TABLE.put("MatrixWave_Muelsyse", () -> { conc(3); });
        DESCS.put("MatrixWave_Muelsyse", "受孕+3。");
        TABLE.put("MyShadow_Muelsyse", () -> { conc(3); });
        DESCS.put("MyShadow_Muelsyse", "受孕+3。");
        TABLE.put("SeriousSpawn_Muelsyse", () -> { conc(3); });
        DESCS.put("SeriousSpawn_Muelsyse", "受孕+3。");
        TABLE.put("LifeDeduction_Muelsyse", () -> { conc(4); });
        DESCS.put("LifeDeduction_Muelsyse", "受孕+4。");
        TABLE.put("Orchid_Muelsyse", () -> { fert(3); });
        DESCS.put("Orchid_Muelsyse", "受胎+3。");
        TABLE.put("FineBlend_Muelsyse", () -> { fert(3); });
        DESCS.put("FineBlend_Muelsyse", "受胎+3。");
        TABLE.put("CompositeNutrient_Muelsyse", () -> { fert(3); });
        DESCS.put("CompositeNutrient_Muelsyse", "受胎+3。");
        TABLE.put("Reflux_Muelsyse", () -> { fert(3); });
        DESCS.put("Reflux_Muelsyse", "受胎+3。");
        TABLE.put("LightFlow_Muelsyse", () -> { fert(3); });
        DESCS.put("LightFlow_Muelsyse", "受胎+3。");
        TABLE.put("TransRapid_Muelsyse", () -> { fert(3); });
        DESCS.put("TransRapid_Muelsyse", "受胎+3。");
        TABLE.put("TransCleanWater_Muelsyse", () -> { fert(3); });
        DESCS.put("TransCleanWater_Muelsyse", "受胎+3。");
        TABLE.put("SoilBack_Muelsyse", () -> { fert(3); });
        DESCS.put("SoilBack_Muelsyse", "受胎+3。");
        TABLE.put("UnknownFluid_Muelsyse", () -> { fert(3); });
        DESCS.put("UnknownFluid_Muelsyse", "受胎+3。");
        TABLE.put("DeepReflect_Muelsyse", () -> { fert(3); });
        DESCS.put("DeepReflect_Muelsyse", "受胎+3。");
        TABLE.put("CurseOfSpirit_Muelsyse", () -> { fert(3); });
        DESCS.put("CurseOfSpirit_Muelsyse", "受胎+3。");
        TABLE.put("EcologicalCoupling_Muelsyse", () -> { fert(3); });
        DESCS.put("EcologicalCoupling_Muelsyse", "受胎+3。");
        TABLE.put("FlowerSpread_Muelsyse", () -> { fert(3); });
        DESCS.put("FlowerSpread_Muelsyse", "受胎+3。");
        TABLE.put("FlowerParasitism_Muelsyse", () -> { fert(3); });
        DESCS.put("FlowerParasitism_Muelsyse", "受胎+3。");
        TABLE.put("FlowerCure_Muelsyse", () -> { fert(3); });
        DESCS.put("FlowerCure_Muelsyse", "受胎+3。");
        TABLE.put("FlowerWater_Muelsyse", () -> { fert(3); });
        DESCS.put("FlowerWater_Muelsyse", "受胎+3。");
        TABLE.put("TerraPatrol_Muelsyse", () -> { if (has("arknsfw:VibeEggRelic")) { ex(2); if (exAt(60)) { draw(1); } } });
        DESCS.put("TerraPatrol_Muelsyse", "戴跳蛋：兴奋+2，兴奋≥60再抽1。");
        TABLE.put("Reconfiguration_Muelsyse", () -> { if (has("arknsfw:VibeEggRelic")) { ex(2); if (exAt(60)) { draw(1); } } });
        DESCS.put("Reconfiguration_Muelsyse", "戴跳蛋：兴奋+2，兴奋≥60再抽1。");
        TABLE.put("InverseChange_Muelsyse", () -> { if (has("arknsfw:VibeEggRelic")) { ex(2); if (exAt(60)) { draw(1); } } });
        DESCS.put("InverseChange_Muelsyse", "戴跳蛋：兴奋+2，兴奋≥60再抽1。");
        TABLE.put("DorothyVision_Muelsyse", () -> { if (has("arknsfw:VibeEggRelic")) { ex(2); if (exAt(60)) { draw(1); } } });
        DESCS.put("DorothyVision_Muelsyse", "戴跳蛋：兴奋+2，兴奋≥60再抽1。");
        TABLE.put("MeetInSnow_Muelsyse", () -> { if (has("arknsfw:VibeEggRelic")) { ex(2); if (exAt(60)) { draw(1); } } });
        DESCS.put("MeetInSnow_Muelsyse", "戴跳蛋：兴奋+2，兴奋≥60再抽1。");
        TABLE.put("TimeRiver_Muelsyse", () -> { if (has("arknsfw:VibeEggRelic")) { ex(2); if (exAt(60)) { draw(1); } } });
        DESCS.put("TimeRiver_Muelsyse", "戴跳蛋：兴奋+2，兴奋≥60再抽1。");
        TABLE.put("UncertainForm_Muelsyse", () -> { if (has("arknsfw:VibeEggRelic")) { ex(2); if (exAt(60)) { draw(1); } } });
        DESCS.put("UncertainForm_Muelsyse", "戴跳蛋：兴奋+2，兴奋≥60再抽1。");
        TABLE.put("RemainDust_Muelsyse", () -> { if (has("arknsfw:VibeEggRelic")) { ex(2); if (exAt(60)) { draw(1); } } });
        DESCS.put("RemainDust_Muelsyse", "戴跳蛋：兴奋+2，兴奋≥60再抽1。");
        TABLE.put("LivingEcho_Muelsyse", () -> { if (has("arknsfw:VibeEggRelic")) { ex(2); if (exAt(60)) { draw(1); } } });
        DESCS.put("LivingEcho_Muelsyse", "戴跳蛋：兴奋+2，兴奋≥60再抽1。");
        TABLE.put("LoneStar_Muelsyse", () -> { if (has("arknsfw:VibeEggRelic")) { ex(2); if (exAt(60)) { draw(1); } } });
        DESCS.put("LoneStar_Muelsyse", "戴跳蛋：兴奋+2，兴奋≥60再抽1。");
        TABLE.put("CloudyBarrier_Muelsyse", () -> { if (has("arknsfw:VibeEggRelic")) { ex(2); if (exAt(60)) { draw(1); } } });
        DESCS.put("CloudyBarrier_Muelsyse", "戴跳蛋：兴奋+2，兴奋≥60再抽1。");
        TABLE.put("Mibo_Muelsyse", () -> { if (has("arknsfw:VibeEggRelic")) { ex(2); if (exAt(60)) { draw(1); } } });
        DESCS.put("Mibo_Muelsyse", "戴跳蛋：兴奋+2，兴奋≥60再抽1。");
        TABLE.put("ForSeek_Muelsyse", () -> { if (nearClimax(10)) { energy(1); } });
        DESCS.put("ForSeek_Muelsyse", "濒临高潮（距阈值10内）：+1能量。");
        TABLE.put("Hypothermia_Muelsyse", () -> { ex(-4); });
        DESCS.put("Hypothermia_Muelsyse", "兴奋-4。");
        TABLE.put("WaterDefend_Muelsyse", () -> { if (exAt(30)) { blk(2 + (has("arknsfw:RopeBindRelic") ? 2 : 0)); } });
        DESCS.put("WaterDefend_Muelsyse", "兴奋≥30：+2格挡（绳缚再+2）。");
        TABLE.put("LossShield_Muelsyse", () -> { if (exAt(30)) { blk(2 + (has("arknsfw:RopeBindRelic") ? 2 : 0)); } });
        DESCS.put("LossShield_Muelsyse", "兴奋≥30：+2格挡（绳缚再+2）。");
        TABLE.put("Rest_Muelsyse", () -> { if (exAt(30)) { blk(2 + (has("arknsfw:RopeBindRelic") ? 2 : 0)); } });
        DESCS.put("Rest_Muelsyse", "兴奋≥30：+2格挡（绳缚再+2）。");
        TABLE.put("Trail_Muelsyse", () -> { if (exAt(30)) { blk(2 + (has("arknsfw:RopeBindRelic") ? 2 : 0)); } });
        DESCS.put("Trail_Muelsyse", "兴奋≥30：+2格挡（绳缚再+2）。");
        TABLE.put("Irrigate_Muelsyse", () -> { if (exAt(30)) { blk(2 + (has("arknsfw:RopeBindRelic") ? 2 : 0)); } });
        DESCS.put("Irrigate_Muelsyse", "兴奋≥30：+2格挡（绳缚再+2）。");
        TABLE.put("DreamBubble_Muelsyse", () -> { if (exAt(30)) { blk(2 + (has("arknsfw:RopeBindRelic") ? 2 : 0)); } });
        DESCS.put("DreamBubble_Muelsyse", "兴奋≥30：+2格挡（绳缚再+2）。");
        TABLE.put("WaterShield_Muelsyse", () -> { if (exAt(30)) { blk(2 + (has("arknsfw:RopeBindRelic") ? 2 : 0)); } });
        DESCS.put("WaterShield_Muelsyse", "兴奋≥30：+2格挡（绳缚再+2）。");
        TABLE.put("Revive_Muelsyse", () -> { if (exAt(30)) { blk(2 + (has("arknsfw:RopeBindRelic") ? 2 : 0)); } });
        DESCS.put("Revive_Muelsyse", "兴奋≥30：+2格挡（绳缚再+2）。");
        TABLE.put("NoManMachine_Muelsyse", () -> { if (exAt(30)) { blk(2 + (has("arknsfw:RopeBindRelic") ? 2 : 0)); } });
        DESCS.put("NoManMachine_Muelsyse", "兴奋≥30：+2格挡（绳缚再+2）。");
        TABLE.put("BlueHyacinth_Muelsyse", () -> { if (exAt(30)) { blk(2 + (has("arknsfw:RopeBindRelic") ? 2 : 0)); } });
        DESCS.put("BlueHyacinth_Muelsyse", "兴奋≥30：+2格挡（绳缚再+2）。");
        TABLE.put("LifeRipple_Muelsyse", () -> { if (exAt(30)) { blk(2 + (has("arknsfw:RopeBindRelic") ? 2 : 0)); } });
        DESCS.put("LifeRipple_Muelsyse", "兴奋≥30：+2格挡（绳缚再+2）。");
        TABLE.put("ItIs_Muelsyse", () -> { blk(Math.min(8, arknsfw.helpers.ArkGearSetHelper.equipmentCount())); });
        DESCS.put("ItIs_Muelsyse", "每件拘束装备+1格挡（至多8）。");
        TABLE.put("WaterStrikeO_Muelsyse", () -> { if (exAt(50)) { mechPlus(1); } if (has("arknsfw:BodyCrestRelic")) { ex(4); } });
        DESCS.put("WaterStrikeO_Muelsyse", "兴奋≥50：本源机制+1；戴淫纹：兴奋+4。");
        TABLE.put("Ocean_Muelsyse", () -> { if (exAt(50)) { mechPlus(1); } if (has("arknsfw:BodyCrestRelic")) { ex(4); } });
        DESCS.put("Ocean_Muelsyse", "兴奋≥50：本源机制+1；戴淫纹：兴奋+4。");
        TABLE.put("RainWorld_Muelsyse", () -> { if (exAt(50)) { mechPlus(1); } if (has("arknsfw:BodyCrestRelic")) { ex(4); } });
        DESCS.put("RainWorld_Muelsyse", "兴奋≥50：本源机制+1；戴淫纹：兴奋+4。");
        TABLE.put("Dandelion_Muelsyse", () -> { if (exAt(50)) { mechPlus(1); } if (has("arknsfw:BodyCrestRelic")) { ex(4); } });
        DESCS.put("Dandelion_Muelsyse", "兴奋≥50：本源机制+1；戴淫纹：兴奋+4。");
        TABLE.put("ChorusFlower_Muelsyse", () -> { if (exAt(50)) { mechPlus(1); } if (has("arknsfw:BodyCrestRelic")) { ex(4); } });
        DESCS.put("ChorusFlower_Muelsyse", "兴奋≥50：本源机制+1；戴淫纹：兴奋+4。");
        TABLE.put("Ifrit_Muelsyse", () -> { ex(2); });
        DESCS.put("Ifrit_Muelsyse", "兴奋+2。");
        TABLE.put("Typhon_Muelsyse", () -> { ex(2); });
        DESCS.put("Typhon_Muelsyse", "兴奋+2。");
        TABLE.put("Greyy_Muelsyse", () -> { ex(2); });
        DESCS.put("Greyy_Muelsyse", "兴奋+2。");
        TABLE.put("Kafka_Muelsyse", () -> { ex(2); });
        DESCS.put("Kafka_Muelsyse", "兴奋+2。");
        TABLE.put("Dorothy_Muelsyse", () -> { ex(2); });
        DESCS.put("Dorothy_Muelsyse", "兴奋+2。");
        TABLE.put("Astgenne_Muelsyse", () -> { ex(2); });
        DESCS.put("Astgenne_Muelsyse", "兴奋+2。");
        TABLE.put("Amiya_Muelsyse", () -> { ex(2); });
        DESCS.put("Amiya_Muelsyse", "兴奋+2。");
        TABLE.put("Saria_Muelsyse", () -> { conc(3); });
        DESCS.put("Saria_Muelsyse", "受孕+3。");
        TABLE.put("Mechanist_Muelsyse", () -> { conc(3); });
        DESCS.put("Mechanist_Muelsyse", "受孕+3。");
        TABLE.put("Silence_Muelsyse", () -> { conc(3); });
        DESCS.put("Silence_Muelsyse", "受孕+3。");
        TABLE.put("Ptilopsis_Muelsyse", () -> { conc(3); });
        DESCS.put("Ptilopsis_Muelsyse", "受孕+3。");
        TABLE.put("Megallan_Muelsyse", () -> { conc(3); });
        DESCS.put("Megallan_Muelsyse", "受孕+3。");
        TABLE.put("Mayer_Muelsyse", () -> { conc(3); });
        DESCS.put("Mayer_Muelsyse", "受孕+3。");
        TABLE.put("Saileach_Muelsyse", () -> { conc(3); });
        DESCS.put("Saileach_Muelsyse", "受孕+3。");
        TABLE.put("Reed_Muelsyse", () -> { conc(3); });
        DESCS.put("Reed_Muelsyse", "受孕+3。");
        TABLE.put("SilverAsh_Muelsyse", () -> { conc(3); });
        DESCS.put("SilverAsh_Muelsyse", "受孕+3。");
        TABLE.put("Astesia_Muelsyse", () -> { conc(3); });
        DESCS.put("Astesia_Muelsyse", "受孕+3。");
        TABLE.put("Tulip_Muelsyse", () -> { conc(3); });
        DESCS.put("Tulip_Muelsyse", "受孕+3。");
        TABLE.put("Pepe_Muelsyse", () -> { conc(3); });
        DESCS.put("Pepe_Muelsyse", "受孕+3。");
        TABLE.put("Narantuya_Muelsyse", () -> { conc(3); });
        DESCS.put("Narantuya_Muelsyse", "受孕+3。");
        TABLE.put("Shu_Muelsyse", () -> { conc(3); });
        DESCS.put("Shu_Muelsyse", "受孕+3。");
        TABLE.put("Eyjafjalla_Muelsyse", () -> { conc(3); });
        DESCS.put("Eyjafjalla_Muelsyse", "受孕+3。");
        TABLE.put("SilenceP_Muelsyse", () -> { conc(3); });
        DESCS.put("SilenceP_Muelsyse", "受孕+3。");
        TABLE.put("Marcille_Muelsyse", () -> { conc(3); });
        DESCS.put("Marcille_Muelsyse", "受孕+3。");
        TABLE.put("TinMan_Muelsyse", () -> { conc(3); });
        DESCS.put("TinMan_Muelsyse", "受孕+3。");
        TABLE.put("Theresa_Muelsyse", () -> { conc(3); });
        DESCS.put("Theresa_Muelsyse", "受孕+3。");
        TABLE.put("Isharmla_Muelsyse", () -> { conc(3); });
        DESCS.put("Isharmla_Muelsyse", "受孕+3。");
    }
}
