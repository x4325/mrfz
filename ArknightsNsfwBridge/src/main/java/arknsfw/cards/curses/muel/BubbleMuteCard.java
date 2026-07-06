package arknsfw.cards.curses.muel;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import liesecore.helpers.NsfwRunStats;
import arknsfw.ArkNsfwMod;
import arknsfw.cards.AbstractArkCurseCard;
import arknsfw.helpers.ArkCharMechanicsHelper;
import arknsfw.powers.muel.BubbleGagPower;

/** 泡沫禁言：抽到时泡沫封口 +2、兴奋 +12。 */
public class BubbleMuteCard extends AbstractArkCurseCard {
    public static final String ID = ArkNsfwMod.makeID("BubbleMuteCard");

    public BubbleMuteCard() {
        super(ID, "curse_bubble_mute.png");
    }

    @Override
    public void triggerWhenDrawn() {
        int gag = ArkCharMechanicsHelper.isCultivating() ? 3 : 2;
        NsfwRunStats.addExcitement(12);
        addToBot(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player,
                new BubbleGagPower(AbstractDungeon.player, gag), gag));
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
    }

    @Override
    public void upgrade() {
    }

    @Override
    public AbstractCard makeCopy() {
        return new BubbleMuteCard();
    }
}
