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
public class HighmoreBloodRush extends AbstractHighmoreCard {
    public static final String ID = HighmoreMod.makeID("BloodRush");

    public HighmoreBloodRush() {
        super(ID, 0, CardType.SKILL, CardRarity.UNCOMMON, CardTarget.SELF);
        
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new LoseHPAction(p, p, 2));
        addToBot(new ApplyPowerAction(p, p, new ReapPower(p, 2), 2));
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
        }
    }

    @Override
    public AbstractCard makeCopy() { return new HighmoreBloodRush(); }
}
