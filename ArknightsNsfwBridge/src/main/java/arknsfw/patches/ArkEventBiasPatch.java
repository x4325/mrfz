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
            // 堕落模式：事件全部固定为「堕落供物」三选一
            if (arknsfw.helpers.ArkFallMode.active()) {
                return SpireReturn.Return(new arknsfw.events.ArkFallOfferingEvent());
            }
            if (!NsfwCharacterRegistry.isActive() || !ArkCharacterSetup.isArkNsfwRun() || rng == null) {
                return SpireReturn.Continue();
            }
            if (AbstractDungeon.eventList == null || AbstractDungeon.eventList.isEmpty()) {
                return SpireReturn.Continue();
            }
            // 先剔除其他角色/其他路线的专属事件，杜绝事件互串
            ArkCharacterSetup.purgeIneligibleEvents(AbstractDungeon.eventList);
            if (AbstractDungeon.eventList.isEmpty()) {
                return SpireReturn.Continue();
            }
            ArrayList<String> eligible = ArkCharacterSetup.eligibleCharacterEvents(AbstractDungeon.eventList);
            if (!eligible.isEmpty() && rng.randomBoolean(CHAR_EVENT_CHANCE)) {
                String picked = eligible.get(rng.random(eligible.size() - 1));
                if (AbstractDungeon.eventList.remove(picked)) {
                    AbstractEvent event = instantiateEvent(picked);
                    if (event != null) {
                        return SpireReturn.Return(event);
                    }
                    AbstractDungeon.eventList.add(picked);
                }
            }
            // 走原版随机时：只从“非专属事件”里抽，防止抽到同角色但路线/幕不匹配的专属事件
            ArrayList<String> vanillaPool = ArkCharacterSetup.nonCharacterEvents(AbstractDungeon.eventList);
            if (vanillaPool.isEmpty() || vanillaPool.size() == AbstractDungeon.eventList.size()) {
                return SpireReturn.Continue();
            }
            String vp = vanillaPool.get(rng.random(vanillaPool.size() - 1));
            if (!AbstractDungeon.eventList.remove(vp)) {
                return SpireReturn.Continue();
            }
            AbstractEvent event = instantiateEvent(vp);
            if (event == null) {
                AbstractDungeon.eventList.add(vp);
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
