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

/** 环形口枷：每回合开始兴奋+3；高潮失控时不再失去格挡（呻吟反而振奋）。 */
public class RingGagRelic extends AbstractArkNsfwRelic {
    public static final String ID = ArkNsfwMod.makeID("RingGagRelic");

    public RingGagRelic() {
        super(ID, "equipment_ringgag.png", RelicTier.UNCOMMON, LandingSound.CLINK);
    }

    @Override
    public void atTurnStart() {
        NsfwRunStats.addExcitement(3);
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }
}
