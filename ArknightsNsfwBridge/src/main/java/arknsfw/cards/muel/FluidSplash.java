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
import Muelsyse.patches.ColorEnum;

public class FluidSplash extends AbstractArkNsfwCard {
    public static final String ID = ArkNsfwMod.makeID("FluidSplash");

    public FluidSplash() {
        super(ID, 1, CardType.ATTACK, Muelsyse.patches.ColorEnum.Muelsyse_COLOR, CardRarity.COMMON, CardTarget.ENEMY, "card_fluid_splash.png");
        baseDamage = 9;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        int dmg = damage + (ArkCharMechanicsHelper.hasManifold() ? 6 : 0);
        addToBot(new DamageAction(m, new DamageInfo(p, dmg, DamageInfo.DamageType.NORMAL), AbstractGameAction.AttackEffect.SLASH_DIAGONAL));
        NsfwRunStats.addExcitement(12 + (ArkCharMechanicsHelper.manifoldTookDamageThisCombat() ? 8 : 0));
        NsfwRunStats.addConception(4, false);
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
        return new FluidSplash();
    }
}
