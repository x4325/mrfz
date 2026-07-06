package arknsfw.relics.curses.eyja;

import com.megacrit.cardcrawl.relics.AbstractRelic;
import liesecore.api.NsfwCharacterRegistry;
import liesecore.helpers.NsfwRunStats;
import arknsfw.ArkNsfwMod;
import arknsfw.helpers.ArkCharMechanicsHelper;
import arknsfw.relics.curses.AbstractArkCurseRelic;

/** 绻佽偛绁潧锛氭垬鏂楀紑濮嬪己鍔涘彈瀛曪紱姣忓洖鍚堟寔缁崌娓┿€傝壘闆咃細浜戦噺/鐐庢伅鍔犻€熺箒鑲层€?*/
public class BreedingAltarCurseRelic extends AbstractArkCurseRelic {
    public static final String ID = ArkNsfwMod.makeID("BreedingAltarCurseRelic");

    public BreedingAltarCurseRelic() {
        super(ID, "curse_breeding_altar.png", LandingSound.SOLID);
    }

    @Override
    public void atBattleStart() {
        if (!NsfwCharacterRegistry.isActive()) {
            return;
        }
        flash();
        if (NsfwRunStats.pregnant) {
            NsfwRunStats.addPregnancyProgress(20);
        } else {
            NsfwRunStats.addConception(15, false);
            NsfwRunStats.addFertility(0, 12, false);
        }
        ArkCharMechanicsHelper.gainCloudEnergy(2);
        ArkCharMechanicsHelper.applyFireMarkPower(com.megacrit.cardcrawl.dungeons.AbstractDungeon.player, 2);
    }

    @Override
    public void atTurnStart() {
        if (!NsfwCharacterRegistry.isActive()) {
            return;
        }
        NsfwRunStats.addConception(5 + ArkCharMechanicsHelper.fireMarkPowerAmount(), false);
        NsfwRunStats.addExcitement(8 + ArkCharMechanicsHelper.cloudCardCount() * 2);
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

    @Override
    public AbstractRelic makeCopy() {
        return new BreedingAltarCurseRelic();
    }
}

