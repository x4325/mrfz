package arknsfw.cards.eyja;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.VulnerablePower;
import liesecore.helpers.NsfwRunStats;
import arknsfw.ArkNsfwMod;
import arknsfw.cards.AbstractArkNsfwCard;
import arknsfw.helpers.ArkCharMechanicsHelper;
import Eyjafjalla.modcore.ColorEnum;

public class FeverCaress extends AbstractArkNsfwCard {
    public static final String ID = ArkNsfwMod.makeID("FeverCaress");

    public FeverCaress() {
        super(ID, 1, CardType.SKILL, ColorEnum.Eyjafjalla_COLOR, CardRarity.UNCOMMON, CardTarget.ENEMY, "card_fever_caress.png");
        baseMagicNumber = magicNumber = 6;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        int dmg = magicNumber;
        if (ArkCharMechanicsHelper.cloudCardCount() >= 2) {
            dmg += 4;
        }
        addToBot(new DamageAction(m, new DamageInfo(p, dmg, DamageInfo.DamageType.THORNS), AbstractGameAction.AttackEffect.FIRE));
        NsfwRunStats.addExcitement(12 + (ArkCharMechanicsHelper.cloudEnergy() >= 1 ? 6 : 0));
        addToBot(new ApplyPowerAction(m, p, new VulnerablePower(m, 1, false), 1));
        ArkCharMechanicsHelper.markPyrobreath(this);
        if (ArkCharMechanicsHelper.cloudEnergy() < 3) {
            ArkCharMechanicsHelper.gainCloudEnergy(1);
        }
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeMagicNumber(3);
            rawDescription = cardStrings.UPGRADE_DESCRIPTION;
            initializeDescription();
        }
    }

    @Override
    public AbstractCard makeCopy() {
        return new FeverCaress();
    }
}
