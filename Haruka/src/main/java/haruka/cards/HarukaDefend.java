package haruka.cards;

import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import haruka.HarukaMod;

public class HarukaDefend extends AbstractHarukaCard {
    public static final String ID = HarukaMod.makeID("Defend");

    public HarukaDefend() {
        super(ID, 1, CardType.SKILL, CardRarity.BASIC, CardTarget.SELF, "card_defend.png");
        baseBlock = 5;
        this.tags.add(CardTags.STARTER_DEFEND);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new GainBlockAction(p, p, block));
    }

    @Override
    public void upgrade() {
        if (!upgraded) { upgradeName(); upgradeBlock(3); }
    }

    @Override
    public AbstractCard makeCopy() { return new HarukaDefend(); }
}
