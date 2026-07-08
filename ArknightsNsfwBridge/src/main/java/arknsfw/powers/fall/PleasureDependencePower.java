package arknsfw.powers.fall;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.LoseHPAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import liesecore.helpers.NsfwRunStats;
import arknsfw.ArkNsfwMod;

/** 快感依存：回合结束时，若本回合没有获得过兴奋，失去 amount×4 生命。 */
public class PleasureDependencePower extends AbstractArkFallPower {

    public static final String POWER_ID = ArkNsfwMod.makeID("PleasureDependencePower");

    private int snapshot = -1;

    public PleasureDependencePower(AbstractCreature owner, int amount) {
        super(POWER_ID, owner, amount, PowerType.DEBUFF, "power_dependence", "flex");
    }

    @Override
    public void atStartOfTurn() {
        snapshot = NsfwRunStats.excitement;
    }

    @Override
    public void atEndOfTurn(boolean isPlayer) {
        if (!isPlayer || snapshot < 0) {
            return;
        }
        if (NsfwRunStats.excitement <= snapshot) {
            flash();
            AbstractDungeon.actionManager.addToBottom(
                    new LoseHPAction(owner, owner, amount * 4,
                            AbstractGameAction.AttackEffect.NONE));
        }
    }

    @Override
    public void updateDescription() {
        description = str(0) + (amount * 4) + str(1);
    }
}
