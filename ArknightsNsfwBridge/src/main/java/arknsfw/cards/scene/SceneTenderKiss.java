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
import com.megacrit.cardcrawl.actions.common.DamageAction;

public class SceneTenderKiss extends AbstractArkNsfwCard {
    public static final String ID = ArkNsfwMod.makeID("SceneTenderKiss");

    public SceneTenderKiss() {
        super(ID, 0, CardType.SKILL, ColorEnum.SCENE_COLOR, CardRarity.COMMON, CardTarget.ENEMY, "card_scene_scenetenderkiss.png");
        baseMagicNumber = magicNumber = 5;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new DamageAction(m, new DamageInfo(p, magicNumber, DamageInfo.DamageType.THORNS), AbstractGameAction.AttackEffect.NONE));
        NsfwRunStats.addExcitement(12);
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeMagicNumber(3);
        }
    }

    @Override
    public AbstractCard makeCopy() { return new SceneTenderKiss(); }
}
