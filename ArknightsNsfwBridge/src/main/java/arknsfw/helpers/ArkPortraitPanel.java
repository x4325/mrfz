package arknsfw.helpers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import liesecore.helpers.NsfwRunStats;
import liesecore.helpers.TextureHelper;
import arknsfw.ArkNsfwMod;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

/**
 * 战斗立绘面板：角色左侧官方立绘（膝上、随皮肤），四档差分随兴奋/衣装切换。
 * 附动态情欲演出：呼吸起伏、随兴奋脉动的粉雾、上浮的心形粒子。
 */
public final class ArkPortraitPanel {

    private static final HashMap<String, Texture> CACHE = new HashMap<String, Texture>();
    private static Texture heartTex;

    // 心形粒子: x, y, vy, sway-phase, life, scale
    private static final ArrayList<float[]> HEARTS = new ArrayList<float[]>();
    private static float time = 0.0F;
    private static float spawnTimer = 0.0F;

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
        int tier = tier();
        Texture tex = resolve(key, ArkCharDebuffs.currentSkinIndex(), tier);
        if (tex == null) {
            return;
        }
        float dt = Gdx.graphics.getDeltaTime();
        time += dt;

        float h = 460.0F * Settings.scale;
        float w = h * 450.0F / 630.0F;
        float x = 10.0F * Settings.scale;
        float y = AbstractDungeon.floorY - 60.0F * Settings.scale;

        // 呼吸起伏：档位越高呼吸越急促、幅度越大
        float breathFreq = 1.6F + tier * 0.9F;
        float breathAmp = tier == 0 ? 0.002F : 0.004F + tier * 0.003F;
        float breath = 1.0F + breathAmp * MathUtils.sin(time * breathFreq * MathUtils.PI2 * 0.5F);
        float bw = w * breath;
        float bh = h * breath;

        // 脉动粉雾（tier2+）：画在立绘之下
        if (tier >= 2) {
            float pulse = 0.10F + 0.08F * (0.5F + 0.5F * MathUtils.sin(time * (2.0F + tier) * 1.7F));
            sb.setColor(1.0F, 0.35F, 0.55F, pulse);
            sb.draw(tex, x - 8.0F * Settings.scale, y - 8.0F * Settings.scale,
                    bw + 16.0F * Settings.scale, bh + 16.0F * Settings.scale);
        }

        sb.setColor(1.0F, 1.0F, 1.0F, 0.95F);
        sb.draw(tex, x, y, bw, bh);

        // 心形粒子
        renderHearts(sb, dt, tier, x, y, bw, bh);

        sb.setColor(Color.WHITE);
        int threshold = Math.max(1, NsfwRunStats.getClimaxThreshold());
        String info = ArkExposureHelper.stageName() + "    兴奋 " + NsfwRunStats.excitement + "/" + threshold;
        FontHelper.renderFontLeftTopAligned(sb, FontHelper.tipBodyFont, info,
                x + 6.0F * Settings.scale, y - 4.0F * Settings.scale, Settings.CREAM_COLOR);
    }

    private static void renderHearts(SpriteBatch sb, float dt, int tier, float x, float y, float w, float h) {
        if (heartTex == null) {
            heartTex = TextureHelper.getTexture(ArkNsfwMod.makeImagePath("ui/heart.png"));
            if (heartTex == null) {
                return;
            }
        }
        // 生成频率：tier0 无，tier1 偶尔，tier2 持续，tier3 密集
        float interval = tier <= 0 ? -1.0F : tier == 1 ? 1.6F : tier == 2 ? 0.7F : 0.35F;
        if (interval > 0) {
            spawnTimer += dt;
            while (spawnTimer >= interval) {
                spawnTimer -= interval;
                HEARTS.add(new float[]{
                        x + MathUtils.random(w * 0.2F, w * 0.85F),
                        y + MathUtils.random(h * 0.25F, h * 0.7F),
                        MathUtils.random(26.0F, 46.0F) * Settings.scale,
                        MathUtils.random(0.0F, MathUtils.PI2),
                        0.0F,
                        MathUtils.random(0.5F, 1.0F)});
                if (HEARTS.size() > 40) {
                    HEARTS.remove(0);
                }
            }
        }
        Iterator<float[]> it = HEARTS.iterator();
        while (it.hasNext()) {
            float[] p = it.next();
            p[4] += dt;
            if (p[4] >= 2.2F) {
                it.remove();
                continue;
            }
            float lifeT = p[4] / 2.2F;
            float px = p[0] + 10.0F * Settings.scale * MathUtils.sin(p[3] + p[4] * 3.0F);
            float py = p[1] + p[2] * p[4];
            float alpha = lifeT < 0.15F ? lifeT / 0.15F : 1.0F - (lifeT - 0.15F) / 0.85F;
            float size = 26.0F * Settings.scale * p[5] * (1.0F + 0.15F * MathUtils.sin(p[4] * 8.0F));
            sb.setColor(1.0F, 1.0F, 1.0F, Math.max(0.0F, alpha * 0.85F));
            sb.draw(heartTex, px - size / 2.0F, py - size / 2.0F, size, size);
        }
        sb.setColor(Color.WHITE);
    }
}
