package highmore.powers;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import highmore.HighmoreMod;


/** 血鳞：每当失去生命，获得 amount 层收割。 */
public class BloodScalePower extends AbstractPower {
    public static final String POWER_ID = HighmoreMod.makeID("BloodScalePower");
    private static final PowerStrings ps = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);

    public BloodScalePower(AbstractCreature owner, int amount) {
        name = ps.NAME;
        ID = POWER_ID;
        this.owner = owner;
        this.amount = amount;
        type = PowerType.BUFF;
        isTurnBased = false;
        updateDescription();
        loadRegion("double");
    }

    @Override
    public int onLoseHp(int damageAmount) {
        if (damageAmount > 0 && owner != null && owner.isPlayer) {
            flash();
            addToBot(new ApplyPowerAction(owner, owner, new ReapPower(owner, amount), amount));
        }
        return damageAmount;
    }

    public void stackPower(int n) { super.stackPower(n); updateDescription(); }
    public void updateDescription() { description = ps.DESCRIPTIONS[0] + amount + ps.DESCRIPTIONS[1]; }
}
