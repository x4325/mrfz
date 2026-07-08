package arknsfw.powers.nymph;

import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PowerStrings;
import liesecore.helpers.NsfwRunStats;
import arknsfw.ArkNsfwMod;
import arknsfw.powers.AbstractArkDebuffPower;

/** 心蚀低语：回合开始兴奋 +2/层；回合结束流失 2 点格挡/层（心防被啃噬）。 */
public class HeartGnawPower extends AbstractArkDebuffPower {
    public static final String POWER_ID = ArkNsfwMod.makeID("HeartGnawPower");
    private static final PowerStrings STR = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);

    public HeartGnawPower(AbstractCreature owner, int amount) {
        super(POWER_ID, owner, amount);
        this.name = STR.NAME;
        this.DESCRIPTIONS = STR.DESCRIPTIONS;
        loadDebuffIcon("heart_gnaw", "choked");
        updateDescription();
    }

    @Override
    public void atStartOfTurn() {
        arknsfw.helpers.ArkSafeStats.addExcitementDeferred(2 * amount);
    }

    @Override
    public void atEndOfTurn(boolean isPlayer) {
        if (isPlayer && owner != null && owner.currentBlock > 0) {
            owner.loseBlock(Math.min(owner.currentBlock, 2 * amount));
        }
    }

    @Override
    public void updateDescription() {
        description = amount + STR.DESCRIPTIONS[1];
        if (isPermanentlyLocked() && STR.DESCRIPTIONS.length > 2) {
            description += STR.DESCRIPTIONS[2];
        }
    }
}
