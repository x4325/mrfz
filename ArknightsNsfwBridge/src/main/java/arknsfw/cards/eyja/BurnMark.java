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
import Eyjafjalla.modcore.ColorEnum;

public class BurnMark extends AbstractArkNsfwCard {
    public static final String ID = ArkNsfwMod.makeID("BurnMark");

    public BurnMark() {
        super(ID, 1, CardType.SKILL, ColorEnum.Eyjafjalla_COLOR, CardRarity.UNCOMMON, CardTarget.ENEMY, "card_burn_mark.png");
        baseMagicNumber = magicNumber = 2;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        int vuln = magicNumber;
        if (ArkCharMechanicsHelper.cloudEnergy() >= 1) {
            vuln++;
        }
        addToBot(new ApplyPowerAction(m, p, new VulnerablePower(m, vuln, false), vuln));
        NsfwRunStats.addExcitement(8);
        NsfwRunStats.addConception(3 + (ArkCharMechanicsHelper.cloudEnergy() >= 1 ? 3 : 0), false);
        ArkCharMechanicsHelper.gainCloudEnergy(1);
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
        return new BurnMark();
    }
}
