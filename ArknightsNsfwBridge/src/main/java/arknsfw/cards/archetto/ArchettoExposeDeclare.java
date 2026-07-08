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

/** 鸣弦·露出宣言 */
public class ArchettoExposeDeclare extends AbstractArkNsfwCard {
    public static final String ID = ArkNsfwMod.makeID("ArchettoExposeDeclare");

    public ArchettoExposeDeclare() {
        super(ID, 1, CardType.SKILL, ColorEnum.ARCHETTO_COLOR, CardRarity.UNCOMMON, CardTarget.SELF, "card_archetto_archettosilentcaress.png");
        this.exhaust = true;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        ArkExposureHelper.tear();
        addToBot(new GainEnergyAction(1));
        addToBot(new DrawCardAction(p, 2));
        NsfwRunStats.addExcitement(8);
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeBaseCost(0);
            applyUpgradeDescription();
        }
    }

    @Override
    public AbstractCard makeCopy() { return new ArchettoExposeDeclare(); }
}
