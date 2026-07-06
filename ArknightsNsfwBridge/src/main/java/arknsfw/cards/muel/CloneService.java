package arknsfw.cards.muel;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import liesecore.helpers.NsfwRunStats;
import arknsfw.ArkNsfwMod;
import arknsfw.cards.AbstractArkNsfwCard;
import arknsfw.helpers.ArkCharMechanicsHelper;
import Muelsyse.patches.ColorEnum;

public class CloneService extends AbstractArkNsfwCard {
    public static final String ID = ArkNsfwMod.makeID("CloneService");

    public CloneService() {
        super(ID, 2, CardType.ATTACK, ColorEnum.Muelsyse_COLOR, CardRarity.RARE, CardTarget.ALL_ENEMY, "card_clone_service.png");
        baseDamage = 9;
        isMultiDamage = true;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        int bonus = ArkCharMechanicsHelper.hasManifold() ? ArkCharMechanicsHelper.manifoldTotalAmount() : 0;
        for (int i = 0; i < multiDamage.length; i++) {
            multiDamage[i] = damage + bonus;
        }
        addToBot(new DamageAllEnemiesAction(p, multiDamage, DamageInfo.DamageType.NORMAL, AbstractGameAction.AttackEffect.SLASH_HORIZONTAL));
        NsfwRunStats.addExcitement(14 + (ArkCharMechanicsHelper.manifoldTookDamageThisCombat() ? 8 : 0));
        NsfwRunStats.addFertility(6, 5, false);
        if (ArkCharMechanicsHelper.hasManifold()) {
            ArkCharMechanicsHelper.boostManifold(1);
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
        return new CloneService();
    }
}
