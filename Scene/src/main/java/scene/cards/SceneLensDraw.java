package scene.cards;

import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import scene.SceneMod;
import scene.powers.FocusPower;

/** 换镜：0 费获得取景并抽牌。 */
public class SceneLensDraw extends AbstractSceneCard {
    public static final String ID = SceneMod.makeID("LensDraw");

    public SceneLensDraw() {
        super(ID, 0, CardType.SKILL, CardRarity.UNCOMMON, CardTarget.SELF, "card_scene_lensdraw.png");
        baseMagicNumber = magicNumber = 2;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new ApplyPowerAction(p, p, new FocusPower(p, magicNumber), magicNumber));
        addToBot(new DrawCardAction(p, 1));
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName(); upgradeMagicNumber(1);
        }
    }

    @Override
    public AbstractCard makeCopy() { return new SceneLensDraw(); }
}
