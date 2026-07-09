package arknsfw.events.highmore;

import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.events.AbstractImageEvent;
import com.megacrit.cardcrawl.localization.EventStrings;
import liesecore.helpers.NsfwRunStats;
import arknsfw.ArkNsfwMod;
import arknsfw.helpers.ArkDebuffHelper;
import arknsfw.powers.highmore.TideBrandPower;

/** 刻印事件：永久封印本角色的专属 debuff，换取强力补偿。 */
public class HighmoreDebuffSealEvent extends AbstractImageEvent {
    public static final String ID = ArkNsfwMod.makeID("HighmoreDebuffSeal");
    private final EventStrings strings;
    private int phase = 0;

    public HighmoreDebuffSealEvent() {
        super(
                CardCrawlGame.languagePack.getEventString(ID).NAME,
                CardCrawlGame.languagePack.getEventString(ID).DESCRIPTIONS[0],
                ArkNsfwMod.makeImagePath("events/event_highmore_debuff_seal.png")
        );
        strings = CardCrawlGame.languagePack.getEventString(ID);
        imageEventText.setDialogOption(strings.OPTIONS[0]);
        imageEventText.setDialogOption(strings.OPTIONS[1]);
    }

    @Override
    protected void buttonEffect(int buttonUsed) {
        if (phase == 0) {
            if (buttonUsed == 0) {
                if (!ArkDebuffHelper.playerHasDebuff(TideBrandPower.POWER_ID)) {
                    ArkDebuffHelper.applyAndLock(AbstractDungeon.player, new TideBrandPower(AbstractDungeon.player, 2));
                } else {
                    ArkDebuffHelper.lockPermanent(TideBrandPower.POWER_ID);
                    AbstractDungeon.player.getPower(TideBrandPower.POWER_ID).updateDescription();
                }
                AbstractDungeon.player.increaseMaxHp(6, true);
                show(strings.DESCRIPTIONS[1]);
            } else {
                NsfwRunStats.addExcitement(5);
                show(strings.DESCRIPTIONS[2]);
            }
        } else {
            openMap();
        }
    }

    private void show(String body) {
        imageEventText.updateBodyText(body);
        imageEventText.clearAllDialogs();
        imageEventText.setDialogOption(strings.OPTIONS[2]);
        phase = 1;
    }
}
