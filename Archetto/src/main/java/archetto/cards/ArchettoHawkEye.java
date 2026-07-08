package archetto.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.VulnerablePower;
import archetto.ArchettoMod;
import archetto.powers.AimPower;

/** 鹰眼 */
public class ArchettoHawkEye extends AbstractArchettoCard {
    public static final String ID = ArchettoMod.makeID("HawkEye");

    public ArchettoHawkEye() {
        super(ID, 1, CardType.POWER, CardRarity.RARE, CardTarget.SELF, "card_archetto_hawkeye.png");

    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new ApplyPowerAction(p, p, new archetto.powers.HawkEyePower(p, 1), 1));
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeBaseCost(0);
        }
    }

    @Override
    public AbstractCard makeCopy() { return new ArchettoHawkEye(); }
}
