package arknsfw.helpers;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.esotericsoftware.spine.Bone;
import com.esotericsoftware.spine.Skeleton;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.creatures.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import liesecore.helpers.NsfwRunStats;
import liesecore.helpers.TextureHelper;
import arknsfw.ArkNsfwMod;

import java.lang.reflect.Field;
import java.util.ArrayList;

/**
 * 战斗小人爱心眼：兴奋濒临高潮或淫乱状态时，在骨骼的眼球骨骼位置叠加搏动的心形。
 * 通过反射读取 AbstractCreature.skeleton，按骨骼名定位双眼，不修改任何贴图集。
 */
public final class ArkBattleHeartEyes {

    private static Texture heartTex;
    private static Field skeletonField;
    private static boolean fieldSearched = false;

    private static Object cachedPlayer;
    private static final ArrayList<Bone> EYE_BONES = new ArrayList<Bone>();
    private static float time = 0.0F;

    private ArkBattleHeartEyes() {
    }

    public static void render(SpriteBatch sb) {
        try {
            renderInner(sb);
        } catch (Throwable ignored) {
        }
    }

    private static void renderInner(SpriteBatch sb) {
        if (AbstractDungeon.player == null || AbstractDungeon.currMapNode == null
                || ArkCharDebuffs.currentCharKey() == null) {
            return;
        }
        AbstractRoom room = AbstractDungeon.getCurrRoom();
        if (room == null || room.phase != AbstractRoom.RoomPhase.COMBAT || AbstractDungeon.isScreenUp) {
            return;
        }
        boolean lewd = AbstractDungeon.player.hasPower(arknsfw.powers.LewdTrancePower.POWER_ID);
        int threshold = Math.max(1, LieseCompat.climaxThreshold());
        boolean nearClimax = NsfwRunStats.excitement * 100 / threshold >= 85;
        if (!lewd && !nearClimax) {
            return;
        }
        Skeleton skeleton = playerSkeleton();
        if (skeleton == null) {
            return;
        }
        if (heartTex == null) {
            heartTex = TextureHelper.getTexture(ArkNsfwMod.makeImagePath("ui/heart.png"));
            if (heartTex == null) {
                return;
            }
        }
        if (cachedPlayer != AbstractDungeon.player || EYE_BONES.isEmpty()) {
            cachedPlayer = AbstractDungeon.player;
            findEyeBones(skeleton);
        }
        time += com.badlogic.gdx.Gdx.graphics.getDeltaTime();
        float pulse = 1.0F + 0.18F * MathUtils.sin(time * 7.0F);
        float size = 13.0F * Settings.scale * pulse;
        sb.setColor(1.0F, 0.55F, 0.75F, 0.92F);
        for (Bone bone : EYE_BONES) {
            float bx = bone.getWorldX();
            float by = bone.getWorldY();
            sb.draw(heartTex, bx - size / 2.0F, by - size / 2.0F, size, size);
        }
        sb.setColor(1.0F, 1.0F, 1.0F, 1.0F);
    }

    private static void findEyeBones(Skeleton skeleton) {
        EYE_BONES.clear();
        ArrayList<Bone> fallback = new ArrayList<Bone>();
        for (Bone bone : skeleton.getBones()) {
            String n = bone.getData().getName().toLowerCase();
            if (n.contains("eyeball")) {
                EYE_BONES.add(bone);
            } else if (n.contains("eye")
                    && !n.contains("brow") && !n.contains("lash") && !n.contains("white")
                    && !n.contains("light") && !n.contains("close") && !n.contains("smile")
                    && !n.contains("cut")) {
                fallback.add(bone);
            }
        }
        if (EYE_BONES.isEmpty()) {
            EYE_BONES.addAll(fallback);
        }
        while (EYE_BONES.size() > 2) {
            EYE_BONES.remove(EYE_BONES.size() - 1);
        }
    }

    private static Skeleton playerSkeleton() {
        if (!fieldSearched) {
            fieldSearched = true;
            try {
                Field f = AbstractCreature.class.getDeclaredField("skeleton");
                f.setAccessible(true);
                skeletonField = f;
            } catch (Throwable ignored) {
            }
        }
        if (skeletonField == null) {
            return null;
        }
        try {
            Object v = skeletonField.get(AbstractDungeon.player);
            return v instanceof Skeleton ? (Skeleton) v : null;
        } catch (Throwable t) {
            return null;
        }
    }
}
