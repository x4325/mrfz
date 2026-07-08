package archetto.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import archetto.ArchettoMod;
import archetto.powers.AimPower;

public class ArchettoBulkGuard02 extends AbstractArchettoCard {
    public static final String ID = ArchettoMod.makeID("BulkGuard02");

    public ArchettoBulkGuard02() {
        super(ID, 1, CardType.SKILL, CardRarity.COMMON, CardTarget.SELF, "card_archetto_bulkguard02.png");
        baseBlock = 6;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new GainBlockAction(p, p, block));
        addToBot(new ApplyPowerAction(p, p, new AimPower(p, 1), 1));
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName(); upgradeBlock(3);
        }
    }

    @Override
    public AbstractCard makeCopy() { return new ArchettoBulkGuard02(); }
}
