package arknsfw.cards.muel;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import arknsfw.ArkNsfwMod;
import arknsfw.cards.AbstractArkNsfwCard;
import arknsfw.helpers.ArkCharMechanicsHelper;
import arknsfw.powers.muel.HydrationPower;
import Muelsyse.patches.ColorEnum;

public class GreenhouseMist extends AbstractArkNsfwCard {
    public static final String ID = ArkNsfwMod.makeID("GreenhouseMist");

    public GreenhouseMist() {
        super(ID, 1, CardType.POWER, ColorEnum.Muelsyse_COLOR, CardRarity.UNCOMMON, CardTarget.SELF, "card_greenhouse_mist.png");
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        int amt = 1 + (ArkCharMechanicsHelper.hasManifold() ? 1 : 0);
        addToBot(new ApplyPowerAction(p, p, new HydrationPower(p, amt), amt));
        if (ArkCharMechanicsHelper.isCultivating()) {
            ArkCharMechanicsHelper.applyRootage(p, 1);
        }
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
        return new GreenhouseMist();
    }
}
