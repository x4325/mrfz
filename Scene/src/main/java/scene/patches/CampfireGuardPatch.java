package scene.patches;

import com.evacipated.cardcrawl.modthespire.lib.SpireInstrumentPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.ui.campfire.CampfireUI;
import javassist.CannotCompileException;
import javassist.expr.ExprEditor;
import javassist.expr.MethodCall;

import java.util.HashSet;

/**
 * 营火防黑屏兜底（角色包自带，不依赖桥接包）：
 * CampfireUI.render/update 内任何调用抛异常都不再中断整个界面，
 * 首个异常堆栈打进 ModTheSpire.log 便于定位。
 */
public class CampfireGuardPatch {

    private static final HashSet<String> SEEN = new HashSet<>();

    public static void report(Throwable t) {
        StackTraceElement[] st = t.getStackTrace();
        String key = t.getClass().getName() + "@" + (st != null && st.length > 0 ? st[0].toString() : "?");
        if (SEEN.add(key)) {
            System.out.println("[scene] 营火渲染异常已拦截(黑屏元凶): " + key);
            t.printStackTrace();
        }
    }

    private static ExprEditor guardEditor() {
        return new ExprEditor() {
            @Override
            public void edit(MethodCall m) throws CannotCompileException {
                m.replace("try { $_ = $proceed($$); } catch (Throwable __t) {"
                        + " scene.patches.CampfireGuardPatch.report(__t); }");
            }
        };
    }

    @SpirePatch(clz = CampfireUI.class, method = "render")
    public static class RenderGuard {
        @SpireInstrumentPatch
        public static ExprEditor guard() {
            return guardEditor();
        }
    }

    @SpirePatch(clz = CampfireUI.class, method = "update")
    public static class UpdateGuard {
        @SpireInstrumentPatch
        public static ExprEditor guard() {
            return guardEditor();
        }
    }
}
