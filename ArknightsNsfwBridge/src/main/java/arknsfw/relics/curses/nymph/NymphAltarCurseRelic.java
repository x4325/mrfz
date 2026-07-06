package arknsfw.relics.curses.nymph;

import com.megacrit.cardcrawl.relics.AbstractRelic;
import arknsfw.ArkNsfwMod;
import arknsfw.relics.AbstractArkNsfwRelic;

public class NymphAltarCurseRelic extends AbstractArkNsfwRelic {
    public static final String ID = ArkNsfwMod.makeID("NymphAltarCurseRelic");

    public NymphAltarCurseRelic() {
        super(ID, "relic_curse_nymph_nymphaltarcurserelic.png", RelicTier.SPECIAL, LandingSound.CLINK);
    }

    @Override public String getUpdatedDescription() { return DESCRIPTIONS[0]; }
    @Override public AbstractRelic makeCopy() { return new NymphAltarCurseRelic(); }
}
