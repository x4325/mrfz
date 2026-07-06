package arknsfw.powers.eyja;

import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import liesecore.helpers.NsfwRunStats;
import arknsfw.ArkNsfwMod;

public class GeothermalPower extends AbstractPower {
    public static final String POWER_ID = ArkNsfwMod.makeID("GeothermalPower");
    private static final PowerStrings STR = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);

    public GeothermalPower(com.megacrit.cardcrawl.core.AbstractCreature owner, int amount) {
        this.name = STR.NAME;
        this.ID = POWER_ID;
        this.owner = owner;
        this.amount = amount;
        this.type = PowerType.BUFF;
        this.updateDescription();
        loadRegion("flex");
    }

    @Override
    public void atStartOfTurn() {
        NsfwRunStats.addExcitement(4 * amount);
    }

    @Override
    public void updateDescription() {
        description = STR.DESCRIPTIONS[0] + amount + STR.DESCRIPTIONS[1];
    }
}
