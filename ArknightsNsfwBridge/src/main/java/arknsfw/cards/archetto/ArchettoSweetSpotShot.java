package arknsfw.cards.archetto;

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
import archetto.core.ColorEnum;

/** 鸣弦·敏点狙击 */
public class ArchettoSweetSpotShot extends AbstractArkNsfwCard {
    public static final String ID = ArkNsfwMod.makeID("ArchettoSweetSpotShot");

    public ArchettoSweetSpotShot() {
        super(ID, 1, CardType.ATTACK, ColorEnum.ARCHETTO_COLOR, CardRarity.COMMON, CardTarget.ENEMY, "card_archetto_archettowarmembrace.png");
        baseDamage = 6;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new DamageAction(m, new DamageInfo(p, damage, damageTypeForTurn), AbstractGameAction.AttackEffect.SLASH_DIAGONAL));
        addToBot(new ApplyPowerAction(m, p, new WeakPower(m, 1, false), 1));
        ArkCharMechanicsHelper.applyAimPower(p, 1);
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeDamage(2);
            applyUpgradeDescription();
        }
    }

    @Override
    public AbstractCard makeCopy() { return new ArchettoSweetSpotShot(); }
}
