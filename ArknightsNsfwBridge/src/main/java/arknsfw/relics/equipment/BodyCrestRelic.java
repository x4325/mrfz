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

/** 淫纹拓印：全身各处浮现小淫纹，兴奋获取+25%；每回合开始受孕+1。 */
public class BodyCrestRelic extends AbstractArkNsfwRelic {
    public static final String ID = ArkNsfwMod.makeID("BodyCrestRelic");

    public BodyCrestRelic() {
        super(ID, "equipment_bodycrest.png", RelicTier.RARE, LandingSound.MAGICAL);
    }

    @Override
    public void atTurnStart() {
        NsfwRunStats.addConception(1, false);
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }
}
