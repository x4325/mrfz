package arknsfw.cards.curses.muel;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import liesecore.helpers.NsfwRunStats;
import arknsfw.ArkNsfwMod;
import arknsfw.cards.AbstractArkCurseCard;
import arknsfw.helpers.ArkCharMechanicsHelper;

/** 播种子宫：抽到时视为强力中出；若已怀孕则妊娠进度 +22。 */
public class SeedWombCard extends AbstractArkCurseCard {
    public static final String ID = ArkNsfwMod.makeID("SeedWombCard");

    public SeedWombCard() {
        super(ID, "curse_seed_womb.png");
    }

    @Override
    public void triggerWhenDrawn() {
        NsfwRunStats.addExcitement(14);
        if (NsfwRunStats.pregnant) {
            NsfwRunStats.addPregnancyProgress(22 + ArkCharMechanicsHelper.rootageAmount() * 2);
        } else {
            int fertA = 14 + (ArkCharMechanicsHelper.hasManifold() ? 6 : 0);
            int fertB = 12 + (ArkCharMechanicsHelper.hasManifold() ? 6 : 0);
            NsfwRunStats.addFertility(fertA, fertB, true);
        }
        if (ArkCharMechanicsHelper.hasManifold()) {
            ArkCharMechanicsHelper.boostManifold(1);
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
        return new SeedWombCard();
    }
}
