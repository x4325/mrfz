package arknsfw.relics.scene;

import com.megacrit.cardcrawl.relics.AbstractRelic;
import liesecore.helpers.NsfwRunStats;
import arknsfw.ArkNsfwMod;
import arknsfw.relics.AbstractArkNsfwRelic;

public class SceneWarmCharmRelic extends AbstractArkNsfwRelic {
    public static final String ID = ArkNsfwMod.makeID("SceneWarmCharmRelic");

    public SceneWarmCharmRelic() {
        super(ID, "relic_scene_scenewarmcharmrelic.png", RelicTier.COMMON, LandingSound.CLINK);
    }

    @Override public void atTurnStart() { if (NsfwRunStats.excitement >= 30) flash(); }

    @Override public String getUpdatedDescription() { return DESCRIPTIONS[0]; }
    @Override public AbstractRelic makeCopy() { return new SceneWarmCharmRelic(); }
}
