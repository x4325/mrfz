package arknsfw.relics.curses.archetto;

import com.megacrit.cardcrawl.relics.AbstractRelic;
import arknsfw.ArkNsfwMod;
import arknsfw.relics.AbstractArkNsfwRelic;

public class ArchettoLoopCurseRelic extends AbstractArkNsfwRelic {
    public static final String ID = ArkNsfwMod.makeID("ArchettoLoopCurseRelic");

    public ArchettoLoopCurseRelic() {
        super(ID, "relic_curse_archetto_archettoloopcurserelic.png", RelicTier.SPECIAL, LandingSound.CLINK);
    }

    @Override public String getUpdatedDescription() { return DESCRIPTIONS[0]; }
    @Override public AbstractRelic makeCopy() { return new ArchettoLoopCurseRelic(); }
}
