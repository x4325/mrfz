package arknsfw.potions.eyja;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.potions.AbstractPotion;
import liesecore.helpers.NsfwRunStats;
import arknsfw.ArkNsfwMod;
import arknsfw.potions.AbstractArkPotion;
import arknsfw.powers.eyja.CoreFeverPower;
import arknsfw.powers.eyja.GeothermalPower;

public class HeatLingerPotion extends AbstractArkPotion {
    public static final String ID = ArkNsfwMod.makeID("HeatLingerPotion");

    public HeatLingerPotion() {
        super(ID, PotionRarity.COMMON, PotionSize.S, PotionColor.FIRE);
    }

    @Override
    public void use(AbstractCreature target) {
        AbstractPlayer p = AbstractDungeon.player;
        if (p == null) {
            return;
        }
        NsfwRunStats.addExcitement(getPotency());
        addToBot(new ApplyPowerAction(p, p, new CoreFeverPower(p, 1), 1));
        addToBot(new ApplyPowerAction(p, p, new GeothermalPower(p, 1), 1));
    }

    @Override
    public int getPotency(int ascensionLevel) {
        return ascensionLevel >= 2 ? 10 : 12;
    }

    @Override
    public AbstractPotion makeCopy() {
        return new HeatLingerPotion();
    }
}
