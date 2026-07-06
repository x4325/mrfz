package arknsfw.cards.eyja;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import arknsfw.ArkNsfwMod;
import arknsfw.cards.AbstractArkNsfwCard;
import arknsfw.helpers.ArkCharMechanicsHelper;
import arknsfw.powers.eyja.GeothermalPower;
import Eyjafjalla.modcore.ColorEnum;

public class HeatResonance extends AbstractArkNsfwCard {
    public static final String ID = ArkNsfwMod.makeID("HeatResonance");

    public HeatResonance() {
        super(ID, 1, CardType.POWER, ColorEnum.Eyjafjalla_COLOR, CardRarity.UNCOMMON, CardTarget.SELF, "card_heat_resonance.png");
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new ApplyPowerAction(p, p, new GeothermalPower(p, 1), 1));
        if (ArkCharMechanicsHelper.cloudCardCount() >= 1) {
            ArkCharMechanicsHelper.applyFireMarkPower(p, 1);
        }
        ArkCharMechanicsHelper.gainCloudEnergy(1);
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            rawDescription = cardStrings.UPGRADE_DESCRIPTION;
            initializeDescription();
        }
    }

    @Override
    public AbstractCard makeCopy() {
        return new HeatResonance();
    }
}
