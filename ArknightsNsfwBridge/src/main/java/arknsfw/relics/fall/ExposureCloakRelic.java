package arknsfw.relics.fall;

import com.megacrit.cardcrawl.cards.AbstractCard;
import arknsfw.ArkNsfwMod;
import arknsfw.helpers.ArkExposureHelper;
import arknsfw.relics.AbstractArkNsfwRelic;

/** 淫语墨书：衣装不再修复（含篝火）；每档破损攻击伤害 +5%。 */
public class ExposureCloakRelic extends AbstractArkNsfwRelic {
    public static final String ID = ArkNsfwMod.makeID("ExposureCloakRelic");

    public ExposureCloakRelic() {
        super(ID, "fall_exposure_cloak.png", RelicTier.RARE, LandingSound.FLAT);
    }

    @Override
    public float atDamageModify(float damage, AbstractCard c) {
        if (c != null && c.type == AbstractCard.CardType.ATTACK) {
            return damage * (1.0f + 0.05f * ArkExposureHelper.stage());
        }
        return damage;
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }
}
