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

/** 熔心·子宫烙印 */
public class EyjaWombBrandCard extends AbstractArkNsfwCard {
    public static final String ID = ArkNsfwMod.makeID("EyjaWombBrandCard");

    public EyjaWombBrandCard() {
        super(ID, 1, CardType.ATTACK, ColorEnum.Eyjafjalla_COLOR, CardRarity.UNCOMMON, CardTarget.ENEMY, "card_eruption_peak.png");
        baseDamage = 10;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new DamageAction(m, new DamageInfo(p, damage, damageTypeForTurn), AbstractGameAction.AttackEffect.FIRE));
        ArkSafeStats.addConceptionDeferred(8, false);
        ArkSafeStats.addFertilityDeferred(4, 4, false);
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeDamage(4);
            applyUpgradeDescription();
        }
    }

    @Override
    public AbstractCard makeCopy() { return new EyjaWombBrandCard(); }
}
