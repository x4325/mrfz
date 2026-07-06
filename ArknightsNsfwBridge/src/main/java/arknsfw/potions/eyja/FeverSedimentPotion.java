package arknsfw.potions.eyja;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.potions.AbstractPotion;
import liesecore.helpers.NsfwRunStats;
import arknsfw.ArkNsfwMod;
import arknsfw.potions.AbstractArkPotion;
import arknsfw.powers.eyja.CoreStrainPower;
import arknsfw.powers.eyja.VolcanicFlushPower;

public class FeverSedimentPotion extends AbstractArkPotion {
    public static final String ID = ArkNsfwMod.makeID("FeverSedimentPotion");

    public FeverSedimentPotion() {
        super(ID, PotionRarity.UNCOMMON, PotionSize.S, PotionColor.POISON);
    }

    @Override
    public void use(AbstractCreature target) {
        AbstractPlayer p = AbstractDungeon.player;
        if (p == null) {
            return;
        }
        int flush = 2;
        int strain = 1;
        NsfwRunStats.addExcitement(getPotency());
        NsfwRunStats.addConception(getPotency() / 2, false);
        addToBot(new ApplyPowerAction(p, p, new VolcanicFlushPower(p, flush), flush));
        addToBot(new ApplyPowerAction(p, p, new CoreStrainPower(p, strain), strain));
        p.damage(new DamageInfo(null, 3, DamageInfo.DamageType.HP_LOSS));
    }

    @Override
    public void initializeData() {
        super.initializeData();
        potency = getPotency();
        description = potionStrings.DESCRIPTIONS[0] + potency + potionStrings.DESCRIPTIONS[1] + (potency / 2)
                + potionStrings.DESCRIPTIONS[2];
        tips.clear();
        tips.add(new com.megacrit.cardcrawl.helpers.PowerTip(name, description));
    }

    @Override
    public int getPotency(int ascensionLevel) {
        return ascensionLevel >= 2 ? 16 : 12;
    }

    @Override
    public AbstractPotion makeCopy() {
        return new FeverSedimentPotion();
    }
}
