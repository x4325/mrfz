package arknsfw.cards.curses.archetto;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import liesecore.helpers.NsfwRunStats;
import arknsfw.ArkNsfwMod;
import arknsfw.cards.AbstractArkCurseCard;
import arknsfw.helpers.ArkCharMechanicsHelper;

public class ArchettoWombMarkCard extends AbstractArkCurseCard {
    public static final String ID = ArkNsfwMod.makeID("ArchettoWombMarkCard");

    public ArchettoWombMarkCard() {
        super(ID, "curse_archetto_archettowombmarkcard.png");
    }

    @Override
    public void triggerWhenDrawn() {
        NsfwRunStats.addExcitement(8); if (NsfwRunStats.pregnant) { NsfwRunStats.addPregnancyProgress(15); } else { NsfwRunStats.addConception(12, false); }
    }

    @Override public void use(AbstractPlayer p, AbstractMonster m) {}
    @Override public void upgrade() {}
    @Override public AbstractCard makeCopy() { return new ArchettoWombMarkCard(); }
}
