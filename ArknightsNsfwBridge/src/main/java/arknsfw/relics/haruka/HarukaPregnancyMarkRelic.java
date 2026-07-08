package arknsfw.relics.haruka;

import basemod.abstracts.CustomRelic;
import liesecore.helpers.PregnancyMarkLogic;
import arknsfw.ArkNsfwMod;
import arknsfw.relics.AbstractArkNsfwRelic;

/** 遥：妊娠阶段战利品。 */
public class HarukaPregnancyMarkRelic extends AbstractArkNsfwRelic {
    public static final String ID = ArkNsfwMod.makeID("HarukaPregnancyMarkRelic");

    public HarukaPregnancyMarkRelic() {
        super(ID, "haruka_pregnancy_mark.png", RelicTier.SPECIAL, LandingSound.MAGICAL);
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
        return new HarukaPregnancyMarkRelic();
    }
}
