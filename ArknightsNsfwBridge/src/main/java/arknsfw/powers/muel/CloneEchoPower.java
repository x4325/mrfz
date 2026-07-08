package arknsfw.powers.muel;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import liesecore.helpers.NsfwRunStats;
import arknsfw.ArkNsfwMod;
import arknsfw.powers.AbstractArkDebuffPower;

/** 分身回音：回合开始兴奋上升；首张攻击牌伤害 -2/层。 */
public class CloneEchoPower extends AbstractArkDebuffPower {
    public static final String POWER_ID = ArkNsfwMod.makeID("CloneEchoPower");
    private static final PowerStrings STR = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    private boolean attackPenaltyUsed = false;

    public CloneEchoPower(AbstractCreature owner, int amount) {
        super(POWER_ID, owner, amount);
        this.name = STR.NAME;
        this.DESCRIPTIONS = STR.DESCRIPTIONS;
        loadDebuffIcon("clone_echo", "double");
        updateDescription();
    }

    @Override
    public void atStartOfTurn() {
        attackPenaltyUsed = false;
        arknsfw.helpers.ArkSafeStats.addExcitementDeferred(2 * amount);
    }

    @Override
    public float atDamageGive(float damage, DamageInfo.DamageType type, AbstractCard card) {
        if (type != DamageInfo.DamageType.NORMAL || card == null || card.type != AbstractCard.CardType.ATTACK) {
            return damage;
        }
        if (attackPenaltyUsed || AbstractDungeon.player == null) {
            return damage;
        }
        attackPenaltyUsed = true;
        return Math.max(0, damage - 2 * amount);
    }

    @Override
    public void updateDescription() {
        description = amount + STR.DESCRIPTIONS[1];
        if (isPermanentlyLocked() && STR.DESCRIPTIONS.length > 2) {
            description += STR.DESCRIPTIONS[2];
        }
    }
}
