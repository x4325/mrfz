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
public class SceneBulkDraw18 extends AbstractSceneCard {
    public static final String ID = SceneMod.makeID("BulkDraw18");

    public SceneBulkDraw18() {
        super(ID, 0, CardType.SKILL, CardRarity.COMMON, CardTarget.SELF);
        
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new DrawCardAction(p, 2));
        addToBot(new ApplyPowerAction(p, p, new FocusPower(p, 1), 1));
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName(); rawDescription = cardStrings.UPGRADE_DESCRIPTION; initializeDescription();
        }
    }

    @Override
    public AbstractCard makeCopy() { return new SceneBulkDraw18(); }
}
