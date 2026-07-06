package arknsfw.patches;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePrefixPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireReturn;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.powers.AbstractPower;
import arknsfw.helpers.ArkDebuffHelper;

import java.lang.reflect.Field;

public class ArkDebuffLockPatch {

    private static String readString(Object target, String fieldName) {
        try {
            Field f = target.getClass().getDeclaredField(fieldName);
            f.setAccessible(true);
            return (String) f.get(target);
        } catch (ReflectiveOperationException e) {
            return null;
        }
    }

    private static AbstractPower readPower(Object target) {
        try {
            Field f = target.getClass().getDeclaredField("powerInstance");
            f.setAccessible(true);
            return (AbstractPower) f.get(target);
        } catch (ReflectiveOperationException e) {
            return null;
        }
    }

    private static boolean shouldBlock(String id, AbstractPower instance) {
        if (ArkDebuffHelper.isLocked(id)) {
            return true;
        }
        return ArkDebuffHelper.isLocked(instance);
    }

    @SpirePatch(clz = RemoveSpecificPowerAction.class, method = "update")
    public static class BlockRemove {
        @SpirePrefixPatch
        public static SpireReturn Prefix(RemoveSpecificPowerAction __instance) {
            if (shouldBlock(readString(__instance, "powerToRemove"), readPower(__instance))) {
                __instance.isDone = true;
                return SpireReturn.Return(null);
            }
            return SpireReturn.Continue();
        }
    }

    @SpirePatch(clz = ReducePowerAction.class, method = "update")
    public static class BlockReduce {
        @SpirePrefixPatch
        public static SpireReturn Prefix(ReducePowerAction __instance) {
            if (shouldBlock(readString(__instance, "powerID"), readPower(__instance))) {
                __instance.isDone = true;
                return SpireReturn.Return(null);
            }
            return SpireReturn.Continue();
        }
    }

    @SpirePatch(clz = liesecore.helpers.DebuffHelper.class, method = "isRemovableDebuff", paramtypez = {AbstractPower.class})
    public static class ExtendRemovable {
        @SpirePrefixPatch
        public static SpireReturn<Boolean> Prefix(AbstractPower p) {
            if (ArkDebuffHelper.isRemovable(p)) {
                return SpireReturn.Return(true);
            }
            if (p instanceof arknsfw.powers.AbstractArkDebuffPower && ArkDebuffHelper.isLocked(p)) {
                return SpireReturn.Return(false);
            }
            return SpireReturn.Continue();
        }
    }
}
