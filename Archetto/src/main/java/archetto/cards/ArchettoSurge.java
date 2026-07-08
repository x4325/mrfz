package archetto.cards;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.StrengthPower;
import archetto.ArchettoMod;

public class ArchettoSurge extends AbstractArchettoCard {
    public static final String ID = ArchettoMod.makeID("Surge");

    public ArchettoSurge() {
        super(ID, 1, CardType.POWER, CardRarity.RARE, CardTarget.SELF, "card_archetto_surge.png");
        baseMagicNumber = magicNumber = 2;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new ApplyPowerAction(p, p, new StrengthPower(p, magicNumber), magicNumber));
    }

    @Override
    public void upgrade() {
        if (!upgraded) { upgradeName(); upgradeMagicNumber(1); }
    }

    @Override
    public AbstractCard makeCopy() { return new ArchettoSurge(); }
}
