package arknsfw.relics.muel;

import basemod.abstracts.CustomRelic;
import liesecore.helpers.PregnancyMarkLogic;
import arknsfw.ArkNsfwMod;
import arknsfw.relics.AbstractArkNsfwRelic;

/** 缪尔赛思：妊娠阶段战利品。 */
public class MuelPregnancyMarkRelic extends AbstractArkNsfwRelic {
    public static final String ID = ArkNsfwMod.makeID("MuelPregnancyMarkRelic");

    public MuelPregnancyMarkRelic() {
        super(ID, "muel_pregnancy_mark.png", RelicTier.SPECIAL, LandingSound.MAGICAL);
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
        return new MuelPregnancyMarkRelic();
    }
}
