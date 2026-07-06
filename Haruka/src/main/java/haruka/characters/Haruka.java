package haruka.characters;

import basemod.abstracts.CustomPlayer;
import basemod.animations.SpineAnimation;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.esotericsoftware.spine.AnimationState;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.EnergyManager;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.helpers.ScreenShake;
import com.megacrit.cardcrawl.localization.CharacterStrings;
import com.megacrit.cardcrawl.screens.CharSelectInfo;
import haruka.HarukaMod;
import haruka.core.ClassEnum;
import haruka.core.ColorEnum;
import haruka.cards.HarukaStrike;
import haruka.cards.HarukaDefend;
import haruka.cards.HarukaSparkKick;
import haruka.cards.HarukaFireworkPrep;
import haruka.screens.SkinSelectScreen;
import haruka.relics.HarukaStarterRelic;

import java.util.ArrayList;

public class Haruka extends CustomPlayer {

    public static final String ID = HarukaMod.makeID("Haruka");

    // Eyja/Muel style: orb textures + vfx + layer speed for the multi-layer energy orb.
    // CustomEnergyOrb requires orbTextures.length ODD (1 baseLayer + N*2 paired layers).
    // Eyja uses 11 (1 base + 5*2). LAYER_SPEED length = 10 (per-layer speed).
    public static final String[] orbTextures = new String[] {
        HarukaMod.imgPath("orbs/1.png"),  // baseLayer
        HarukaMod.imgPath("orbs/2.png"), HarukaMod.imgPath("orbs/3.png"),
        HarukaMod.imgPath("orbs/4.png"), HarukaMod.imgPath("orbs/5.png"),
        HarukaMod.imgPath("orbs/6.png"), HarukaMod.imgPath("orbs/1.png"),
        HarukaMod.imgPath("orbs/2.png"), HarukaMod.imgPath("orbs/3.png"),
        HarukaMod.imgPath("orbs/4.png"), HarukaMod.imgPath("orbs/5.png"),
    };
    public static final String VFX = HarukaMod.imgPath("orbs/6.png");
    public static final float[] LAYER_SPEED = new float[] {
        -32.0f, -16.0f, -8.0f, 8.0f, 16.0f,
        -32.0f, -16.0f, -8.0f, 8.0f, 16.0f,
    };

    // Eyja/Muel style: shoulder path constant
    public static final String IMG_SHOULDER = HarukaMod.imgPath("char/char_shoulder.png");

    private static final String SPINE_ATLAS = HarukaMod.imgPath("char/idle/char_4202_haruka.atlas");
    private static final String SPINE_JSON = HarukaMod.imgPath("char/idle/char_4202_haruka.json");
    private static final float SPINE_SCALE = 1.75f;

    private static SpineAnimation spineAnimation() {
        return new SpineAnimation(SPINE_ATLAS, SPINE_JSON, SPINE_SCALE);
    }
    private static final int ENERGY_PER_TURN = 3;
    private static final int STARTING_HP = 72;
    private static final int MAX_HP = 72;
    private static final int STARTING_GOLD = 99;
    private static final int HAND_SIZE = 5;
    private static final int ASCENSION_MAX_HP_LOSS = 5;

    private static CharacterStrings strings() {
        return CardCrawlGame.languagePack.getCharacterString(ID);
    }

    private static String name(int i, String fb) {
        CharacterStrings s = strings();
        return (s == null || s.NAMES == null || s.NAMES.length <= i) ? fb : s.NAMES[i];
    }

    private static String text(int i, String fb) {
        CharacterStrings s = strings();
        return (s == null || s.TEXT == null || s.TEXT.length <= i) ? fb : s.TEXT[i];
    }

    public Haruka(String name) {
        // 7-arg CustomPlayer: (name, playerClass, orbTextures[], orbVfx, layerSpeed, shoulder2, shoulder)
        // Shoulder paths passed as null here; real shoulder loaded in initializeClass + loadCharacterImages.
        // Spine via BaseMod SpineAnimation (.json); binary .skel converted at build time.
        super(name, ClassEnum.HARUKA, orbTextures, VFX, LAYER_SPEED, null, null);
        dialogX = (drawX * Settings.scale) + 0 * Settings.scale;
        dialogY = (drawY * Settings.scale) + 240 * Settings.scale;
        initializeClass(
                null,
                HarukaMod.imgPath("char/shoulder_skin0.png"),
                HarukaMod.imgPath("char/shoulder_skin0.png"),
                "images/characters/ironclad/corpse.png",
                new CharSelectInfo(
                        getStaticLocalizedCharacterName(),
                        getStaticFlavorText(),
                        STARTING_HP, MAX_HP, 0, STARTING_GOLD, HAND_SIZE,
                        this, getStartingRelics(), getStartingDeck(), false
                ),
                20.0f, -15.0f, 220.0f, 290.0f,
                new EnergyManager(ENERGY_PER_TURN)
        );
        loadCharacterImages();
        refreshSkin();
    }

    public void refreshSkin() {
        int idx = SkinSelectScreen.getSkin().index;
        loadAnimation(
                SkinSelectScreen.atlasPath(idx),
                SkinSelectScreen.jsonPath(idx),
                SPINE_SCALE
        );
        setupSpineAnimations();
        // 营火/肩部立绘固定使用精一立绘
        this.shoulderImg = shoulderTex(0);
        this.shoulder2Img = this.shoulderImg;
        this.img = this.shoulderImg;
    }


    private void setupSpineAnimations() {
        if (this.state == null || this.stateData == null) {
            return;
        }
        mixIfPresent("Idle", "Attack", 0.1f);
        mixIfPresent("Attack", "Idle", 0.1f);
        mixIfPresent("Die", "Idle", 0.2f);
        String idleName = this.stateData.getSkeletonData().findAnimation("Idle") != null ? "Idle" : "Default";
        AnimationState.TrackEntry idle = this.state.setAnimation(0, idleName, true);
        if (idle != null) {
            idle.setTimeScale(0.6f);
        }
    }

    private void mixIfPresent(String from, String to, float duration) {
        com.esotericsoftware.spine.SkeletonData data = this.stateData.getSkeletonData();
        if (data.findAnimation(from) != null && data.findAnimation(to) != null) {
            this.stateData.setMix(from, to, duration);
        }
    }

    private static final java.util.HashMap<String, com.badlogic.gdx.graphics.Texture> SHOULDER_CACHE =
            new java.util.HashMap<String, com.badlogic.gdx.graphics.Texture>();

    private static com.badlogic.gdx.graphics.Texture shoulderTex(int skinIndex) {
        String path = HarukaMod.imgPath("char/shoulder_skin" + skinIndex + ".png");
        com.badlogic.gdx.graphics.Texture tex = SHOULDER_CACHE.get(path);
        if (tex == null) {
            try {
                tex = new com.badlogic.gdx.graphics.Texture(com.badlogic.gdx.Gdx.files.internal(path));
            } catch (Exception e) {
                tex = ImageMaster.loadImage(HarukaMod.imgPath("char/shoulder_skin0.png"));
            }
            SHOULDER_CACHE.put(path, tex);
        }
        return tex;
    }

    private void loadCharacterImages() {
        this.shoulderImg = shoulderTex(0);
        this.shoulder2Img = this.shoulderImg;
        this.corpseImg = ImageMaster.loadImage("images/characters/ironclad/corpse.png");
        this.img = this.shoulderImg;
    }

    @Override
                                public ArrayList<String> getStartingDeck() {
        ArrayList<String> d = new ArrayList<>();
        d.add(HarukaStrike.ID); d.add(HarukaStrike.ID); d.add(HarukaStrike.ID); d.add(HarukaStrike.ID);
        d.add(HarukaDefend.ID); d.add(HarukaDefend.ID); d.add(HarukaDefend.ID); d.add(HarukaDefend.ID);
        d.add(HarukaSparkKick.ID);
        d.add(HarukaFireworkPrep.ID);
        return d;
    }

    @Override
    public ArrayList<String> getStartingRelics() {
        ArrayList<String> r = new ArrayList<>();
        r.add(HarukaStarterRelic.ID);
        return r;
    }

    @Override
    public CharSelectInfo getLoadout() {
        return new CharSelectInfo(getStaticLocalizedCharacterName(), getStaticFlavorText(),
                STARTING_HP, MAX_HP, 0, STARTING_GOLD, HAND_SIZE,
                this, getStartingRelics(), getStartingDeck(), false);
    }

    @Override
    public AbstractCard.CardColor getCardColor() { return ColorEnum.HARUKA_COLOR; }

    @Override
    public AbstractCard getStartCardForEvent() { return new HarukaStrike(); }

    @Override
    public Color getCardRenderColor() { return new Color(0.90f,0.55f,0.65f, 1.0f); }

    @Override
    public Color getCardTrailColor() { return new Color(0.90f,0.55f,0.65f, 1.0f); }

    @Override
    public int getAscensionMaxHPLoss() { return ASCENSION_MAX_HP_LOSS; }

    @Override
    public BitmapFont getEnergyNumFont() { return FontHelper.energyNumFontRed; }

    @Override
    public String getLocalizedCharacterName() { return name(0, "遥"); }

    public static String getStaticLocalizedCharacterName() { return name(0, "遥"); }

    public static String getStaticFlavorText() { return text(0, "东国的护佑者，花火在指尖迸发。"); }

    @Override
    public AbstractPlayer newInstance() { return new Haruka(name(0, "遥")); }

    @Override
    public String getSpireHeartText() { return text(1, "你点燃了决心。"); }

    @Override
    public Color getSlashAttackColor() { return new Color(0.90f,0.55f,0.65f, 1.0f); }

    @Override
    public AbstractGameAction.AttackEffect[] getSpireHeartSlashEffect() {
        return new AbstractGameAction.AttackEffect[]{ AbstractGameAction.AttackEffect.SLASH_HEAVY };
    }

    @Override
    public String getVampireText() { return text(2, "这感觉……不对。"); }

    @Override
    public String getCustomModeCharacterButtonSoundKey() { return "ATTACK_HEAVY"; }

    @Override
    public String getTitle(AbstractPlayer.PlayerClass playerClass) { return name(1, "护佑者"); }

    @Override
    public String getPortraitImageName() { return HarukaMod.imgPath("char/char_shoulder.png"); }

    @Override
    public void doCharSelectScreenSelectEffect() {
        CardCrawlGame.screenShake.shake(ScreenShake.ShakeIntensity.MED, ScreenShake.ShakeDur.SHORT, false);
        CardCrawlGame.sound.playA("ATTACK_HEAVY", -0.3f);
    }



    // ================= 骨骼动画触发 =================

    private String idleAnimName() {
        if (this.stateData == null) {
            return "Idle";
        }
        return this.stateData.getSkeletonData().findAnimation("Idle") != null ? "Idle" : "Default";
    }

    /** 播放一次指定动作，结束后自动回到待机（含混合过渡）。 */
    public void playCharAnimation(String name) {
        if (this.state == null || this.stateData == null || name == null) {
            return;
        }
        if (this.stateData.getSkeletonData().findAnimation(name) == null) {
            return;
        }
        AnimationState.TrackEntry e = this.state.setAnimation(0, name, false);
        e.setTimeScale(1.0f);
        AnimationState.TrackEntry idle = this.state.addAnimation(0, idleAnimName(), true, 0.0f);
        idle.setTimeScale(0.6f);
    }

    @Override
    public void useFastAttackAnimation() {
        super.useFastAttackAnimation();
        playCharAnimation("Attack");
    }

    /** 登场动作（战斗开始时由 Mod 调用）。 */
    public void playIntroAnimation() {
        playCharAnimation("Start");
    }

    @Override
    public void damage(com.megacrit.cardcrawl.cards.DamageInfo info) {
        int before = this.currentHealth;
        super.damage(info);
        if (before > 0 && this.currentHealth <= 0) {
            playCharAnimation("Die");
        }
    }
}
