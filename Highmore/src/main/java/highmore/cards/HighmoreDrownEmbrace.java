package highmore.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.VulnerablePower;
import highmore.HighmoreMod;
import highmore.powers.ReapPower;

/** 溺亡之抱 */
public class HighmoreDrownEmbrace extends AbstractHighmoreCard {
    public static final String ID = HighmoreMod.makeID("DrownEmbrace");

    public HighmoreDrownEmbrace() {
        super(ID, 3, CardType.ATTACK, CardRarity.RARE, CardTarget.ENEMY);
        baseDamage = 16;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractPower reap = p.getPower(ReapPower.POWER_ID);
        int stacks = reap == null ? 0 : reap.amount;
        addToBot(new DamageAction(m, new DamageInfo(p, damage + 2 * stacks, DamageInfo.DamageType.NORMAL), AbstractGameAction.AttackEffect.SLASH_HEAVY));
        if (stacks > 0) {
            addToBot(new ReducePowerAction(p, p, ReapPower.POWER_ID, stacks));
        }
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeDamage(6);
        }
    }

    @Override
    public AbstractCard makeCopy() { return new HighmoreDrownEmbrace(); }
}
