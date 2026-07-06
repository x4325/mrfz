package arknsfw.relics.curses.eyja;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import liesecore.api.NsfwCharacterRegistry;
import liesecore.helpers.NsfwRunStats;
import arknsfw.ArkNsfwMod;
import arknsfw.helpers.ArkCharMechanicsHelper;
import arknsfw.powers.eyja.VolcanicFlushPower;
import arknsfw.relics.curses.AbstractArkCurseRelic;

/** 鐏北鐑欏嵃锛氭垬鏂楀紑濮嬬伀灞辨疆绾?+2锛涙瘡鍥炲悎鍏村 +10銆傝壘闆咃細棰濆浜戦噺/鐐庢伅鑱斿姩銆?*/
public class VolcanoBrandCurseRelic extends AbstractArkCurseRelic {
    public static final String ID = ArkNsfwMod.makeID("VolcanoBrandCurseRelic");

    public VolcanoBrandCurseRelic() {
        super(ID, "curse_volcano_brand.png", LandingSound.MAGICAL);
    }

    @Override
    public void atBattleStart() {
        if (!NsfwCharacterRegistry.isActive()) {
            return;
        }
        flash();
        addToBot(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player,
                new VolcanicFlushPower(AbstractDungeon.player, 2), 2));
        ArkCharMechanicsHelper.gainCloudEnergy(1);
        ArkCharMechanicsHelper.applyFireMarkPower(AbstractDungeon.player, 1);
    }

    @Override
    public void atTurnStart() {
        if (!NsfwCharacterRegistry.isActive()) {
            return;
        }
        NsfwRunStats.addExcitement(10 + ArkCharMechanicsHelper.cloudCardCount() * 2);
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

    @Override
    public AbstractRelic makeCopy() {
        return new VolcanoBrandCurseRelic();
    }
}

