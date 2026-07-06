package arknsfw.relics.highmore;

import com.megacrit.cardcrawl.relics.AbstractRelic;
import liesecore.helpers.NsfwRunStats;
import arknsfw.ArkNsfwMod;
import arknsfw.relics.AbstractArkNsfwRelic;

public class HighmoreWarmCharmRelic extends AbstractArkNsfwRelic {
    public static final String ID = ArkNsfwMod.makeID("HighmoreWarmCharmRelic");

    public HighmoreWarmCharmRelic() {
        super(ID, "relic_highmore_highmorewarmcharmrelic.png", RelicTier.COMMON, LandingSound.CLINK);
    }

    @Override public void atTurnStart() { if (NsfwRunStats.excitement >= 30) flash(); }

    @Override public String getUpdatedDescription() { return DESCRIPTIONS[0]; }
    @Override public AbstractRelic makeCopy() { return new HighmoreWarmCharmRelic(); }
}
