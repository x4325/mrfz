package arknsfw.cards.nymph;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import liesecore.helpers.NsfwRunStats;
import arknsfw.ArkNsfwMod;
import arknsfw.cards.AbstractArkNsfwCard;
import arknsfw.helpers.ArkCharMechanicsHelper;
import nymph.core.ColorEnum;

/** 淫乱状态：资源引擎+持续兴奋 */
public class NymphLewdTrance extends AbstractArkNsfwCard {
    public static final String ID = ArkNsfwMod.makeID("NymphLewdTrance");

    public NymphLewdTrance() {
        super(ID, 2, CardType.POWER, ColorEnum.NYMPH_COLOR, CardRarity.RARE, CardTarget.SELF, "card_nymph_nymphmoistbarrier.png");
        baseMagicNumber = magicNumber = 2;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new com.megacrit.cardcrawl.actions.common.ApplyPowerAction(p, p,
                new arknsfw.powers.LewdTrancePower(p, magicNumber), magicNumber));
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeMagicNumber(1);
        }
    }

    @Override
    public AbstractCard makeCopy() { return new NymphLewdTrance(); }
}
