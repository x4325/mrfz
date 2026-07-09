package arknsfw.relics.equipment;

import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import liesecore.helpers.NsfwRunStats;
import arknsfw.ArkNsfwMod;
import arknsfw.relics.AbstractArkNsfwRelic;

/** 麻绳束缚：绳结紧勒反成支撑，每回合开始获得3格挡；每打出攻击牌摩擦兴奋+2。 */
public class RopeBindRelic extends AbstractArkNsfwRelic {
    public static final String ID = ArkNsfwMod.makeID("RopeBindRelic");

    public RopeBindRelic() {
        super(ID, "equipment_rope.png", RelicTier.UNCOMMON, LandingSound.FLAT);
    }

    @Override
    public void atTurnStart() {
        AbstractPlayer p = AbstractDungeon.player;
        if (p != null) {
            flash();
            addToBot(new GainBlockAction(p, 3));
        }
    }

    @Override
    public void onUseCard(AbstractCard card, com.megacrit.cardcrawl.actions.utility.UseCardAction action) {
        if (card.type == AbstractCard.CardType.ATTACK) {
            arknsfw.helpers.ArkSafeStats.addExcitementDeferred(2);
        }
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }
}
