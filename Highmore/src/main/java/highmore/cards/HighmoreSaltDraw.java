package highmore.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.VulnerablePower;
import highmore.HighmoreMod;
import highmore.powers.ReapPower;

/** 盐析 */
public class HighmoreSaltDraw extends AbstractHighmoreCard {
    public static final String ID = HighmoreMod.makeID("SaltDraw");

    public HighmoreSaltDraw() {
        super(ID, 0, CardType.SKILL, CardRarity.COMMON, CardTarget.SELF, "card_highmore_saltdraw.png");
        baseMagicNumber = magicNumber = 2;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new LoseHPAction(p, p, 3));
        addToBot(new DrawCardAction(p, magicNumber));
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeMagicNumber(1);
        }
    }

    @Override
    public AbstractCard makeCopy() { return new HighmoreSaltDraw(); }
}
