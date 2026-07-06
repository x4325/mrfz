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
public class NymphHeartSeal extends AbstractNymphCard {
    public static final String ID = NymphMod.makeID("HeartSeal");

    public NymphHeartSeal() {
        super(ID, 2, CardType.SKILL, CardRarity.UNCOMMON, CardTarget.ENEMY);
        baseMagicNumber = magicNumber = 2;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new ApplyPowerAction(m, p, new HeartLockPower(m, magicNumber), magicNumber));
        addToBot(new ApplyPowerAction(m, p, new HexPower(m, 1), 1));
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName(); upgradeMagicNumber(1);
        }
    }

    @Override
    public AbstractCard makeCopy() { return new NymphHeartSeal(); }
}
