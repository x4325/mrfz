package arknsfw.relics.curses.haruka;

import com.megacrit.cardcrawl.relics.AbstractRelic;
import arknsfw.ArkNsfwMod;
import arknsfw.relics.AbstractArkNsfwRelic;

/** 祭坛诅咒：事件中获得的受孕度翻倍（逻辑见 ArkAltarCursePatch）。 */
public class HarukaAltarCurseRelic extends AbstractArkNsfwRelic {
    public static final String ID = ArkNsfwMod.makeID("HarukaAltarCurseRelic");

    public HarukaAltarCurseRelic() {
        super(ID, "relic_curse_haruka_harukaaltarcurserelic.png", RelicTier.SPECIAL, LandingSound.CLINK);
    }

    @Override public String getUpdatedDescription() { return DESCRIPTIONS[0]; }
    @Override public AbstractRelic makeCopy() { return new HarukaAltarCurseRelic(); }
}
