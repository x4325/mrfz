package arknsfw.powers.archetto;

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

/** 颤抖弓手：每回合首张攻击牌费用 +1/层（上限 +2）。 */
public class TremblingGripPower extends AbstractArkDebuffPower {
    public static final String POWER_ID = ArkNsfwMod.makeID("TremblingGripPower");
    private static final PowerStrings STR = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public boolean attackPlayedThisTurn = false;

    public TremblingGripPower(AbstractCreature owner, int amount) {
        super(POWER_ID, owner, amount);
        this.name = STR.NAME;
        this.DESCRIPTIONS = STR.DESCRIPTIONS;
        loadDebuffIcon("trembling_grip", "weak");
        updateDescription();
    }

    @Override
    public void atStartOfTurn() {
        attackPlayedThisTurn = false;
    }

    @Override
    public void onPlayCard(AbstractCard card, AbstractMonster m) {
        if (card != null && card.type == AbstractCard.CardType.ATTACK) {
            attackPlayedThisTurn = true;
        }
    }

    public static int getExtraCost(AbstractCard card) {
        if (!ArkCharacterSetup.isArchettoRun() || card == null || AbstractDungeon.player == null) {
            return 0;
        }
        if (card.type != AbstractCard.CardType.ATTACK) {
            return 0;
        }
        if (card.costForTurn < 0 || card.freeToPlay()) {
            return 0;
        }
        AbstractPower p = AbstractDungeon.player.getPower(POWER_ID);
        if (p instanceof TremblingGripPower) {
            TremblingGripPower grip = (TremblingGripPower) p;
            if (!grip.attackPlayedThisTurn) {
                return Math.min(grip.amount, 2);
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
