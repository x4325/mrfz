package arknsfw.relics.eyja;

import com.megacrit.cardcrawl.relics.AbstractRelic;
import liesecore.helpers.NsfwRunStats;
import arknsfw.ArkNsfwMod;
import arknsfw.helpers.ArkCharMechanicsHelper;
import arknsfw.relics.AbstractArkNsfwRelic;

public class EmberSeedRelic extends AbstractArkNsfwRelic {
    public static final String ID = ArkNsfwMod.makeID("EmberSeedRelic");

    public EmberSeedRelic() {
        super(ID, "ember_seed.png", RelicTier.BOSS, LandingSound.MAGICAL);
    }

    @Override
    public void onPlayerEndTurn() {
        if (NsfwRunStats.excitement >= 50) {
            int gain = 5 + ArkCharMechanicsHelper.fireMarkPowerAmount() * 2;
            NsfwRunStats.addConception(gain, false);
            ArkCharMechanicsHelper.gainCloudEnergy(1);
        }
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

    @Override
    public AbstractRelic makeCopy() {
        return new EmberSeedRelic();
    }
}
