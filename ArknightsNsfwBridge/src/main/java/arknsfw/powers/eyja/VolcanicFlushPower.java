package arknsfw.powers.eyja;

import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PowerStrings;
import liesecore.helpers.NsfwRunStats;
import arknsfw.ArkNsfwMod;
import arknsfw.powers.AbstractArkDebuffPower;

/** 火山潮红：回合开始兴奋上升，受击后更易升温。 */
public class VolcanicFlushPower extends AbstractArkDebuffPower {
    public static final String POWER_ID = ArkNsfwMod.makeID("VolcanicFlushPower");
    private static final PowerStrings STR = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);

    public VolcanicFlushPower(AbstractCreature owner, int amount) {
        super(POWER_ID, owner, amount);
        this.name = STR.NAME;
        this.DESCRIPTIONS = STR.DESCRIPTIONS;
        loadDebuffIcon("volcanic_flush", "heat");
        updateDescription();
    }

    @Override
    public void atStartOfTurn() {
        NsfwRunStats.addExcitement(3 * amount);
    }

    @Override
    public int onLoseHp(int damageAmount) {
        if (damageAmount > 0 && owner != null && owner.isPlayer) {
            NsfwRunStats.addExcitement(2 * amount);
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
