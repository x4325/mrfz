package arknsfw.events.haruka;

import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;
import arknsfw.ArkNsfwMod;
import arknsfw.events.AbstractArkFiveRouteEvent;
import arknsfw.helpers.ArkEventImages;
import arknsfw.helpers.ArkRunProgress;
import arknsfw.powers.haruka.LingeringHeatPower;

public class HarukaFallAct1Event extends AbstractArkFiveRouteEvent {
    public static final String ID = ArkNsfwMod.makeID("HarukaFallAct1");

    public HarukaFallAct1Event() {
        super(ID, ArkEventImages.path(ArkEventImages.HARUKA_FALL_ACT1), ArkRunProgress.Route.FALL, 1);
    }

    @Override
    protected AbstractPower freshDebuff(int layers) {
        return new LingeringHeatPower(AbstractDungeon.player, layers);
    }
}
