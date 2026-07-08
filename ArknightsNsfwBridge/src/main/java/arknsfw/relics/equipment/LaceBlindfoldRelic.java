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

/** 蕾丝眼罩：视觉被夺、感官集中，受到的攻击伤害-1；每回合开始兴奋+2。 */
public class LaceBlindfoldRelic extends AbstractArkNsfwRelic {
    public static final String ID = ArkNsfwMod.makeID("LaceBlindfoldRelic");

    public LaceBlindfoldRelic() {
        super(ID, "equipment_laceblindfold.png", RelicTier.RARE, LandingSound.FLAT);
    }

    @Override
    public void atTurnStart() {
        arknsfw.helpers.ArkSafeStats.addExcitementDeferred(2);
    }

    @Override
    public int onAttackedToChangeDamage(DamageInfo info, int damageAmount) {
        if (info.type == DamageInfo.DamageType.NORMAL && damageAmount > 1) {
            return damageAmount - 1;
        }
        return damageAmount;
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }
}
