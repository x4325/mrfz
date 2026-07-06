package arknsfw.relics.archetto;

import com.megacrit.cardcrawl.relics.AbstractRelic;
import liesecore.helpers.NsfwRunStats;
import arknsfw.ArkNsfwMod;
import arknsfw.relics.AbstractArkNsfwRelic;

public class ArchettoMoistFlaskRelic extends AbstractArkNsfwRelic {
    public static final String ID = ArkNsfwMod.makeID("ArchettoMoistFlaskRelic");

    public ArchettoMoistFlaskRelic() {
        super(ID, "relic_archetto_archettomoistflaskrelic.png", RelicTier.SHOP, LandingSound.CLINK);
    }

    @Override public void onVictory() { NsfwRunStats.addExcitement(5); }

    @Override public String getUpdatedDescription() { return DESCRIPTIONS[0]; }
    @Override public AbstractRelic makeCopy() { return new ArchettoMoistFlaskRelic(); }
}
