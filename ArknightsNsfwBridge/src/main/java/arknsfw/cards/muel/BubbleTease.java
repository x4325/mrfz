package arknsfw.cards.muel;

import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import liesecore.helpers.NsfwRunStats;
import arknsfw.ArkNsfwMod;
import arknsfw.cards.AbstractArkNsfwCard;
import arknsfw.helpers.ArkCharMechanicsHelper;
import Muelsyse.patches.ColorEnum;

public class BubbleTease extends AbstractArkNsfwCard {
    public static final String ID = ArkNsfwMod.makeID("BubbleTease");

    public BubbleTease() {
        super(ID, 1, CardType.SKILL, ColorEnum.Muelsyse_COLOR, CardRarity.UNCOMMON, CardTarget.SELF, "card_bubble_tease.png");
        baseBlock = 8;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        int blk = block + (ArkCharMechanicsHelper.hasManifold() ? 4 : 0);
        addToBot(new GainBlockAction(p, blk));
        NsfwRunStats.addExcitement(10);
        NsfwRunStats.addConception(4 + (ArkCharMechanicsHelper.rootageAmount() >= 1 ? 3 : 0), false);
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeBlock(4);
            rawDescription = cardStrings.UPGRADE_DESCRIPTION;
            initializeDescription();
        }
    }

    @Override
    public AbstractCard makeCopy() {
        return new BubbleTease();
    }
}
