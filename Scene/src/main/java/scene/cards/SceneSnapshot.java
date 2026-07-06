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
public class SceneSnapshot extends AbstractSceneCard {
    public static final String ID = SceneMod.makeID("Snapshot");

    public SceneSnapshot() {
        super(ID, 2, CardType.SKILL, CardRarity.UNCOMMON, CardTarget.ALL_ENEMY);
        baseDamage = 18;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        if (FocusPower.get(p) >= 3 && FocusPower.spend(p, 3)) {
            addToBot(new DamageAllEnemiesAction(p, damage, DamageInfo.DamageType.NORMAL, AbstractGameAction.AttackEffect.FIRE));
        }
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName(); upgradeDamage(6);
        }
    }

    @Override
    public AbstractCard makeCopy() { return new SceneSnapshot(); }
}
