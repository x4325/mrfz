package highmore.cards;

import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import highmore.HighmoreMod;
import highmore.powers.ReapPower;

/** 观澜：1 费抽 2 + 收割（升级后收割 +1）。 */
public class HighmoreBulkDraw08 extends AbstractHighmoreCard {
    public static final String ID = HighmoreMod.makeID("BulkDraw08");

    public HighmoreBulkDraw08() {
        super(ID, 1, CardType.SKILL, CardRarity.UNCOMMON, CardTarget.SELF, "card_highmore_bulkdraw08.png");
        baseMagicNumber = magicNumber = 1;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new DrawCardAction(p, 2));
        addToBot(new ApplyPowerAction(p, p, new ReapPower(p, magicNumber), magicNumber));
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName(); upgradeMagicNumber(1);
        }
    }

    @Override
    public AbstractCard makeCopy() { return new HighmoreBulkDraw08(); }
}
