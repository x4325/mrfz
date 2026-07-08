package arknsfw.relics.fall;

import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import liesecore.helpers.NsfwRunStats;
import arknsfw.ArkNsfwMod;
import arknsfw.helpers.ArkCharMechanicsHelper;
import arknsfw.relics.AbstractArkNsfwRelic;

/** 母性光辉胸针：怀孕时战斗开始获得 10 格挡并回复 3 生命（海沫改为格挡）。 */
public class MotherGlowBroochRelic extends AbstractArkNsfwRelic {
    public static final String ID = ArkNsfwMod.makeID("MotherGlowBroochRelic");

    public MotherGlowBroochRelic() {
        super(ID, "fall_mother_brooch.png", RelicTier.SPECIAL, LandingSound.MAGICAL);
    }

    @Override
    public void atBattleStart() {
        if (!NsfwRunStats.pregnant) {
            return;
        }
        flash();
        addToBot(new GainBlockAction(AbstractDungeon.player, 10));
        ArkCharMechanicsHelper.healOrBlock(AbstractDungeon.player, 3);
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }
}
