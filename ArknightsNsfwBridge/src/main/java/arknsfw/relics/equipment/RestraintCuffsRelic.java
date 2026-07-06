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

/** 拘束手铐：每回合随机一张手牌费用+1（当回合）；受到攻击伤害时兴奋+3。 */
public class RestraintCuffsRelic extends AbstractArkNsfwRelic {
    public static final String ID = ArkNsfwMod.makeID("RestraintCuffsRelic");

    public RestraintCuffsRelic() {
        super(ID, "equipment_cuffs.png", RelicTier.UNCOMMON, LandingSound.CLINK);
    }

    @Override
    public void atTurnStart() {
        AbstractPlayer p = AbstractDungeon.player;
        if (p == null || p.hand == null || p.hand.isEmpty()) {
            return;
        }
        AbstractCard c = p.hand.getRandomCard(AbstractDungeon.cardRandomRng);
        if (c != null && c.costForTurn >= 0) {
            c.setCostForTurn(c.costForTurn + 1);
            flash();
        }
    }

    @Override
    public int onAttacked(DamageInfo info, int damageAmount) {
        if (info.type == DamageInfo.DamageType.NORMAL && damageAmount > 0) {
            NsfwRunStats.addExcitement(3);
        }
        return damageAmount;
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }
}
