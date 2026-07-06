package arknsfw.relics.curses.haruka;

import com.megacrit.cardcrawl.relics.AbstractRelic;
import arknsfw.ArkNsfwMod;
import arknsfw.relics.AbstractArkNsfwRelic;

public class HarukaLoopCurseRelic extends AbstractArkNsfwRelic {
    public static final String ID = ArkNsfwMod.makeID("HarukaLoopCurseRelic");

    public HarukaLoopCurseRelic() {
        super(ID, "relic_curse_haruka_harukaloopcurserelic.png", RelicTier.SPECIAL, LandingSound.CLINK);
    }

    @Override public String getUpdatedDescription() { return DESCRIPTIONS[0]; }
    @Override public AbstractRelic makeCopy() { return new HarukaLoopCurseRelic(); }
}
