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

/** 贞操带：受孕获取减半；欲望无处发泄，每回合开始兴奋+4。 */
public class ChastityBeltRelic extends AbstractArkNsfwRelic {
    public static final String ID = ArkNsfwMod.makeID("ChastityBeltRelic");

    public ChastityBeltRelic() {
        super(ID, "equipment_belt.png", RelicTier.RARE, LandingSound.CLINK);
    }

    @Override
    public void atTurnStart() {
        NsfwRunStats.addExcitement(4);
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }
}
