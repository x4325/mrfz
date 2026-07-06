package arknsfw.relics.eyja;

import basemod.abstracts.CustomRelic;
import liesecore.helpers.PregnancyDeliveryLogic;
import arknsfw.ArkNsfwMod;
import arknsfw.relics.AbstractArkNsfwRelic;

public class EyjaPregnancyDeliveryRelic extends AbstractArkNsfwRelic {
    public static final String ID = ArkNsfwMod.makeID("EyjaPregnancyDeliveryRelic");

    public EyjaPregnancyDeliveryRelic() {
        super(ID, "eyja_pregnancy_mark.png", RelicTier.SPECIAL, LandingSound.MAGICAL);
        this.counter = 0;
    }

    @Override
    public void atBattleStart() {
        PregnancyDeliveryLogic.onBattleStart(this);
    }

    @Override
    public String getUpdatedDescription() {
        return PregnancyDeliveryLogic.tierDescription(this, DESCRIPTIONS);
    }

    @Override
    public CustomRelic makeCopy() {
        return new EyjaPregnancyDeliveryRelic();
    }
}
