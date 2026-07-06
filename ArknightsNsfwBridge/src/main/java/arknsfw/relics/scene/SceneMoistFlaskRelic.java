package arknsfw.relics.scene;

import com.megacrit.cardcrawl.relics.AbstractRelic;
import liesecore.helpers.NsfwRunStats;
import arknsfw.ArkNsfwMod;
import arknsfw.relics.AbstractArkNsfwRelic;

public class SceneMoistFlaskRelic extends AbstractArkNsfwRelic {
    public static final String ID = ArkNsfwMod.makeID("SceneMoistFlaskRelic");

    public SceneMoistFlaskRelic() {
        super(ID, "relic_scene_scenemoistflaskrelic.png", RelicTier.SHOP, LandingSound.CLINK);
    }

    @Override public void onVictory() { NsfwRunStats.addExcitement(5); }

    @Override public String getUpdatedDescription() { return DESCRIPTIONS[0]; }
    @Override public AbstractRelic makeCopy() { return new SceneMoistFlaskRelic(); }
}
