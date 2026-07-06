package arknsfw.powers.muel;

import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import liesecore.helpers.NsfwRunStats;
import arknsfw.ArkNsfwMod;

public class HydrationPower extends AbstractPower {
    public static final String POWER_ID = ArkNsfwMod.makeID("HydrationPower");
    private static final PowerStrings STR = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);

    public HydrationPower(com.megacrit.cardcrawl.core.AbstractCreature owner, int amount) {
        this.name = STR.NAME;
        this.ID = POWER_ID;
        this.owner = owner;
        this.amount = amount;
        this.type = PowerType.BUFF;
        this.updateDescription();
        loadRegion("malleable");
    }

    @Override
    public void atEndOfTurn(boolean isPlayer) {
        if (isPlayer) {
            NsfwRunStats.addConception(2 * amount, false);
        }
    }

    @Override
    public void updateDescription() {
        description = STR.DESCRIPTIONS[0] + amount + STR.DESCRIPTIONS[1];
    }
}
