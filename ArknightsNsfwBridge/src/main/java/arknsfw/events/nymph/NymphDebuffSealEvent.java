package arknsfw.events.nymph;

import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.events.AbstractImageEvent;
import com.megacrit.cardcrawl.localization.EventStrings;
import liesecore.helpers.NsfwRunStats;
import arknsfw.ArkNsfwMod;
import arknsfw.helpers.ArkDebuffHelper;
import arknsfw.powers.nymph.HeartGnawPower;

/** 刻印事件：永久封印本角色的专属 debuff，换取强力补偿。 */
public class NymphDebuffSealEvent extends AbstractImageEvent {
    public static final String ID = ArkNsfwMod.makeID("NymphDebuffSeal");
    private final EventStrings strings;
    private int phase = 0;

    public NymphDebuffSealEvent() {
        super(
                CardCrawlGame.languagePack.getEventString(ID).NAME,
                CardCrawlGame.languagePack.getEventString(ID).DESCRIPTIONS[0],
                ArkNsfwMod.makeImagePath("events/event_nymph_debuff_seal.png")
        );
        strings = CardCrawlGame.languagePack.getEventString(ID);
        imageEventText.setDialogOption(strings.OPTIONS[0]);
        imageEventText.setDialogOption(strings.OPTIONS[1]);
    }

    @Override
    protected void buttonEffect(int buttonUsed) {
        if (phase == 0) {
            if (buttonUsed == 0) {
                if (!ArkDebuffHelper.playerHasDebuff(HeartGnawPower.POWER_ID)) {
                    ArkDebuffHelper.applyAndLock(AbstractDungeon.player, new HeartGnawPower(AbstractDungeon.player, 2));
                } else {
                    ArkDebuffHelper.lockPermanent(HeartGnawPower.POWER_ID);
                    AbstractDungeon.player.getPower(HeartGnawPower.POWER_ID).updateDescription();
                }
                NsfwRunStats.addFertility(12, 8, false);
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
