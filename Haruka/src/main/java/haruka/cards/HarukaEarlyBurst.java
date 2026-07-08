package haruka.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.VulnerablePower;
import haruka.HarukaMod;
import haruka.powers.PyroPower;

/** 提前引爆 */
public class HarukaEarlyBurst extends AbstractHarukaCard {
    public static final String ID = HarukaMod.makeID("EarlyBurst");

    public HarukaEarlyBurst() {
        super(ID, 1, CardType.SKILL, CardRarity.UNCOMMON, CardTarget.SELF, "card_haruka_earlyburst.png");
        baseMagicNumber = magicNumber = 8;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractPower pyro = p.getPower(PyroPower.POWER_ID);
        if (pyro != null && pyro.amount >= 6) {
            addToBot(new ReducePowerAction(p, p, PyroPower.POWER_ID, 6));
            addToBot(new DamageAllEnemiesAction(p, com.megacrit.cardcrawl.cards.DamageInfo.createDamageMatrix(magicNumber, true), DamageInfo.DamageType.THORNS, AbstractGameAction.AttackEffect.FIRE));
        }
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeMagicNumber(3);
        }
    }

    @Override
    public AbstractCard makeCopy() { return new HarukaEarlyBurst(); }
}
