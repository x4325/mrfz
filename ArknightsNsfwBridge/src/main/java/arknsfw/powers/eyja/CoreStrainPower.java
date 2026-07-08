package arknsfw.powers.eyja;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import liesecore.helpers.NsfwRunStats;
import arknsfw.ArkNsfwMod;
import arknsfw.powers.AbstractArkDebuffPower;

/** 核心过载：获得格挡 -2/层；打出技能牌后兴奋 +3/层。 */
public class CoreStrainPower extends AbstractArkDebuffPower {
    public static final String POWER_ID = ArkNsfwMod.makeID("CoreStrainPower");
    private static final PowerStrings STR = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);

    public CoreStrainPower(AbstractCreature owner, int amount) {
        super(POWER_ID, owner, amount);
        this.name = STR.NAME;
        this.DESCRIPTIONS = STR.DESCRIPTIONS;
        loadDebuffIcon("core_strain", "painful");
        updateDescription();
    }

    @Override
    public void atEndOfTurn(boolean isPlayer) {
        if (isPlayer && owner != null && owner.isPlayer && amount > 0) {
            owner.damage(new com.megacrit.cardcrawl.cards.DamageInfo(null, amount, com.megacrit.cardcrawl.cards.DamageInfo.DamageType.HP_LOSS));
        }
    }

    @Override
    public void onPlayCard(AbstractCard card, AbstractMonster m) {
        if (card != null && card.type == AbstractCard.CardType.SKILL) {
            arknsfw.helpers.ArkSafeStats.addExcitementDeferred(3 * amount);
        }
    }

    @Override
    public float atDamageReceive(float damage, DamageInfo.DamageType type) {
        return damage;
    }

    @Override
    public void updateDescription() {
        description = amount + STR.DESCRIPTIONS[1];
        if (isPermanentlyLocked() && STR.DESCRIPTIONS.length > 2) {
            description += STR.DESCRIPTIONS[2];
        }
    }
}
