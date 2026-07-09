package arknsfw.relics.highmore;

import basemod.abstracts.CustomRelic;
import liesecore.helpers.PregnancyMarkLogic;
import arknsfw.ArkNsfwMod;
import arknsfw.relics.AbstractArkNsfwRelic;

/** 海沫：妊娠阶段战利品。 */
public class HighmorePregnancyMarkRelic extends AbstractArkNsfwRelic {
    public static final String ID = ArkNsfwMod.makeID("HighmorePregnancyMarkRelic");

    public HighmorePregnancyMarkRelic() {
        super(ID, "highmore_pregnancy_mark.png", RelicTier.SPECIAL, LandingSound.MAGICAL);
        this.counter = 0;
    }

    @Override
    public void atBattleStart() {
        PregnancyMarkLogic.onBattleStart(this);
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

    @Override
    public CustomRelic makeCopy() {
        return new HighmorePregnancyMarkRelic();
    }
}
