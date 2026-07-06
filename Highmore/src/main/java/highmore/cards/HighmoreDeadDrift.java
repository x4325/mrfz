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
public class HighmoreDeadDrift extends AbstractHighmoreCard {
    public static final String ID = HighmoreMod.makeID("DeadDrift");

    public HighmoreDeadDrift() {
        super(ID, 1, CardType.POWER, CardRarity.RARE, CardTarget.SELF);
        baseMagicNumber = magicNumber = 1;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new ApplyPowerAction(p, p, new ReapPower(p, 2), 2));
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
        }
    }

    @Override
    public AbstractCard makeCopy() { return new HighmoreDeadDrift(); }
}
