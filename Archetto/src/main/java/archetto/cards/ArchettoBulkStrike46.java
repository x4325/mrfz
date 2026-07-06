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
public class ArchettoBulkStrike46 extends AbstractArchettoCard {
    public static final String ID = ArchettoMod.makeID("BulkStrike46");

    public ArchettoBulkStrike46() {
        super(ID, 0, CardType.ATTACK, CardRarity.RARE, CardTarget.ENEMY);
        baseDamage = 11;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new ApplyPowerAction(p, p, new AimPower(p, 1), 1));
        addToBot(new DamageAction(m, new DamageInfo(p, damage, DamageInfo.DamageType.NORMAL), AbstractGameAction.AttackEffect.SLASH_HORIZONTAL));
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName(); upgradeDamage(3);
        }
    }

    @Override
    public AbstractCard makeCopy() { return new ArchettoBulkStrike46(); }
}
