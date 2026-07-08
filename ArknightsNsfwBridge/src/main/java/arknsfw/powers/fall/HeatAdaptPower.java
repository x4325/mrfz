package arknsfw.powers.fall;

import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import liesecore.helpers.NsfwRunStats;
import arknsfw.ArkNsfwMod;

/** 发情适应：每回合开始，若兴奋高于上回合，抽 amount 张牌。 */
public class HeatAdaptPower extends AbstractArkFallPower {

    public static final String POWER_ID = ArkNsfwMod.makeID("HeatAdaptPower");

    private int lastExcitement = -1;

    public HeatAdaptPower(AbstractCreature owner, int amount) {
        super(POWER_ID, owner, amount, PowerType.BUFF, "power_heatadapt", "flex");
    }

    @Override
    public void atStartOfTurn() {
        int now = NsfwRunStats.excitement;
        if (lastExcitement >= 0 && now > lastExcitement) {
            flash();
            AbstractDungeon.actionManager.addToBottom(
                    new DrawCardAction(owner, amount));
        }
        lastExcitement = now;
    }

    @Override
    public void updateDescription() {
        description = str(0) + amount + str(1);
    }
}
