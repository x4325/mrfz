package arknsfw.cards.curses.scene;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import liesecore.helpers.NsfwRunStats;
import arknsfw.ArkNsfwMod;
import arknsfw.cards.AbstractArkCurseCard;
import arknsfw.helpers.ArkCharMechanicsHelper;

public class SceneWombMarkCard extends AbstractArkCurseCard {
    public static final String ID = ArkNsfwMod.makeID("SceneWombMarkCard");

    public SceneWombMarkCard() {
        super(ID, "curse_scene_scenewombmarkcard.png");
    }

    @Override
    public void triggerWhenDrawn() {
        NsfwRunStats.addExcitement(8); if (NsfwRunStats.pregnant) { NsfwRunStats.addPregnancyProgress(15); } else { NsfwRunStats.addConception(12, false); }
    }

    @Override public void use(AbstractPlayer p, AbstractMonster m) {}
    @Override public void upgrade() {}
    @Override public AbstractCard makeCopy() { return new SceneWombMarkCard(); }
}
