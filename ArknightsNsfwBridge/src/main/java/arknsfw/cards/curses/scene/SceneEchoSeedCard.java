package arknsfw.cards.curses.scene;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import liesecore.helpers.NsfwRunStats;
import arknsfw.ArkNsfwMod;
import arknsfw.cards.AbstractArkCurseCard;
import arknsfw.helpers.ArkCharMechanicsHelper;

public class SceneEchoSeedCard extends AbstractArkCurseCard {
    public static final String ID = ArkNsfwMod.makeID("SceneEchoSeedCard");

    public SceneEchoSeedCard() {
        super(ID, "curse_scene_sceneechoseedcard.png");
    }

    @Override
    public void triggerWhenDrawn() {
        NsfwRunStats.addExcitement(8); NsfwRunStats.addConception(8, false); if (NsfwRunStats.pregnant) NsfwRunStats.addPregnancyProgress(8);
    }

    @Override public void use(AbstractPlayer p, AbstractMonster m) {}
    @Override public void upgrade() {}
    @Override public AbstractCard makeCopy() { return new SceneEchoSeedCard(); }
}
