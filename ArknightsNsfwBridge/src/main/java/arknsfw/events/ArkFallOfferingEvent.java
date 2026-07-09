package arknsfw.events;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.events.AbstractImageEvent;
import com.megacrit.cardcrawl.localization.EventStrings;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndObtainEffect;
import liesecore.helpers.NsfwRunStats;
import arknsfw.ArkNsfwMod;
import arknsfw.helpers.ArkFallMode;

/**
 * 堕落供物（堕落模式固定事件）：色情卡 / 色情遗物 / 色情状态 三选一。
 * 不注册进事件池，由 ArkEventBiasPatch 在堕落模式下直接实例化。
 */
public class ArkFallOfferingEvent extends AbstractImageEvent {

    public static final String ID = ArkNsfwMod.makeID("FallOffering");
    private static final EventStrings STR = CardCrawlGame.languagePack.getEventString(ID);

    private int screen = 0;

    public ArkFallOfferingEvent() {
        super(STR.NAME, STR.DESCRIPTIONS[0], "images/events/theLab.jpg");
        this.imageEventText.setDialogOption(STR.OPTIONS[0]);
        this.imageEventText.setDialogOption(STR.OPTIONS[1]);
        this.imageEventText.setDialogOption(STR.OPTIONS[2]);
        this.imageEventText.setDialogOption(STR.OPTIONS[3]);
    }

    @Override
    protected void buttonEffect(int buttonPressed) {
        if (screen != 0) {
            openMap();
            return;
        }
        switch (buttonPressed) {
            case 0: { // 色情卡
                AbstractCard card = ArkFallMode.randomNsfwCard(
                        rollRarity(), AbstractDungeon.cardRandomRng);
                if (card != null) {
                    AbstractDungeon.effectList.add(new ShowCardAndObtainEffect(
                            card.makeCopy(), Settings.WIDTH / 2.0F, Settings.HEIGHT / 2.0F));
                }
                NsfwRunStats.addExcitement(8);
                finish(1);
                break;
            }
            case 1: { // 色情遗物
                AbstractRelic relic = ArkFallMode.randomNsfwRelic(AbstractDungeon.relicRng, false);
                if (relic != null) {
                    AbstractDungeon.getCurrRoom().spawnRelicAndObtain(
                            Settings.WIDTH / 2.0F, Settings.HEIGHT / 2.0F, relic);
                } else {
                    AbstractDungeon.player.gainGold(50);
                }
                NsfwRunStats.addExcitement(8);
                finish(2);
                break;
            }
            case 2: { // 色情状态（祝福）
                AbstractRelic blessing = ArkFallMode.randomBlessing(AbstractDungeon.relicRng);
                if (blessing != null) {
                    AbstractDungeon.getCurrRoom().spawnRelicAndObtain(
                            Settings.WIDTH / 2.0F, Settings.HEIGHT / 2.0F, blessing);
                } else {
                    // 祝福集齐后：改为泄压
                    NsfwRunStats.addExcitement(-30);
                }
                NsfwRunStats.addConception(6, false);
                finish(3);
                break;
            }
            default:
                finish(4);
                break;
        }
    }

    private AbstractCard.CardRarity rollRarity() {
        int roll = AbstractDungeon.cardRandomRng.random(99);
        if (roll < 10) {
            return AbstractCard.CardRarity.RARE;
        }
        if (roll < 45) {
            return AbstractCard.CardRarity.UNCOMMON;
        }
        return AbstractCard.CardRarity.COMMON;
    }

    private void finish(int descIndex) {
        screen = 1;
        this.imageEventText.updateBodyText(STR.DESCRIPTIONS[Math.min(descIndex, STR.DESCRIPTIONS.length - 1)]);
        this.imageEventText.clearAllDialogs();
        this.imageEventText.setDialogOption(STR.OPTIONS[4]);
    }
}
