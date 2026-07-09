package arknsfw.powers.haruka;

import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PowerStrings;
import liesecore.helpers.NsfwRunStats;
import arknsfw.ArkNsfwMod;
import arknsfw.powers.AbstractArkDebuffPower;

/** 余焰灼身：回合开始兴奋 +2/层；受到伤害时余焰窜起，兴奋再 +2/层。 */
public class LingeringHeatPower extends AbstractArkDebuffPower {
    public static final String POWER_ID = ArkNsfwMod.makeID("LingeringHeatPower");
    private static final PowerStrings STR = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);

    public LingeringHeatPower(AbstractCreature owner, int amount) {
        super(POWER_ID, owner, amount);
        this.name = STR.NAME;
        this.DESCRIPTIONS = STR.DESCRIPTIONS;
        loadDebuffIcon("lingering_heat", "painful");
        updateDescription();
    }

    @Override
    public void atStartOfTurn() {
        arknsfw.helpers.ArkSafeStats.addExcitementDeferred(2 * amount);
    }

    @Override
    public int onLoseHp(int damageAmount) {
        if (damageAmount > 0 && owner != null && owner.isPlayer) {
            arknsfw.helpers.ArkSafeStats.addExcitementDeferred(2 * amount);
        }
        return damageAmount;
    }

    @Override
    public void updateDescription() {
        description = amount + STR.DESCRIPTIONS[1];
        if (isPermanentlyLocked() && STR.DESCRIPTIONS.length > 2) {
            description += STR.DESCRIPTIONS[2];
        }
    }
}
