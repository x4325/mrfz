package arknsfw.cards.nymph;

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
import nymph.core.ColorEnum;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;

public class NymphMoistBarrier extends AbstractArkNsfwCard {
    public static final String ID = ArkNsfwMod.makeID("NymphMoistBarrier");

    public NymphMoistBarrier() {
        super(ID, 1, CardType.SKILL, ColorEnum.NYMPH_COLOR, CardRarity.COMMON, CardTarget.SELF, "card_nymph_nymphmoistbarrier.png");
        baseBlock = 10;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new GainBlockAction(p, block));
        NsfwRunStats.addExcitement(6);
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeBlock(4); baseBlock=10;
        }
    }

    @Override
    public AbstractCard makeCopy() { return new NymphMoistBarrier(); }
}
