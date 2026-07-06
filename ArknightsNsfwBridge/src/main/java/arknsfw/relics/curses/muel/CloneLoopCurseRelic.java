package arknsfw.relics.curses.muel;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import liesecore.api.NsfwCharacterRegistry;
import liesecore.helpers.NsfwRunStats;
import arknsfw.ArkNsfwMod;
import arknsfw.helpers.ArkCharMechanicsHelper;
import arknsfw.powers.muel.CloneEchoPower;
import arknsfw.powers.muel.LeakPower;
import arknsfw.relics.curses.AbstractArkCurseRelic;

/** 鍒嗚韩寰幆锛氭垬鏂楀紑濮嬪垎韬洖闊?+2銆佹笚婕?+1锛涙瘡鍥炲悎鍏村 +8銆傜吉灏旓細娴佸舰/鏍藉煿鑱斿姩銆?*/
public class CloneLoopCurseRelic extends AbstractArkCurseRelic {
    public static final String ID = ArkNsfwMod.makeID("CloneLoopCurseRelic");

    public CloneLoopCurseRelic() {
        super(ID, "curse_clone_loop.png", LandingSound.FLAT);
    }

    @Override
    public void atBattleStart() {
        if (!NsfwCharacterRegistry.isActive()) {
            return;
        }
        flash();
        addToBot(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player,
                new CloneEchoPower(AbstractDungeon.player, 2), 2));
        addToBot(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player,
                new LeakPower(AbstractDungeon.player, 1), 1));
        if (ArkCharMechanicsHelper.hasManifold()) {
            ArkCharMechanicsHelper.boostManifold(1);
        }
        if (ArkCharMechanicsHelper.isCultivating()) {
            ArkCharMechanicsHelper.applyRootage(AbstractDungeon.player, 1);
        }
    }

    @Override
    public void atTurnStart() {
        if (NsfwCharacterRegistry.isActive()) {
            NsfwRunStats.addExcitement(8 + ArkCharMechanicsHelper.manifoldTotalAmount() * 2);
        }
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

    @Override
    public AbstractRelic makeCopy() {
        return new CloneLoopCurseRelic();
    }
}

