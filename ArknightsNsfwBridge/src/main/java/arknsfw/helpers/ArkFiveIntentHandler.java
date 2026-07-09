package arknsfw.helpers;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
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

/** 五名自制干员共用的敌人调教意图：以各自专属羞耻印记为核心。 */
public final class ArkFiveIntentHandler implements SpecialIntentHandler {

    public static final ArkFiveIntentHandler INSTANCE = new ArkFiveIntentHandler();

    private ArkFiveIntentHandler() {
    }

    @Override
    public void execute(AbstractMonster monster, EnemyIntentType type) {
        AbstractPlayer player = AbstractDungeon.player;
        if (player == null || monster == null) {
            return;
        }
        switch (type) {
            case HARASS:
                NsfwRunStats.addExcitement(liesecore.relics.EarplugRelic.scaleIntentExcitement(12));
                NsfwRunStats.addConception(4, false);
                addPower(player, monster, new ExposedPower(player, 1));
                break;
            case BIND:
                NsfwRunStats.addExcitement(liesecore.relics.EarplugRelic.scaleIntentExcitement(8));
                addPower(player, monster, ArkCharDebuffs.fresh(player, 1));
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
                break;
            }
            case INJECT:
                NsfwRunStats.addExcitement(10);
                NsfwRunStats.addFertility(0, 7, false);
                addPower(player, monster, new FakePregnantPower(player, 2));
                addPower(player, monster, new AphrodisiacPower(player, 1));
                addPower(player, monster, new arknsfw.powers.fall.AphroToxinPower(player, 4));
                break;
            case TEASE:
                NsfwRunStats.addExcitement(12);
                addPower(player, monster, ArkCharDebuffs.fresh(player, 1));
                addPower(player, monster, new arknsfw.powers.fall.SensitivePower(player, 2));
                break;
            case HUMILIATE: {
                boolean combo = IntentComboHelper.consumeIfMatches(EnemyIntentType.HUMILIATE);
                NsfwRunStats.addExcitement(combo ? 22 : 15);
                NsfwRunStats.addConception(combo ? 10 : 6, false);
                if (combo) {
                    UiHelper.showCenterText(player, "余韵·羞辱");
                }
                addPower(player, monster, ArkCharDebuffs.fresh(player, combo ? 2 : 1));
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
                addPower(player, monster, ArkCharDebuffs.fresh(player, combo ? 2 : 1));
                addPower(player, monster, new ExposedPower(player, combo ? 3 : 2));
                ArkExposureHelper.tear();
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
                ArkExposureHelper.tear();
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

    private static void addPower(AbstractPlayer player, AbstractMonster source, AbstractPower power) {
        if (power != null) {
            addToBot(new ApplyPowerAction(player, source, power, power.amount));
        }
    }

    private static void addToBot(AbstractGameAction action) {
        AbstractDungeon.actionManager.addToBottom(action);
    }
}
