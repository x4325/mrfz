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
    }
}
