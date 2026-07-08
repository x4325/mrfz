package arknsfw.relics.fall;

import arknsfw.ArkNsfwMod;
import arknsfw.relics.AbstractArkNsfwRelic;

/** 快感转换器：高潮失控时不再失去格挡，改为对全体敌人释放快感冲击（见 ArkClimaxHelper）。 */
public class PleasureConverterRelic extends AbstractArkNsfwRelic {
    public static final String ID = ArkNsfwMod.makeID("PleasureConverterRelic");

    public PleasureConverterRelic() {
        super(ID, "fall_pleasure_converter.png", RelicTier.RARE, LandingSound.MAGICAL);
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }
}
