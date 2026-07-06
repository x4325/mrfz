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
import arknsfw.powers.muel.LeakPower;

/** 洪痕印记：抽到时渗漏 +2、受孕 +12，并失去全部格挡。 */
public class FloodMarkCard extends AbstractArkCurseCard {
    public static final String ID = ArkNsfwMod.makeID("FloodMarkCard");

    public FloodMarkCard() {
        super(ID, "curse_flood_mark.png");
    }

    @Override
    public void triggerWhenDrawn() {
        NsfwRunStats.addConception(12 + ArkCharMechanicsHelper.rootageAmount() * 2, false);
        NsfwRunStats.addExcitement(10);
        if (AbstractDungeon.player.currentBlock > 0) {
            AbstractDungeon.player.loseBlock(AbstractDungeon.player.currentBlock);
        }
        int leak = 2 + (ArkCharMechanicsHelper.hasManifold() ? 1 : 0);
        addToBot(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player,
                new LeakPower(AbstractDungeon.player, leak), leak));
        if (ArkCharMechanicsHelper.manifoldTookDamageThisCombat()) {
            NsfwRunStats.addConception(6, false);
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
        return new FloodMarkCard();
    }
}
