package arknsfw.powers.fall;

import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import arknsfw.ArkNsfwMod;

/** 绝顶余韵：获得的格挡 -25%；每回合层数 -1。 */
public class AfterglowPower extends AbstractArkFallPower {

    public static final String POWER_ID = ArkNsfwMod.makeID("AfterglowPower");

    public AfterglowPower(AbstractCreature owner, int amount) {
        super(POWER_ID, owner, amount, PowerType.DEBUFF, "power_afterglow", "frail");
        this.isTurnBased = true;
    }

    @Override
    public float modifyBlock(float blockAmount) {
        return blockAmount * 0.75f;
    }

    @Override
    public void atEndOfRound() {
        if (amount <= 1) {
            AbstractDungeon.actionManager.addToBottom(
                    new RemoveSpecificPowerAction(owner, owner, POWER_ID));
        } else {
            AbstractDungeon.actionManager.addToBottom(
                    new ReducePowerAction(owner, owner, POWER_ID, 1));
        }
    }

    @Override
    public void updateDescription() {
        description = str(0);
    }
}
