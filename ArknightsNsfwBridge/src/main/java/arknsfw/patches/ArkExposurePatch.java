package arknsfw.patches;

import com.evacipated.cardcrawl.modthespire.lib.ByRef;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePrefixPatch;
import liesecore.helpers.NsfwRunStats;
import arknsfw.helpers.ArkCharacterSetup;
import arknsfw.helpers.ArkExposureHelper;
import arknsfw.helpers.ArkSensitivity;

/** 兴奋/受孕获取倍率：衣装破损 +25%/档；敏感值 兴奋+10%/点、受孕/妊娠+5%/点。 */
public class ArkExposurePatch {

    @SpirePatch(clz = NsfwRunStats.class, method = "addExcitement", paramtypez = {int.class})
    public static class ScaleExcitement {
        @SpirePrefixPatch
        public static void Prefix(@ByRef int[] amount) {
            if (amount[0] > 0 && ArkCharacterSetup.isArkNsfwRun()) {
                int percent = ArkExposureHelper.excitementPercent()
                        + ArkSensitivity.excitementBonusPercent();
                amount[0] = amount[0] * percent / 100;
            }
        }
    }

    @SpirePatch(clz = NsfwRunStats.class, method = "addConception", paramtypez = {int.class, boolean.class})
    public static class ScaleConception {
        @SpirePrefixPatch
        public static void Prefix(@ByRef int[] amount, boolean inside) {
            if (amount[0] > 0 && ArkCharacterSetup.isArkNsfwRun()) {
                amount[0] = amount[0] * (100 + ArkSensitivity.conceptionBonusPercent()) / 100;
            }
        }
    }

    @SpirePatch(clz = NsfwRunStats.class, method = "addPregnancyProgress", paramtypez = {int.class})
    public static class ScalePregnancy {
        @SpirePrefixPatch
        public static void Prefix(@ByRef int[] amount) {
            if (amount[0] > 0 && ArkCharacterSetup.isArkNsfwRun()) {
                amount[0] = amount[0] * (100 + ArkSensitivity.conceptionBonusPercent()) / 100;
            }
        }
    }
}
