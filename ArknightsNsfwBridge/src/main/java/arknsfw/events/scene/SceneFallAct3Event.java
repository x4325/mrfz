package arknsfw.events.scene;

import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;
import arknsfw.ArkNsfwMod;
import arknsfw.events.AbstractArkFiveRouteEvent;
import arknsfw.helpers.ArkEventImages;
import arknsfw.helpers.ArkRunProgress;
import arknsfw.powers.scene.ExposedLensPower;

public class SceneFallAct3Event extends AbstractArkFiveRouteEvent {
    public static final String ID = ArkNsfwMod.makeID("SceneFallAct3");

    public SceneFallAct3Event() {
        super(ID, ArkEventImages.path(ArkEventImages.SCENE_FALL_ACT3), ArkRunProgress.Route.FALL, 3);
    }

    @Override
    protected AbstractPower freshDebuff(int layers) {
        return new ExposedLensPower(AbstractDungeon.player, layers);
    }
}
