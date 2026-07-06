package highmore.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.*;
import highmore.HighmoreMod;
import highmore.cards.AbstractHighmoreCard;
import highmore.powers.ReapPower;
public class HighmoreBulkFinale40 extends AbstractHighmoreCard {
    public static final String ID = HighmoreMod.makeID("BulkFinale40");

    public HighmoreBulkFinale40() {
        super(ID, 2, CardType.ATTACK, CardRarity.UNCOMMON, CardTarget.ENEMY);
        baseDamage = 11;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractPower stacks = p.getPower(ReapPower.POWER_ID);
        int bonus = (stacks != null && stacks.amount >= 3) ? 6 : 0;
        addToBot(new DamageAction(m, new DamageInfo(p, damage + bonus, DamageInfo.DamageType.NORMAL), AbstractGameAction.AttackEffect.SLASH_HEAVY));
        if (stacks != null && stacks.amount >= 3) { stacks.amount -= 3; }
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName(); upgradeDamage(4);
        }
    }

    @Override
    public AbstractCard makeCopy() { return new HighmoreBulkFinale40(); }
}
