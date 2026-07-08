package arknsfw.cards.highmore;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.EnergizedPower;
import com.megacrit.cardcrawl.powers.VulnerablePower;
import com.megacrit.cardcrawl.powers.WeakPower;
import com.megacrit.cardcrawl.ui.panels.EnergyPanel;
import liesecore.helpers.NsfwRunStats;
import arknsfw.ArkNsfwMod;
import arknsfw.cards.AbstractArkNsfwCard;
import arknsfw.helpers.ArkCharMechanicsHelper;
import arknsfw.helpers.ArkExposureHelper;
import arknsfw.helpers.ArkGearSetHelper;
import arknsfw.helpers.ArkSafeStats;
import arknsfw.helpers.LieseCompat;
import highmore.core.ColorEnum;

/** 幽潮·沉溺 */
public class HighmoreIndulgeCard extends AbstractArkNsfwCard {
    public static final String ID = ArkNsfwMod.makeID("HighmoreIndulgeCard");

    public HighmoreIndulgeCard() {
        super(ID, 2, CardType.SKILL, ColorEnum.HIGHMORE_COLOR, CardRarity.UNCOMMON, CardTarget.SELF, "card_highmore_highmoredeepresonance.png");
        baseMagicNumber = magicNumber = 12;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        int amount = Math.min(magicNumber, NsfwRunStats.excitement / 8);
        if (amount > 0) { ArkCharMechanicsHelper.healOrBlock(p, amount); }
        NsfwRunStats.addExcitement(-10);
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeMagicNumber(4);
            applyUpgradeDescription();
        }
    }

    @Override
    public AbstractCard makeCopy() { return new HighmoreIndulgeCard(); }
}
