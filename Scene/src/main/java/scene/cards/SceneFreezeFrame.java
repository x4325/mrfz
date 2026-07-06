package scene.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.*;
import scene.SceneMod;
import scene.cards.AbstractSceneCard;
import scene.powers.FocusPower;
public class SceneFreezeFrame extends AbstractSceneCard {
    public static final String ID = SceneMod.makeID("FreezeFrame");

    public SceneFreezeFrame() {
        super(ID, 2, CardType.SKILL, CardRarity.RARE, CardTarget.SELF);
        baseBlock = 12;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new GainBlockAction(p, block));
        addToBot(new ApplyPowerAction(p, p, new FocusPower(p, 2), 2));
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName(); upgradeBlock(4);
        }
    }

    @Override
    public AbstractCard makeCopy() { return new SceneFreezeFrame(); }
}
