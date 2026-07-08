package arknsfw.powers.fall;

import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import arknsfw.ArkNsfwMod;
import arknsfw.helpers.ArkSafeStats;

/** 敏感体质：每回合开始兴奋 +4×层数；每回合结束层数 -1。 */
public class SensitivePower extends AbstractArkFallPower {

    public static final String POWER_ID = ArkNsfwMod.makeID("SensitivePower");

    public SensitivePower(AbstractCreature owner, int amount) {
        super(POWER_ID, owner, amount, PowerType.DEBUFF, "power_sensitive", "flex");
        this.isTurnBased = true;
    }

    @Override
    public void atStartOfTurn() {
        flash();
        ArkSafeStats.addExcitementDeferred(4 * amount);
    }

    @Override
    public void atEndOfRound() {
        if (amount <= 1) {
            AbstractDungeon.actionManager.addToBottom(
                    new com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction(owner, owner, POWER_ID));
        } else {
            AbstractDungeon.actionManager.addToBottom(
                    new ReducePowerAction(owner, owner, POWER_ID, 1));
        }
    }

    @Override
    public void updateDescription() {
        description = str(0) + (4 * amount) + str(1);
    }
}
