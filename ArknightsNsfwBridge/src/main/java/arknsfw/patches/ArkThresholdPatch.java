package arknsfw.patches;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePostfixPatch;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import liesecore.helpers.NsfwRunStats;
import arknsfw.relics.eyja.ThermometerCharmRelic;

public class ArkThresholdPatch {

    private static final String[] BRAND_CURSE_IDS = {
            arknsfw.relics.curses.highmore.HighmoreBrandCurseRelic.ID,
            arknsfw.relics.curses.scene.SceneBrandCurseRelic.ID,
            arknsfw.relics.curses.archetto.ArchettoBrandCurseRelic.ID,
            arknsfw.relics.curses.haruka.HarukaBrandCurseRelic.ID,
            arknsfw.relics.curses.nymph.NymphBrandCurseRelic.ID,
    };

    @SpirePatch(clz = NsfwRunStats.class, method = "getClimaxThreshold")
    public static class ThermometerCharmBonus {
        @SpirePostfixPatch
        public static int postfix(int result) {
            if (AbstractDungeon.player == null) {
                return result;
            }
            // 烙印诅咒：兴奋上限 +20
            for (String id : BRAND_CURSE_IDS) {
                if (AbstractDungeon.player.hasRelic(id)) {
                    result += 20;
                    break;
                }
            }
            if (AbstractDungeon.player.hasRelic(ThermometerCharmRelic.ID)) {
                return Math.max(100, result - ThermometerCharmRelic.THRESHOLD_BONUS);
            }
            return result;
        }
    }
}
