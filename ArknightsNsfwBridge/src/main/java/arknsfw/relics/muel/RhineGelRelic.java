package arknsfw.relics.muel;

import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import liesecore.helpers.NsfwRunStats;
import arknsfw.ArkNsfwMod;
import arknsfw.helpers.ArkCharMechanicsHelper;
import arknsfw.relics.AbstractArkNsfwRelic;
import com.megacrit.cardcrawl.relics.AbstractRelic;

public class RhineGelRelic extends AbstractArkNsfwRelic {
    public static final String ID = ArkNsfwMod.makeID("RhineGelRelic");

    public RhineGelRelic() {
        super(ID, "rhine_gel.png", RelicTier.UNCOMMON, LandingSound.CLINK);
    }

    @Override
    public void atTurnStart() {
        flash();
        NsfwRunStats.addExcitement(5 + (ArkCharMechanicsHelper.hasManifold() ? 3 : 0));
        if (ArkCharMechanicsHelper.hasManifold()) {
            NsfwRunStats.addConception(2, false);
        }
    }

    @Override
    public void onPlayerEndTurn() {
        if (AbstractDungeon.player != null) {
            NsfwRunStats.addConception(3, false);
            if (ArkCharMechanicsHelper.manifoldTookDamageThisCombat()) {
                NsfwRunStats.addConception(4, false);
            }
        }
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

    @Override
    public AbstractRelic makeCopy() {
        return new RhineGelRelic();
    }
}
