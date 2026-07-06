package arknsfw.relics.archetto;

import com.megacrit.cardcrawl.relics.AbstractRelic;
import liesecore.helpers.NsfwRunStats;
import arknsfw.ArkNsfwMod;
import arknsfw.relics.AbstractArkNsfwRelic;

public class ArchettoPulsePlugRelic extends AbstractArkNsfwRelic {
    public static final String ID = ArkNsfwMod.makeID("ArchettoPulsePlugRelic");

    public ArchettoPulsePlugRelic() {
        super(ID, "relic_archetto_archettopulseplugrelic.png", RelicTier.RARE, LandingSound.CLINK);
    }

    @Override public void atTurnStart() { NsfwRunStats.addExcitement(6); flash(); }

    @Override public String getUpdatedDescription() { return DESCRIPTIONS[0]; }
    @Override public AbstractRelic makeCopy() { return new ArchettoPulsePlugRelic(); }
}
