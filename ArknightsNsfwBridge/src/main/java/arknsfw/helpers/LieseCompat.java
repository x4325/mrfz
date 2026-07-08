package arknsfw.helpers;

import liesecore.helpers.NsfwRunStats;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * liesecore 兼容层：不确定签名的 API 一律走反射，保证桥接包在任何 liesecore 版本下都能编译。
 * （此前直接静态调用 getClimaxThreshold 造成 javac 失败，旧 jar 一直未被替换。）
 */
public final class LieseCompat {

    private static Method thresholdMethod;
    private static Field thresholdField;
    private static boolean searched = false;

    private LieseCompat() {
    }

    /** 高潮阈值；一律反射读取，找不到时退回 200。 */
    public static int climaxThreshold() {
        if (!searched) {
            searched = true;
            try {
                Method m = NsfwRunStats.class.getDeclaredMethod("getClimaxThreshold");
                m.setAccessible(true);
                thresholdMethod = m;
            } catch (Throwable ignored) {
                for (String name : new String[]{"climaxThreshold", "CLIMAX_THRESHOLD", "threshold"}) {
                    try {
                        Field f = NsfwRunStats.class.getDeclaredField(name);
                        if (f.getType() == int.class) {
                            f.setAccessible(true);
                            thresholdField = f;
                            break;
                        }
                    } catch (Throwable ignored2) {
                    }
                }
            }
        }
        try {
            if (thresholdMethod != null) {
                Object v = thresholdMethod.invoke(null);
                if (v instanceof Integer) {
                    return (Integer) v;
                }
            }
            if (thresholdField != null) {
                return thresholdField.getInt(null);
            }
        } catch (Throwable ignored) {
        }
        return 200;
    }
}
