package archetto.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.*;
import archetto.ArchettoMod;
import archetto.cards.AbstractArchettoCard;
import archetto.powers.AimPower;
public class ArchettoBulkDraw33 extends AbstractArchettoCard {
    public static final String ID = ArchettoMod.makeID("BulkDraw33");

    public ArchettoBulkDraw33() {
        super(ID, 1, CardType.SKILL, CardRarity.UNCOMMON, CardTarget.SELF);
        
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new DrawCardAction(p, 1));
        addToBot(new ApplyPowerAction(p, p, new AimPower(p, 1), 1));
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName(); rawDescription = cardStrings.UPGRADE_DESCRIPTION; initializeDescription();
        }
    }

    @Override
    public AbstractCard makeCopy() { return new ArchettoBulkDraw33(); }
}
