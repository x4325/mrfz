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

/** 终夜舞 */
public class HarukaFinalDance extends AbstractHarukaCard {
    public static final String ID = HarukaMod.makeID("FinalDance");

    public HarukaFinalDance() {
        super(ID, 3, CardType.ATTACK, CardRarity.RARE, CardTarget.ENEMY, "card_haruka_finaldance.png");
        baseDamage = 12;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        int bonus = haruka.powers.PyroPower.burstsThisCombat(p) >= 1 ? 10 : 0;
        addToBot(new DamageAction(m, new DamageInfo(p, damage + bonus, DamageInfo.DamageType.NORMAL), AbstractGameAction.AttackEffect.FIRE));
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeDamage(5);
        }
    }

    @Override
    public AbstractCard makeCopy() { return new HarukaFinalDance(); }
}
