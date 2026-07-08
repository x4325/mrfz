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

/** 快感炸弹：全体伤害=兴奋/4（上限=潜能） */
public class PleasureBombPotion extends AbstractArkPotion {
    public static final String ID = ArkNsfwMod.makeID("PleasureBombPotion");

    public PleasureBombPotion() {
        super(ID, PotionRarity.RARE, PotionSize.M, PotionColor.FIRE);
    }

    @Override
    public void use(AbstractCreature target) {
        AbstractPlayer p = AbstractDungeon.player;
        if (p == null) {
            return;
        }
        int dmg = Math.max(5, Math.min(NsfwRunStats.excitement / 4, getPotency()));
        addToBot(new DamageAllEnemiesAction(p, DamageInfo.createDamageMatrix(dmg, true),
                DamageInfo.DamageType.THORNS, AbstractGameAction.AttackEffect.FIRE));
    }

    @Override
    public int getPotency(int ascensionLevel) {
        return 30;
    }

    @Override
    public AbstractPotion makeCopy() {
        return new PleasureBombPotion();
    }
}
