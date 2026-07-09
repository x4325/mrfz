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

/** 发情香水：敌全体虚弱+易伤各2，兴奋+15 */
public class HeatPerfumePotion extends AbstractArkPotion {
    public static final String ID = ArkNsfwMod.makeID("HeatPerfumePotion");

    public HeatPerfumePotion() {
        super(ID, PotionRarity.RARE, PotionSize.M, PotionColor.FIRE);
    }

    @Override
    public void use(AbstractCreature target) {
        AbstractPlayer p = AbstractDungeon.player;
        if (p == null) {
            return;
        }
        NsfwRunStats.addExcitement(15);
        for (AbstractMonster mo : AbstractDungeon.getMonsters().monsters) {
            if (!mo.isDead && !mo.isDying) {
                addToBot(new ApplyPowerAction(mo, p, new WeakPower(mo, 2, false), 2));
                addToBot(new ApplyPowerAction(mo, p, new VulnerablePower(mo, 2, false), 2));
            }
        }
    }

    @Override
    public int getPotency(int ascensionLevel) {
        return 15;
    }

    @Override
    public AbstractPotion makeCopy() {
        return new HeatPerfumePotion();
    }
}
