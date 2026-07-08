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

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

/**
 * 战斗立绘面板（角色左侧、膝上、精一立绘，不随皮肤）。
 * 兴奋四档差分 × 怀孕三阶段孕肚差分；含呼吸/摇曳/脉动粉雾/心形粒子动态演出。
 * 覆盖七名角色（含艾雅法拉、缪尔赛思，素材取自其自带立绘）。
 */
public final class ArkPortraitPanel {

    private static final HashMap<String, Texture> CACHE = new HashMap<String, Texture>();
    private static final HashMap<String, Texture> CREST_CACHE = new HashMap<String, Texture>();
    private static Texture heartTex;
    private static final ArrayList<float[]> HEARTS = new ArrayList<float[]>();
    private static float time = 0.0F;
    private static float spawnTimer = 0.0F;

    private static Field pregProgressField;
    private static boolean pregFieldSearched = false;

    private ArkPortraitPanel() {
    }

    private static String portraitKey() {
        if (ArkCharacterSetup.isEyjaRun()) return "eyja";
        if (ArkCharacterSetup.isMuelsyseRun()) return "muel";
        return ArkCharDebuffs.currentCharKey();
    }

    private static Texture loadCrest(String key) {
        if (CREST_CACHE.containsKey(key)) {
            return CREST_CACHE.get(key);
        }
        Texture tex = TextureHelper.tryGetTexture(ArkNsfwMod.makeImagePath("crests/" + key + ".png"));
        if (tex != null) {
            CREST_CACHE.put(key, tex);
        }
        return tex;
    }

    private static boolean hasRelic(String id) {
        return AbstractDungeon.player != null && AbstractDungeon.player.hasRelic(id);
    }

    private static void drawOverlay(SpriteBatch sb, String key, String item,
                                    float x, float y, float w, float h, float alpha) {
        Texture t = load("overlays/" + key + "_" + item);
        if (t != null) {
            sb.setColor(1.0F, 1.0F, 1.0F, alpha);
            sb.draw(t, x, y, w, h);
            sb.setColor(1.0F, 1.0F, 1.0F, 0.95F);
        }
    }

    /** 淫纹浮现条件：怀孕，或本角色专属印记已被刻印锁定。 */
    private static boolean crestVisible(String key) {
        if (NsfwRunStats.pregnant) {
            return true;
        }
        return lockVisible(key);
    }

    /** 专属印记是否已刻印锁定。 */
    private static boolean lockVisible(String key) {
        if ("eyja".equals(key)) {
            return ArkDebuffHelper.isLocked(arknsfw.powers.eyja.VolcanicFlushPower.POWER_ID)
                    || ArkDebuffHelper.isLocked(arknsfw.powers.eyja.AshShamePower.POWER_ID)
                    || ArkDebuffHelper.isLocked(arknsfw.powers.eyja.CoreStrainPower.POWER_ID);
        }
        if ("muel".equals(key)) {
            return ArkDebuffHelper.isLocked(arknsfw.powers.muel.BubbleGagPower.POWER_ID)
                    || ArkDebuffHelper.isLocked(arknsfw.powers.muel.LeakPower.POWER_ID)
                    || ArkDebuffHelper.isLocked(arknsfw.powers.muel.CloneEchoPower.POWER_ID);
        }
        if ("highmore".equals(key)) return ArkDebuffHelper.isLocked(arknsfw.powers.highmore.TideBrandPower.POWER_ID);
        if ("scene".equals(key)) return ArkDebuffHelper.isLocked(arknsfw.powers.scene.ExposedLensPower.POWER_ID);
        if ("archetto".equals(key)) return ArkDebuffHelper.isLocked(arknsfw.powers.archetto.TremblingGripPower.POWER_ID);
        if ("haruka".equals(key)) return ArkDebuffHelper.isLocked(arknsfw.powers.haruka.LingeringHeatPower.POWER_ID);
        if ("nymph".equals(key)) return ArkDebuffHelper.isLocked(arknsfw.powers.nymph.HeartGnawPower.POWER_ID);
        return false;
    }

    private static Texture load(String name) {
        String path = ArkNsfwMod.makeImagePath("portraits/" + name + ".png");
        if (CACHE.containsKey(path)) {
            return CACHE.get(path);
        }
        Texture tex = TextureHelper.tryGetTexture(path);
        if (tex != null) {
            CACHE.put(path, tex);
        }
        return tex;
    }

    /** 妊娠阶段 1..3；未孕返回 0。优先反射读取 liesecore 进度，失败按幕数。 */
    private static int pregStage() {
        if (!NsfwRunStats.pregnant) {
            return 0;
        }
        int progress = -1;
        if (!pregFieldSearched) {
            pregFieldSearched = true;
            for (String name : new String[]{"pregnancyProgress", "pregnancy", "pregProgress"}) {
                try {
                    Field f = NsfwRunStats.class.getDeclaredField(name);
                    if (f.getType() == int.class) {
                        f.setAccessible(true);
                        pregProgressField = f;
                        break;
                    }
                } catch (Exception ignored) {
                }
            }
        }
        if (pregProgressField != null) {
            try {
                progress = pregProgressField.getInt(null);
            } catch (Exception ignored) {
            }
        }
        if (progress >= 0) {
            if (progress >= 67) return 3;
            if (progress >= 34) return 2;
            return 1;
        }
        int act = Math.max(1, AbstractDungeon.actNum);
        return Math.min(3, act);
    }

    private static int tier() {
        int threshold = Math.max(1, LieseCompat.climaxThreshold());
        int pct = NsfwRunStats.excitement * 100 / threshold;
        if (pct >= 85) return 3;
        if (pct >= 60) return 2;
        if (pct >= 30) return 1;
        return 0;
    }

    private static Texture resolve(String key, int preg, int tier) {
        String base = preg > 0 ? key + "_preg" + preg : key;
        Texture tex = load(base + "_tier" + tier);
        if (tex == null && tier > 0) {
            tex = load(base + "_tier0");
        }
        if (tex == null && preg > 0) {
            tex = load(key + "_tier" + tier);
            if (tex == null) {
                tex = load(key + "_tier0");
            }
        }
        return tex;
    }

    public static void render(SpriteBatch sb) {
        try {
            renderInner(sb);
        } catch (Throwable ignored) {
            // 任何异常/Error 都不允许拖垮整帧渲染
        }
    }

    private static void renderInner(SpriteBatch sb) {
        String key = portraitKey();
        if (key == null || AbstractDungeon.player == null || AbstractDungeon.currMapNode == null) {
            return;
        }
        AbstractRoom room = AbstractDungeon.getCurrRoom();
        if (room == null || room.phase != AbstractRoom.RoomPhase.COMBAT) {
            return;
        }
        // 注意：不能检查 AbstractDungeon.screen != NONE —— 该字段会保留上一次打开的界面枚举，
        // 导致进过地图/牌组后条件永远为真、立绘永不渲染。只用 isScreenUp 判断。
        if (AbstractDungeon.isScreenUp) {
            return;
        }
        int tier = tier();
        Texture tex = resolve(key, pregStage(), tier);
        if (tex == null) {
            // 素材缺失诊断：提示而不是无声消失
            FontHelper.renderFontLeftTopAligned(sb, FontHelper.tipBodyFont,
                    "[arknsfw] 立绘素材缺失: portraits/" + key + "_tier0.png",
                    20.0F * Settings.scale, AbstractDungeon.floorY + 380.0F * Settings.scale, Settings.CREAM_COLOR);
            return;
        }
        float dt = Gdx.graphics.getDeltaTime();
        time += dt;

        float h = 460.0F * Settings.scale;
        float w = h * 450.0F / 630.0F;
        float x = 10.0F * Settings.scale;
        float y = AbstractDungeon.floorY - 60.0F * Settings.scale;

        // 动态：呼吸起伏 + 高兴奋时轻微摇曳
        float breathFreq = 1.6F + tier * 0.9F;
        float breathAmp = tier == 0 ? 0.002F : 0.004F + tier * 0.003F;
        float breath = 1.0F + breathAmp * MathUtils.sin(time * breathFreq * MathUtils.PI2 * 0.5F);
        float swayX = tier >= 2 ? 3.0F * Settings.scale * MathUtils.sin(time * 1.3F) : 0.0F;
        float bob = tier >= 1 ? 2.0F * Settings.scale * MathUtils.sin(time * breathFreq * 0.8F) : 0.0F;
        float bw = w * breath;
        float bh = h * breath;

        if (tier >= 2) {
            float pulse = 0.10F + 0.08F * (0.5F + 0.5F * MathUtils.sin(time * (2.0F + tier) * 1.7F));
            sb.setColor(1.0F, 0.35F, 0.55F, pulse);
            sb.draw(tex, x + swayX - 8.0F * Settings.scale, y + bob - 8.0F * Settings.scale,
                    bw + 16.0F * Settings.scale, bh + 16.0F * Settings.scale);
        }

        sb.setColor(1.0F, 1.0F, 1.0F, 0.95F);
        sb.draw(tex, x + swayX, y + bob, bw, bh);

        // ---- 拘束差分层（状态触发 + 装备遗物触发），与立绘同画布对齐 ----
        boolean fall = ArkRunProgress.isRoute(ArkRunProgress.Route.FALL);
        boolean locked = lockVisible(key);
        boolean lewd = AbstractDungeon.player.hasPower(arknsfw.powers.LewdTrancePower.POWER_ID);
        float ox = x + swayX, oy = y + bob;

        // 身体装备（遗物驱动）
        if (hasRelic(arknsfw.relics.equipment.RestraintCuffsRelic.ID)) drawOverlay(sb, key, "cuffs", ox, oy, bw, bh, 1.0F);
        if (hasRelic(arknsfw.relics.equipment.ChastityBeltRelic.ID)) drawOverlay(sb, key, "belt", ox, oy, bw, bh, 1.0F);
        if (hasRelic(arknsfw.relics.equipment.LaceGarterRelic.ID)) drawOverlay(sb, key, "garter", ox, oy, bw, bh, 1.0F);
        if (hasRelic(arknsfw.relics.equipment.VibeEggRelic.ID)) drawOverlay(sb, key, "vibe", ox, oy, bw, bh, 1.0F);
        if (hasRelic(arknsfw.relics.equipment.BodyCrestRelic.ID)) {
            float g = 0.7F + 0.3F * (0.5F + 0.5F * MathUtils.sin(time * 3.0F));
            drawOverlay(sb, key, "bodycrest", ox, oy, bw, bh, g);
        }

        // 项圈类：印记锁定出基础项圈；牵引绳/铃铛铭牌叠加其上
        if (locked || hasRelic(arknsfw.relics.equipment.LeashRelic.ID)
                || hasRelic(arknsfw.relics.equipment.BellTagRelic.ID)) {
            drawOverlay(sb, key, "collar", ox, oy, bw, bh, 1.0F);
        }
        if (hasRelic(arknsfw.relics.equipment.LeashRelic.ID)) drawOverlay(sb, key, "leash", ox, oy, bw, bh, 1.0F);
        if (hasRelic(arknsfw.relics.equipment.BellTagRelic.ID)) drawOverlay(sb, key, "belltag", ox, oy, bw, bh, 1.0F);

        // 绳缚：持有麻绳束缚遗物，或衣装破损/堕落路线
        if (hasRelic(arknsfw.relics.equipment.RopeBindRelic.ID)
                || fall || ArkExposureHelper.stage() >= 1) {
            drawOverlay(sb, key, "rope", ox, oy, bw, bh, 0.96F);
        }

        // 口部：环口枷 > 布口塞 > 状态球口枷（互斥）
        String mouthItem = null;
        if (hasRelic(arknsfw.relics.equipment.RingGagRelic.ID)) mouthItem = "ringgag";
        else if (hasRelic(arknsfw.relics.equipment.ClothGagRelic.ID)) mouthItem = "clothgag";
        else if (fall && tier >= 2) mouthItem = "gag";

        // 眼部：蕾丝眼罩 > 状态黑眼罩 > 爱心眼（互斥）
        String eyeItem = null;
        if (hasRelic(arknsfw.relics.equipment.LaceBlindfoldRelic.ID)) eyeItem = "laceblindfold";
        else if (fall && tier >= 3) eyeItem = "blindfold";

        if (mouthItem != null && eyeItem == null) {
            drawOverlay(sb, key, mouthItem, ox, oy, bw, bh, 1.0F);
        } else if (mouthItem != null) {
            drawOverlay(sb, key, mouthItem, ox, oy, bw, bh, 1.0F);
        }
        if (eyeItem != null) {
            drawOverlay(sb, key, eyeItem, ox, oy, bw, bh, 1.0F);
        } else if (tier >= 3 || lewd) {
            float pulse = 0.75F + 0.25F * (0.5F + 0.5F * MathUtils.sin(time * 6.5F));
            drawOverlay(sb, key, "hearteyes", ox, oy, bw, bh, pulse);
        }

        // 淫纹：下腹位置搏动发光，兴奋越高越亮越快，孕肚阶段随之放大
        if (crestVisible(key)) {
            Texture crest = loadCrest(key);
            if (crest != null) {
                int stage = pregStage();
                float cw = bw * (0.40F + 0.05F * stage);
                float cxp = x + swayX + bw * 0.5F;
                float cyp = y + bob + bh * (1.0F - 0.60F);
                float speed = 1.6F + tier * 1.1F;
                float glow = 0.42F + 0.10F * tier + 0.22F * (0.5F + 0.5F * MathUtils.sin(time * speed * 2.2F));
                float cs = cw * (1.0F + 0.035F * MathUtils.sin(time * speed * 2.2F));
                sb.setColor(1.0F, 1.0F, 1.0F, Math.min(1.0F, glow));
                sb.draw(crest, cxp - cs / 2.0F, cyp - cs / 2.0F, cs, cs);
            }
        }

        renderHearts(sb, dt, tier, x, y, bw, bh);

        sb.setColor(Color.WHITE);
        int threshold = Math.max(1, LieseCompat.climaxThreshold());
        String info = ArkExposureHelper.stageName() + "    兴奋 " + NsfwRunStats.excitement + "/" + threshold;
        if (NsfwRunStats.pregnant) {
            info = "孕·第" + pregStage() + "期    " + info;
        }
        FontHelper.renderFontLeftTopAligned(sb, FontHelper.tipBodyFont, info,
                x + 6.0F * Settings.scale, y - 4.0F * Settings.scale, Settings.CREAM_COLOR);
    }

    private static void renderHearts(SpriteBatch sb, float dt, int tier, float x, float y, float w, float h) {
        if (heartTex == null) {
            heartTex = TextureHelper.tryGetTexture(ArkNsfwMod.makeImagePath("ui/heart.png"));
            if (heartTex == null) {
                return;
            }
        }
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
