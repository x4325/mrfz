package scene.cards;

import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import scene.SceneMod;

public class SceneDefend extends AbstractSceneCard {
    public static final String ID = SceneMod.makeID("Defend");

    public SceneDefend() {
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
    public AbstractCard makeCopy() { return new SceneDefend(); }
}
