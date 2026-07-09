package arknsfw.cards.haruka;

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
import haruka.core.ColorEnum;

/** 烬焰·敏感汲取 */
public class HarukaSenseDrain extends AbstractArkNsfwCard {
    public static final String ID = ArkNsfwMod.makeID("HarukaSenseDrain");

    public HarukaSenseDrain() {
        super(ID, 1, CardType.SKILL, ColorEnum.HARUKA_COLOR, CardRarity.UNCOMMON, CardTarget.SELF, "card_haruka_harukaoverflowpulse.png");
        baseMagicNumber = magicNumber = 3;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        if (ArkSensitivity.points() >= magicNumber) {
            addToBot(new GainEnergyAction(1));
            addToBot(new DrawCardAction(p, 2));
        } else {
            NsfwRunStats.addExcitement(10);
        }
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeMagicNumber(-1);
            applyUpgradeDescription();
        }
    }

    @Override
    public AbstractCard makeCopy() { return new HarukaSenseDrain(); }
}
