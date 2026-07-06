package arknsfw.relics.muel;

import com.megacrit.cardcrawl.relics.AbstractRelic;
import liesecore.helpers.NsfwRunStats;
import arknsfw.ArkNsfwMod;
import arknsfw.helpers.ArkCharMechanicsHelper;
import arknsfw.relics.AbstractArkNsfwRelic;

public class CloneTagRelic extends AbstractArkNsfwRelic {
    public static final String ID = ArkNsfwMod.makeID("CloneTagRelic");

    public CloneTagRelic() {
        super(ID, "clone_tag.png", RelicTier.RARE, LandingSound.MAGICAL);
    }

    @Override
    public void atBattleStart() {
        flash();
        NsfwRunStats.addExcitement(8);
        NsfwRunStats.addConception(6 + ArkCharMechanicsHelper.manifoldTotalAmount(), false);
        if (ArkCharMechanicsHelper.hasManifold()) {
            ArkCharMechanicsHelper.boostManifold(1);
        }
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

    @Override
    public AbstractRelic makeCopy() {
        return new CloneTagRelic();
    }
}
