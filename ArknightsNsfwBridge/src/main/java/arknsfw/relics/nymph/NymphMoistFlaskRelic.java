package arknsfw.relics.nymph;

import com.megacrit.cardcrawl.relics.AbstractRelic;
import liesecore.helpers.NsfwRunStats;
import arknsfw.ArkNsfwMod;
import arknsfw.relics.AbstractArkNsfwRelic;

public class NymphMoistFlaskRelic extends AbstractArkNsfwRelic {
    public static final String ID = ArkNsfwMod.makeID("NymphMoistFlaskRelic");

    public NymphMoistFlaskRelic() {
        super(ID, "relic_nymph_nymphmoistflaskrelic.png", RelicTier.SHOP, LandingSound.CLINK);
    }

    @Override public void onVictory() { NsfwRunStats.addExcitement(5); }

    @Override public String getUpdatedDescription() { return DESCRIPTIONS[0]; }
    @Override public AbstractRelic makeCopy() { return new NymphMoistFlaskRelic(); }
}
