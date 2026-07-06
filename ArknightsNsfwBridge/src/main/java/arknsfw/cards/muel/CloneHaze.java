package arknsfw.cards.muel;

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
import arknsfw.powers.muel.CloneHazePower;
import Muelsyse.patches.ColorEnum;

public class CloneHaze extends AbstractArkNsfwCard {
    public static final String ID = ArkNsfwMod.makeID("CloneHaze");

    public CloneHaze() {
        super(ID, 2, CardType.POWER, Muelsyse.patches.ColorEnum.Muelsyse_COLOR, CardRarity.RARE, CardTarget.SELF, "card_clone_haze.png");
        baseMagicNumber = magicNumber = 1;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        int amt = magicNumber + (ArkCharMechanicsHelper.hasManifold() ? 1 : 0);
        addToBot(new ApplyPowerAction(p, p, new CloneHazePower(p, amt), amt));
        if (ArkCharMechanicsHelper.hasManifold()) {
            ArkCharMechanicsHelper.boostManifold(1);
        }
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
        return new CloneHaze();
    }
}
