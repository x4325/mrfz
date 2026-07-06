package nymph.helpers;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.FontHelper;

/** 桥接包版本检测：桥接包缺失/过旧时在战斗界面直接显示红字提示。 */
public final class BridgeWatchdog {

    private static final String EXPECTED_PREFIX = "0.4.6";
    private static String status = null;

    private BridgeWatchdog() {
    }

    public static void render(SpriteBatch sb) {
        try {
            if (AbstractDungeon.player == null
                    || !(AbstractDungeon.player instanceof nymph.characters.Nymph)
                    || AbstractDungeon.getCurrRoom() == null) {
                return;
            }
            if (status == null) {
                status = compute();
            }
            if (status.isEmpty()) {
                return;
            }
            FontHelper.renderFontLeftTopAligned(sb, FontHelper.tipBodyFont, status,
                    20.0F * Settings.scale, Settings.HEIGHT - 132.0F * Settings.scale,
                    Settings.RED_TEXT_COLOR);
        } catch (Throwable ignored) {
        }
    }

    private static String compute() {
        try {
            for (com.evacipated.cardcrawl.modthespire.ModInfo info
                    : com.evacipated.cardcrawl.modthespire.Loader.MODINFOS) {
                if ("arknsfw".equals(info.ID)) {
                    String v = String.valueOf(info.ModVersion);
                    if (v.startsWith(EXPECTED_PREFIX)) {
                        return "";
                    }
                    return "[!] 桥接包 arknsfw 版本过旧(" + v + ")：立绘/事件/药水未生效，请重新构建 ArknightsNsfwBridge 并把构建报错发给作者";
                }
            }
            return "[!] 未加载桥接包 arknsfw：立绘/NSFW系统不会生效";
        } catch (Throwable t) {
            return "";
        }
    }
}
