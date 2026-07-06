package haruka.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import haruka.HarukaMod;
import haruka.powers.PyroPower;

public class HarukaBulkFinale30 extends AbstractHarukaCard {
    public static final String ID = HarukaMod.makeID("BulkFinale30");

    public HarukaBulkFinale30() {
        super(ID, 2, CardType.ATTACK, CardRarity.UNCOMMON, CardTarget.ENEMY);
        baseDamage = 13;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractPower stacks = p.getPower(PyroPower.POWER_ID);
        int bonus = (stacks != null && stacks.amount >= 6) ? 12 : 0;
        addToBot(new DamageAction(m, new DamageInfo(p, damage + bonus, DamageInfo.DamageType.NORMAL), AbstractGameAction.AttackEffect.SLASH_HEAVY));
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName(); upgradeDamage(5);
        }
    }

    @Override
    public AbstractCard makeCopy() { return new HarukaBulkFinale30(); }
}
