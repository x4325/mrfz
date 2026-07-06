package arknsfw.relics.curses.nymph;

import com.megacrit.cardcrawl.relics.AbstractRelic;
import arknsfw.ArkNsfwMod;
import arknsfw.relics.AbstractArkNsfwRelic;

public class NymphBrandCurseRelic extends AbstractArkNsfwRelic {
    public static final String ID = ArkNsfwMod.makeID("NymphBrandCurseRelic");

    public NymphBrandCurseRelic() {
        super(ID, "relic_curse_nymph_nymphbrandcurserelic.png", RelicTier.SPECIAL, LandingSound.CLINK);
    }

    @Override public String getUpdatedDescription() { return DESCRIPTIONS[0]; }
    @Override public AbstractRelic makeCopy() { return new NymphBrandCurseRelic(); }
}
