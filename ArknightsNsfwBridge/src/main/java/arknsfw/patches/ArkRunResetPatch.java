package arknsfw.patches;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePostfixPatch;
import liesecore.helpers.NsfwRunStats;
import arknsfw.helpers.ArkRunProgress;

public class ArkRunResetPatch {

    @SpirePatch(clz = NsfwRunStats.class, method = "resetForNewRun")
    public static class ResetArkRoute {
        @SpirePostfixPatch
        public static void postfix() {
            ArkRunProgress.resetForNewRun();
            arknsfw.helpers.ArkPostBattleChoice.resetForNewRun();
            arknsfw.helpers.ArkExposureHelper.resetForNewRun();
            arknsfw.helpers.ArkDefeatHelper.resetForNewRun();
        }
    }
}
