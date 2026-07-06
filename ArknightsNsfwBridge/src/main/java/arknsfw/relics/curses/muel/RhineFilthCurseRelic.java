package arknsfw.relics.curses.muel;

import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import liesecore.api.NsfwCharacterRegistry;
import liesecore.helpers.NsfwRunStats;
import arknsfw.ArkNsfwMod;
import arknsfw.helpers.ArkCharMechanicsHelper;
import arknsfw.relics.curses.AbstractArkCurseRelic;

/** 鑾辫尩姹＄Ы锛氭瘡鍥炲悎鍏村 +14銆佸彈瀛?+8銆傜吉灏旓細婧簮/鏍藉煿棰濆姹℃煋銆?*/
public class RhineFilthCurseRelic extends AbstractArkCurseRelic {
    public static final String ID = ArkNsfwMod.makeID("RhineFilthCurseRelic");

    public RhineFilthCurseRelic() {
        super(ID, "curse_rhine_filth.png", LandingSound.MAGICAL);
    }

    @Override
    public void atTurnStart() {
        if (!NsfwCharacterRegistry.isActive()) {
            return;
        }
        flash();
        NsfwRunStats.addExcitement(14 + ArkCharMechanicsHelper.manifoldTotalAmount() * 2);
        NsfwRunStats.addConception(8 + ArkCharMechanicsHelper.rootageAmount() * 2, false);
        if (ArkCharMechanicsHelper.isCultivating()) {
            ArkCharMechanicsHelper.applyRootage(AbstractDungeon.player, 1);
        }
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

    @Override
    public AbstractRelic makeCopy() {
        return new RhineFilthCurseRelic();
    }
}

