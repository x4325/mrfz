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

/** 蜜露药水：回复15%生命，兴奋+10 */
public class HoneyDewPotion extends AbstractArkPotion {
    public static final String ID = ArkNsfwMod.makeID("HoneyDewPotion");

    public HoneyDewPotion() {
        super(ID, PotionRarity.COMMON, PotionSize.M, PotionColor.ANCIENT);
    }

    @Override
    public void use(AbstractCreature target) {
        AbstractPlayer p = AbstractDungeon.player;
        if (p == null) {
            return;
        }
        p.heal(Math.max(1, p.maxHealth * 15 / 100));
        NsfwRunStats.addExcitement(10);
    }

    @Override
    public int getPotency(int ascensionLevel) {
        return 15;
    }

    @Override
    public AbstractPotion makeCopy() {
        return new HoneyDewPotion();
    }
}
