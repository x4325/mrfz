package arknsfw.cards.curses.scene;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import liesecore.helpers.NsfwRunStats;
import arknsfw.ArkNsfwMod;
import arknsfw.cards.AbstractArkCurseCard;
import arknsfw.helpers.ArkCharMechanicsHelper;

public class SceneShameContractCard extends AbstractArkCurseCard {
    public static final String ID = ArkNsfwMod.makeID("SceneShameContractCard");

    public SceneShameContractCard() {
        super(ID, "curse_scene_sceneshamecontractcard.png");
    }

    @Override
    public void triggerWhenDrawn() {
        NsfwRunStats.addExcitement(10); com.megacrit.cardcrawl.dungeons.AbstractDungeon.player.damage(new com.megacrit.cardcrawl.cards.DamageInfo(null, 3));
    }

    @Override public void use(AbstractPlayer p, AbstractMonster m) {}
    @Override public void upgrade() {}
    @Override public AbstractCard makeCopy() { return new SceneShameContractCard(); }
}
