package arknsfw.relics.archetto;

import com.megacrit.cardcrawl.relics.AbstractRelic;
import liesecore.helpers.NsfwRunStats;
import arknsfw.ArkNsfwMod;
import arknsfw.relics.AbstractArkNsfwRelic;

public class ArchettoSoftCollarRelic extends AbstractArkNsfwRelic {
    public static final String ID = ArkNsfwMod.makeID("ArchettoSoftCollarRelic");

    public ArchettoSoftCollarRelic() {
        super(ID, "relic_archetto_archettosoftcollarrelic.png", RelicTier.UNCOMMON, LandingSound.CLINK);
    }

    @Override public void atBattleStart() { NsfwRunStats.addExcitement(10); flash(); }

    @Override public String getUpdatedDescription() { return DESCRIPTIONS[0]; }
    @Override public AbstractRelic makeCopy() { return new ArchettoSoftCollarRelic(); }
}
