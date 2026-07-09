package arknsfw.relics.fall;

import arknsfw.ArkNsfwMod;
import arknsfw.relics.AbstractArkNsfwRelic;

/** 敏感烙印：堕落模式败北时自动获得并加深。counter=敏感值。
 *  每点敏感值：兴奋获取 +10%，受孕/妊娠进度 +5%。 */
public class SensitivityBrandRelic extends AbstractArkNsfwRelic {
    public static final String ID = ArkNsfwMod.makeID("SensitivityBrandRelic");

    public SensitivityBrandRelic() {
        super(ID, "fall_sensitivity_brand.png", RelicTier.SPECIAL, LandingSound.MAGICAL);
        this.counter = 0;
    }

    public void addPoint() {
        this.counter++;
        flash();
        this.description = getUpdatedDescription();
        this.tips.clear();
        this.tips.add(new com.megacrit.cardcrawl.helpers.PowerTip(this.name, this.description));
        this.initializeTips();
    }

    @Override
    public String getUpdatedDescription() {
        int n = Math.max(0, this.counter);
        return DESCRIPTIONS[0] + n + DESCRIPTIONS[1]
                + (n * 10) + DESCRIPTIONS[2] + (n * 5) + DESCRIPTIONS[3];
    }
}
