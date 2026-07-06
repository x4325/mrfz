package arknsfw.powers.eyja;

import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import liesecore.helpers.NsfwRunStats;
import arknsfw.ArkNsfwMod;

public class CoreFeverPower extends AbstractPower {
    public static final String POWER_ID = ArkNsfwMod.makeID("CoreFeverPower");
    private static final PowerStrings STR = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);

    public CoreFeverPower(com.megacrit.cardcrawl.core.AbstractCreature owner, int amount) {
        this.name = STR.NAME;
        this.ID = POWER_ID;
        this.owner = owner;
        this.amount = amount;
        this.type = PowerType.BUFF;
        this.updateDescription();
        loadRegion("combust");
    }

    @Override
    public int onLoseHp(int damageAmount) {
        if (damageAmount > 0 && owner != null && owner.isPlayer) {
            NsfwRunStats.addExcitement(5 * amount);
        }
        return damageAmount;
    }

    @Override
    public void updateDescription() {
        description = STR.DESCRIPTIONS[0] + (5 * amount) + STR.DESCRIPTIONS[1];
    }
}
