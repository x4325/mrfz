package arknsfw.cards.eyja;

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
import arknsfw.helpers.ArkSensitivity;
import arknsfw.helpers.LieseCompat;
import Eyjafjalla.modcore.ColorEnum;

/** 熔心·沉溺 */
public class EyjaIndulgeCard extends AbstractArkNsfwCard {
    public static final String ID = ArkNsfwMod.makeID("EyjaIndulgeCard");

    public EyjaIndulgeCard() {
        super(ID, 2, CardType.SKILL, ColorEnum.Eyjafjalla_COLOR, CardRarity.UNCOMMON, CardTarget.SELF, "card_volcanic_embrace.png");
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
    public AbstractCard makeCopy() { return new EyjaIndulgeCard(); }
}
