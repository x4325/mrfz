package arknsfw.relics.eyja;

import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import liesecore.helpers.NsfwRunStats;
import arknsfw.ArkNsfwMod;
import arknsfw.helpers.ArkCharMechanicsHelper;
import arknsfw.relics.AbstractArkNsfwRelic;

public class HeatStickerRelic extends AbstractArkNsfwRelic {
    public static final String ID = ArkNsfwMod.makeID("HeatStickerRelic");
    private int turns = 0;

    public HeatStickerRelic() {
        super(ID, "heat_sticker.png", RelicTier.UNCOMMON, LandingSound.FLAT);
    }

    @Override
    public void atTurnStart() {
        turns++;
        NsfwRunStats.addExcitement(3 + ArkCharMechanicsHelper.cloudCardCount());
        if (turns % 3 == 0 && AbstractDungeon.player != null) {
            ArkCharMechanicsHelper.applyFireMarkPower(AbstractDungeon.player, 1);
        }
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

    @Override
    public AbstractRelic makeCopy() {
        return new HeatStickerRelic();
    }
}
