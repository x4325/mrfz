package haruka.screens;

import basemod.BaseMod;
import basemod.abstracts.CustomSavable;
import basemod.interfaces.ISubscriber;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.esotericsoftware.spine.AnimationState;
import com.esotericsoftware.spine.AnimationStateData;
import com.esotericsoftware.spine.Skeleton;
import com.esotericsoftware.spine.SkeletonJson;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.helpers.Hitbox;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.helpers.input.InputHelper;
import com.megacrit.cardcrawl.localization.UIStrings;
import haruka.HarukaMod;
import haruka.characters.Haruka;
import haruka.core.ClassEnum;

import java.util.ArrayList;

public class SkinSelectScreen implements ISubscriber, CustomSavable<Integer> {

    public static SkinSelectScreen Inst;

    public Hitbox leftHb;
    public Hitbox rightHb;

    public TextureAtlas atlas;
    public Skeleton skeleton;
    public AnimationStateData stateData;
    public AnimationState state;

    public String curName = "";
    public String nextName = "";
    public int index = 0;

    private static final String SAVE_KEY = "haruka_skin";
    private static final String UI_ID = HarukaMod.makeID("SkinSelect");
    private static final String CHAR_PREFIX = HarukaMod.imgPath("char/");
    private static final float SPINE_SCALE = 1.75f;

    public static final String[] SKIN_PATHS = {
            "idle/char_4202_haruka",
            "skins/char_4202_haruka_iteration_6/char_4202_haruka_iteration_6",
    };

    private static final ArrayList<Skin> skins = new ArrayList<>();
    private static UIStrings uiStrings;

    static {
        uiStrings = CardCrawlGame.languagePack.getUIString(UI_ID);
        for (int i = 0; i < SKIN_PATHS.length; i++) {
            skins.add(new Skin(i));
        }
    }

    public static class Skin {
        public int index;
        public String name;

        public Skin(int index) {
            this.index = index;
            if (uiStrings != null && uiStrings.TEXT != null && uiStrings.TEXT.length > index + 1) {
                this.name = uiStrings.TEXT[index + 1];
            } else {
                this.name = "Skin " + index;
            }
        }
    }

    public static Skin getSkin() {
        if (Inst == null) {
            return skins.get(0);
        }
        return skins.get(Inst.index);
    }

    public static String atlasPath(int skinIndex) {
        return CHAR_PREFIX + SKIN_PATHS[skinIndex] + ".atlas";
    }

    public static String jsonPath(int skinIndex) {
        return CHAR_PREFIX + SKIN_PATHS[skinIndex] + ".json";
    }

    public SkinSelectScreen() {
        leftHb = new Hitbox(70.0f * Settings.scale, 70.0f * Settings.scale);
        rightHb = new Hitbox(70.0f * Settings.scale, 70.0f * Settings.scale);
        BaseMod.subscribe(this);
        BaseMod.addSaveField(SAVE_KEY, this);
        refresh();
    }

    public void loadAnimation(int skinIndex, float scale) {
        if (this.atlas != null) {
            this.atlas.dispose();
            this.atlas = null;
        }
        String atlasPath = atlasPath(skinIndex);
        String jsonPath = jsonPath(skinIndex);
        this.atlas = new TextureAtlas(Gdx.files.internal(atlasPath));
        SkeletonJson json = new SkeletonJson(this.atlas);
        json.setScale(Settings.renderScale / scale);
        com.esotericsoftware.spine.SkeletonData data = json.readSkeletonData(Gdx.files.internal(jsonPath));
        this.skeleton = new Skeleton(data);
        this.skeleton.setColor(Color.WHITE);
        this.stateData = new AnimationStateData(data);
        this.state = new AnimationState(this.stateData);
        String idle = data.findAnimation("Idle") != null ? "Idle" : "Default";
        this.state.setAnimation(0, idle, true);
    }

    public void refresh() {
        Skin skin = skins.get(this.index);
        this.curName = skin.name;
        loadAnimation(skin.index, SPINE_SCALE);
        this.nextName = skins.get(nextIndex()).name;
        if (AbstractDungeon.player instanceof Haruka) {
            ((Haruka) AbstractDungeon.player).refreshSkin();
        }
    }

    public int prevIndex() {
        return this.index - 1 >= 0 ? this.index - 1 : skins.size() - 1;
    }

    public int nextIndex() {
        return this.index + 1 > skins.size() - 1 ? 0 : this.index + 1;
    }

    public void update() {
        float x = Settings.WIDTH * 0.2f;
        float y = Settings.HEIGHT * 0.73f;
        leftHb.move(x - 200.0f * Settings.scale, y);
        rightHb.move(x + 200.0f * Settings.scale, y);
        updateInput();
    }

    private void updateInput() {
        if (CardCrawlGame.chosenCharacter != ClassEnum.HARUKA) {
            return;
        }
        leftHb.update();
        rightHb.update();
        if (leftHb.clicked) {
            leftHb.clicked = false;
            CardCrawlGame.sound.play("UI_CLICK_1");
            this.index = prevIndex();
            refresh();
        }
        if (rightHb.clicked) {
            rightHb.clicked = false;
            CardCrawlGame.sound.play("UI_CLICK_1");
            this.index = nextIndex();
            refresh();
        }
        if (InputHelper.justClickedLeft) {
            if (leftHb.hovered) {
                leftHb.clickStarted = true;
            }
            if (rightHb.hovered) {
                rightHb.clickStarted = true;
            }
        }
    }

    public void render(SpriteBatch sb) {
        if (CardCrawlGame.chosenCharacter != ClassEnum.HARUKA) {
            return;
        }
        float x = Settings.WIDTH * 0.2f;
        float y = Settings.HEIGHT * 0.73f;
        renderSkin(sb, x, y);
        FontHelper.renderFontCentered(sb, FontHelper.cardTitleFont, uiStrings.TEXT[0],
                x, y + 250.0f * Settings.scale, Color.WHITE, 1.25f);
        Color nextColor = Settings.GOLD_COLOR.cpy();
        nextColor.a /= 2.0f;
        float offset = 100.0f * Settings.scale;
        FontHelper.renderFontCentered(sb, FontHelper.cardTitleFont, curName, x, y, Settings.GOLD_COLOR);
        FontHelper.renderFontCentered(sb, FontHelper.cardTitleFont, nextName,
                x + offset * 1.5f, y - offset * 0.5f, nextColor);
        if (skins.size() > 1) {
            sb.setColor(leftHb.hovered ? Color.LIGHT_GRAY : Color.WHITE);
            sb.draw(ImageMaster.CF_LEFT_ARROW,
                    leftHb.cX - 24.0f, leftHb.cY - 24.0f,
                    24.0f, 24.0f, 48.0f, 48.0f,
                    Settings.scale, Settings.scale, 0.0f,
                    0, 0, 48, 48, false, false);
            sb.setColor(rightHb.hovered ? Color.LIGHT_GRAY : Color.WHITE);
            sb.draw(ImageMaster.CF_RIGHT_ARROW,
                    rightHb.cX - 24.0f, rightHb.cY - 24.0f,
                    24.0f, 24.0f, 48.0f, 48.0f,
                    Settings.scale, Settings.scale, 0.0f,
                    0, 0, 48, 48, false, false);
            rightHb.render(sb);
            leftHb.render(sb);
        }
    }

    public void renderSkin(SpriteBatch sb, float x, float y) {
        if (this.atlas == null || this.skeleton == null || this.state == null) {
            return;
        }
        this.state.update(Gdx.graphics.getDeltaTime());
        this.state.apply(this.skeleton);
        this.skeleton.updateWorldTransform();
        this.skeleton.setPosition(x, y);
        sb.end();
        CardCrawlGame.psb.begin();
        AbstractCreature.sr.draw(CardCrawlGame.psb, this.skeleton);
        CardCrawlGame.psb.end();
        sb.begin();
    }

    @Override
    public Integer onSave() {
        return this.index;
    }

    @Override
    public void onLoad(Integer save) {
        if (save != null && save >= 0 && save < skins.size()) {
            this.index = save;
        }
        refresh();
    }
}
