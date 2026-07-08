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

/** 羞耻发汗：兴奋换抽牌 */
public class NymphShameSweat extends AbstractArkNsfwCard {
    public static final String ID = ArkNsfwMod.makeID("NymphShameSweat");

    public NymphShameSweat() {
        super(ID, 1, CardType.SKILL, ColorEnum.NYMPH_COLOR, CardRarity.COMMON, CardTarget.SELF, "card_nymph_nymphmoistbarrier.png");

    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new DrawCardAction(p, 2));
        NsfwRunStats.addExcitement(8);
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeBaseCost(0);
        }
    }

    @Override
    public AbstractCard makeCopy() { return new NymphShameSweat(); }
}
