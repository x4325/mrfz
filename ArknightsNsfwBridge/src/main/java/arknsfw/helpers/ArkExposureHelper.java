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
        // 每进入新的一幕自动整理着装
        if (AbstractDungeon.actNum != lastAct) {
            lastAct = AbstractDungeon.actNum;
            stage = 0;
            accumulated = 0;
        }
    }

    /** 受伤累计：每累计 14 点破损一档。 */
    public static void onPlayerLoseHp(int amount) {
        if (ArkCharDebuffs.currentCharKey() == null || amount <= 0) {
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

    /** 修复一档（拼死抵抗、温存照料）。 */
    public static void repair(int stages) {
        syncAct();
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
