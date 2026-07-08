package haruka.cards;

import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import haruka.HarukaMod;
import haruka.powers.FinaleEchoPower;

/** 终章：能力牌，每回合开始获得花火。 */
public class HarukaFinaleMark extends AbstractHarukaCard {
    public static final String ID = HarukaMod.makeID("FinaleMark");

    public HarukaFinaleMark() {
        super(ID, 1, CardType.POWER, CardRarity.RARE, CardTarget.SELF, "card_haruka_finalemark.png");
        baseMagicNumber = magicNumber = 2;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new ApplyPowerAction(p, p, new FinaleEchoPower(p, magicNumber), magicNumber));
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName(); upgradeMagicNumber(1);
        }
    }

    @Override
    public AbstractCard makeCopy() { return new HarukaFinaleMark(); }
}
