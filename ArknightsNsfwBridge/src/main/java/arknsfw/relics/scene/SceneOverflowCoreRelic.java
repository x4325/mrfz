package arknsfw.relics.scene;

import com.megacrit.cardcrawl.relics.AbstractRelic;
import liesecore.helpers.NsfwRunStats;
import arknsfw.ArkNsfwMod;
import arknsfw.relics.AbstractArkNsfwRelic;

public class SceneOverflowCoreRelic extends AbstractArkNsfwRelic {
    public static final String ID = ArkNsfwMod.makeID("SceneOverflowCoreRelic");

    public SceneOverflowCoreRelic() {
        super(ID, "relic_scene_sceneoverflowcorerelic.png", RelicTier.BOSS, LandingSound.CLINK);
    }

    @Override public void onVictory() { NsfwRunStats.addExcitement(20); flash(); }

    @Override public String getUpdatedDescription() { return DESCRIPTIONS[0]; }
    @Override public AbstractRelic makeCopy() { return new SceneOverflowCoreRelic(); }
}
