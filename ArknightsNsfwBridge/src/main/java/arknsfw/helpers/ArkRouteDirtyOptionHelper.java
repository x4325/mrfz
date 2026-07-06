package arknsfw.helpers;

import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;
import liesecore.helpers.NsfwRunStats;

/** 路线事件第四选项：温存 / 抑制 / 堕落，按 Act 递进。 */
public final class ArkRouteDirtyOptionHelper {

    private ArkRouteDirtyOptionHelper() {
    }

    public static void applyNormalWarmth(int act) {
        if (AbstractDungeon.player == null) {
            return;
        }
        int hp;
        int pregProg;
        int conc;
        int exc;
        switch (act) {
            case 1:
                hp = 3;
                pregProg = 12;
                conc = 10;
                exc = 12;
                break;
            case 2:
                hp = 4;
                pregProg = 16;
                conc = 12;
                exc = 14;
                break;
            default:
                hp = 5;
                pregProg = 20;
                conc = 14;
                exc = 16;
                break;
        }
        AbstractDungeon.player.increaseMaxHp(hp, true);
        if (NsfwRunStats.pregnant) {
            NsfwRunStats.addPregnancyProgress(pregProg);
        } else {
            NsfwRunStats.addConception(conc, false);
        }
        NsfwRunStats.addExcitement(exc);
    }

    public static void applyShameSelfHumiliate(AbstractPlayer player, AbstractPower failPower, int act) {
        int excSuccess;
        int excFail;
        switch (act) {
            case 1:
                excSuccess = 22;
                excFail = 18;
                break;
            case 2:
                excSuccess = 26;
                excFail = 22;
                break;
            default:
                excSuccess = 30;
                excFail = 26;
                break;
        }
        if (ArkDebuffHelper.removeRandomDebuff()) {
            NsfwRunStats.addExcitement(excSuccess);
        } else {
            ArkDebuffHelper.apply(player, failPower);
            NsfwRunStats.addExcitement(excFail);
        }
    }

    public static void applyEyjaFallDirty(int act) {
        int exc;
        int conc;
        int fertA;
        int fertB;
        int fireMark;
        switch (act) {
            case 1:
                exc = 30;
                conc = 14;
                fertA = 8;
                fertB = 10;
                fireMark = 1;
                break;
            case 2:
                exc = 35;
                conc = 16;
                fertA = 10;
                fertB = 12;
                fireMark = 1;
                break;
            default:
                exc = 40;
                conc = 18;
                fertA = 12;
                fertB = 15;
                fireMark = 2;
                break;
        }
        NsfwRunStats.addExcitement(exc);
        NsfwRunStats.addConception(conc, false);
        NsfwRunStats.addFertility(fertA, fertB, true);
        if (AbstractDungeon.player != null) {
            ArkCharMechanicsHelper.applyFireMarkPower(AbstractDungeon.player, fireMark);
        }
        if (act >= 3 && NsfwRunStats.pregnant) {
            NsfwRunStats.addPregnancyProgress(10);
        }
    }

    public static void applyMuelFallDirty(int act) {
        int exc;
        int conc;
        int fertA;
        int fertB;
        int cloneEcho;
        switch (act) {
            case 1:
                exc = 30;
                conc = 14;
                fertA = 8;
                fertB = 10;
                cloneEcho = 2;
                break;
            case 2:
                exc = 35;
                conc = 16;
                fertA = 10;
                fertB = 12;
                cloneEcho = 2;
                break;
            default:
                exc = 40;
                conc = 18;
                fertA = 12;
                fertB = 15;
                cloneEcho = 3;
                break;
        }
        NsfwRunStats.addExcitement(exc);
        NsfwRunStats.addConception(conc, false);
        NsfwRunStats.addFertility(fertA, fertB, true);
        if (AbstractDungeon.player != null) {
            ArkDebuffHelper.apply(AbstractDungeon.player,
                    new arknsfw.powers.muel.CloneEchoPower(AbstractDungeon.player, cloneEcho));
            if (ArkCharMechanicsHelper.hasManifold()) {
                ArkCharMechanicsHelper.boostManifold(1);
            }
        }
        if (act >= 3 && NsfwRunStats.pregnant) {
            NsfwRunStats.addPregnancyProgress(10);
        }
    }
}
