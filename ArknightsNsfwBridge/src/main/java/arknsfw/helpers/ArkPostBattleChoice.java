package arknsfw.helpers;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.helpers.Hitbox;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.helpers.input.InputHelper;
import com.megacrit.cardcrawl.localization.UIStrings;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import liesecore.helpers.NsfwRunStats;

/**
 * 战后三选一：忍受屈辱 / 主动献身 / 拼死抵抗。
 * 前两项带色情后果；第三项完全干净但整局只能使用 3 次；献身每幕限 2 次。
 * 选项文案随角色变化（UIStrings: arknsfw:PostBattle_<char>）。
 */
public final class ArkPostBattleChoice {

    private static final int RESIST_PER_RUN = 3;
    private static final int DEVOTE_PER_ACT = 2;

    private static boolean active = false;
    private static int resistLeft = RESIST_PER_RUN;
    private static int devoteLeft = DEVOTE_PER_ACT;
    private static int devoteAct = -1;

    private static final float BTN_W = 340.0f;
    private static final float BTN_H = 56.0f;
    private static final Hitbox[] HB = {
            new Hitbox(BTN_W * Settings.scale, BTN_H * Settings.scale),
            new Hitbox(BTN_W * Settings.scale, BTN_H * Settings.scale),
            new Hitbox(BTN_W * Settings.scale, BTN_H * Settings.scale),
    };

    private ArkPostBattleChoice() {
    }

    public static void resetForNewRun() {
        active = false;
        resistLeft = RESIST_PER_RUN;
        devoteLeft = DEVOTE_PER_ACT;
        devoteAct = -1;
    }

    private static String charKey() {
        if (ArkCharacterSetup.isHighmoreRun()) return "highmore";
        if (ArkCharacterSetup.isSceneRun()) return "scene";
        if (ArkCharacterSetup.isArchettoRun()) return "archetto";
        if (ArkCharacterSetup.isHarukaRun()) return "haruka";
        if (ArkCharacterSetup.isNymphRun()) return "nymph";
        return null;
    }

    public static void onBattleEnd(AbstractRoom room) {
        if (charKey() == null || room == null || AbstractDungeon.player == null
                || AbstractDungeon.player.isDead || AbstractDungeon.player.isDying) {
            return;
        }
        syncAct();
        active = true;
    }

    private static void syncAct() {
        if (devoteAct != AbstractDungeon.actNum) {
            devoteAct = AbstractDungeon.actNum;
            devoteLeft = DEVOTE_PER_ACT;
        }
    }

    private static boolean shouldShow() {
        if (!active || AbstractDungeon.player == null || AbstractDungeon.currMapNode == null) {
            return false;
        }
        AbstractRoom room = AbstractDungeon.getCurrRoom();
        if (room == null || room.phase != AbstractRoom.RoomPhase.COMPLETE) {
            active = false;
            return false;
        }
        return true;
    }

    private static UIStrings strings() {
        return CardCrawlGame.languagePack.getUIString("arknsfw:PostBattle_" + charKey());
    }

    public static void update() {
        if (!shouldShow()) {
            return;
        }
        float x = Settings.WIDTH * 0.82f;
        float y0 = Settings.HEIGHT * 0.62f;
        for (int i = 0; i < 3; i++) {
            HB[i].move(x, y0 - i * (BTN_H + 14.0f) * Settings.scale);
            HB[i].update();
        }
        if (InputHelper.justClickedLeft) {
            for (int i = 0; i < 3; i++) {
                if (HB[i].hovered) {
                    if (i == 1 && devoteLeft <= 0) return;
                    if (i == 2 && resistLeft <= 0) return;
                    InputHelper.justClickedLeft = false;
                    choose(i);
                    return;
                }
            }
        }
    }

    private static void choose(int i) {
        int act = Math.max(1, AbstractDungeon.actNum);
        CardCrawlGame.sound.play("UI_CLICK_1");
        if (i == 0) {
            // 忍受屈辱：被动的色情后果 + 屈辱补偿
            NsfwRunStats.addExcitement(8 + 2 * act);
            NsfwRunStats.addConception(2, false);
            AbstractDungeon.player.gainGold(15);
        } else if (i == 1) {
            // 主动献身：更重的色情后果 + 事后被照料（每幕限 2 次）
            devoteLeft--;
            NsfwRunStats.addExcitement(15 + 5 * act);
            NsfwRunStats.addFertility(5, 4, act >= 2);
            AbstractDungeon.player.heal(Math.max(1, AbstractDungeon.player.maxHealth / 10));
        } else {
            // 拼死抵抗：完全干净地脱身，但整局只有 3 次
            resistLeft--;
            AbstractDungeon.player.damage(new com.megacrit.cardcrawl.cards.DamageInfo(null, 2,
                    com.megacrit.cardcrawl.cards.DamageInfo.DamageType.HP_LOSS));
        }
        active = false;
    }

    public static void render(SpriteBatch sb) {
        if (!shouldShow()) {
            return;
        }
        UIStrings ui = strings();
        if (ui == null || ui.TEXT == null || ui.TEXT.length < 6) {
            return;
        }
        float x = Settings.WIDTH * 0.82f;
        float y0 = Settings.HEIGHT * 0.62f;
        float panelW = (BTN_W + 40.0f) * Settings.scale;
        float panelH = (3 * (BTN_H + 14.0f) + 90.0f) * Settings.scale;
        // panel
        sb.setColor(new Color(0.05f, 0.05f, 0.08f, 0.82f));
        sb.draw(ImageMaster.WHITE_SQUARE_IMG, x - panelW / 2.0f, y0 - panelH + 70.0f * Settings.scale,
                panelW, panelH);
        FontHelper.renderFontCentered(sb, FontHelper.cardTitleFont, ui.TEXT[0],
                x, y0 + 46.0f * Settings.scale, Settings.GOLD_COLOR);
        for (int i = 0; i < 3; i++) {
            boolean disabled = (i == 1 && devoteLeft <= 0) || (i == 2 && resistLeft <= 0);
            float by = y0 - i * (BTN_H + 14.0f) * Settings.scale;
            Color bg;
            if (disabled) {
                bg = new Color(0.22f, 0.22f, 0.24f, 0.9f);
            } else if (HB[i].hovered) {
                bg = new Color(0.45f, 0.30f, 0.45f, 0.95f);
            } else {
                bg = new Color(0.28f, 0.18f, 0.30f, 0.9f);
            }
            if (i == 2 && !disabled) {
                bg = HB[i].hovered ? new Color(0.25f, 0.42f, 0.45f, 0.95f) : new Color(0.16f, 0.28f, 0.32f, 0.9f);
            }
            sb.setColor(bg);
            sb.draw(ImageMaster.WHITE_SQUARE_IMG,
                    x - BTN_W * Settings.scale / 2.0f, by - BTN_H * Settings.scale / 2.0f,
                    BTN_W * Settings.scale, BTN_H * Settings.scale);
            String label = ui.TEXT[1 + i];
            if (i == 1) {
                label += ui.TEXT[4] + devoteLeft + ui.TEXT[5];
            } else if (i == 2) {
                label += ui.TEXT[4] + resistLeft + ui.TEXT[5];
            }
            Color tc = disabled ? Color.DARK_GRAY : Color.WHITE;
            FontHelper.renderFontCentered(sb, FontHelper.buttonLabelFont, label, x, by, tc);
        }
        sb.setColor(Color.WHITE);
    }
}
