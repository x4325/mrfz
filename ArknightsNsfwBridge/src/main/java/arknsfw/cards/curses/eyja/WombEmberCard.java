package arknsfw.cards.curses.eyja;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import liesecore.helpers.NsfwRunStats;
import arknsfw.ArkNsfwMod;
import arknsfw.cards.AbstractArkCurseCard;
import arknsfw.helpers.ArkCharMechanicsHelper;

/** 子宫余烬：抽到时受孕 +15；若已怀孕则妊娠进度 +18。 */
public class WombEmberCard extends AbstractArkCurseCard {
    public static final String ID = ArkNsfwMod.makeID("WombEmberCard");

    public WombEmberCard() {
        super(ID, "curse_womb_ember.png");
    }

    @Override
    public void triggerWhenDrawn() {
        NsfwRunStats.addExcitement(8);
        ArkCharMechanicsHelper.applyFireMarkPower(com.megacrit.cardcrawl.dungeons.AbstractDungeon.player, 1);
        ArkCharMechanicsHelper.gainCloudEnergy(1);
        if (NsfwRunStats.pregnant) {
            NsfwRunStats.addPregnancyProgress(18);
        } else {
            NsfwRunStats.addConception(15, false);
            NsfwRunStats.addFertility(0, 10, false);
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
        return new WombEmberCard();
    }
}
