package arknsfw.cards.curses.eyja;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import liesecore.helpers.NsfwRunStats;
import arknsfw.ArkNsfwMod;
import arknsfw.cards.AbstractArkCurseCard;
import arknsfw.helpers.ArkCharMechanicsHelper;

/** 熔岩回响：留在手牌时，回合结束兴奋 +10、受孕 +8。 */
public class MagmaEchoCard extends AbstractArkCurseCard {
    public static final String ID = ArkNsfwMod.makeID("MagmaEchoCard");

    public MagmaEchoCard() {
        super(ID, "curse_magma_echo.png");
    }

    @Override
    public void triggerOnEndOfPlayerTurn() {
        if (AbstractDungeon.player != null && AbstractDungeon.player.hand.contains(this)) {
            int cloud = ArkCharMechanicsHelper.cloudCardCount();
            NsfwRunStats.addExcitement(10 + cloud * 5);
            NsfwRunStats.addConception(8, false);
            NsfwRunStats.addFertility(0, 6, false);
            if (cloud >= 1) {
                ArkCharMechanicsHelper.gainCloudEnergy(1);
            }
        }
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
    }

    @Override
    public void upgrade() {
    }

    @Override
    public AbstractCard makeCopy() {
        return new MagmaEchoCard();
    }
}
