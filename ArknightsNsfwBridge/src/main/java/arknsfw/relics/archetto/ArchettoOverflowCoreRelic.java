package arknsfw.relics.archetto;

import com.megacrit.cardcrawl.relics.AbstractRelic;
import liesecore.helpers.NsfwRunStats;
import arknsfw.ArkNsfwMod;
import arknsfw.relics.AbstractArkNsfwRelic;

public class ArchettoOverflowCoreRelic extends AbstractArkNsfwRelic {
    public static final String ID = ArkNsfwMod.makeID("ArchettoOverflowCoreRelic");

    public ArchettoOverflowCoreRelic() {
        super(ID, "relic_archetto_archettooverflowcorerelic.png", RelicTier.BOSS, LandingSound.CLINK);
    }

    @Override public void onVictory() { NsfwRunStats.addExcitement(20); flash(); }

    @Override public String getUpdatedDescription() { return DESCRIPTIONS[0]; }
    @Override public AbstractRelic makeCopy() { return new ArchettoOverflowCoreRelic(); }
}
