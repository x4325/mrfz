package arknsfw.potions.shared;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.potions.AbstractPotion;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.VulnerablePower;
import com.megacrit.cardcrawl.powers.WeakPower;
import liesecore.helpers.NsfwRunStats;
import arknsfw.ArkNsfwMod;
import arknsfw.helpers.ArkCharDebuffs;
import arknsfw.helpers.ArkCharMechanicsHelper;
import arknsfw.helpers.ArkDebuffHelper;
import arknsfw.potions.AbstractArkPotion;

/** 堕落原液：专属印记+2，兴奋+30，获得2点能量 */
public class CorruptionEssencePotion extends AbstractArkPotion {
    public static final String ID = ArkNsfwMod.makeID("CorruptionEssencePotion");

    public CorruptionEssencePotion() {
        super(ID, PotionRarity.RARE, PotionSize.ANVIL, PotionColor.POISON);
    }

    @Override
    public void use(AbstractCreature target) {
        AbstractPlayer p = AbstractDungeon.player;
        if (p == null) {
            return;
        }
        AbstractPower brand = ArkCharDebuffs.fresh(p, 2);
        if (brand != null) {
            ArkDebuffHelper.apply(p, brand);
        }
        NsfwRunStats.addExcitement(30);
        addToBot(new GainEnergyAction(2));
    }

    @Override
    public int getPotency(int ascensionLevel) {
        return 30;
    }

    @Override
    public AbstractPotion makeCopy() {
        return new CorruptionEssencePotion();
    }
}
