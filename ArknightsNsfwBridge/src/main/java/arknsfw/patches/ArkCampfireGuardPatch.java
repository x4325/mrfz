package arknsfw.patches;

import com.evacipated.cardcrawl.modthespire.lib.SpireInstrumentPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.rooms.CampfireUI;
import javassist.CannotCompileException;
import javassist.expr.ExprEditor;
import javassist.expr.MethodCall;

import java.util.HashSet;

/**
 * 营火界面防黑屏兜底：把 CampfireUI.render/update 内部的每个调用都包上 try/catch。
 * 任何一个调用抛异常都不再中断整个界面渲染（此前表现为休息处全黑屏），
 * 并把首次出现的异常堆栈打进 ModTheSpire.log，方便定位真正元凶。
 */
public class ArkCampfireGuardPatch {

    private static final HashSet<String> SEEN = new HashSet<>();

    public static void report(Throwable t) {
        String head = t.getClass().getName();
        StackTraceElement[] st = t.getStackTrace();
        String key = head + "@" + (st != null && st.length > 0 ? st[0].toString() : "?");
        if (SEEN.add(key)) {
            System.out.println("[arknsfw] 营火渲染异常已拦截(黑屏元凶): " + key);
            t.printStackTrace();
        }
    }

    private static ExprEditor guardEditor() {
        return new ExprEditor() {
            @Override
            public void edit(MethodCall m) throws CannotCompileException {
                m.replace("try { $_ = $proceed($$); } catch (Throwable __t) {"
                        + " arknsfw.patches.ArkCampfireGuardPatch.report(__t); }");
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
