package haruka.cards;

import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import haruka.HarukaMod;
import haruka.powers.PyroPower;

/** 安可呐喊：0 费回能 + 花火，消耗。 */
public class HarukaEncoreShout extends AbstractHarukaCard {
    public static final String ID = HarukaMod.makeID("EncoreShout");

    public HarukaEncoreShout() {
        super(ID, 0, CardType.SKILL, CardRarity.UNCOMMON, CardTarget.SELF, "card_haruka_encoreshout.png");
        baseMagicNumber = magicNumber = 1;
        exhaust = true;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new ApplyPowerAction(p, p, new PyroPower(p, magicNumber), magicNumber));
        addToBot(new GainEnergyAction(1));
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName(); upgradeMagicNumber(2);
        }
    }

    @Override
    public AbstractCard makeCopy() { return new HarukaEncoreShout(); }
}
