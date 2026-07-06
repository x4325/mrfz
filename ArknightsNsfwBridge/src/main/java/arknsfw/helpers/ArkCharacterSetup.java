package arknsfw.helpers;

import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import arknsfw.relics.eyja.EyjaPregnancyDeliveryRelic;
import arknsfw.relics.eyja.EyjaPregnancyMarkRelic;
import arknsfw.relics.muel.MuelPregnancyDeliveryRelic;
import arknsfw.relics.muel.MuelPregnancyMarkRelic;
import Eyjafjalla.character.Eyjafjalla;
import liesecore.api.NsfwCharacterRegistry;
import liesecore.api.PregnancyDeliveryRegistry;
import liesecore.api.PregnancyMarkRegistry;
import liesecore.helpers.NsfwEventPool;
import Muelsyse.characters.Muelsyse;
import scene.characters.Scene;
import highmore.characters.Highmore;
import archetto.characters.Archetto;
import haruka.characters.Haruka;
import nymph.characters.Nymph;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public final class ArkCharacterSetup {

    public static final class EventMeta {
        public final String character;
        public final ArkRunProgress.Route route;
        public final int act;

        EventMeta(String character, ArkRunProgress.Route route, int act) {
            this.character = character;
            this.route = route;
            this.act = act;
        }
    }

    private static final Map<String, EventMeta> EVENT_META = new HashMap<>();
    private static final Set<String> EYJA_EVENTS = new HashSet<>();
    private static final Set<String> MUEL_EVENTS = new HashSet<>();
    private static final Set<String> FIVE_CHAR_EVENTS = new HashSet<>();

    private static void regFive(String character, String prefix) {
        String[][] routes = {
                {"Normal", "NORMAL"}, {"Shame", "SHAME"}, {"Fall", "FALL"}
        };
        for (String[] r : routes) {
            for (int act = 1; act <= 3; act++) {
                String id = "arknsfw:" + prefix + r[0] + "Act" + act;
                EVENT_META.put(id, new EventMeta(character, ArkRunProgress.Route.valueOf(r[1]), act));
                FIVE_CHAR_EVENTS.add(id);
            }
        }
        String sealId = "arknsfw:" + prefix + "DebuffSeal";
        EVENT_META.put(sealId, new EventMeta(character, null, 0));
        FIVE_CHAR_EVENTS.add(sealId);
    }

    static {
        regFive("highmore", "Highmore");
        regFive("scene", "Scene");
        regFive("archetto", "Archetto");
        regFive("haruka", "Haruka");
        regFive("nymph", "Nymph");

        reg("arknsfw:EyjaNormalVolcanoRest", "eyja", ArkRunProgress.Route.NORMAL, 1);
        reg("arknsfw:EyjaNormalFieldCamp", "eyja", ArkRunProgress.Route.NORMAL, 2);
        reg("arknsfw:EyjaNormalQuietEmbrace", "eyja", ArkRunProgress.Route.NORMAL, 3);
        reg("arknsfw:EyjaShamePublicSample", "eyja", ArkRunProgress.Route.SHAME, 1);
        reg("arknsfw:EyjaShameOpenSeminar", "eyja", ArkRunProgress.Route.SHAME, 2);
        reg("arknsfw:EyjaShameAuditHall", "eyja", ArkRunProgress.Route.SHAME, 3);
        reg("arknsfw:EyjaFallHeatNeed", "eyja", ArkRunProgress.Route.FALL, 1);
        reg("arknsfw:EyjaFallBreedingRite", "eyja", ArkRunProgress.Route.FALL, 2);
        reg("arknsfw:EyjaFallAshWomb", "eyja", ArkRunProgress.Route.FALL, 3);

        reg("arknsfw:MuelNormalBubbleBreak", "muel", ArkRunProgress.Route.NORMAL, 1);
        reg("arknsfw:MuelNormalGardenTea", "muel", ArkRunProgress.Route.NORMAL, 2);
        reg("arknsfw:MuelNormalStarPool", "muel", ArkRunProgress.Route.NORMAL, 3);
        reg("arknsfw:MuelShameLabAudit", "muel", ArkRunProgress.Route.SHAME, 1);
        reg("arknsfw:MuelShameLiveDemo", "muel", ArkRunProgress.Route.SHAME, 2);
        reg("arknsfw:MuelShameBroadcast", "muel", ArkRunProgress.Route.SHAME, 3);
        reg("arknsfw:MuelFallCloneNeed", "muel", ArkRunProgress.Route.FALL, 1);
        reg("arknsfw:MuelFallSeedGreenhouse", "muel", ArkRunProgress.Route.FALL, 2);
        reg("arknsfw:MuelFallFluidOverflow", "muel", ArkRunProgress.Route.FALL, 3);

        EYJA_EVENTS.add("arknsfw:EyjaDebuffSealEvent");
        MUEL_EVENTS.add("arknsfw:MuelDebuffSealEvent");
        EVENT_META.put("arknsfw:EyjaDebuffSealEvent", new EventMeta("eyja", null, 0));
        EVENT_META.put("arknsfw:MuelDebuffSealEvent", new EventMeta("muel", null, 0));
    }

    private static void reg(String id, String character, ArkRunProgress.Route route, int act) {
        EVENT_META.put(id, new EventMeta(character, route, act));
        if ("eyja".equals(character)) {
            EYJA_EVENTS.add(id);
        } else {
            MUEL_EVENTS.add(id);
        }
    }

    private ArkCharacterSetup() {
    }

    public static void registerCharacters() {
        NsfwCharacterRegistry.register(Eyjafjalla.class, ArkSpecialIntentHandler.EYJA);
        NsfwCharacterRegistry.register(Muelsyse.class, ArkSpecialIntentHandler.MUEL);
        PregnancyMarkRegistry.register(Eyjafjalla.class, EyjaPregnancyMarkRelic.ID);
        PregnancyMarkRegistry.register(Muelsyse.class, MuelPregnancyMarkRelic.ID);
        PregnancyDeliveryRegistry.register(Eyjafjalla.class, EyjaPregnancyDeliveryRelic.ID);
        PregnancyDeliveryRegistry.register(Muelsyse.class, MuelPregnancyDeliveryRelic.ID);
        NsfwCharacterRegistry.register(Scene.class, ArkFiveIntentHandler.INSTANCE);
        NsfwCharacterRegistry.register(Highmore.class, ArkFiveIntentHandler.INSTANCE);
        NsfwCharacterRegistry.register(Archetto.class, ArkFiveIntentHandler.INSTANCE);
        NsfwCharacterRegistry.register(Haruka.class, ArkFiveIntentHandler.INSTANCE);
        NsfwCharacterRegistry.register(Nymph.class, ArkFiveIntentHandler.INSTANCE);
        PregnancyMarkRegistry.register(Scene.class, arknsfw.relics.scene.ScenePregnancyMarkRelic.ID);
        PregnancyMarkRegistry.register(Highmore.class, arknsfw.relics.highmore.HighmorePregnancyMarkRelic.ID);
        PregnancyMarkRegistry.register(Archetto.class, arknsfw.relics.archetto.ArchettoPregnancyMarkRelic.ID);
        PregnancyMarkRegistry.register(Haruka.class, arknsfw.relics.haruka.HarukaPregnancyMarkRelic.ID);
        PregnancyMarkRegistry.register(Nymph.class, arknsfw.relics.nymph.NymphPregnancyMarkRelic.ID);
        PregnancyDeliveryRegistry.register(Scene.class, arknsfw.relics.scene.ScenePregnancyDeliveryRelic.ID);
        PregnancyDeliveryRegistry.register(Highmore.class, arknsfw.relics.highmore.HighmorePregnancyDeliveryRelic.ID);
        PregnancyDeliveryRegistry.register(Archetto.class, arknsfw.relics.archetto.ArchettoPregnancyDeliveryRelic.ID);
        PregnancyDeliveryRegistry.register(Haruka.class, arknsfw.relics.haruka.HarukaPregnancyDeliveryRelic.ID);
        PregnancyDeliveryRegistry.register(Nymph.class, arknsfw.relics.nymph.NymphPregnancyDeliveryRelic.ID);
        for (String id : EYJA_EVENTS) {
            NsfwEventPool.registerEvent(id);
        }
        for (String id : MUEL_EVENTS) {
            NsfwEventPool.registerEvent(id);
        }
        for (String id : FIVE_CHAR_EVENTS) {
            NsfwEventPool.registerEvent(id);
        }
    }

    public static boolean isEyjaRun() {
        return AbstractDungeon.player instanceof Eyjafjalla;
    }

    public static boolean isMuelsyseRun() {
        return AbstractDungeon.player instanceof Muelsyse;
    }

    public static boolean isArkNsfwRun() {
        return isEyjaRun() || isMuelsyseRun() || isSceneRun() || isHighmoreRun() || isArchettoRun() || isHarukaRun() || isNymphRun();
    }
    public static boolean isSceneRun() {
        return AbstractDungeon.player instanceof Scene;
    }
    public static boolean isHighmoreRun() {
        return AbstractDungeon.player instanceof Highmore;
    }
    public static boolean isArchettoRun() {
        return AbstractDungeon.player instanceof Archetto;
    }
    public static boolean isHarukaRun() {
        return AbstractDungeon.player instanceof Haruka;
    }
    public static boolean isNymphRun() {
        return AbstractDungeon.player instanceof Nymph;
    }

    public static ArrayList<String> eligibleCharacterEvents(ArrayList<String> eventList) {
        ArrayList<String> eligible = new ArrayList<>();
        if (eventList == null || !NsfwCharacterRegistry.isActive()) {
            return eligible;
        }
        String character = isEyjaRun() ? "eyja" : isMuelsyseRun() ? "muel"
                : isSceneRun() ? "scene" : isHighmoreRun() ? "highmore"
                : isArchettoRun() ? "archetto" : isHarukaRun() ? "haruka"
                : isNymphRun() ? "nymph" : null;
        if (character == null) {
            return eligible;
        }
        int act = currentAct();
        for (String id : eventList) {
            EventMeta meta = EVENT_META.get(id);
            if (meta == null || !character.equals(meta.character)) {
                continue;
            }
            if (meta.route == null && meta.act == 0) {
                if (act >= 2) {
                    eligible.add(id);
                }
                continue;
            }
            if (!ArkRunProgress.hasRoute()) {
                if (meta.act == 1) {
                    eligible.add(id);
                }
            } else if (ArkRunProgress.isRoute(meta.route) && meta.act == act) {
                eligible.add(id);
            }
        }
        return eligible;
    }

    private static int currentAct() {
        if (AbstractDungeon.actNum > 0) {
            return AbstractDungeon.actNum;
        }
        return 1;
    }

    public static boolean eventAppliesToCurrentPlayer(String eventId) {
        EventMeta meta = EVENT_META.get(eventId);
        if (meta == null) {
            return true;
        }
        switch (meta.character) {
            case "eyja": return isEyjaRun();
            case "muel": return isMuelsyseRun();
            case "scene": return isSceneRun();
            case "highmore": return isHighmoreRun();
            case "archetto": return isArchettoRun();
            case "haruka": return isHarukaRun();
            case "nymph": return isNymphRun();
            default: return true;
        }
    }
}
