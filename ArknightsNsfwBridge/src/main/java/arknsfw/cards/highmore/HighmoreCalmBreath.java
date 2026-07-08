package arknsfw.cards.highmore;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import liesecore.helpers.NsfwRunStats;
import arknsfw.ArkNsfwMod;
import arknsfw.cards.AbstractArkNsfwCard;
import highmore.core.ColorEnum;

/** 平复呼吸：主动压制兴奋，对抗高潮失控。 */
public class HighmoreCalmBreath extends AbstractArkNsfwCard {
    public static final String ID = ArkNsfwMod.makeID("HighmoreCalmBreath");

    public HighmoreCalmBreath() {
        super(ID, 0, CardType.SKILL, ColorEnum.HIGHMORE_COLOR, CardRarity.COMMON, CardTarget.SELF, "card_highmore_highmoremoistbarrier.png");
        baseMagicNumber = magicNumber = 15;
        exhaust = true;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        NsfwRunStats.addExcitement(-magicNumber);
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeMagicNumber(7);
        }
    }

    @Override
    public AbstractCard makeCopy() { return new HighmoreCalmBreath(); }
}
