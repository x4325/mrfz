package arknsfw.helpers;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.WeakPower;
import arknsfw.powers.eyja.AshShamePower;
import arknsfw.powers.eyja.CoreStrainPower;
import arknsfw.powers.eyja.VolcanicFlushPower;
import arknsfw.powers.muel.BubbleGagPower;
import arknsfw.powers.muel.CloneEchoPower;
import arknsfw.powers.muel.LeakPower;
import liesecore.helpers.DebuffIntentHelper;
import liesecore.helpers.NsfwRunStats;
import liesecore.helpers.UiHelper;

public final class ArkDebuffIntentHelper {

    public static final String[] EYJA_SEAL_IDS = {
            VolcanicFlushPower.POWER_ID,
            AshShamePower.POWER_ID,
            CoreStrainPower.POWER_ID,
    };

    public static final String[] MUEL_SEAL_IDS = {
            BubbleGagPower.POWER_ID,
            LeakPower.POWER_ID,
            CloneEchoPower.POWER_ID,
    };

    private ArkDebuffIntentHelper() {
    }

    public static void executeSeal(AbstractMonster monster, AbstractPlayer player, String powerId, boolean eyja) {
        if (player == null || monster == null || powerId == null) {
            return;
        }
        NsfwRunStats.addExcitement(8);
        if (eyja) {
            sealEyja(player, powerId, 2);
        } else {
            sealMuel(player, powerId, 2);
        }
        UiHelper.showCenterText(player, "刻印");
    }

    public static void executeExploit(AbstractMonster monster, AbstractPlayer player, String powerId, boolean eyja) {
        if (player == null || monster == null || powerId == null) {
            return;
        }
        if (!ArkDebuffHelper.isLocked(powerId)) {
            NsfwRunStats.addExcitement(5);
            AbstractDungeon.actionManager.addToBottom(
                    new DamageAction(player, new DamageInfo(monster, 3), AbstractGameAction.AttackEffect.BLUNT_LIGHT));
            UiHelper.showCenterText(player, "刻印未生效");
            return;
        }
        if (eyja) {
            exploitEyja(monster, player, powerId);
        } else {
            exploitMuel(monster, player, powerId);
        }
    }

    private static void sealEyja(AbstractPlayer player, String powerId, int amount) {
        if (VolcanicFlushPower.POWER_ID.equals(powerId)) {
            ArkDebuffHelper.applyAndLock(player, new VolcanicFlushPower(player, amount));
        } else if (AshShamePower.POWER_ID.equals(powerId)) {
            ArkDebuffHelper.applyAndLock(player, new AshShamePower(player, amount));
        } else if (CoreStrainPower.POWER_ID.equals(powerId)) {
            ArkDebuffHelper.applyAndLock(player, new CoreStrainPower(player, amount));
        }
    }

    private static void sealMuel(AbstractPlayer player, String powerId, int amount) {
        if (BubbleGagPower.POWER_ID.equals(powerId)) {
            ArkDebuffHelper.applyAndLock(player, new BubbleGagPower(player, amount));
        } else if (LeakPower.POWER_ID.equals(powerId)) {
            ArkDebuffHelper.applyAndLock(player, new LeakPower(player, amount));
        } else if (CloneEchoPower.POWER_ID.equals(powerId)) {
            ArkDebuffHelper.applyAndLock(player, new CloneEchoPower(player, amount));
        }
    }

    private static void exploitEyja(AbstractMonster monster, AbstractPlayer player, String powerId) {
        if (VolcanicFlushPower.POWER_ID.equals(powerId)) {
            NsfwRunStats.addExcitement(18);
            NsfwRunStats.addConception(6, false);
            addPower(player, monster, new VolcanicFlushPower(player, 2));
            ArkCharMechanicsHelper.applyFireMarkPower(player, 1);
            UiHelper.showCenterText(player, "刻印·潮热");
        } else if (AshShamePower.POWER_ID.equals(powerId)) {
            NsfwRunStats.addExcitement(15);
            NsfwRunStats.addConception(10, false);
            addPower(player, monster, new AshShamePower(player, 2));
            UiHelper.showCenterText(player, "刻印·灰烬");
        } else if (CoreStrainPower.POWER_ID.equals(powerId)) {
            NsfwRunStats.addExcitement(12);
            NsfwRunStats.addFertility(8, 10, false);
            addToBot(new DamageAction(player, new DamageInfo(monster, 7), AbstractGameAction.AttackEffect.FIRE));
            addPower(player, monster, new CoreStrainPower(player, 2));
            UiHelper.showCenterText(player, "刻印·熔核");
        }
    }

    private static void exploitMuel(AbstractMonster monster, AbstractPlayer player, String powerId) {
        if (BubbleGagPower.POWER_ID.equals(powerId)) {
            NsfwRunStats.addExcitement(14);
            addPower(player, monster, new BubbleGagPower(player, 2));
            addPower(player, monster, new WeakPower(player, 1, false));
            UiHelper.showCenterText(player, "刻印·泡沫");
        } else if (LeakPower.POWER_ID.equals(powerId)) {
            NsfwRunStats.addExcitement(16);
            NsfwRunStats.addConception(8, false);
            addPower(player, monster, new LeakPower(player, 2));
            UiHelper.showCenterText(player, "刻印·渗漏");
        } else if (CloneEchoPower.POWER_ID.equals(powerId)) {
            NsfwRunStats.addExcitement(15);
            addPower(player, monster, new CloneEchoPower(player, 2));
            if (ArkCharMechanicsHelper.hasManifold()) {
                ArkCharMechanicsHelper.boostManifold(1);
            }
            UiHelper.showCenterText(player, "刻印·回声");
        }
    }

    public static String[] poolFor(boolean eyja) {
        return eyja ? EYJA_SEAL_IDS : MUEL_SEAL_IDS;
    }

    public static String rollTarget(boolean eyja) {
        return DebuffIntentHelper.rollTarget(poolFor(eyja));
    }

    private static void addPower(AbstractPlayer player, AbstractMonster monster,
                                 com.megacrit.cardcrawl.powers.AbstractPower power) {
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(player, monster, power, power.amount));
    }

    private static void addToBot(AbstractGameAction action) {
        AbstractDungeon.actionManager.addToBottom(action);
    }
}
