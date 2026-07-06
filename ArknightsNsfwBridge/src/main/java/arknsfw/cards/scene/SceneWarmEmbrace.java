package arknsfw.cards.scene;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import liesecore.helpers.NsfwRunStats;
import arknsfw.ArkNsfwMod;
import arknsfw.cards.AbstractArkNsfwCard;
import arknsfw.helpers.ArkCharMechanicsHelper;
import scene.core.ColorEnum;
import scene.powers.FocusPower;

public class SceneWarmEmbrace extends AbstractArkNsfwCard {
    public static final String ID = ArkNsfwMod.makeID("SceneWarmEmbrace");

    public SceneWarmEmbrace() {
        super(ID, 1, CardType.SKILL, ColorEnum.SCENE_COLOR, CardRarity.UNCOMMON, CardTarget.SELF, "card_scene_scenewarmembrace.png");
        
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        NsfwRunStats.addExcitement(10);
        ArkCharMechanicsHelper.applyFocusPower(p, 2);
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            // no upgrade
        }
    }

    @Override
    public AbstractCard makeCopy() { return new SceneWarmEmbrace(); }
}
