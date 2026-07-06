package archetto.powers;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import archetto.ArchettoMod;

/** 协奏：每回合开始获得 amount 层瞄准。 */
public class ConcertoPower extends AbstractPower {
    public static final String POWER_ID = ArchettoMod.makeID("ConcertoPower");
    private static final PowerStrings ps = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);

    public ConcertoPower(AbstractCreature owner, int amount) {
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
        addToBot(new ApplyPowerAction(owner, owner, new AimPower(owner, amount), amount));
    }

    public void stackPower(int n) { super.stackPower(n); updateDescription(); }
    public void updateDescription() { description = ps.DESCRIPTIONS[0] + amount + ps.DESCRIPTIONS[1]; }
}
