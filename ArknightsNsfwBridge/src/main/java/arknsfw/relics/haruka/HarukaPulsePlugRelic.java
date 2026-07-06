package arknsfw.relics.haruka;

import com.megacrit.cardcrawl.relics.AbstractRelic;
import liesecore.helpers.NsfwRunStats;
import arknsfw.ArkNsfwMod;
import arknsfw.relics.AbstractArkNsfwRelic;

public class HarukaPulsePlugRelic extends AbstractArkNsfwRelic {
    public static final String ID = ArkNsfwMod.makeID("HarukaPulsePlugRelic");

    public HarukaPulsePlugRelic() {
        super(ID, "relic_haruka_harukapulseplugrelic.png", RelicTier.RARE, LandingSound.CLINK);
    }

    @Override public void atTurnStart() { NsfwRunStats.addExcitement(6); flash(); }

    @Override public String getUpdatedDescription() { return DESCRIPTIONS[0]; }
    @Override public AbstractRelic makeCopy() { return new HarukaPulsePlugRelic(); }
}
