package arknsfw.powers.fall;

import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import arknsfw.ArkNsfwMod;
import arknsfw.helpers.ArkSafeStats;

/** 催淫毒素：回合结束兴奋 +amount，之后 amount+2 递增；单回合打出≥3张攻击牌可解毒。 */
public class AphroToxinPower extends AbstractArkFallPower {

    public static final String POWER_ID = ArkNsfwMod.makeID("AphroToxinPower");

    public AphroToxinPower(AbstractCreature owner, int amount) {
        super(POWER_ID, owner, amount, PowerType.DEBUFF, "power_aphrotoxin", "poison");
        this.isTurnBased = true;
    }

    private int attacksThisTurn() {
        int n = 0;
        if (AbstractDungeon.actionManager != null
                && AbstractDungeon.actionManager.cardsPlayedThisTurn != null) {
            for (AbstractCard c : AbstractDungeon.actionManager.cardsPlayedThisTurn) {
                if (c.type == AbstractCard.CardType.ATTACK) {
                    n++;
                }
            }
        }
        return n;
    }

    @Override
    public void atEndOfTurn(boolean isPlayer) {
        if (!isPlayer) {
            return;
        }
        if (attacksThisTurn() >= 3) {
            flash();
            AbstractDungeon.actionManager.addToBottom(
                    new RemoveSpecificPowerAction(owner, owner, POWER_ID));
            return;
        }
        flash();
        ArkSafeStats.addExcitementDeferred(amount);
        amount += 2;
        updateDescription();
    }

    @Override
    public void updateDescription() {
        description = str(0) + amount + str(1);
    }
}
