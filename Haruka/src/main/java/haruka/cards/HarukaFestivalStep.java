package haruka.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.*;
import haruka.HarukaMod;
import haruka.cards.AbstractHarukaCard;
import haruka.powers.PyroPower;
public class HarukaFestivalStep extends AbstractHarukaCard {
    public static final String ID = HarukaMod.makeID("FestivalStep");

    public HarukaFestivalStep() {
        super(ID, 1, CardType.SKILL, CardRarity.UNCOMMON, CardTarget.SELF);
        
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new ApplyPowerAction(p, p, new PyroPower(p, 3), 3));
        addToBot(new DrawCardAction(p, 1));
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
        }
    }

    @Override
    public AbstractCard makeCopy() { return new HarukaFestivalStep(); }
}
