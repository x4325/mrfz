package arknsfw.cards.scene;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.EnergizedPower;
import com.megacrit.cardcrawl.powers.VulnerablePower;
import com.megacrit.cardcrawl.powers.WeakPower;
import com.megacrit.cardcrawl.ui.panels.EnergyPanel;
import liesecore.helpers.NsfwRunStats;
import arknsfw.ArkNsfwMod;
import arknsfw.cards.AbstractArkNsfwCard;
import arknsfw.helpers.ArkCharMechanicsHelper;
import arknsfw.helpers.ArkExposureHelper;
import arknsfw.helpers.ArkGearSetHelper;
import arknsfw.helpers.ArkSafeStats;
import arknsfw.helpers.ArkSensitivity;
import arknsfw.helpers.LieseCompat;
import scene.core.ColorEnum;

/** 留影·敏感刻深 */
public class SceneSenseDeepen extends AbstractArkNsfwCard {
    public static final String ID = ArkNsfwMod.makeID("SceneSenseDeepen");

    public SceneSenseDeepen() {
        super(ID, 0, CardType.SKILL, ColorEnum.SCENE_COLOR, CardRarity.RARE, CardTarget.SELF, "card_scene_sceneblushmark.png");
        baseBlock = 8;
        this.exhaust = true;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        ArkSensitivity.addPoint(p);
        addToBot(new GainBlockAction(p, block));
        addToBot(new DrawCardAction(p, 2));
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeBlock(4);
            applyUpgradeDescription();
        }
    }

    @Override
    public AbstractCard makeCopy() { return new SceneSenseDeepen(); }
}
