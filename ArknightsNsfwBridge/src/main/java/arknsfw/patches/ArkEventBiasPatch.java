package arknsfw.patches;

import basemod.BaseMod;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePrefixPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireReturn;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.events.AbstractEvent;
import com.megacrit.cardcrawl.helpers.EventHelper;
import com.megacrit.cardcrawl.random.Random;
import liesecore.api.NsfwCharacterRegistry;
import arknsfw.helpers.ArkCharacterSetup;

import java.util.ArrayList;

/** 方舟角色 run 优先抽角色专属 NSFW 事件。 */
public class ArkEventBiasPatch {

    public static float CHAR_EVENT_CHANCE = 0.85f;

    @SpirePatch(clz = AbstractDungeon.class, method = "getEvent", paramtypez = {Random.class})
    public static class BiasArkCharacterEvents {
        @SpirePrefixPatch
        public static SpireReturn<AbstractEvent> prefix(Random rng) {
            if (!NsfwCharacterRegistry.isActive() || !ArkCharacterSetup.isArkNsfwRun() || rng == null) {
                return SpireReturn.Continue();
            }
            if (AbstractDungeon.eventList == null || AbstractDungeon.eventList.isEmpty()) {
                return SpireReturn.Continue();
            }
            ArrayList<String> eligible = ArkCharacterSetup.eligibleCharacterEvents(AbstractDungeon.eventList);
            if (eligible.isEmpty() || !rng.randomBoolean(CHAR_EVENT_CHANCE)) {
                return SpireReturn.Continue();
            }
            String picked = eligible.get(rng.random(eligible.size() - 1));
            if (!AbstractDungeon.eventList.remove(picked)) {
                return SpireReturn.Continue();
            }
            AbstractEvent event = instantiateEvent(picked);
            if (event == null) {
                AbstractDungeon.eventList.add(picked);
                return SpireReturn.Continue();
            }
            return SpireReturn.Return(event);
        }
    }

    private static AbstractEvent instantiateEvent(String eventId) {
        Class<? extends AbstractEvent> cls = BaseMod.getEvent(eventId);
        if (cls != null) {
            try {
                return cls.newInstance();
            } catch (ReflectiveOperationException ignored) {
            }
        }
        return EventHelper.getEvent(eventId);
    }
}
