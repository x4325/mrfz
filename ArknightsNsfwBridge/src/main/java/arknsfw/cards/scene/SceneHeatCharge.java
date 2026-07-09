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

/** 留影·发情冲锋 */
public class SceneHeatCharge extends AbstractArkNsfwCard {
    public static final String ID = ArkNsfwMod.makeID("SceneHeatCharge");

    public SceneHeatCharge() {
        super(ID, 1, CardType.ATTACK, ColorEnum.SCENE_COLOR, CardRarity.UNCOMMON, CardTarget.ENEMY, "card_scene_scenepassionthrust.png");
        baseDamage = 9;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        int dmg = damage;
        if (NsfwRunStats.excitement >= 50) { dmg *= 2; }
        addToBot(new DamageAction(m, new DamageInfo(p, dmg, damageTypeForTurn), AbstractGameAction.AttackEffect.BLUNT_HEAVY));
        NsfwRunStats.addExcitement(5);
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeDamage(3);
            applyUpgradeDescription();
        }
    }

    @Override
    public AbstractCard makeCopy() { return new SceneHeatCharge(); }
}
