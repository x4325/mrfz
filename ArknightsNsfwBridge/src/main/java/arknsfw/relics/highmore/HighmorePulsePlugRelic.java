package arknsfw.relics.highmore;

import com.megacrit.cardcrawl.relics.AbstractRelic;
import liesecore.helpers.NsfwRunStats;
import arknsfw.ArkNsfwMod;
import arknsfw.relics.AbstractArkNsfwRelic;

public class HighmorePulsePlugRelic extends AbstractArkNsfwRelic {
    public static final String ID = ArkNsfwMod.makeID("HighmorePulsePlugRelic");

    public HighmorePulsePlugRelic() {
        super(ID, "relic_highmore_highmorepulseplugrelic.png", RelicTier.RARE, LandingSound.CLINK);
    }

    @Override public void atTurnStart() { NsfwRunStats.addExcitement(6); flash(); }

    @Override public String getUpdatedDescription() { return DESCRIPTIONS[0]; }
    @Override public AbstractRelic makeCopy() { return new HighmorePulsePlugRelic(); }
}
