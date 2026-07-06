package arknsfw.relics.highmore;

import com.megacrit.cardcrawl.relics.AbstractRelic;
import liesecore.helpers.NsfwRunStats;
import arknsfw.ArkNsfwMod;
import arknsfw.relics.AbstractArkNsfwRelic;

public class HighmoreSoftCollarRelic extends AbstractArkNsfwRelic {
    public static final String ID = ArkNsfwMod.makeID("HighmoreSoftCollarRelic");

    public HighmoreSoftCollarRelic() {
        super(ID, "relic_highmore_highmoresoftcollarrelic.png", RelicTier.UNCOMMON, LandingSound.CLINK);
    }

    @Override public void atBattleStart() { NsfwRunStats.addExcitement(10); flash(); }

    @Override public String getUpdatedDescription() { return DESCRIPTIONS[0]; }
    @Override public AbstractRelic makeCopy() { return new HighmoreSoftCollarRelic(); }
}
