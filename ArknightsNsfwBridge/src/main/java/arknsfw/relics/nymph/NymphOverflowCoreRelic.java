package arknsfw.relics.nymph;

import com.megacrit.cardcrawl.relics.AbstractRelic;
import liesecore.helpers.NsfwRunStats;
import arknsfw.ArkNsfwMod;
import arknsfw.relics.AbstractArkNsfwRelic;

public class NymphOverflowCoreRelic extends AbstractArkNsfwRelic {
    public static final String ID = ArkNsfwMod.makeID("NymphOverflowCoreRelic");

    public NymphOverflowCoreRelic() {
        super(ID, "relic_nymph_nymphoverflowcorerelic.png", RelicTier.BOSS, LandingSound.CLINK);
    }

    @Override public void onVictory() { NsfwRunStats.addExcitement(20); flash(); }

    @Override public String getUpdatedDescription() { return DESCRIPTIONS[0]; }
    @Override public AbstractRelic makeCopy() { return new NymphOverflowCoreRelic(); }
}
