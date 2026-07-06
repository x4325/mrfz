package haruka.powers;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import haruka.HarukaMod;

/** 终章余韵：每回合开始获得 amount 层花火。 */
public class FinaleEchoPower extends AbstractPower {
    public static final String POWER_ID = HarukaMod.makeID("FinaleEchoPower");
    private static final PowerStrings ps = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);

    public FinaleEchoPower(AbstractCreature owner, int amount) {
        name = ps.NAME;
        ID = POWER_ID;
        this.owner = owner;
        this.amount = amount;
        type = PowerType.BUFF;
        isTurnBased = false;
        updateDescription();
    }

    @Override
    public void atStartOfTurn() {
        flash();
        addToBot(new ApplyPowerAction(owner, owner, new PyroPower(owner, amount), amount));
    }

    public void stackPower(int n) { super.stackPower(n); updateDescription(); }
    public void updateDescription() { description = ps.DESCRIPTIONS[0] + amount + ps.DESCRIPTIONS[1]; }
}
