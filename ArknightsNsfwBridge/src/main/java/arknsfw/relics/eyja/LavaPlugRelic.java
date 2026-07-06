package arknsfw.relics.eyja;

import com.megacrit.cardcrawl.relics.AbstractRelic;
import liesecore.helpers.NsfwRunStats;
import arknsfw.ArkNsfwMod;
import arknsfw.helpers.ArkCharMechanicsHelper;
import arknsfw.relics.AbstractArkNsfwRelic;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class LavaPlugRelic extends AbstractArkNsfwRelic {
    public static final String ID = ArkNsfwMod.makeID("LavaPlugRelic");

    public LavaPlugRelic() {
        super(ID, "lava_plug.png", RelicTier.RARE, LandingSound.SOLID);
    }

    @Override
    public void onPlayCard(com.megacrit.cardcrawl.cards.AbstractCard c, com.megacrit.cardcrawl.monsters.AbstractMonster m) {
        if (c.type == com.megacrit.cardcrawl.cards.AbstractCard.CardType.ATTACK && c.costForTurn >= 2) {
            NsfwRunStats.addFertility(0, 2, false);
            ArkCharMechanicsHelper.markPyrobreath(c);
            if (ArkCharMechanicsHelper.cloudEnergy() >= 1) {
                NsfwRunStats.addFertility(0, 3, false);
            }
        }
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

    @Override
    public AbstractRelic makeCopy() {
        return new LavaPlugRelic();
    }
}
