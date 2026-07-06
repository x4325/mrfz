package arknsfw.cards.curses.eyja;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import liesecore.helpers.NsfwRunStats;
import arknsfw.ArkNsfwMod;
import arknsfw.cards.AbstractArkCurseCard;
import arknsfw.helpers.ArkCharMechanicsHelper;
import arknsfw.powers.eyja.CoreStrainPower;
import arknsfw.powers.eyja.VolcanicFlushPower;

/** 热症契约：抽到时兴奋 +20、火山潮红 +2、失去 3 生命。 */
public class FeverContractCard extends AbstractArkCurseCard {
    public static final String ID = ArkNsfwMod.makeID("FeverContractCard");

    public FeverContractCard() {
        super(ID, "curse_fever_contract.png");
    }

    @Override
    public void triggerWhenDrawn() {
        NsfwRunStats.addExcitement(20);
        addToBot(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player,
                new VolcanicFlushPower(AbstractDungeon.player, 2), 2));
        AbstractDungeon.player.damage(new DamageInfo(null, 3, DamageInfo.DamageType.HP_LOSS));
        ArkCharMechanicsHelper.gainCloudEnergy(2);
        if (ArkCharMechanicsHelper.fireMarkPowerAmount() >= 2) {
            addToBot(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player,
                    new CoreStrainPower(AbstractDungeon.player, 1), 1));
        }
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
    }

    @Override
    public void upgrade() {
    }

    @Override
    public AbstractCard makeCopy() {
        return new FeverContractCard();
    }
}
