package arknsfw.relics.nymph;

import com.megacrit.cardcrawl.relics.AbstractRelic;
import liesecore.helpers.NsfwRunStats;
import arknsfw.ArkNsfwMod;
import arknsfw.relics.AbstractArkNsfwRelic;

public class NymphSoftCollarRelic extends AbstractArkNsfwRelic {
    public static final String ID = ArkNsfwMod.makeID("NymphSoftCollarRelic");

    public NymphSoftCollarRelic() {
        super(ID, "relic_nymph_nymphsoftcollarrelic.png", RelicTier.UNCOMMON, LandingSound.CLINK);
    }

    @Override public void atBattleStart() { NsfwRunStats.addExcitement(10); flash(); }

    @Override public String getUpdatedDescription() { return DESCRIPTIONS[0]; }
    @Override public AbstractRelic makeCopy() { return new NymphSoftCollarRelic(); }
}
