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

/** 对所有敌人造成伤害并施加咒灵。 */
public class NymphBulkSweep04 extends AbstractNymphCard {
    public static final String ID = NymphMod.makeID("BulkSweep04");

    public NymphBulkSweep04() {
        super(ID, 2, CardType.ATTACK, CardRarity.COMMON, CardTarget.ALL_ENEMY);
        baseDamage = 7;
        isMultiDamage = true;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        for (AbstractMonster mo : AbstractDungeon.getMonsters().monsters) {
            if (!mo.isDead && !mo.isDying) {
                addToBot(new ApplyPowerAction(mo, p, new HexPower(mo, 1), 1));
            }
        }
        addToBot(new DamageAllEnemiesAction(p, multiDamage, DamageInfo.DamageType.NORMAL, AbstractGameAction.AttackEffect.FIRE));
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName(); upgradeDamage(3);
        }
    }

    @Override
    public AbstractCard makeCopy() { return new NymphBulkSweep04(); }
}
