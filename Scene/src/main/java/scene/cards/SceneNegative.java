package scene.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.VulnerablePower;
import scene.SceneMod;
import scene.powers.FocusPower;

/** 负片 */
public class SceneNegative extends AbstractSceneCard {
    public static final String ID = SceneMod.makeID("Negative");

    public SceneNegative() {
        super(ID, 2, CardType.SKILL, CardRarity.RARE, CardTarget.SELF);

    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        int cur = FocusPower.get(p);
        if (cur > 0) {
            int add = Math.min(cur, 6);
            addToBot(new ApplyPowerAction(p, p, new FocusPower(p, add), add));
        }
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeBaseCost(1);
        }
    }

    @Override
    public AbstractCard makeCopy() { return new SceneNegative(); }
}
