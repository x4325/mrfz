package highmore.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import highmore.HighmoreMod;
import highmore.powers.ReapPower;

/** 大收割：收割 ≥4 时大爆发并消耗 4 层。 */
public class HighmoreBulkFinale30 extends AbstractHighmoreCard {
    public static final String ID = HighmoreMod.makeID("BulkFinale30");

    public HighmoreBulkFinale30() {
        super(ID, 2, CardType.ATTACK, CardRarity.UNCOMMON, CardTarget.ENEMY);
        baseDamage = 13;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        if (p instanceof highmore.characters.Highmore) {
            ((highmore.characters.Highmore) p).playCharAnimation("Skill_2_Attack");
        }
        AbstractPower stacks = p.getPower(ReapPower.POWER_ID);
        boolean burst = stacks != null && stacks.amount >= 4;
        int bonus = burst ? 12 : 0;
        addToBot(new DamageAction(m, new DamageInfo(p, damage + bonus, DamageInfo.DamageType.NORMAL), AbstractGameAction.AttackEffect.SLASH_HEAVY));
        if (burst) {
            addToBot(new ReducePowerAction(p, p, ReapPower.POWER_ID, 4));
        }
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName(); upgradeDamage(5);
        }
    }

    @Override
    public AbstractCard makeCopy() { return new HighmoreBulkFinale30(); }
}
