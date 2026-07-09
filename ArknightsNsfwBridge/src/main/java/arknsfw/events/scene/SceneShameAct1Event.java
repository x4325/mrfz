package arknsfw.events.scene;

import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;
import arknsfw.ArkNsfwMod;
import arknsfw.events.AbstractArkFiveRouteEvent;
import arknsfw.helpers.ArkEventImages;
import arknsfw.helpers.ArkRunProgress;
import arknsfw.powers.scene.ExposedLensPower;

public class SceneShameAct1Event extends AbstractArkFiveRouteEvent {
    public static final String ID = ArkNsfwMod.makeID("SceneShameAct1");

    public SceneShameAct1Event() {
        super(ID, ArkEventImages.path(ArkEventImages.SCENE_SHAME_ACT1), ArkRunProgress.Route.SHAME, 1);
    }

    @Override
    protected AbstractPower freshDebuff(int layers) {
        return new ExposedLensPower(AbstractDungeon.player, layers);
    }
}
