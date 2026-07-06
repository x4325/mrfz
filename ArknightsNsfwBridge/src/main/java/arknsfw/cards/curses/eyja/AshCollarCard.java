package arknsfw.cards.curses.eyja;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import liesecore.helpers.NsfwRunStats;
import arknsfw.ArkNsfwMod;
import arknsfw.cards.AbstractArkCurseCard;
import arknsfw.helpers.ArkCharMechanicsHelper;
import arknsfw.powers.eyja.AshShamePower;

/** 灰烬项圈印：抽到时灰烬羞怯 +2、兴奋 +15。 */
public class AshCollarCard extends AbstractArkCurseCard {
    public static final String ID = ArkNsfwMod.makeID("AshCollarCard");

    public AshCollarCard() {
        super(ID, "curse_ash_collar.png");
    }

    @Override
    public void triggerWhenDrawn() {
        NsfwRunStats.addExcitement(15 + ArkCharMechanicsHelper.cloudCardCount() * 3);
        addToBot(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player,
                new AshShamePower(AbstractDungeon.player, 2), 2));
        if (ArkCharMechanicsHelper.cloudCardCount() >= 2) {
            ArkCharMechanicsHelper.applyFireMarkPower(AbstractDungeon.player, 1);
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
        return new AshCollarCard();
    }
}
