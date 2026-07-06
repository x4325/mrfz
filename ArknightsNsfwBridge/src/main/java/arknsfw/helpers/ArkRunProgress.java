package arknsfw.helpers;

/** 本 run 的 NSFW 事件路线：首次 Act1 专属事件选项锁定后，只出现同路线 Act2/3。 */
public final class ArkRunProgress {

    public enum Route {
        NONE, NORMAL, SHAME, FALL
    }

    public static Route route = Route.NONE;

    private ArkRunProgress() {
    }

    public static void resetForNewRun() {
        route = Route.NONE;
        ArkDebuffHelper.resetForNewRun();
    }

    public static void lockRoute(Route r) {
        if (r != null && r != Route.NONE && route == Route.NONE) {
            route = r;
        }
    }

    public static boolean isRoute(Route r) {
        return route == r;
    }

    public static boolean hasRoute() {
        return route != Route.NONE;
    }
}
