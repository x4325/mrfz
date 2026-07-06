package arknsfw.relics.nymph;

import com.megacrit.cardcrawl.relics.AbstractRelic;
import liesecore.helpers.NsfwRunStats;
import arknsfw.ArkNsfwMod;
import arknsfw.relics.AbstractArkNsfwRelic;

public class NymphTwinMirrorRelic extends AbstractArkNsfwRelic {
    public static final String ID = ArkNsfwMod.makeID("NymphTwinMirrorRelic");

    public NymphTwinMirrorRelic() {
        super(ID, "relic_nymph_nymphtwinmirrorrelic.png", RelicTier.RARE, LandingSound.CLINK);
    }

    @Override public void onVictory() { flash(); }

    @Override public String getUpdatedDescription() { return DESCRIPTIONS[0]; }
    @Override public AbstractRelic makeCopy() { return new NymphTwinMirrorRelic(); }
}
