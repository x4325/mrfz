package archetto.cards;

import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import archetto.ArchettoMod;
import archetto.powers.ConcertoPower;

/** 协奏：能力牌，每回合开始获得瞄准。 */
public class ArchettoConcerto extends AbstractArchettoCard {
    public static final String ID = ArchettoMod.makeID("Concerto");

    public ArchettoConcerto() {
        super(ID, 1, CardType.POWER, CardRarity.RARE, CardTarget.SELF, "card_archetto_concerto.png");
        baseMagicNumber = magicNumber = 1;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new ApplyPowerAction(p, p, new ConcertoPower(p, magicNumber), magicNumber));
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName(); upgradeMagicNumber(1);
        }
    }

    @Override
    public AbstractCard makeCopy() { return new ArchettoConcerto(); }
}
