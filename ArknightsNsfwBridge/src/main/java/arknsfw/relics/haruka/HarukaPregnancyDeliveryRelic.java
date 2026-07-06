package arknsfw.relics.haruka;

import basemod.abstracts.CustomRelic;
import liesecore.helpers.PregnancyDeliveryLogic;
import arknsfw.ArkNsfwMod;
import arknsfw.relics.AbstractArkNsfwRelic;

public class HarukaPregnancyDeliveryRelic extends AbstractArkNsfwRelic {
    public static final String ID = ArkNsfwMod.makeID("HarukaPregnancyDeliveryRelic");

    public HarukaPregnancyDeliveryRelic() {
        super(ID, "haruka_pregnancy_mark.png", RelicTier.SPECIAL, LandingSound.MAGICAL);
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
        return new HarukaPregnancyDeliveryRelic();
    }
}
