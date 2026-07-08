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

/** 跳蛋：每回合开始兴奋+5；每打出3张牌，震动加剧，兴奋再+3。 */
public class VibeEggRelic extends AbstractArkNsfwRelic {
    public static final String ID = ArkNsfwMod.makeID("VibeEggRelic");

    public VibeEggRelic() {
        super(ID, "equipment_vibe.png", RelicTier.UNCOMMON, LandingSound.FLAT);
        this.counter = 0;
    }

    @Override
    public void atTurnStart() {
        NsfwRunStats.addExcitement(5);
    }

    @Override
    public void onUseCard(AbstractCard card, com.megacrit.cardcrawl.actions.utility.UseCardAction action) {
        this.counter++;
        if (this.counter >= 3) {
            this.counter = 0;
            flash();
            NsfwRunStats.addExcitement(3);
        }
    }

    @Override
    public void onVictory() {
        this.counter = 0;
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }
}
