package archetto.cards;

import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import archetto.ArchettoMod;
import archetto.powers.AimPower;

/** 装填：0 费获得瞄准并抽牌。 */
public class ArchettoReload extends AbstractArchettoCard {
    public static final String ID = ArchettoMod.makeID("Reload");

    public ArchettoReload() {
        super(ID, 0, CardType.SKILL, CardRarity.UNCOMMON, CardTarget.SELF, "card_archetto_reload.png");
        baseMagicNumber = magicNumber = 1;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new ApplyPowerAction(p, p, new AimPower(p, magicNumber), magicNumber));
        addToBot(new DrawCardAction(p, 1));
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName(); upgradeMagicNumber(1);
        }
    }

    @Override
    public AbstractCard makeCopy() { return new ArchettoReload(); }
}
