package arknsfw.potions.eyja;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.HealAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.potions.AbstractPotion;
import liesecore.helpers.NsfwRunStats;
import arknsfw.ArkNsfwMod;
import arknsfw.helpers.ArkCharMechanicsHelper;
import arknsfw.potions.AbstractArkPotion;
import arknsfw.powers.eyja.GeothermalPower;

public class CloudWarmTonicPotion extends AbstractArkPotion {
    public static final String ID = ArkNsfwMod.makeID("CloudWarmTonicPotion");

    public CloudWarmTonicPotion() {
        super(ID, PotionRarity.COMMON, PotionSize.M, PotionColor.FIRE);
    }

    @Override
    public void use(AbstractCreature target) {
        AbstractPlayer p = AbstractDungeon.player;
        if (p == null) {
            return;
        }
        int cloud = 2;
        int heat = 1;
        ArkCharMechanicsHelper.gainCloudEnergy(cloud);
        NsfwRunStats.addExcitement(cloud * 4);
        addToBot(new ApplyPowerAction(p, p, new GeothermalPower(p, heat), heat));
    }

    @Override
    public int getPotency(int ascensionLevel) {
        return 8;
    }

    @Override
    public AbstractPotion makeCopy() {
        return new CloudWarmTonicPotion();
    }
}
