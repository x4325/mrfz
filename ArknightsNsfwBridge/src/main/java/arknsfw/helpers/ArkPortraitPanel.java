package arknsfw.helpers;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import liesecore.helpers.NsfwRunStats;
import liesecore.helpers.TextureHelper;
import arknsfw.ArkNsfwMod;

import java.util.HashMap;

/**
 * 战斗立绘面板：显示在角色左侧，按当前皮肤对应官方立绘（膝上），
 * 差分档位随兴奋/衣装状态切换：tier0 平静 / tier1 泛红 / tier2 濡湿 / tier3 大破（占位）。
 */
public final class ArkPortraitPanel {

    private static final HashMap<String, Texture> CACHE = new HashMap<String, Texture>();

    private ArkPortraitPanel() {
    }

    private static Texture load(String key, int skin, int tier) {
        String path = ArkNsfwMod.makeImagePath("portraits/" + key + "_skin" + skin + "_tier" + tier + ".png");
        if (CACHE.containsKey(path)) {
            return CACHE.get(path);
        }
        Texture tex = TextureHelper.getTexture(path);
        CACHE.put(path, tex);
        return tex;
    }

    private static Texture resolve(String key, int skin, int tier) {
        Texture tex = load(key, skin, tier);
        if (tex == null && tier > 0) {
            tex = load(key, skin, 0);
        }
        if (tex == null && skin > 0) {
            tex = load(key, 0, tier);
            if (tex == null) {
                tex = load(key, 0, 0);
            }
        }
        return tex;
    }

    private static int tier() {
        if (ArkExposureHelper.stage() >= 2) {
            return 3;
        }
        int threshold = Math.max(1, NsfwRunStats.getClimaxThreshold());
        int pct = NsfwRunStats.excitement * 100 / threshold;
        if (pct >= 75) return 2;
        if (pct >= 35) return 1;
        return 0;
    }

    public static void render(SpriteBatch sb) {
        String key = ArkCharDebuffs.currentCharKey();
        if (key == null || AbstractDungeon.player == null || AbstractDungeon.currMapNode == null) {
            return;
        }
        AbstractRoom room = AbstractDungeon.getCurrRoom();
        if (room == null || room.phase != AbstractRoom.RoomPhase.COMBAT) {
            return;
        }
        if (AbstractDungeon.screen != AbstractDungeon.CurrentScreen.NONE || AbstractDungeon.isScreenUp) {
            return;
        }
        Texture tex = resolve(key, ArkCharDebuffs.currentSkinIndex(), tier());
        if (tex == null) {
            return;
        }
        float h = 460.0F * Settings.scale;
        float w = h * 450.0F / 630.0F;
        float x = 10.0F * Settings.scale;
        float y = AbstractDungeon.floorY - 60.0F * Settings.scale;
        sb.setColor(1.0F, 1.0F, 1.0F, 0.95F);
        sb.draw(tex, x, y, w, h);
        sb.setColor(Color.WHITE);
        // 状态行：衣装 + 兴奋
        int threshold = Math.max(1, NsfwRunStats.getClimaxThreshold());
        String info = ArkExposureHelper.stageName() + "    兴奋 " + NsfwRunStats.excitement + "/" + threshold;
        FontHelper.renderFontLeftTopAligned(sb, FontHelper.tipBodyFont, info,
                x + 6.0F * Settings.scale, y - 4.0F * Settings.scale, Settings.CREAM_COLOR);
    }
}
