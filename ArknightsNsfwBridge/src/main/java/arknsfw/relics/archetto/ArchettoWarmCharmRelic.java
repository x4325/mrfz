package arknsfw.relics.archetto;

import com.megacrit.cardcrawl.relics.AbstractRelic;
import liesecore.helpers.NsfwRunStats;
import arknsfw.ArkNsfwMod;
import arknsfw.relics.AbstractArkNsfwRelic;

public class ArchettoWarmCharmRelic extends AbstractArkNsfwRelic {
    public static final String ID = ArkNsfwMod.makeID("ArchettoWarmCharmRelic");

    public ArchettoWarmCharmRelic() {
        super(ID, "relic_archetto_archettowarmcharmrelic.png", RelicTier.COMMON, LandingSound.CLINK);
    }

    @Override public void atTurnStart() { if (NsfwRunStats.excitement >= 30) flash(); }

    @Override public String getUpdatedDescription() { return DESCRIPTIONS[0]; }
    @Override public AbstractRelic makeCopy() { return new ArchettoWarmCharmRelic(); }
}
