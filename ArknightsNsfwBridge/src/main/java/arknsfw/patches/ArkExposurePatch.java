package arknsfw.patches;

import com.evacipated.cardcrawl.modthespire.lib.ByRef;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePrefixPatch;
import liesecore.helpers.NsfwRunStats;
import arknsfw.helpers.ArkCharDebuffs;
import arknsfw.helpers.ArkExposureHelper;

/** 暴露度：衣装破损越重，兴奋获取越快（+25%/档）。 */
public class ArkExposurePatch {

    @SpirePatch(clz = NsfwRunStats.class, method = "addExcitement", paramtypez = {int.class})
    public static class ScaleExcitement {
        @SpirePrefixPatch
        public static void Prefix(@ByRef int[] amount) {
            if (amount[0] > 0 && ArkCharDebuffs.currentCharKey() != null) {
                amount[0] = amount[0] * ArkExposureHelper.excitementPercent() / 100;
            }
        }
    }
}
