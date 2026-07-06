package nymph.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.*;
import nymph.NymphMod;
import nymph.cards.AbstractNymphCard;
import nymph.powers.HexPower;
import nymph.powers.FearPower;
import nymph.powers.HeartLockPower;
public class NymphBulkDraw13 extends AbstractNymphCard {
    public static final String ID = NymphMod.makeID("BulkDraw13");

    public NymphBulkDraw13() {
        super(ID, 1, CardType.SKILL, CardRarity.COMMON, CardTarget.SELF);
        
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new DrawCardAction(p, 1));
        addToBot(new ApplyPowerAction(p, p, new HexPower(p, 1), 1));
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName(); rawDescription = cardStrings.UPGRADE_DESCRIPTION; initializeDescription();
        }
    }

    @Override
    public AbstractCard makeCopy() { return new NymphBulkDraw13(); }
}
