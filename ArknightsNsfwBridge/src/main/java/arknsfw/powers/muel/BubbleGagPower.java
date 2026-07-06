package arknsfw.powers.muel;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import arknsfw.ArkNsfwMod;
import arknsfw.helpers.ArkCharacterSetup;
import arknsfw.powers.AbstractArkDebuffPower;

/** 泡沫封口：每回合首张技能牌费用 +1/层。 */
public class BubbleGagPower extends AbstractArkDebuffPower {
    public static final String POWER_ID = ArkNsfwMod.makeID("BubbleGagPower");
    private static final PowerStrings STR = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public boolean skillPlayedThisTurn = false;

    public BubbleGagPower(AbstractCreature owner, int amount) {
        super(POWER_ID, owner, amount);
        this.name = STR.NAME;
        this.DESCRIPTIONS = STR.DESCRIPTIONS;
        loadDebuffIcon("bubble_gag", "choked");
        updateDescription();
    }

    @Override
    public void atStartOfTurn() {
        skillPlayedThisTurn = false;
    }

    @Override
    public void onPlayCard(AbstractCard card, AbstractMonster m) {
        if (card != null && card.type == AbstractCard.CardType.SKILL) {
            skillPlayedThisTurn = true;
        }
    }

    public static int getExtraCost(AbstractCard card) {
        if (!ArkCharacterSetup.isMuelsyseRun() || card == null || AbstractDungeon.player == null) {
            return 0;
        }
        if (card.type != AbstractCard.CardType.SKILL) {
            return 0;
        }
        if (card.costForTurn < 0 || card.freeToPlay()) {
            return 0;
        }
        AbstractPower p = AbstractDungeon.player.getPower(POWER_ID);
        if (p instanceof BubbleGagPower) {
            BubbleGagPower gag = (BubbleGagPower) p;
            if (!gag.skillPlayedThisTurn) {
                return Math.min(gag.amount, 2);
            }
        }
        return 0;
    }

    @Override
    public void updateDescription() {
        description = amount + STR.DESCRIPTIONS[1];
        if (isPermanentlyLocked() && STR.DESCRIPTIONS.length > 2) {
            description += STR.DESCRIPTIONS[2];
        }
    }
}
