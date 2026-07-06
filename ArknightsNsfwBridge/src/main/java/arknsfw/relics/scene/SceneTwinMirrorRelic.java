package arknsfw.relics.scene;

import com.megacrit.cardcrawl.relics.AbstractRelic;
import liesecore.helpers.NsfwRunStats;
import arknsfw.ArkNsfwMod;
import arknsfw.relics.AbstractArkNsfwRelic;

public class SceneTwinMirrorRelic extends AbstractArkNsfwRelic {
    public static final String ID = ArkNsfwMod.makeID("SceneTwinMirrorRelic");

    public SceneTwinMirrorRelic() {
        super(ID, "relic_scene_scenetwinmirrorrelic.png", RelicTier.RARE, LandingSound.CLINK);
    }

    @Override public void onVictory() { flash(); }

    @Override public String getUpdatedDescription() { return DESCRIPTIONS[0]; }
    @Override public AbstractRelic makeCopy() { return new SceneTwinMirrorRelic(); }
}
