package archetto.cards;

import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import archetto.ArchettoMod;

public class ArchettoWard extends AbstractArchettoCard {
    public static final String ID = ArchettoMod.makeID("Ward");

    public ArchettoWard() {
        super(ID, 1, CardType.SKILL, CardRarity.COMMON, CardTarget.SELF, "card_archetto_ward.png");
        baseBlock = 7;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new GainBlockAction(p, block));
    }

    @Override
    public void upgrade() {
        if (!upgraded) { upgradeName(); upgradeBlock(3); }
    }

    @Override
    public AbstractCard makeCopy() { return new ArchettoWard(); }
}
