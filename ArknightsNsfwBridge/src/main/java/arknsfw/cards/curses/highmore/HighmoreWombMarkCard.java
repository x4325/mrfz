package arknsfw.cards.curses.highmore;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import liesecore.helpers.NsfwRunStats;
import arknsfw.ArkNsfwMod;
import arknsfw.cards.AbstractArkCurseCard;
import arknsfw.helpers.ArkCharMechanicsHelper;

public class HighmoreWombMarkCard extends AbstractArkCurseCard {
    public static final String ID = ArkNsfwMod.makeID("HighmoreWombMarkCard");

    public HighmoreWombMarkCard() {
        super(ID, "curse_highmore_highmorewombmarkcard.png");
    }

    @Override
    public void triggerWhenDrawn() {
        NsfwRunStats.addExcitement(8); if (NsfwRunStats.pregnant) { NsfwRunStats.addPregnancyProgress(15); } else { NsfwRunStats.addConception(12, false); }
    }

    @Override public void use(AbstractPlayer p, AbstractMonster m) {}
    @Override public void upgrade() {}
    @Override public AbstractCard makeCopy() { return new HighmoreWombMarkCard(); }
}
