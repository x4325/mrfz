package arknsfw.events.haruka;

import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;
import arknsfw.ArkNsfwMod;
import arknsfw.events.AbstractArkFiveRouteEvent;
import arknsfw.helpers.ArkEventImages;
import arknsfw.helpers.ArkRunProgress;
import arknsfw.powers.haruka.LingeringHeatPower;

public class HarukaShameAct2Event extends AbstractArkFiveRouteEvent {
    public static final String ID = ArkNsfwMod.makeID("HarukaShameAct2");

    public HarukaShameAct2Event() {
        super(ID, ArkEventImages.path(ArkEventImages.HARUKA_SHAME_ACT2), ArkRunProgress.Route.SHAME, 2);
    }

    @Override
    protected AbstractPower freshDebuff(int layers) {
        return new LingeringHeatPower(AbstractDungeon.player, layers);
    }
}
