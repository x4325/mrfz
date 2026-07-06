package arknsfw.patches;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePostfixPatch;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import liesecore.helpers.NsfwRunStats;
import arknsfw.relics.eyja.ThermometerCharmRelic;

public class ArkThresholdPatch {

    @SpirePatch(clz = NsfwRunStats.class, method = "getClimaxThreshold")
    public static class ThermometerCharmBonus {
        @SpirePostfixPatch
        public static int postfix(int result) {
            if (AbstractDungeon.player != null
                    && AbstractDungeon.player.hasRelic(ThermometerCharmRelic.ID)) {
                return Math.max(100, result - ThermometerCharmRelic.THRESHOLD_BONUS);
            }
            return result;
        }
    }
}
