package arknsfw.potions.muel;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.potions.AbstractPotion;
import liesecore.helpers.NsfwRunStats;
import arknsfw.ArkNsfwMod;
import arknsfw.potions.AbstractArkPotion;
import arknsfw.powers.muel.CloneEchoPower;
import arknsfw.powers.muel.LeakPower;

public class EchoSludgePotion extends AbstractArkPotion {
    public static final String ID = ArkNsfwMod.makeID("EchoSludgePotion");

    public EchoSludgePotion() {
        super(ID, PotionRarity.RARE, PotionSize.S, PotionColor.SMOKE);
    }

    @Override
    public void use(AbstractCreature target) {
        AbstractPlayer p = AbstractDungeon.player;
        if (p == null) {
            return;
        }
        NsfwRunStats.addExcitement(getPotency());
        NsfwRunStats.addConception(getPotency() / 2, false);
        addToBot(new ApplyPowerAction(p, p, new CloneEchoPower(p, 3), 3));
        addToBot(new ApplyPowerAction(p, p, new LeakPower(p, 1), 1));
        p.damage(new DamageInfo(null, 4, DamageInfo.DamageType.HP_LOSS));
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
        return ascensionLevel >= 2 ? 14 : 12;
    }

    @Override
    public AbstractPotion makeCopy() {
        return new EchoSludgePotion();
    }
}
