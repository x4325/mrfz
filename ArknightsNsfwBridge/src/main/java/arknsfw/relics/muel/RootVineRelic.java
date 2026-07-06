package arknsfw.relics.muel;

import com.megacrit.cardcrawl.relics.AbstractRelic;
import liesecore.helpers.NsfwRunStats;
import arknsfw.ArkNsfwMod;
import arknsfw.helpers.ArkCharMechanicsHelper;
import arknsfw.relics.AbstractArkNsfwRelic;

public class RootVineRelic extends AbstractArkNsfwRelic {
    public static final String ID = ArkNsfwMod.makeID("RootVineRelic");

    public RootVineRelic() {
        super(ID, "root_vine.png", RelicTier.SHOP, LandingSound.SOLID);
    }

    @Override
    public void onPlayerEndTurn() {
        NsfwRunStats.addConception(3 + ArkCharMechanicsHelper.rootageAmount() * 2, false);
        if (NsfwRunStats.excitement >= 40) {
            NsfwRunStats.addConception(3, false);
        }
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

    @Override
    public AbstractRelic makeCopy() {
        return new RootVineRelic();
    }
}
