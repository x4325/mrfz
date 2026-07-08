package arknsfw.cards.muel;

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
import Muelsyse.patches.ColorEnum;

/** 清漪·孕欲涌动 */
public class MuelWombUrge extends AbstractArkNsfwCard {
    public static final String ID = ArkNsfwMod.makeID("MuelWombUrge");

    public MuelWombUrge() {
        super(ID, 1, CardType.SKILL, ColorEnum.Muelsyse_COLOR, CardRarity.UNCOMMON, CardTarget.SELF, "card_twin_pleasure.png");
        baseMagicNumber = magicNumber = 2;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        NsfwRunStats.addConception(10, false);
        NsfwRunStats.addFertility(0, 5, false);
        addToBot(new DrawCardAction(p, magicNumber));
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeMagicNumber(1);
            applyUpgradeDescription();
        }
    }

    @Override
    public AbstractCard makeCopy() { return new MuelWombUrge(); }
}
