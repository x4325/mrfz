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
public class SceneDevelop extends AbstractSceneCard {
    public static final String ID = SceneMod.makeID("Develop");

    public SceneDevelop() {
        super(ID, 1, CardType.SKILL, CardRarity.UNCOMMON, CardTarget.SELF);
        baseBlock = 8;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new GainBlockAction(p, block));
        if (FocusPower.get(p) >= 2 && FocusPower.spend(p, 2)) {
            addToBot(new HealAction(p, p, 6));
        }
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName(); upgradeBlock(3);
        }
    }

    @Override
    public AbstractCard makeCopy() { return new SceneDevelop(); }
}
