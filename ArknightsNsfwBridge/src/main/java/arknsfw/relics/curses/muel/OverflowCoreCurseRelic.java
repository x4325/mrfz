package arknsfw.relics.curses.muel;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import liesecore.api.NsfwCharacterRegistry;
import liesecore.helpers.NsfwRunStats;
import arknsfw.ArkNsfwMod;
import arknsfw.helpers.ArkCharMechanicsHelper;
import arknsfw.relics.curses.AbstractArkCurseRelic;

/** 婧㈡祦鏍稿績锛氭瘡鎵撳嚭鑻ュ共鎶€鑳界墝瑙嗕负涓嚭銆傜吉灏旓細娴佸舰鍦ㄥ満鏃堕槇鍊兼洿浣庛€?*/
public class OverflowCoreCurseRelic extends AbstractArkCurseRelic {
    public static final String ID = ArkNsfwMod.makeID("OverflowCoreCurseRelic");
    private int skillCount = 0;

    public OverflowCoreCurseRelic() {
        super(ID, "curse_overflow_core.png", LandingSound.SOLID);
    }

    @Override
    public void atTurnStart() {
        skillCount = 0;
    }

    @Override
    public void onPlayCard(AbstractCard c, AbstractMonster m) {
        if (!NsfwCharacterRegistry.isActive() || c == null || c.type != AbstractCard.CardType.SKILL) {
            return;
        }
        skillCount++;
        int need = ArkCharMechanicsHelper.hasManifold() ? 1 : 2;
        if (skillCount >= need) {
            skillCount = 0;
            flash();
            NsfwRunStats.addFertility(12, 10, true);
            NsfwRunStats.addExcitement(15);
            if (ArkCharMechanicsHelper.hasManifold()) {
                ArkCharMechanicsHelper.boostManifold(1);
            }
            if (ArkCharMechanicsHelper.isCultivating()) {
                ArkCharMechanicsHelper.applyRootage(AbstractDungeon.player, 1);
            }
        }
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

    @Override
    public AbstractRelic makeCopy() {
        return new OverflowCoreCurseRelic();
    }
}

