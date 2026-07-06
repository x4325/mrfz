package arknsfw.relics.eyja;

import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import liesecore.helpers.NsfwRunStats;
import arknsfw.ArkNsfwMod;
import arknsfw.helpers.ArkCharMechanicsHelper;
import arknsfw.relics.AbstractArkNsfwRelic;

public class ThermometerCharmRelic extends AbstractArkNsfwRelic {
    public static final String ID = ArkNsfwMod.makeID("ThermometerCharmRelic");
    public static final int THRESHOLD_BONUS = 15;

    public ThermometerCharmRelic() {
        super(ID, "thermometer_charm.png", RelicTier.UNCOMMON, LandingSound.FLAT);
    }

    @Override
    public void onEquip() {
        beginLongPulse();
    }

    @Override
    public void atBattleStart() {
        flash();
        addToBot(new RelicAboveCreatureAction(AbstractDungeon.player, this));
        NsfwRunStats.addExcitement(8);
        ArkCharMechanicsHelper.gainCloudEnergy(1);
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

    @Override
    public AbstractRelic makeCopy() {
        return new ThermometerCharmRelic();
    }
}
