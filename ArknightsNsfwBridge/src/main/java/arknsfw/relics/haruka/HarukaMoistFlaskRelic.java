package arknsfw.relics.haruka;

import com.megacrit.cardcrawl.relics.AbstractRelic;
import liesecore.helpers.NsfwRunStats;
import arknsfw.ArkNsfwMod;
import arknsfw.relics.AbstractArkNsfwRelic;

public class HarukaMoistFlaskRelic extends AbstractArkNsfwRelic {
    public static final String ID = ArkNsfwMod.makeID("HarukaMoistFlaskRelic");

    public HarukaMoistFlaskRelic() {
        super(ID, "relic_haruka_harukamoistflaskrelic.png", RelicTier.SHOP, LandingSound.CLINK);
    }

    @Override public void onVictory() { NsfwRunStats.addExcitement(5); }

    @Override public String getUpdatedDescription() { return DESCRIPTIONS[0]; }
    @Override public AbstractRelic makeCopy() { return new HarukaMoistFlaskRelic(); }
}
