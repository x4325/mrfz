package arknsfw.powers.eyja;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import arknsfw.ArkNsfwMod;
import arknsfw.helpers.ArkCharacterSetup;
import arknsfw.powers.AbstractArkDebuffPower;

/** 灰烬羞怯：每回合首张牌费用 +1/层。 */
public class AshShamePower extends AbstractArkDebuffPower {
    public static final String POWER_ID = ArkNsfwMod.makeID("AshShamePower");
    private static final PowerStrings STR = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);

    public AshShamePower(AbstractCreature owner, int amount) {
        super(POWER_ID, owner, amount);
        this.name = STR.NAME;
        this.DESCRIPTIONS = STR.DESCRIPTIONS;
        loadDebuffIcon("ash_shame", "weak");
        updateDescription();
    }

    public static int getExtraCost(AbstractCard card) {
        if (!ArkCharacterSetup.isEyjaRun() || card == null || AbstractDungeon.player == null) {
            return 0;
        }
        if (AbstractDungeon.player.cardsPlayedThisTurn > 0) {
            return 0;
        }
        if (card.costForTurn < 0 || card.freeToPlay()) {
            return 0;
        }
        if (AbstractDungeon.player.hasPower(POWER_ID)) {
            return Math.min(AbstractDungeon.player.getPower(POWER_ID).amount, 2);
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
