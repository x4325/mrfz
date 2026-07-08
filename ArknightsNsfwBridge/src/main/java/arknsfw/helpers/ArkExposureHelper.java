package arknsfw.helpers;

import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import liesecore.helpers.NsfwRunStats;

/**
 * 服装系统：0=完好 1=破损。累计受伤破损；拼死抵抗修复；换幕重整。
 * 破损时兴奋获取 +25%（见 ArkExposurePatch）。
 */
public final class ArkExposureHelper {

    public static final int MAX_STAGE = 4;
    private static final int DAMAGE_PER_STAGE = 14;

    private static int stage = 0;
    private static int accumulated = 0;
    private static int lastAct = -1;

    private ArkExposureHelper() {
    }

    public static void resetForNewRun() {
        stage = 0;
        accumulated = 0;
        lastAct = -1;
    }

    public static int stage() {
        syncAct();
        return stage;
    }

    private static void syncAct() {
        // 每进入新的一幕自动整理着装（露出斗篷持有者除外）
        if (AbstractDungeon.actNum != lastAct) {
            lastAct = AbstractDungeon.actNum;
            boolean cloak = AbstractDungeon.player != null
                    && AbstractDungeon.player.hasRelic("arknsfw:ExposureCloakRelic");
            if (!cloak) {
                stage = 0;
                accumulated = 0;
            }
        }
    }

    /** 受伤累计：每累计 14 点破损一档。 */
    public static void onPlayerLoseHp(int amount) {
        // 覆盖全部七名角色（此前只认五个自制角色，艾雅/缪尔的衣装永远不会破损）
        if (!ArkCharacterSetup.isArkNsfwRun() || amount <= 0) {
            return;
        }
        syncAct();
        accumulated += amount;
        while (accumulated >= DAMAGE_PER_STAGE && stage < MAX_STAGE) {
            accumulated -= DAMAGE_PER_STAGE;
            stage++;
            NsfwRunStats.addExcitement(6);
        }
    }

    /** 服装再破损一档（主动献身等）。 */
    public static void tear() {
        syncAct();
        if (stage < MAX_STAGE) {
            stage++;
        }
    }

    /** 修复一档（拼死抵抗、温存照料）。持有露出斗篷时衣装无法修复。 */
    public static void repair(int stages) {
        syncAct();
        if (AbstractDungeon.player != null
                && AbstractDungeon.player.hasRelic(arknsfw.relics.fall.ExposureCloakRelic.ID)) {
            return;
        }
        stage = Math.max(0, stage - stages);
        accumulated = 0;
    }

    /** 兴奋获取倍率（%）。 */
    public static int excitementPercent() {
        syncAct();
        return 100 + 25 * stage;
    }

    public static String stageName() {
        switch (stage()) {
            case 0: return "衣装：完好";
            case 1: return "衣装：破损";
            case 2: return "衣装：大破";
            case 3: return "衣装：褴褛";
            default: return "衣装：全裸";
        }
    }
}
