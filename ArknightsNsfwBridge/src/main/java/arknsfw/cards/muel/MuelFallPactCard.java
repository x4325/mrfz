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
import arknsfw.helpers.LieseCompat;
import Muelsyse.patches.ColorEnum;

/** 清漪·堕落契约 */
public class MuelFallPactCard extends AbstractArkNsfwCard {
    public static final String ID = ArkNsfwMod.makeID("MuelFallPactCard");

    public MuelFallPactCard() {
        super(ID, 1, CardType.SKILL, ColorEnum.Muelsyse_COLOR, CardRarity.RARE, CardTarget.SELF, "card_moist_barrier.png");
        this.exhaust = true;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new GainEnergyAction(2));
        addToBot(new DrawCardAction(p, 2));
        NsfwRunStats.addExcitement(10);
        AbstractCard curse = com.megacrit.cardcrawl.helpers.CardLibrary.getCurse();
        if (curse != null) {
            addToBot(new MakeTempCardInDiscardAction(curse.makeCopy(), 1));
        }
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
    public AbstractCard makeCopy() { return new MuelFallPactCard(); }
}
