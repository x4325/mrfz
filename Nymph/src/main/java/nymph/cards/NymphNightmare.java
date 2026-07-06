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
public class NymphNightmare extends AbstractNymphCard {
    public static final String ID = NymphMod.makeID("Nightmare");

    public NymphNightmare() {
        super(ID, 1, CardType.POWER, CardRarity.RARE, CardTarget.ENEMY);
        baseMagicNumber = magicNumber = 1;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new ApplyPowerAction(m, p, new FearPower(m, magicNumber), magicNumber));
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName(); upgradeMagicNumber(1);
        }
    }

    @Override
    public AbstractCard makeCopy() { return new NymphNightmare(); }
}
