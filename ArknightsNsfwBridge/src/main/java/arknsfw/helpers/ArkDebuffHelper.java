package arknsfw.helpers;

import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import liesecore.helpers.PowerHelper;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public final class ArkDebuffHelper {

    private static final Set<String> LOCKED = new HashSet<>();

    private ArkDebuffHelper() {
    }

    public static void resetForNewRun() {
        LOCKED.clear();
    }

    public static void lockPermanent(String powerId) {
        if (powerId != null) {
            LOCKED.add(powerId);
        }
    }

    public static boolean isLocked(String powerId) {
        return powerId != null && LOCKED.contains(powerId);
    }

    public static boolean isLocked(AbstractPower power) {
        return power != null && isLocked(power.ID);
    }

    public static boolean isRemovable(AbstractPower power) {
        if (power == null || power.type != AbstractPower.PowerType.DEBUFF) {
            return false;
        }
        if (isLocked(power)) {
            return false;
        }
        return power instanceof arknsfw.powers.AbstractArkDebuffPower;
    }

    public static void apply(AbstractPlayer player, AbstractPower power) {
        PowerHelper.apply(player, power);
    }

    /** 获得层数并永久刻印该 debuff。 */
    public static void applyAndLock(AbstractPlayer player, AbstractPower power) {
        apply(player, power);
        lockPermanent(power.ID);
    }

    public static boolean playerHasDebuff(String powerId) {
        return AbstractDungeon.player != null && AbstractDungeon.player.hasPower(powerId);
    }

    public static int getDebuffAmount(String powerId) {
        if (!playerHasDebuff(powerId)) {
            return 0;
        }
        return AbstractDungeon.player.getPower(powerId).amount;
    }

    /** 事件等场景：随机移除一层未刻印的方舟 debuff。 */
    public static boolean removeRandomDebuff() {
        if (AbstractDungeon.player == null) {
            return false;
        }
        ArrayList<AbstractPower> pool = new ArrayList<>();
        for (AbstractPower p : AbstractDungeon.player.powers) {
            if (isRemovable(p)) {
                pool.add(p);
            }
        }
        if (pool.isEmpty()) {
            return false;
        }
        AbstractPower pick = pool.get(AbstractDungeon.cardRandomRng.random(pool.size() - 1));
        if (AbstractDungeon.getCurrRoom() != null
                && AbstractDungeon.getCurrRoom().phase == AbstractRoom.RoomPhase.COMBAT) {
            AbstractDungeon.actionManager.addToBottom(
                    new RemoveSpecificPowerAction(AbstractDungeon.player, AbstractDungeon.player, pick));
        } else if (AbstractDungeon.player.powers.contains(pick)) {
            AbstractDungeon.player.powers.remove(pick);
            pick.onRemove();
            AbstractDungeon.player.updatePowers();
        }
        return true;
    }
}
