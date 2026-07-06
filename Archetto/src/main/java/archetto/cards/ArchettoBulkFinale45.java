package archetto.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.*;
import archetto.ArchettoMod;
import archetto.cards.AbstractArchettoCard;
import archetto.powers.AimPower;
public class ArchettoBulkFinale45 extends AbstractArchettoCard {
    public static final String ID = ArchettoMod.makeID("BulkFinale45");

    public ArchettoBulkFinale45() {
        super(ID, 2, CardType.ATTACK, CardRarity.RARE, CardTarget.ENEMY);
        baseDamage = 10;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractPower stacks = p.getPower(AimPower.POWER_ID);
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
    public AbstractCard makeCopy() { return new ArchettoBulkFinale45(); }
}
