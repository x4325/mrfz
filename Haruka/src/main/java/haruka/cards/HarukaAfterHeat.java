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

/** 余热 */
public class HarukaAfterHeat extends AbstractHarukaCard {
    public static final String ID = HarukaMod.makeID("AfterHeat");

    public HarukaAfterHeat() {
        super(ID, 1, CardType.SKILL, CardRarity.UNCOMMON, CardTarget.SELF, "card_haruka_afterheat.png");
        baseBlock = 3;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractPower pyro = p.getPower(PyroPower.POWER_ID);
        int bonus = pyro == null ? 0 : Math.min(pyro.amount / 2, 9);
        addToBot(new GainBlockAction(p, p, block + bonus));
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeBlock(3);
        }
    }

    @Override
    public AbstractCard makeCopy() { return new HarukaAfterHeat(); }
}
