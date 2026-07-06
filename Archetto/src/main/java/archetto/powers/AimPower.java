package archetto.powers;

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
import archetto.ArchettoMod;

public class AimPower extends AbstractPower {
    public static final String POWER_ID = ArchettoMod.makeID("AimPower");
    private static final PowerStrings ps = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);

    public AimPower(AbstractCreature owner, int amount) {
        name = ps.NAME;
        ID = POWER_ID;
        this.owner = owner;
        this.amount = amount;
        type = PowerType.BUFF;
        isTurnBased = false;
        updateDescription();
    }
    public static int get(AbstractCreature o) {
        AbstractPower p = o.getPower(POWER_ID);
        return p == null ? 0 : p.amount;
    }
    public float atDamageGive(float damage, DamageInfo.DamageType type, AbstractCard card) {
        if (amount > 0 && card.type == AbstractCard.CardType.ATTACK) {
            return damage + amount * 2;
        }
        return damage;
    }
    public void onAttack(DamageInfo info, int damageAmount, AbstractCreature target) {
        if (info.owner == this.owner && amount > 0) {
            addToBot(new ReducePowerAction(owner, owner, POWER_ID, amount));
        }
    }
    public void stackPower(int n) { super.stackPower(n); updateDescription(); }
    public void updateDescription() { description = ps.DESCRIPTIONS[0] + (amount * 2) + ps.DESCRIPTIONS[1]; }
}
