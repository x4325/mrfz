package arknsfw.cards.eyja;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.VulnerablePower;
import com.megacrit.cardcrawl.powers.WeakPower;
import liesecore.helpers.NsfwRunStats;
import arknsfw.ArkNsfwMod;
import arknsfw.cards.AbstractArkNsfwCard;
import arknsfw.helpers.ArkCharMechanicsHelper;
import arknsfw.powers.eyja.GeothermalPower;
import arknsfw.powers.eyja.CoreFeverPower;
import Eyjafjalla.modcore.ColorEnum;

public class CoreFever extends AbstractArkNsfwCard {
    public static final String ID = ArkNsfwMod.makeID("CoreFever");

    public CoreFever() {
        super(ID, 2, CardType.POWER, ColorEnum.Eyjafjalla_COLOR, CardRarity.RARE, CardTarget.SELF, "card_core_fever.png");
        baseMagicNumber = magicNumber = 1;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        int amt = magicNumber;
        if (ArkCharMechanicsHelper.cloudCardCount() >= 2) {
            amt++;
        }
        addToBot(new ApplyPowerAction(p, p, new GeothermalPower(p, amt), amt));
        addToBot(new ApplyPowerAction(p, p, new CoreFeverPower(p, amt), amt));
        ArkCharMechanicsHelper.gainCloudEnergy(1);
        ArkCharMechanicsHelper.applyFireMarkPower(p, 1);
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeMagicNumber(1);
        }
    }

    @Override
    public AbstractCard makeCopy() {
        return new CoreFever();
    }
}
