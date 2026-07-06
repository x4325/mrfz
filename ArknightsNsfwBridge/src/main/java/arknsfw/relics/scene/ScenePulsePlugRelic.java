package arknsfw.relics.scene;

import com.megacrit.cardcrawl.relics.AbstractRelic;
import liesecore.helpers.NsfwRunStats;
import arknsfw.ArkNsfwMod;
import arknsfw.relics.AbstractArkNsfwRelic;

public class ScenePulsePlugRelic extends AbstractArkNsfwRelic {
    public static final String ID = ArkNsfwMod.makeID("ScenePulsePlugRelic");

    public ScenePulsePlugRelic() {
        super(ID, "relic_scene_scenepulseplugrelic.png", RelicTier.RARE, LandingSound.CLINK);
    }

    @Override public void atTurnStart() { NsfwRunStats.addExcitement(6); flash(); }

    @Override public String getUpdatedDescription() { return DESCRIPTIONS[0]; }
    @Override public AbstractRelic makeCopy() { return new ScenePulsePlugRelic(); }
}
