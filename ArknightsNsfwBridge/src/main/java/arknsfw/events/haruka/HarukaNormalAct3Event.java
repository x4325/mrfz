package arknsfw.events.haruka;

import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;
import arknsfw.ArkNsfwMod;
import arknsfw.events.AbstractArkFiveRouteEvent;
import arknsfw.helpers.ArkEventImages;
import arknsfw.helpers.ArkRunProgress;
import arknsfw.powers.haruka.LingeringHeatPower;

public class HarukaNormalAct3Event extends AbstractArkFiveRouteEvent {
    public static final String ID = ArkNsfwMod.makeID("HarukaNormalAct3");

    public HarukaNormalAct3Event() {
        super(ID, ArkEventImages.path(ArkEventImages.HARUKA_NORMAL_ACT3), ArkRunProgress.Route.NORMAL, 3);
    }

    @Override
    protected AbstractPower freshDebuff(int layers) {
        return new LingeringHeatPower(AbstractDungeon.player, layers);
    }
}
