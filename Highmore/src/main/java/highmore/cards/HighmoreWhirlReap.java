package highmore.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import highmore.HighmoreMod;
import highmore.powers.ReapPower;

/** 旋潮：AOE + 2 收割（多段命中配合收割吸血）。 */
public class HighmoreWhirlReap extends AbstractHighmoreCard {
    public static final String ID = HighmoreMod.makeID("WhirlReap");

    public HighmoreWhirlReap() {
        super(ID, 2, CardType.ATTACK, CardRarity.RARE, CardTarget.ALL_ENEMY, "card_highmore_whirlreap.png");
        baseDamage = 5;
        isMultiDamage = true;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        if (p instanceof highmore.characters.Highmore) {
            ((highmore.characters.Highmore) p).playCharAnimation("Skill_1");
        }
        addToBot(new ApplyPowerAction(p, p, new ReapPower(p, 2), 2));
        addToBot(new DamageAllEnemiesAction(p, multiDamage, DamageInfo.DamageType.NORMAL, AbstractGameAction.AttackEffect.SLASH_HORIZONTAL));
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName(); upgradeDamage(2);
        }
    }

    @Override
    public AbstractCard makeCopy() { return new HighmoreWhirlReap(); }
}
