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

/** 祭火添薪 */
public class HarukaAddFuel extends AbstractHarukaCard {
    public static final String ID = HarukaMod.makeID("AddFuel");

    public HarukaAddFuel() {
        super(ID, 0, CardType.SKILL, CardRarity.COMMON, CardTarget.SELF, "card_haruka_addfuel.png");
        exhaust = true;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new ApplyPowerAction(p, p, new PyroPower(p, 2), 2));
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            exhaust = false;
            this.rawDescription = cardStrings.DESCRIPTION;
            initializeDescription();
        }
    }

    @Override
    public AbstractCard makeCopy() { return new HarukaAddFuel(); }
}
