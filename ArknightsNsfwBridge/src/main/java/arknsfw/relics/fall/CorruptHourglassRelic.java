package arknsfw.relics.fall;

import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import arknsfw.ArkNsfwMod;
import arknsfw.relics.AbstractArkNsfwRelic;

/** 淫堕怀表：战斗开始兴奋 +5；战斗胜利额外获得 12 金币。 */
public class CorruptHourglassRelic extends AbstractArkNsfwRelic {
    public static final String ID = ArkNsfwMod.makeID("CorruptHourglassRelic");

    public CorruptHourglassRelic() {
        super(ID, "fall_corrupt_hourglass.png", RelicTier.UNCOMMON, LandingSound.CLINK);
    }

    @Override
    public void atBattleStart() {
        flash();
        arknsfw.helpers.ArkSafeStats.addExcitementDeferred(5);
    }

    @Override
    public void onVictory() {
        flash();
        AbstractDungeon.player.gainGold(12);
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }
}
