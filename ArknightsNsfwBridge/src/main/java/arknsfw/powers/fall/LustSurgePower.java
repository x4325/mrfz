package arknsfw.powers.fall;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.DexterityPower;
import com.megacrit.cardcrawl.powers.StrengthPower;
import liesecore.helpers.NsfwRunStats;
import arknsfw.ArkNsfwMod;

/** 淫气高涨：每回合开始，兴奋≥50 时获得 amount 点力量与敏捷。 */
public class LustSurgePower extends AbstractArkFallPower {

    public static final String POWER_ID = ArkNsfwMod.makeID("LustSurgePower");

    public LustSurgePower(AbstractCreature owner, int amount) {
        super(POWER_ID, owner, amount, PowerType.BUFF, "power_lustsurge", "flex");
    }

    @Override
    public void atStartOfTurn() {
        if (NsfwRunStats.excitement >= 50) {
            flash();
            AbstractDungeon.actionManager.addToBottom(
                    new ApplyPowerAction(owner, owner, new StrengthPower(owner, amount), amount));
            AbstractDungeon.actionManager.addToBottom(
                    new ApplyPowerAction(owner, owner, new DexterityPower(owner, amount), amount));
        }
    }

    @Override
    public void updateDescription() {
        description = str(0) + amount + str(1);
    }
}
