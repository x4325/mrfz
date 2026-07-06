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

/** 蕾丝腿环：每场战斗第一次打出技能牌时抽1张牌；每回合开始兴奋+2。 */
public class LaceGarterRelic extends AbstractArkNsfwRelic {
    public static final String ID = ArkNsfwMod.makeID("LaceGarterRelic");

    private boolean usedThisCombat = false;

    public LaceGarterRelic() {
        super(ID, "equipment_garter.png", RelicTier.COMMON, LandingSound.FLAT);
    }

    @Override
    public void atBattleStart() {
        usedThisCombat = false;
    }

    @Override
    public void atTurnStart() {
        NsfwRunStats.addExcitement(2);
    }

    @Override
    public void onUseCard(AbstractCard card, com.megacrit.cardcrawl.actions.utility.UseCardAction action) {
        if (!usedThisCombat && card.type == AbstractCard.CardType.SKILL
                && AbstractDungeon.player != null) {
            usedThisCombat = true;
            flash();
            addToBot(new DrawCardAction(AbstractDungeon.player, 1));
        }
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }
}
