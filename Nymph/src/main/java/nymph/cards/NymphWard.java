package nymph.cards;

import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import nymph.NymphMod;

public class NymphWard extends AbstractNymphCard {
    public static final String ID = NymphMod.makeID("Ward");

    public NymphWard() {
        super(ID, 1, CardType.SKILL, CardRarity.COMMON, CardTarget.SELF);
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
    public AbstractCard makeCopy() { return new NymphWard(); }
}
