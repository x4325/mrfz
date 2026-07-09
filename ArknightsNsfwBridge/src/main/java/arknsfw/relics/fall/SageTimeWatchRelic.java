package arknsfw.relics.fall;

import arknsfw.ArkNsfwMod;
import arknsfw.relics.AbstractArkNsfwRelic;

/** 贤者怀表：高潮失控后，下回合获得 1 费与 2 抽（见 ArkClimaxHelper）。 */
public class SageTimeWatchRelic extends AbstractArkNsfwRelic {
    public static final String ID = ArkNsfwMod.makeID("SageTimeWatchRelic");

    public SageTimeWatchRelic() {
        super(ID, "fall_sagetime_watch.png", RelicTier.SPECIAL, LandingSound.CLINK);
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }
}
