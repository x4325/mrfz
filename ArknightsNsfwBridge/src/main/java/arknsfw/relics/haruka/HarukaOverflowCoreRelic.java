package arknsfw.relics.haruka;

import com.megacrit.cardcrawl.relics.AbstractRelic;
import liesecore.helpers.NsfwRunStats;
import arknsfw.ArkNsfwMod;
import arknsfw.relics.AbstractArkNsfwRelic;

public class HarukaOverflowCoreRelic extends AbstractArkNsfwRelic {
    public static final String ID = ArkNsfwMod.makeID("HarukaOverflowCoreRelic");

    public HarukaOverflowCoreRelic() {
        super(ID, "relic_haruka_harukaoverflowcorerelic.png", RelicTier.BOSS, LandingSound.CLINK);
    }

    @Override public void onVictory() { NsfwRunStats.addExcitement(20); flash(); }

    @Override public String getUpdatedDescription() { return DESCRIPTIONS[0]; }
    @Override public AbstractRelic makeCopy() { return new HarukaOverflowCoreRelic(); }
}
