package arknsfw.relics.highmore;

import com.megacrit.cardcrawl.relics.AbstractRelic;
import liesecore.helpers.NsfwRunStats;
import arknsfw.ArkNsfwMod;
import arknsfw.relics.AbstractArkNsfwRelic;

public class HighmoreMoistFlaskRelic extends AbstractArkNsfwRelic {
    public static final String ID = ArkNsfwMod.makeID("HighmoreMoistFlaskRelic");

    public HighmoreMoistFlaskRelic() {
        super(ID, "relic_highmore_highmoremoistflaskrelic.png", RelicTier.SHOP, LandingSound.CLINK);
    }

    @Override public void onVictory() { NsfwRunStats.addExcitement(5); }

    @Override public String getUpdatedDescription() { return DESCRIPTIONS[0]; }
    @Override public AbstractRelic makeCopy() { return new HighmoreMoistFlaskRelic(); }
}
