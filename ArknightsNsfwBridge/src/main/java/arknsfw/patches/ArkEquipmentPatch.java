package arknsfw.patches;

import com.evacipated.cardcrawl.modthespire.lib.ByRef;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePrefixPatch;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import liesecore.helpers.NsfwRunStats;
import arknsfw.relics.equipment.BodyCrestRelic;
import arknsfw.relics.equipment.ChastityBeltRelic;

/** 拘束装备的数值修正：贞操带受孕减半、淫纹拓印兴奋获取+25%。 */
public class ArkEquipmentPatch {

    @SpirePatch(clz = NsfwRunStats.class, method = "addConception", paramtypez = {int.class, boolean.class})
    public static class BeltHalvesConception {
        @SpirePrefixPatch
        public static void prefix(@ByRef int[] amount, boolean inside) {
            if (amount[0] > 0 && AbstractDungeon.player != null
                    && AbstractDungeon.player.hasRelic(ChastityBeltRelic.ID)) {
                amount[0] = Math.max(1, amount[0] / 2);
            }
        }
    }

    @SpirePatch(clz = NsfwRunStats.class, method = "addExcitement", paramtypez = {int.class})
    public static class CrestBoostsExcitement {
        @SpirePrefixPatch
        public static void prefix(@ByRef int[] amount) {
            if (amount[0] > 0 && AbstractDungeon.player != null
                    && AbstractDungeon.player.hasRelic(BodyCrestRelic.ID)) {
                amount[0] = amount[0] + Math.max(1, amount[0] / 4);
            }
        }
    }
}
