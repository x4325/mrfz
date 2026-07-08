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

/** 献身防御：白嫖格挡换受孕 */
public class NymphOfferBody extends AbstractArkNsfwCard {
    public static final String ID = ArkNsfwMod.makeID("NymphOfferBody");

    public NymphOfferBody() {
        super(ID, 0, CardType.SKILL, ColorEnum.NYMPH_COLOR, CardRarity.COMMON, CardTarget.SELF, "card_nymph_nymphmoistbarrier.png");
        baseBlock = 8;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new GainBlockAction(p, p, block));
        NsfwRunStats.addConception(3, false);
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeBlock(3);
        }
    }

    @Override
    public AbstractCard makeCopy() { return new NymphOfferBody(); }
}
