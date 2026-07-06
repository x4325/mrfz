package arknsfw.relics.muel;

import com.megacrit.cardcrawl.relics.AbstractRelic;
import liesecore.helpers.NsfwRunStats;
import arknsfw.ArkNsfwMod;
import arknsfw.helpers.ArkCharMechanicsHelper;
import arknsfw.relics.AbstractArkNsfwRelic;

public class BubbleWandRelic extends AbstractArkNsfwRelic {
    public static final String ID = ArkNsfwMod.makeID("BubbleWandRelic");

    public BubbleWandRelic() {
        super(ID, "bubble_wand.png", RelicTier.UNCOMMON, LandingSound.FLAT);
    }

    private int gainThisTurn = 0;

    @Override
    public int onPlayerGainedBlock(float blockAmount) {
        if (blockAmount > 0 && gainThisTurn < 6) {
            int per = ArkCharMechanicsHelper.hasManifold() ? 3 : 2;
            NsfwRunStats.addExcitement(per);
            gainThisTurn += per;
        }
        return Math.round(blockAmount);
    }

    @Override
    public void atTurnStart() {
        gainThisTurn = 0;
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

    @Override
    public AbstractRelic makeCopy() {
        return new BubbleWandRelic();
    }
}
