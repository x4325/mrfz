package arknsfw.relics.scene;

import com.megacrit.cardcrawl.relics.AbstractRelic;
import liesecore.helpers.NsfwRunStats;
import arknsfw.ArkNsfwMod;
import arknsfw.relics.AbstractArkNsfwRelic;

public class SceneSoftCollarRelic extends AbstractArkNsfwRelic {
    public static final String ID = ArkNsfwMod.makeID("SceneSoftCollarRelic");

    public SceneSoftCollarRelic() {
        super(ID, "relic_scene_scenesoftcollarrelic.png", RelicTier.UNCOMMON, LandingSound.CLINK);
    }

    @Override public void atBattleStart() { NsfwRunStats.addExcitement(10); flash(); }

    @Override public String getUpdatedDescription() { return DESCRIPTIONS[0]; }
    @Override public AbstractRelic makeCopy() { return new SceneSoftCollarRelic(); }
}
