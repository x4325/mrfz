package arknsfw.events.scene;

import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;
import arknsfw.ArkNsfwMod;
import arknsfw.events.AbstractArkFiveRouteEvent;
import arknsfw.helpers.ArkEventImages;
import arknsfw.helpers.ArkRunProgress;
import arknsfw.powers.scene.ExposedLensPower;

public class SceneNormalAct3Event extends AbstractArkFiveRouteEvent {
    public static final String ID = ArkNsfwMod.makeID("SceneNormalAct3");

    public SceneNormalAct3Event() {
        super(ID, ArkEventImages.path(ArkEventImages.SCENE_NORMAL_ACT3), ArkRunProgress.Route.NORMAL, 3);
    }

    @Override
    protected AbstractPower freshDebuff(int layers) {
        return new ExposedLensPower(AbstractDungeon.player, layers);
    }
}
