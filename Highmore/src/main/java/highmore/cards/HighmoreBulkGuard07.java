package highmore.cards;

import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import highmore.HighmoreMod;
import highmore.powers.ReapPower;

/** 深潜姿态：大格挡 + 1 收割。 */
public class HighmoreBulkGuard07 extends AbstractHighmoreCard {
    public static final String ID = HighmoreMod.makeID("BulkGuard07");

    public HighmoreBulkGuard07() {
        super(ID, 1, CardType.SKILL, CardRarity.UNCOMMON, CardTarget.SELF);
        baseBlock = 10;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new GainBlockAction(p, p, block));
        addToBot(new ApplyPowerAction(p, p, new ReapPower(p, 1), 1));
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName(); upgradeBlock(4);
        }
    }

    @Override
    public AbstractCard makeCopy() { return new HighmoreBulkGuard07(); }
}
