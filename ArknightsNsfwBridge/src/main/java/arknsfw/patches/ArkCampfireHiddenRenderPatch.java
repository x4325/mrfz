package arknsfw.patches;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePrefixPatch;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.rooms.CampfireUI;

/**
 * hidden=true 时 CampfireUI 不调用 player.render；MRFZ 骨骼角色在此补画坐姿 shoulder。
 */
@SpirePatch(clz = CampfireUI.class, method = "render")
public class ArkCampfireHiddenRenderPatch {

    @SpirePrefixPatch
    public static void Prefix(CampfireUI __instance, SpriteBatch sb) {
        if (!CampfireUI.hidden) {
            return;
        }
        AbstractPlayer player = AbstractDungeon.player;
        if (!ArkCampfireRenderHelper.isSpineCampfireCharacter(player)) {
            return;
        }
        sb.setColor(Color.WHITE);
        player.renderShoulderImg(sb);
    }
}
