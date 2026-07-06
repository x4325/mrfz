package arknsfw.helpers;

import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import liesecore.helpers.NsfwRunStats;

/**
 * 服装/暴露度系统：0=完好 1=破损 2=大破。
 * 累计受伤升档；拼死抵抗/温存休息可修复；大破时立绘切换到第 4 档差分。
 * 暴露越高，兴奋获取越快（见 ArkExposurePatch）。
 */
public final class ArkExposureHelper {

    public static final int MAX_STAGE = 2;
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
            case 1: return "衣装：破损";
            case 2: return "衣装：大破";
            default: return "衣装：完好";
        }
    }
}
