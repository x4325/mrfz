package arknsfw.powers.scene;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import liesecore.helpers.NsfwRunStats;
import arknsfw.ArkNsfwMod;
import arknsfw.powers.AbstractArkDebuffPower;

/** 走光镜头：回合开始兴奋 +2/层；每打出技能牌（被镜头捕捉）兴奋 +2/层。 */
public class ExposedLensPower extends AbstractArkDebuffPower {
    public static final String POWER_ID = ArkNsfwMod.makeID("ExposedLensPower");
    private static final PowerStrings STR = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);

    public ExposedLensPower(AbstractCreature owner, int amount) {
        super(POWER_ID, owner, amount);
        this.name = STR.NAME;
        this.DESCRIPTIONS = STR.DESCRIPTIONS;
        loadDebuffIcon("exposed_lens", "weak");
        updateDescription();
    }

    @Override
    public void atStartOfTurn() {
        arknsfw.helpers.ArkSafeStats.addExcitementDeferred(2 * amount);
    }

    @Override
    public void onPlayCard(AbstractCard card, AbstractMonster m) {
        if (card != null && card.type == AbstractCard.CardType.SKILL) {
            arknsfw.helpers.ArkSafeStats.addExcitementDeferred(2 * amount);
        }
    }

    @Override
    public void updateDescription() {
        description = amount + STR.DESCRIPTIONS[1];
        if (isPermanentlyLocked() && STR.DESCRIPTIONS.length > 2) {
            description += STR.DESCRIPTIONS[2];
        }
    }
}
