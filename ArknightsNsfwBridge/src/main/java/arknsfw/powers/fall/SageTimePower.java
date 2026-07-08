package arknsfw.powers.fall;

import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import arknsfw.ArkNsfwMod;

/** 贤者时间：下回合开始时获得 1 费与 2 抽，然后消失（高潮失控后的回神）。 */
public class SageTimePower extends AbstractArkFallPower {

    public static final String POWER_ID = ArkNsfwMod.makeID("SageTimePower");

    public SageTimePower(AbstractCreature owner) {
        super(POWER_ID, owner, 1, PowerType.BUFF, "power_sagetime", "flex");
    }

    @Override
    public void atStartOfTurn() {
        flash();
        AbstractDungeon.actionManager.addToBottom(new GainEnergyAction(1));
        AbstractDungeon.actionManager.addToBottom(new DrawCardAction(owner, 2));
        AbstractDungeon.actionManager.addToBottom(
                new RemoveSpecificPowerAction(owner, owner, POWER_ID));
    }

    @Override
    public void updateDescription() {
        description = str(0);
    }
}
