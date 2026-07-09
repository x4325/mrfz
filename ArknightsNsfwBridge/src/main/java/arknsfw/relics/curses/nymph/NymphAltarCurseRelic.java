package arknsfw.relics.curses.nymph;

import com.megacrit.cardcrawl.relics.AbstractRelic;
import arknsfw.ArkNsfwMod;
import arknsfw.relics.AbstractArkNsfwRelic;

/** 祭坛诅咒：事件中获得的受孕度翻倍（逻辑见 ArkAltarCursePatch）。 */
public class NymphAltarCurseRelic extends AbstractArkNsfwRelic {
    public static final String ID = ArkNsfwMod.makeID("NymphAltarCurseRelic");

    public NymphAltarCurseRelic() {
        super(ID, "relic_curse_nymph_nymphaltarcurserelic.png", RelicTier.SPECIAL, LandingSound.CLINK);
    }

    @Override public String getUpdatedDescription() { return DESCRIPTIONS[0]; }
    @Override public AbstractRelic makeCopy() { return new NymphAltarCurseRelic(); }
}
