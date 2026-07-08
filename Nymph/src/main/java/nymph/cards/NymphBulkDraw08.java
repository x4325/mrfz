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

/** 抽牌并对随机敌人施加咒灵。 */
public class NymphBulkDraw08 extends AbstractNymphCard {
    public static final String ID = NymphMod.makeID("BulkDraw08");

    public NymphBulkDraw08() {
        super(ID, 1, CardType.SKILL, CardRarity.UNCOMMON, CardTarget.SELF, "card_nymph_bulkdraw08.png");
        baseMagicNumber = magicNumber = 1;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new DrawCardAction(p, 2));
        AbstractMonster pick = AbstractDungeon.getMonsters().getRandomMonster(true);
        if (pick != null) {
            addToBot(new ApplyPowerAction(pick, p, new HexPower(pick, magicNumber), magicNumber));
        }
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName(); upgradeMagicNumber(1);
        }
    }

    @Override
    public AbstractCard makeCopy() { return new NymphBulkDraw08(); }
}
