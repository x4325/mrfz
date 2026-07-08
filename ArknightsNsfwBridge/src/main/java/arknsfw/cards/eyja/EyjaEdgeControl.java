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

/** 熔心·绝顶边缘 */
public class EyjaEdgeControl extends AbstractArkNsfwCard {
    public static final String ID = ArkNsfwMod.makeID("EyjaEdgeControl");

    public EyjaEdgeControl() {
        super(ID, 0, CardType.SKILL, ColorEnum.Eyjafjalla_COLOR, CardRarity.UNCOMMON, CardTarget.SELF, "card_ash_kiss.png");
        baseMagicNumber = magicNumber = 2;
        this.exhaust = true;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        int th = LieseCompat.climaxThreshold();
        if (th > 0) {
            int target = Math.min(NsfwRunStats.excitement + 20, th - 5);
            if (target > NsfwRunStats.excitement) { NsfwRunStats.addExcitement(target - NsfwRunStats.excitement); }
        } else {
            NsfwRunStats.addExcitement(20);
        }
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
    public AbstractCard makeCopy() { return new EyjaEdgeControl(); }
}
