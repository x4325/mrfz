package arknsfw.relics.highmore;

import com.megacrit.cardcrawl.relics.AbstractRelic;
import liesecore.helpers.NsfwRunStats;
import arknsfw.ArkNsfwMod;
import arknsfw.relics.AbstractArkNsfwRelic;

public class HighmoreOverflowCoreRelic extends AbstractArkNsfwRelic {
    public static final String ID = ArkNsfwMod.makeID("HighmoreOverflowCoreRelic");

    public HighmoreOverflowCoreRelic() {
        super(ID, "relic_highmore_highmoreoverflowcorerelic.png", RelicTier.BOSS, LandingSound.CLINK);
    }

    @Override public void onVictory() { NsfwRunStats.addExcitement(20); flash(); }

    @Override public String getUpdatedDescription() { return DESCRIPTIONS[0]; }
    @Override public AbstractRelic makeCopy() { return new HighmoreOverflowCoreRelic(); }
}
