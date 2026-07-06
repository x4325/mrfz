package arknsfw.cards.curses.haruka;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import liesecore.helpers.NsfwRunStats;
import arknsfw.ArkNsfwMod;
import arknsfw.cards.AbstractArkCurseCard;
import arknsfw.helpers.ArkCharMechanicsHelper;

public class HarukaEchoSeedCard extends AbstractArkCurseCard {
    public static final String ID = ArkNsfwMod.makeID("HarukaEchoSeedCard");

    public HarukaEchoSeedCard() {
        super(ID, "curse_haruka_harukaechoseedcard.png");
    }

    @Override
    public void triggerWhenDrawn() {
        NsfwRunStats.addExcitement(8); NsfwRunStats.addConception(8, false); if (NsfwRunStats.pregnant) NsfwRunStats.addPregnancyProgress(8);
    }

    @Override public void use(AbstractPlayer p, AbstractMonster m) {}
    @Override public void upgrade() {}
    @Override public AbstractCard makeCopy() { return new HarukaEchoSeedCard(); }
}
