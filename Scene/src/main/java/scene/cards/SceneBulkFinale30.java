package scene.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import scene.SceneMod;
import scene.powers.FocusPower;

public class SceneBulkFinale30 extends AbstractSceneCard {
    public static final String ID = SceneMod.makeID("BulkFinale30");

    public SceneBulkFinale30() {
        super(ID, 2, CardType.ATTACK, CardRarity.UNCOMMON, CardTarget.ENEMY, "card_scene_bulkfinale30.png");
        baseDamage = 13;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractPower stacks = p.getPower(FocusPower.POWER_ID);
        boolean burst = stacks != null && stacks.amount >= 4;
        int bonus = burst ? 12 : 0;
        addToBot(new DamageAction(m, new DamageInfo(p, damage + bonus, DamageInfo.DamageType.NORMAL), AbstractGameAction.AttackEffect.SLASH_HEAVY));
        if (burst) {
            addToBot(new ReducePowerAction(p, p, FocusPower.POWER_ID, 4));
        }
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName(); upgradeDamage(5);
        }
    }

    @Override
    public AbstractCard makeCopy() { return new SceneBulkFinale30(); }
}
