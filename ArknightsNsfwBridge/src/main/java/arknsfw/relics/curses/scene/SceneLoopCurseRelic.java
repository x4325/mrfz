package arknsfw.relics.curses.scene;

import com.megacrit.cardcrawl.relics.AbstractRelic;
import arknsfw.ArkNsfwMod;
import arknsfw.relics.AbstractArkNsfwRelic;

public class SceneLoopCurseRelic extends AbstractArkNsfwRelic {
    public static final String ID = ArkNsfwMod.makeID("SceneLoopCurseRelic");

    public SceneLoopCurseRelic() {
        super(ID, "relic_curse_scene_sceneloopcurserelic.png", RelicTier.SPECIAL, LandingSound.CLINK);
    }

    @Override public String getUpdatedDescription() { return DESCRIPTIONS[0]; }
    @Override public AbstractRelic makeCopy() { return new SceneLoopCurseRelic(); }
}
