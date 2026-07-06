package nymph.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import nymph.NymphMod;
import nymph.powers.HexPower;

/** 格挡并对随机敌人施加咒灵。 */
public class NymphBulkGuard07 extends AbstractNymphCard {
    public static final String ID = NymphMod.makeID("BulkGuard07");

    public NymphBulkGuard07() {
        super(ID, 1, CardType.SKILL, CardRarity.UNCOMMON, CardTarget.SELF);
        baseBlock = 10;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new GainBlockAction(p, p, block));
        AbstractMonster pick = AbstractDungeon.getMonsters().getRandomMonster(true);
        if (pick != null) {
            addToBot(new ApplyPowerAction(pick, p, new HexPower(pick, 1), 1));
        }
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName(); upgradeBlock(4);
        }
    }

    @Override
    public AbstractCard makeCopy() { return new NymphBulkGuard07(); }
}
