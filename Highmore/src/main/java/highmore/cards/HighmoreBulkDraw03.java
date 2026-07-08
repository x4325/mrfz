package highmore.cards;

import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import highmore.HighmoreMod;
import highmore.powers.ReapPower;

/** 退潮：0 费抽 1 + 收割（升级后收割 +1）。 */
public class HighmoreBulkDraw03 extends AbstractHighmoreCard {
    public static final String ID = HighmoreMod.makeID("BulkDraw03");

    public HighmoreBulkDraw03() {
        super(ID, 0, CardType.SKILL, CardRarity.COMMON, CardTarget.SELF, "card_highmore_bulkdraw03.png");
        baseMagicNumber = magicNumber = 1;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new DrawCardAction(p, 1));
        addToBot(new ApplyPowerAction(p, p, new ReapPower(p, magicNumber), magicNumber));
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName(); upgradeMagicNumber(1);
        }
    }

    @Override
    public AbstractCard makeCopy() { return new HighmoreBulkDraw03(); }
}
