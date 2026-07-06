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

public class MagmaThrust extends AbstractArkNsfwCard {
    public static final String ID = ArkNsfwMod.makeID("MagmaThrust");

    public MagmaThrust() {
        super(ID, 1, CardType.ATTACK, ColorEnum.Eyjafjalla_COLOR, CardRarity.COMMON, CardTarget.ENEMY, "card_magma_thrust.png");
        baseDamage = 8;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        int dmg = damage;
        if (ArkCharMechanicsHelper.pyrobreathActive(this)) {
            dmg += 4;
        }
        addToBot(new DamageAction(m, new DamageInfo(p, dmg, DamageInfo.DamageType.NORMAL), AbstractGameAction.AttackEffect.FIRE));
        NsfwRunStats.addExcitement(10 + ArkCharMechanicsHelper.fireMarkPowerAmount() * 2);
        NsfwRunStats.addFertility(4, 0, false);
        ArkCharMechanicsHelper.markPyrobreath(this);
        if (ArkCharMechanicsHelper.cloudEnergy() >= 1) {
            ArkCharMechanicsHelper.gainCloudEnergy(1);
        }
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeDamage(4);
        }
    }

    @Override
    public AbstractCard makeCopy() {
        return new MagmaThrust();
    }
}
