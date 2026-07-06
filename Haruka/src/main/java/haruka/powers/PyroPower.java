package haruka.powers;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.actions.common.HealAction;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.StrengthPower;
import haruka.HarukaMod;

public class PyroPower extends AbstractPower {
    public static final String POWER_ID = HarukaMod.makeID("PyroPower");
    private static final PowerStrings ps = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);

    public PyroPower(AbstractCreature owner, int amount) {
        name = ps.NAME;
        ID = POWER_ID;
        this.owner = owner;
        this.amount = amount;
        type = PowerType.BUFF;
        isTurnBased = false;
        updateDescription();
    }
    public static final int THRESHOLD = 10;
    public int burstCount = 0;
    public static int get(AbstractCreature o) {
        AbstractPower p = o.getPower(POWER_ID);
        return p == null ? 0 : p.amount;
    }
    public void onAttack(DamageInfo info, int damageAmount, AbstractCreature target) {
        if (info.owner == this.owner && damageAmount > 0) {
            flash();
            amount += 1;
            if (amount >= THRESHOLD) {
                int dmg = 8 + burstCount * 3;
                addToBot(new DamageAllEnemiesAction((AbstractPlayer) this.owner, dmg,
                        DamageInfo.DamageType.THORNS, AbstractGameAction.AttackEffect.FIRE));
                amount -= THRESHOLD;
                burstCount++;
            }
            updateDescription();
        }
    }
    public void stackPower(int n) { super.stackPower(n); updateDescription(); }
    public void updateDescription() {
        description = ps.DESCRIPTIONS[0] + amount + ps.DESCRIPTIONS[1] + (THRESHOLD - amount) + ps.DESCRIPTIONS[2];
    }
}
