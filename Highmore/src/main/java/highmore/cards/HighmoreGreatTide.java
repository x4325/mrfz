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

/** 大潮咏叹 */
public class HighmoreGreatTide extends AbstractHighmoreCard {
    public static final String ID = HighmoreMod.makeID("GreatTide");

    public HighmoreGreatTide() {
        super(ID, 2, CardType.SKILL, CardRarity.RARE, CardTarget.SELF, "card_highmore_greattide.png");

    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractPower reap = p.getPower(ReapPower.POWER_ID);
        if (reap != null && reap.amount > 0) {
            int add = Math.min(reap.amount, 6);
            addToBot(new ApplyPowerAction(p, p, new ReapPower(p, add), add));
        }
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeBaseCost(1);
        }
    }

    @Override
    public AbstractCard makeCopy() { return new HighmoreGreatTide(); }
}
