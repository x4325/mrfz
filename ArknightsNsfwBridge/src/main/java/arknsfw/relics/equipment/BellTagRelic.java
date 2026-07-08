package arknsfw.relics.equipment;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.WeakPower;
import liesecore.helpers.NsfwRunStats;
import arknsfw.ArkNsfwMod;
import arknsfw.relics.AbstractArkNsfwRelic;

/** 铃铛铭牌：战斗开始时铃声分神，随机敌人虚弱1；兴奋+5。 */
public class BellTagRelic extends AbstractArkNsfwRelic {
    public static final String ID = ArkNsfwMod.makeID("BellTagRelic");

    public BellTagRelic() {
        super(ID, "equipment_belltag.png", RelicTier.COMMON, LandingSound.CLINK);
    }

    @Override
    public void atBattleStart() {
        AbstractPlayer p = AbstractDungeon.player;
        if (p == null || AbstractDungeon.getMonsters() == null) {
            return;
        }
        AbstractMonster m = AbstractDungeon.getMonsters().getRandomMonster(true);
        if (m != null) {
            flash();
            addToBot(new ApplyPowerAction(m, p, new WeakPower(m, 1, false), 1));
        }
        arknsfw.helpers.ArkSafeStats.addExcitementDeferred(5);
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }
}
