package arknsfw.powers.muel;

import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import liesecore.helpers.NsfwRunStats;
import arknsfw.ArkNsfwMod;
import arknsfw.powers.AbstractArkDebuffPower;

/** 渗漏：回合结束失去格挡并上升受孕度。 */
public class LeakPower extends AbstractArkDebuffPower {
    public static final String POWER_ID = ArkNsfwMod.makeID("LeakPower");
    private static final PowerStrings STR = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);

    public LeakPower(AbstractCreature owner, int amount) {
        super(POWER_ID, owner, amount);
        this.name = STR.NAME;
        this.DESCRIPTIONS = STR.DESCRIPTIONS;
        loadDebuffIcon("leak", "poison");
        updateDescription();
    }

    @Override
    public void atEndOfTurn(boolean isPlayer) {
        if (!isPlayer || AbstractDungeon.player == null) {
            return;
        }
        int drain = 3 * amount;
        if (AbstractDungeon.player.currentBlock > 0) {
            AbstractDungeon.player.loseBlock(Math.min(drain, AbstractDungeon.player.currentBlock));
        }
        arknsfw.helpers.ArkSafeStats.addConceptionDeferred(2 * amount, false);
    }

    @Override
    public void updateDescription() {
        description = amount + STR.DESCRIPTIONS[1];
        if (isPermanentlyLocked() && STR.DESCRIPTIONS.length > 2) {
            description += STR.DESCRIPTIONS[2];
        }
    }
}
