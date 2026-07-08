package arknsfw.helpers;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.WeakPower;
import liesecore.api.SpecialIntentHandler;
import liesecore.helpers.EnemyIntentType;
import liesecore.helpers.LieseEnemyFields;
import liesecore.helpers.IntentComboApplier;
import liesecore.helpers.IntentComboHelper;
import liesecore.helpers.NsfwRunStats;
import liesecore.helpers.UiHelper;
import liesecore.powers.AphrodisiacPower;
import liesecore.powers.ExposedPower;
import liesecore.powers.FakePregnantPower;
import arknsfw.powers.eyja.AshShamePower;
import arknsfw.powers.eyja.CoreStrainPower;
import arknsfw.powers.eyja.GeothermalPower;
import arknsfw.powers.eyja.VolcanicFlushPower;
import arknsfw.powers.muel.BubbleGagPower;
import arknsfw.powers.muel.CloneEchoPower;
import arknsfw.powers.muel.HydrationPower;
import arknsfw.powers.muel.LeakPower;

/** 艾雅 / 缪尔赛思专用敌人特殊意图，避免套用龙娘丧威、缚尾等 debuff。 */
public final class ArkSpecialIntentHandler implements SpecialIntentHandler {

    public static final ArkSpecialIntentHandler EYJA = new ArkSpecialIntentHandler(true);
    public static final ArkSpecialIntentHandler MUEL = new ArkSpecialIntentHandler(false);

    private final boolean eyja;

    private ArkSpecialIntentHandler(boolean eyja) {
        this.eyja = eyja;
    }

    @Override
    public void execute(AbstractMonster monster, EnemyIntentType type) {
        AbstractPlayer player = AbstractDungeon.player;
        if (player == null || monster == null) {
            return;
        }
        if (eyja) {
            executeEyja(monster, player, type);
        } else {
            executeMuel(monster, player, type);
        }
    }

    private void executeEyja(AbstractMonster monster, AbstractPlayer player, EnemyIntentType type) {
        switch (type) {
            case HARASS:
                NsfwRunStats.addExcitement(liesecore.relics.EarplugRelic.scaleIntentExcitement(12));
                NsfwRunStats.addConception(4, false);
                addPower(player, monster, new VolcanicFlushPower(player, 1));
                addPower(player, monster, new ExposedPower(player, 1));
                ArkCharMechanicsHelper.gainCloudEnergy(1);
                break;
            case BIND:
                NsfwRunStats.addExcitement(liesecore.relics.EarplugRelic.scaleIntentExcitement(8));
                addPower(player, monster, new AshShamePower(player, 1));
                addPower(player, monster, new CoreStrainPower(player, 1));
                addPower(player, monster, new WeakPower(player, 1, false));
                addPower(player, monster, new arknsfw.powers.fall.PleasureDependencePower(player, 1));
                break;
            case CREAMPIE: {
                boolean combo = IntentComboHelper.consumeIfMatches(EnemyIntentType.CREAMPIE);
                NsfwRunStats.addExcitement(combo ? 24 : 18);
                NsfwRunStats.addConception(0, true);
                if (combo) {
                    NsfwRunStats.addConception(8, false);
                    if (NsfwRunStats.pregnant) {
                        NsfwRunStats.addPregnancyProgress(10);
                    }
                    UiHelper.showCenterText(player, "余韵·中出");
                }
                addToBot(new DamageAction(player,
                        new DamageInfo(monster, IntentComboApplier.creampieDamage(combo)),
                        AbstractGameAction.AttackEffect.SLASH_DIAGONAL));
                ArkCharMechanicsHelper.applyFireMarkPower(player, 1);
                break;
            }
            case INJECT:
                NsfwRunStats.addExcitement(10);
                NsfwRunStats.addFertility(0, 7, false);
                addPower(player, monster, new FakePregnantPower(player, 2));
                addPower(player, monster, new AphrodisiacPower(player, 1));
                addPower(player, monster, new arknsfw.powers.fall.AphroToxinPower(player, 4));
                ArkCharMechanicsHelper.gainCloudEnergy(1);
                break;
            case TEASE:
                NsfwRunStats.addExcitement(12);
                addPower(player, monster, new GeothermalPower(player, 1));
                addPower(player, monster, new VolcanicFlushPower(player, 1));
                addPower(player, monster, new arknsfw.powers.fall.SensitivePower(player, 2));
                ArkCharMechanicsHelper.applyFireMarkPower(player, 1);
                break;
            case HUMILIATE: {
                boolean combo = IntentComboHelper.consumeIfMatches(EnemyIntentType.HUMILIATE);
                NsfwRunStats.addExcitement(combo ? 22 : 15);
                NsfwRunStats.addConception(combo ? 10 : 6, false);
                if (combo) {
                    UiHelper.showCenterText(player, "余韵·羞辱");
                }
                addPower(player, monster, new AshShamePower(player, combo ? 2 : 1));
                addPower(player, monster, new ExposedPower(player, 1));
                break;
            }
            case BRAND: {
                boolean combo = IntentComboHelper.consumeIfMatches(EnemyIntentType.BRAND);
                NsfwRunStats.addExcitement(combo ? 24 : 18);
                NsfwRunStats.addConception(combo ? 12 : 8, false);
                if (combo) {
                    UiHelper.showCenterText(player, "余韵·烙印");
                }
                addPower(player, monster, new VolcanicFlushPower(player, combo ? 3 : 2));
                addPower(player, monster, new ExposedPower(player, combo ? 3 : 2));
                break;
            }
            case OVERRUN: {
                boolean combo = IntentComboHelper.consumeIfMatches(EnemyIntentType.OVERRUN);
                NsfwRunStats.addExcitement(combo ? 16 : 12);
                NsfwRunStats.addFertility(combo ? 10 : 6, combo ? 12 : 8, false);
                if (combo) {
                    UiHelper.showCenterText(player, "余韵·沦陷");
                }
                addToBot(new DamageAction(player,
                        new DamageInfo(monster, IntentComboApplier.overrunDamage(combo)),
                        AbstractGameAction.AttackEffect.BLUNT_HEAVY));
                addPower(player, monster, new CoreStrainPower(player, 1));
                addPower(player, monster, new VolcanicFlushPower(player, 1));
                break;
            }
            case SEAL:
                ArkDebuffIntentHelper.executeSeal(
                        monster, player, LieseEnemyFields.debuffIntentTarget.get(monster), true);
                break;
            case EXPLOIT:
                ArkDebuffIntentHelper.executeExploit(
                        monster, player, LieseEnemyFields.debuffIntentTarget.get(monster), true);
                break;
            default:
                break;
        }
    }

    private void executeMuel(AbstractMonster monster, AbstractPlayer player, EnemyIntentType type) {
        switch (type) {
            case HARASS:
                NsfwRunStats.addExcitement(liesecore.relics.EarplugRelic.scaleIntentExcitement(12));
                NsfwRunStats.addConception(4, false);
                addPower(player, monster, new CloneEchoPower(player, 1));
                addPower(player, monster, new ExposedPower(player, 1));
                break;
            case BIND:
                NsfwRunStats.addExcitement(liesecore.relics.EarplugRelic.scaleIntentExcitement(8));
                addPower(player, monster, new BubbleGagPower(player, 1));
                addPower(player, monster, new LeakPower(player, 1));
                addPower(player, monster, new WeakPower(player, 1, false));
                addPower(player, monster, new arknsfw.powers.fall.PleasureDependencePower(player, 1));
                break;
            case CREAMPIE: {
                boolean combo = IntentComboHelper.consumeIfMatches(EnemyIntentType.CREAMPIE);
                NsfwRunStats.addExcitement(combo ? 24 : 18);
                NsfwRunStats.addConception(0, true);
                if (combo) {
                    NsfwRunStats.addConception(8, false);
                    if (NsfwRunStats.pregnant) {
                        NsfwRunStats.addPregnancyProgress(10);
                    }
                    UiHelper.showCenterText(player, "余韵·中出");
                }
                addToBot(new DamageAction(player,
                        new DamageInfo(monster, IntentComboApplier.creampieDamage(combo)),
                        AbstractGameAction.AttackEffect.SLASH_DIAGONAL));
                if (ArkCharMechanicsHelper.hasManifold()) {
                    ArkCharMechanicsHelper.boostManifold(1);
                }
                break;
            }
            case INJECT:
                NsfwRunStats.addExcitement(10);
                NsfwRunStats.addFertility(0, 7, false);
                addPower(player, monster, new FakePregnantPower(player, 2));
                addPower(player, monster, new AphrodisiacPower(player, 1));
                addPower(player, monster, new HydrationPower(player, 1));
                addPower(player, monster, new arknsfw.powers.fall.AphroToxinPower(player, 4));
                break;
            case TEASE:
                NsfwRunStats.addExcitement(12);
                addPower(player, monster, new HydrationPower(player, 1));
                addPower(player, monster, new CloneEchoPower(player, 1));
                addPower(player, monster, new arknsfw.powers.fall.SensitivePower(player, 2));
                if (ArkCharMechanicsHelper.isCultivating()) {
                    ArkCharMechanicsHelper.applyRootage(player, 1);
                }
                break;
            case HUMILIATE: {
                boolean combo = IntentComboHelper.consumeIfMatches(EnemyIntentType.HUMILIATE);
                NsfwRunStats.addExcitement(combo ? 22 : 15);
                NsfwRunStats.addConception(combo ? 10 : 6, false);
                if (combo) {
                    UiHelper.showCenterText(player, "余韵·羞辱");
                }
                addPower(player, monster, new CloneEchoPower(player, combo ? 3 : 2));
                addPower(player, monster, new LeakPower(player, 1));
                break;
            }
            case BRAND: {
                boolean combo = IntentComboHelper.consumeIfMatches(EnemyIntentType.BRAND);
                NsfwRunStats.addExcitement(combo ? 24 : 18);
                NsfwRunStats.addConception(combo ? 12 : 8, false);
                if (combo) {
                    UiHelper.showCenterText(player, "余韵·烙印");
                }
                addPower(player, monster, new LeakPower(player, combo ? 3 : 2));
                addPower(player, monster, new ExposedPower(player, combo ? 3 : 2));
                break;
            }
            case OVERRUN: {
                boolean combo = IntentComboHelper.consumeIfMatches(EnemyIntentType.OVERRUN);
                NsfwRunStats.addExcitement(combo ? 16 : 12);
                NsfwRunStats.addFertility(combo ? 10 : 6, combo ? 12 : 8, false);
                if (combo) {
                    UiHelper.showCenterText(player, "余韵·沦陷");
                }
                addToBot(new DamageAction(player,
                        new DamageInfo(monster, IntentComboApplier.overrunDamage(combo)),
                        AbstractGameAction.AttackEffect.BLUNT_HEAVY));
                addPower(player, monster, new LeakPower(player, 1));
                addPower(player, monster, new CloneEchoPower(player, 2));
                if (ArkCharMechanicsHelper.hasManifold()) {
                    ArkCharMechanicsHelper.boostManifold(1);
                }
                break;
            }
            case SEAL:
                ArkDebuffIntentHelper.executeSeal(
                        monster, player, LieseEnemyFields.debuffIntentTarget.get(monster), false);
                break;
            case EXPLOIT:
                ArkDebuffIntentHelper.executeExploit(
                        monster, player, LieseEnemyFields.debuffIntentTarget.get(monster), false);
                break;
            default:
                break;
        }
    }

    private static void addPower(AbstractPlayer player, AbstractMonster monster, com.megacrit.cardcrawl.powers.AbstractPower power) {
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(player, monster, power, power.amount));
    }

    private static void addToBot(AbstractGameAction action) {
        AbstractDungeon.actionManager.addToBottom(action);
    }
}
