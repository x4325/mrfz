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
import arknsfw.powers.muel.CloneEchoPower;

/** 分身残留：抽到时分身回音 +2、兴奋 +15。 */
public class CloneResidueCard extends AbstractArkCurseCard {
    public static final String ID = ArkNsfwMod.makeID("CloneResidueCard");

    public CloneResidueCard() {
        super(ID, "curse_clone_residue.png");
    }

    @Override
    public void triggerWhenDrawn() {
        NsfwRunStats.addExcitement(15 + ArkCharMechanicsHelper.manifoldTotalAmount() * 2);
        int echo = 2 + (ArkCharMechanicsHelper.hasManifold() ? 1 : 0);
        addToBot(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player,
                new CloneEchoPower(AbstractDungeon.player, echo), echo));
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
    }

    @Override
    public void upgrade() {
    }

    @Override
    public AbstractCard makeCopy() {
        return new CloneResidueCard();
    }
}
