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

/** 显影加速 */
public class SceneDevelopRush extends AbstractSceneCard {
    public static final String ID = SceneMod.makeID("DevelopRush");

    public SceneDevelopRush() {
        super(ID, 1, CardType.SKILL, CardRarity.COMMON, CardTarget.SELF, "card_scene_developrush.png");
        baseMagicNumber = magicNumber = 1;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        if (FocusPower.get(p) >= 1 && FocusPower.spend(p, 1)) {
            addToBot(new DrawCardAction(p, 2));
        } else {
            addToBot(new DrawCardAction(p, 1));
        }
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            // 升级由本地化描述体现
            this.rawDescription = cardStrings.DESCRIPTION;
            initializeDescription();
        }
    }

    @Override
    public AbstractCard makeCopy() { return new SceneDevelopRush(); }
}
