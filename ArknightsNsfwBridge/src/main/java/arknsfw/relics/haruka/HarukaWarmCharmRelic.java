package arknsfw.relics.haruka;

import com.megacrit.cardcrawl.relics.AbstractRelic;
import liesecore.helpers.NsfwRunStats;
import arknsfw.ArkNsfwMod;
import arknsfw.relics.AbstractArkNsfwRelic;

public class HarukaWarmCharmRelic extends AbstractArkNsfwRelic {
    public static final String ID = ArkNsfwMod.makeID("HarukaWarmCharmRelic");

    public HarukaWarmCharmRelic() {
        super(ID, "relic_haruka_harukawarmcharmrelic.png", RelicTier.COMMON, LandingSound.CLINK);
    }

    @Override public void atTurnStart() { if (NsfwRunStats.excitement >= 30) flash(); }

    @Override public String getUpdatedDescription() { return DESCRIPTIONS[0]; }
    @Override public AbstractRelic makeCopy() { return new HarukaWarmCharmRelic(); }
}
