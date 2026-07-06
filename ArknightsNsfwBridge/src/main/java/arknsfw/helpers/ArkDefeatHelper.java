package arknsfw.helpers;

import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.vfx.TextAboveCreatureEffect;
import liesecore.helpers.NsfwRunStats;

/**
 * 战败凌辱线：HP 归零时不死，而是被彻底占有——
 * 专属印记 +2 并永久刻印、大量受孕、衣装破损、获得一张诅咒，以 30% 生命继续。
 * 每局仅一次。
 */
public final class ArkDefeatHelper {

    private static boolean used = false;

    private ArkDefeatHelper() {
    }

    public static void resetForNewRun() {
        used = false;
    }

    public static boolean tryRescue(AbstractPlayer p) {
        if (used || p == null || ArkCharDebuffs.currentCharKey() == null) {
            return false;
        }
        used = true;
        p.isDead = false;
        p.isDying = false;
        p.currentHealth = Math.max(1, (int) (p.maxHealth * 0.3F));
        p.healthBarUpdatedEvent();
        // 后果：被彻底占有
        AbstractPower brand = ArkCharDebuffs.fresh(p, 2);
        if (brand != null) {
            ArkDebuffHelper.applyAndLock(p, brand);
        }
        NsfwRunStats.addExcitement(30);
        NsfwRunStats.addFertility(15, 10, true);
        if (NsfwRunStats.pregnant) {
            NsfwRunStats.addPregnancyProgress(15);
        }
        ArkCurseHelper.addRandomCurseForCurrentCharacter();
        ArkExposureHelper.tear();
        AbstractDungeon.effectList.add(new TextAboveCreatureEffect(
                p.hb.cX - p.animX, p.hb.cY + p.hb.height / 2.0F, "败北……身体被随意使用了", Color.RED.cpy()));
        return true;
    }
}
