package arknsfw.relics.nymph;

import com.megacrit.cardcrawl.relics.AbstractRelic;
import liesecore.helpers.NsfwRunStats;
import arknsfw.ArkNsfwMod;
import arknsfw.relics.AbstractArkNsfwRelic;

public class NymphWarmCharmRelic extends AbstractArkNsfwRelic {
    public static final String ID = ArkNsfwMod.makeID("NymphWarmCharmRelic");

    public NymphWarmCharmRelic() {
        super(ID, "relic_nymph_nymphwarmcharmrelic.png", RelicTier.COMMON, LandingSound.CLINK);
    }

    @Override public void atTurnStart() { if (NsfwRunStats.excitement >= 30) flash(); }

    @Override public String getUpdatedDescription() { return DESCRIPTIONS[0]; }
    @Override public AbstractRelic makeCopy() { return new NymphWarmCharmRelic(); }
}
