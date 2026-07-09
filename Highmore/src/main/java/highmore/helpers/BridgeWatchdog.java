package highmore.helpers;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.FontHelper;

/**
 * 桥接包检测：不引用 ModTheSpire 内部类型（避免编译期依赖 semver4j），
 * 改用反射探测桥接包的标志类是否存在（每个版本以新增类为指纹）。
 */
public final class BridgeWatchdog {

    /** 0.6.0 指纹类：堕落模式助手（0.6.0 新增）。 */
    private static final String MARKER_CLASS = "arknsfw.helpers.ArkFallMode";
    private static final Color WARN = new Color(1.0F, 0.35F, 0.35F, 1.0F);
    private static String status = null;

    private BridgeWatchdog() {
    }

    public static void render(SpriteBatch sb) {
        try {
            if (AbstractDungeon.player == null
                    || !(AbstractDungeon.player instanceof highmore.characters.Highmore)
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
                    20.0F * Settings.scale, Settings.HEIGHT - 132.0F * Settings.scale, WARN);
        } catch (Throwable ignored) {
        }
    }

    private static String compute() {
        try {
            Class.forName("arknsfw.ArkNsfwMod");
        } catch (Throwable t) {
            return "[!] 未加载桥接包 arknsfw：立绘/NSFW系统不会生效";
        }
        try {
            Class.forName(MARKER_CLASS);
            return "";
        } catch (Throwable t) {
            return "[!] 桥接包 arknsfw 版本过旧：立绘/装备/药水未生效，请重新构建 ArknightsNsfwBridge 并把构建报错发给作者";
        }
    }
}
