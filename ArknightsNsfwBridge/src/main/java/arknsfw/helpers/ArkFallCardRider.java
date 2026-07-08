package arknsfw.helpers;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageRandomEnemyAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import liesecore.helpers.NsfwRunStats;

/**
 * 堕落模式·色情附加（骑乘效果）：原有卡牌保留全部机制，打出时额外触发色情效果，
 * 并与拘束/欲望装备联动。这样角色原机制的强度全部保留，色情度叠在上面。
 *
 * 攻击牌：兴奋+2（跳蛋×2）；兴奋≥50 时快感外溢——随机敌人受（3+敏感值）伤害（淫纹+2）。
 * 技能牌：兴奋≥30 时获得 2 格挡（绳缚+2）。
 * 能力牌：受孕+3。
 */
public final class ArkFallCardRider {

    private ArkFallCardRider() {
    }

    public static void onCardUsed(AbstractCard card) {
        if (card == null || !ArkFallMode.active() || AbstractDungeon.player == null) {
            return;
        }
        AbstractRoom room = AbstractDungeon.getCurrRoom();
        if (room == null || room.phase != AbstractRoom.RoomPhase.COMBAT) {
            return;
        }
        AbstractPlayer p = AbstractDungeon.player;
        switch (card.type) {
            case ATTACK: {
                int excite = p.hasRelic("arknsfw:VibeEggRelic") ? 4 : 2;
                ArkSafeStats.addExcitementDeferred(excite);
                if (NsfwRunStats.excitement >= 50 && AbstractDungeon.getMonsters() != null
                        && !AbstractDungeon.getMonsters().areMonstersBasicallyDead()) {
                    int dmg = 3 + ArkSensitivity.points()
                            + (p.hasRelic("arknsfw:BodyCrestRelic") ? 2 : 0);
                    AbstractDungeon.actionManager.addToBottom(new DamageRandomEnemyAction(
                            new DamageInfo(p, dmg, DamageInfo.DamageType.THORNS),
                            AbstractGameAction.AttackEffect.FIRE));
                }
                break;
            }
            case SKILL: {
                if (NsfwRunStats.excitement >= 30) {
                    int block = 2 + (p.hasRelic("arknsfw:RopeBindRelic") ? 2 : 0);
                    AbstractDungeon.actionManager.addToBottom(new GainBlockAction(p, block));
                }
                break;
            }
            case POWER: {
                ArkSafeStats.addConceptionDeferred(3, false);
                break;
            }
            default:
                break;
        }
    }
}
