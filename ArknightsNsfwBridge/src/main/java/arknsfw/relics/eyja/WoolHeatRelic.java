package arknsfw.relics.eyja;

import com.megacrit.cardcrawl.relics.AbstractRelic;
import liesecore.helpers.NsfwRunStats;
import arknsfw.ArkNsfwMod;
import arknsfw.helpers.ArkCharMechanicsHelper;
import arknsfw.relics.AbstractArkNsfwRelic;

public class WoolHeatRelic extends AbstractArkNsfwRelic {
    public static final String ID = ArkNsfwMod.makeID("WoolHeatRelic");

    public WoolHeatRelic() {
        super(ID, "wool_heat.png", RelicTier.RARE, LandingSound.MAGICAL);
    }

    @Override
    public void atTurnStart() {
        NsfwRunStats.addConception(2, false);
        ArkCharMechanicsHelper.gainCloudEnergy(1);
    }

    @Override
    public void onLoseHp(int damageAmount) {
        if (damageAmount > 0) {
            if (ArkCharMechanicsHelper.fireMarkPowerAmount() > 0) {
                NsfwRunStats.addExcitement(2);
            } else {
                NsfwRunStats.addExcitement(-3);
            }
        }
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

    @Override
    public AbstractRelic makeCopy() {
        return new WoolHeatRelic();
    }
}
