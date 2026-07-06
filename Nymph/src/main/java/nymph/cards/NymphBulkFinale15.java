package nymph.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.*;
import nymph.NymphMod;
import nymph.cards.AbstractNymphCard;
import nymph.powers.HexPower;
import nymph.powers.FearPower;
import nymph.powers.HeartLockPower;
public class NymphBulkFinale15 extends AbstractNymphCard {
    public static final String ID = NymphMod.makeID("BulkFinale15");

    public NymphBulkFinale15() {
        super(ID, 1, CardType.ATTACK, CardRarity.COMMON, CardTarget.ENEMY);
        baseDamage = 10;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractPower stacks = p.getPower(HexPower.POWER_ID);
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
    public AbstractCard makeCopy() { return new NymphBulkFinale15(); }
}
