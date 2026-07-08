package arknsfw.patches;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePostfixPatch;
import com.megacrit.cardcrawl.rooms.CampfireUI;
import com.megacrit.cardcrawl.rooms.RestRoom;

/** 进入休息点时强制显示营火选项（防止 hidden 卡住）。 */
@SpirePatch(clz = RestRoom.class, method = "onPlayerEntry")
public class ArkRestRoomCampfirePatch {

    @SpirePostfixPatch
    public static void Postfix(RestRoom __instance) {
        if (__instance.campfireUI != null) {
            __instance.campfireUI.reopen();
        } else {
            CampfireUI.hidden = false;
        }
    }
}
