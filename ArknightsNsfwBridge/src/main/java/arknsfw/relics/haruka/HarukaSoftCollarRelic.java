package arknsfw.relics.haruka;

import com.megacrit.cardcrawl.relics.AbstractRelic;
import liesecore.helpers.NsfwRunStats;
import arknsfw.ArkNsfwMod;
import arknsfw.relics.AbstractArkNsfwRelic;

public class HarukaSoftCollarRelic extends AbstractArkNsfwRelic {
    public static final String ID = ArkNsfwMod.makeID("HarukaSoftCollarRelic");

    public HarukaSoftCollarRelic() {
        super(ID, "relic_haruka_harukasoftcollarrelic.png", RelicTier.UNCOMMON, LandingSound.CLINK);
    }

    @Override public void atBattleStart() { NsfwRunStats.addExcitement(10); flash(); }

    @Override public String getUpdatedDescription() { return DESCRIPTIONS[0]; }
    @Override public AbstractRelic makeCopy() { return new HarukaSoftCollarRelic(); }
}
