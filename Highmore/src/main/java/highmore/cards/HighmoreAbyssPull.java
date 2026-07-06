package highmore.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.VulnerablePower;
import highmore.HighmoreMod;
import highmore.powers.ReapPower;

/** 深海引力 */
public class HighmoreAbyssPull extends AbstractHighmoreCard {
    public static final String ID = HighmoreMod.makeID("AbyssPull");

    public HighmoreAbyssPull() {
        super(ID, 1, CardType.SKILL, CardRarity.UNCOMMON, CardTarget.SELF);
        baseBlock = 4;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractPower reap = p.getPower(ReapPower.POWER_ID);
        int bonus = reap == null ? 0 : Math.min(reap.amount, 12);
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
    public AbstractCard makeCopy() { return new HighmoreAbyssPull(); }
}
