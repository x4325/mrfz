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

/** 抑制剂：兴奋大幅下降 */
public class SuppressantPotion extends AbstractArkPotion {
    public static final String ID = ArkNsfwMod.makeID("SuppressantPotion");

    public SuppressantPotion() {
        super(ID, PotionRarity.COMMON, PotionSize.T, PotionColor.WHITE);
    }

    @Override
    public void use(AbstractCreature target) {
        AbstractPlayer p = AbstractDungeon.player;
        if (p == null) {
            return;
        }
        NsfwRunStats.addExcitement(-getPotency());
    }

    @Override
    public int getPotency(int ascensionLevel) {
        return 40;
    }

    @Override
    public AbstractPotion makeCopy() {
        return new SuppressantPotion();
    }
}
