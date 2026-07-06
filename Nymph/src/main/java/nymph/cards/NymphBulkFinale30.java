package nymph.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import nymph.NymphMod;
import nymph.powers.HexPower;

/** 目标咒灵达到阈值时爆发并移除其咒灵。 */
public class NymphBulkFinale30 extends AbstractNymphCard {
    public static final String ID = NymphMod.makeID("BulkFinale30");

    public NymphBulkFinale30() {
        super(ID, 2, CardType.ATTACK, CardRarity.UNCOMMON, CardTarget.ENEMY);
        baseDamage = 13;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractPower stacks = m == null ? null : m.getPower(HexPower.POWER_ID);
        boolean burst = stacks != null && stacks.amount >= 4;
        int bonus = burst ? 12 : 0;
        addToBot(new DamageAction(m, new DamageInfo(p, damage + bonus, DamageInfo.DamageType.NORMAL), AbstractGameAction.AttackEffect.SLASH_HEAVY));
        if (burst) {
            addToBot(new ReducePowerAction(m, p, HexPower.POWER_ID, 4));
        }
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName(); upgradeDamage(5);
        }
    }

    @Override
    public AbstractCard makeCopy() { return new NymphBulkFinale30(); }
}
