package arknsfw.cards.curses.haruka;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import liesecore.helpers.NsfwRunStats;
import arknsfw.ArkNsfwMod;
import arknsfw.cards.AbstractArkCurseCard;
import arknsfw.helpers.ArkCharMechanicsHelper;

public class HarukaWombMarkCard extends AbstractArkCurseCard {
    public static final String ID = ArkNsfwMod.makeID("HarukaWombMarkCard");

    public HarukaWombMarkCard() {
        super(ID, "curse_haruka_harukawombmarkcard.png");
    }

    @Override
    public void triggerWhenDrawn() {
        NsfwRunStats.addExcitement(8); if (NsfwRunStats.pregnant) { NsfwRunStats.addPregnancyProgress(15); } else { NsfwRunStats.addConception(12, false); }
    }

    @Override public void use(AbstractPlayer p, AbstractMonster m) {}
    @Override public void upgrade() {}
    @Override public AbstractCard makeCopy() { return new HarukaWombMarkCard(); }
}
