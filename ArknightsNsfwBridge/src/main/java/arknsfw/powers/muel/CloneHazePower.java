package arknsfw.powers.muel;

import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import liesecore.helpers.NsfwRunStats;
import arknsfw.ArkNsfwMod;

public class CloneHazePower extends AbstractPower {
    public static final String POWER_ID = ArkNsfwMod.makeID("CloneHazePower");
    private static final PowerStrings STR = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);

    public CloneHazePower(com.megacrit.cardcrawl.core.AbstractCreature owner, int amount) {
        this.name = STR.NAME;
        this.ID = POWER_ID;
        this.owner = owner;
        this.amount = amount;
        this.type = PowerType.BUFF;
        this.updateDescription();
        loadRegion("blur");
    }

    @Override
    public void atStartOfTurn() {
        NsfwRunStats.addExcitement(5 * amount);
        NsfwRunStats.addConception(2 * amount, false);
    }

    @Override
    public void updateDescription() {
        description = STR.DESCRIPTIONS[0] + (5 * amount) + STR.DESCRIPTIONS[1] + (2 * amount) + STR.DESCRIPTIONS[2];
    }
}
