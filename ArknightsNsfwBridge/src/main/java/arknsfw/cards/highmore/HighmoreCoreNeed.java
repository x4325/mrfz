package arknsfw.cards.highmore;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import liesecore.helpers.NsfwRunStats;
import arknsfw.ArkNsfwMod;
import arknsfw.cards.AbstractArkNsfwCard;
import arknsfw.helpers.ArkCharMechanicsHelper;
import highmore.core.ColorEnum;
import highmore.powers.ReapPower;

public class HighmoreCoreNeed extends AbstractArkNsfwCard {
    public static final String ID = ArkNsfwMod.makeID("HighmoreCoreNeed");

    public HighmoreCoreNeed() {
        super(ID, 2, CardType.POWER, ColorEnum.HIGHMORE_COLOR, CardRarity.RARE, CardTarget.SELF, "card_highmore_highmorecoreneed.png");
        baseMagicNumber = magicNumber = 1;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new com.megacrit.cardcrawl.actions.common.ApplyPowerAction(p, p,
                new arknsfw.powers.CoreNeedPower(p, magicNumber), magicNumber));
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeMagicNumber(1); baseMagicNumber=1;
        }
    }

    @Override
    public AbstractCard makeCopy() { return new HighmoreCoreNeed(); }
}
