package arknsfw.patches;

import com.evacipated.cardcrawl.modthespire.lib.ByRef;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePrefixPatch;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.rooms.EventRoom;
import liesecore.helpers.NsfwRunStats;

/** 祭坛诅咒：持有对应诅咒遗物时，事件房间中获得的受孕度翻倍。 */
public class ArkAltarCursePatch {

    private static final String[] ALTAR_CURSE_IDS = {
            arknsfw.relics.curses.highmore.HighmoreAltarCurseRelic.ID,
            arknsfw.relics.curses.scene.SceneAltarCurseRelic.ID,
            arknsfw.relics.curses.archetto.ArchettoAltarCurseRelic.ID,
            arknsfw.relics.curses.haruka.HarukaAltarCurseRelic.ID,
            arknsfw.relics.curses.nymph.NymphAltarCurseRelic.ID,
    };

    private static boolean active() {
        if (AbstractDungeon.player == null || AbstractDungeon.currMapNode == null) {
            return false;
        }
        if (!(AbstractDungeon.getCurrRoom() instanceof EventRoom)) {
            return false;
        }
        for (String id : ALTAR_CURSE_IDS) {
            if (AbstractDungeon.player.hasRelic(id)) {
                return true;
            }
        }
        return false;
    }

    @SpirePatch(clz = NsfwRunStats.class, method = "addConception", paramtypez = {int.class, boolean.class})
    public static class DoubleEventConception {
        @SpirePrefixPatch
        public static void Prefix(@ByRef int[] amount, boolean creampie) {
            if (amount[0] > 0 && active()) {
                amount[0] *= 2;
            }
        }
    }
}
