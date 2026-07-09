package arknsfw.powers.highmore;

import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PowerStrings;
import liesecore.helpers.NsfwRunStats;
import arknsfw.ArkNsfwMod;
import arknsfw.powers.AbstractArkDebuffPower;

/** 潮湿刻印：回合结束失去 1 HP/层并兴奋 +2/层；身体变得贪婪，治疗效果 +1/层。 */
public class TideBrandPower extends AbstractArkDebuffPower {
    public static final String POWER_ID = ArkNsfwMod.makeID("TideBrandPower");
    private static final PowerStrings STR = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);

    public TideBrandPower(AbstractCreature owner, int amount) {
        super(POWER_ID, owner, amount);
        this.name = STR.NAME;
        this.DESCRIPTIONS = STR.DESCRIPTIONS;
        loadDebuffIcon("tide_brand", "constricted");
        updateDescription();
    }

    @Override
    public void atEndOfTurn(boolean isPlayer) {
        if (isPlayer && owner != null && owner.isPlayer && amount > 0) {
            owner.damage(new DamageInfo(null, amount, DamageInfo.DamageType.HP_LOSS));
            arknsfw.helpers.ArkSafeStats.addExcitementDeferred(2 * amount);
        }
    }

    @Override
    public int onHeal(int healAmount) {
        if (healAmount > 0) {
            return healAmount + amount;
        }
        return healAmount;
    }

    @Override
    public void updateDescription() {
        description = amount + STR.DESCRIPTIONS[1];
        if (isPermanentlyLocked() && STR.DESCRIPTIONS.length > 2) {
            description += STR.DESCRIPTIONS[2];
        }
    }
}
