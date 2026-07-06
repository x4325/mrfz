package haruka.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.*;
import haruka.HarukaMod;
import haruka.cards.AbstractHarukaCard;
import haruka.powers.PyroPower;
public class HarukaBulkSweep34 extends AbstractHarukaCard {
    public static final String ID = HarukaMod.makeID("BulkSweep34");

    public HarukaBulkSweep34() {
        super(ID, 2, CardType.ATTACK, CardRarity.UNCOMMON, CardTarget.ALL_ENEMY);
        baseDamage = 7;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new ApplyPowerAction(p, p, new PyroPower(p, 2), 2));
        addToBot(new DamageAllEnemiesAction(p, damage, DamageInfo.DamageType.NORMAL, AbstractGameAction.AttackEffect.FIRE));
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName(); upgradeDamage(2);
        }
    }

    @Override
    public AbstractCard makeCopy() { return new HarukaBulkSweep34(); }
}
