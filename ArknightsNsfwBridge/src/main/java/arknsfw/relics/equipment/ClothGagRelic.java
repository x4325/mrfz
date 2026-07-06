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

/** 布口塞：每回合第一次失去生命时咬紧忍耐，获得4格挡，兴奋+5。 */
public class ClothGagRelic extends AbstractArkNsfwRelic {
    public static final String ID = ArkNsfwMod.makeID("ClothGagRelic");

    private boolean usedThisTurn = false;

    public ClothGagRelic() {
        super(ID, "equipment_clothgag.png", RelicTier.COMMON, LandingSound.FLAT);
    }

    @Override
    public void atTurnStart() {
        usedThisTurn = false;
    }

    @Override
    public int onLoseHp(int damageAmount) {
        if (!usedThisTurn && damageAmount > 0 && AbstractDungeon.player != null
                && AbstractDungeon.getCurrRoom() != null
                && AbstractDungeon.getCurrRoom().phase == com.megacrit.cardcrawl.rooms.AbstractRoom.RoomPhase.COMBAT) {
            usedThisTurn = true;
            flash();
            addToBot(new GainBlockAction(AbstractDungeon.player, 4));
            NsfwRunStats.addExcitement(5);
        }
        return damageAmount;
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }
}
