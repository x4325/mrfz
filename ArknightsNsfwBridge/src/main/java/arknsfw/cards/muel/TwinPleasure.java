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

public class TwinPleasure extends AbstractArkNsfwCard {
    public static final String ID = ArkNsfwMod.makeID("TwinPleasure");

    public TwinPleasure() {
        super(ID, 2, CardType.ATTACK, Muelsyse.patches.ColorEnum.Muelsyse_COLOR, CardRarity.RARE, CardTarget.ENEMY, "card_twin_pleasure.png");
        baseDamage = 10;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        int hitDmg = damage + (ArkCharMechanicsHelper.hasManifold() ? ArkCharMechanicsHelper.manifoldTotalAmount() : 0);
        int hits = ArkCharMechanicsHelper.hasManifold() ? 3 : 2;
        for (int i = 0; i < hits; i++) {
            addToBot(new DamageAction(m, new DamageInfo(p, hitDmg, DamageInfo.DamageType.NORMAL), AbstractGameAction.AttackEffect.SLASH_HEAVY));
        }
        NsfwRunStats.addExcitement(18 + ArkCharMechanicsHelper.manifoldTotalAmount() * 2);
        NsfwRunStats.addFertility(8, 6, true);
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
        return new TwinPleasure();
    }
}
