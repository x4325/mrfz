package arknsfw.relics.curses.highmore;

import com.megacrit.cardcrawl.relics.AbstractRelic;
import arknsfw.ArkNsfwMod;
import arknsfw.relics.AbstractArkNsfwRelic;

/** 祭坛诅咒：事件中获得的受孕度翻倍（逻辑见 ArkAltarCursePatch）。 */
public class HighmoreAltarCurseRelic extends AbstractArkNsfwRelic {
    public static final String ID = ArkNsfwMod.makeID("HighmoreAltarCurseRelic");

    public HighmoreAltarCurseRelic() {
        super(ID, "relic_curse_highmore_highmorealtarcurserelic.png", RelicTier.SPECIAL, LandingSound.CLINK);
    }

    @Override public String getUpdatedDescription() { return DESCRIPTIONS[0]; }
    @Override public AbstractRelic makeCopy() { return new HighmoreAltarCurseRelic(); }
}
