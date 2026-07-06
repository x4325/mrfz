package arknsfw.events.muel;

import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.events.AbstractImageEvent;
import com.megacrit.cardcrawl.localization.EventStrings;
import liesecore.helpers.NsfwRunStats;
import arknsfw.ArkNsfwMod;
import arknsfw.helpers.ArkDebuffHelper;
import arknsfw.helpers.ArkEventImages;
import arknsfw.powers.muel.BubbleGagPower;
import arknsfw.powers.muel.CloneEchoPower;
import arknsfw.powers.muel.LeakPower;

public class MuelDebuffSealEvent extends AbstractImageEvent {
    public static final String ID = ArkNsfwMod.makeID("MuelDebuffSealEvent");
    private final EventStrings strings;
    private int phase = 0;

    public MuelDebuffSealEvent() {
        super(
                CardCrawlGame.languagePack.getEventString(ID).NAME,
                CardCrawlGame.languagePack.getEventString(ID).DESCRIPTIONS[0],
                ArkEventImages.path(ArkEventImages.MUEL_DEBUFF_SEAL)
        );
        strings = CardCrawlGame.languagePack.getEventString(ID);
        imageEventText.setDialogOption(strings.OPTIONS[0]);
        imageEventText.setDialogOption(strings.OPTIONS[1]);
        imageEventText.setDialogOption(strings.OPTIONS[2]);
    }

    @Override
    protected void buttonEffect(int buttonUsed) {
        if (phase == 0) {
            if (buttonUsed == 0) {
                seal(BubbleGagPower.POWER_ID, new BubbleGagPower(AbstractDungeon.player, 2));
                NsfwRunStats.addConception(8, false);
                show(strings.DESCRIPTIONS[1]);
            } else if (buttonUsed == 1) {
                seal(LeakPower.POWER_ID, new LeakPower(AbstractDungeon.player, 2));
                NsfwRunStats.addExcitement(20);
                show(strings.DESCRIPTIONS[2]);
            } else {
                seal(CloneEchoPower.POWER_ID, new CloneEchoPower(AbstractDungeon.player, 2));
                NsfwRunStats.addFertility(10, 10, true);
                show(strings.DESCRIPTIONS[3]);
            }
        } else {
            openMap();
        }
    }

    private void seal(String id, com.megacrit.cardcrawl.powers.AbstractPower fresh) {
        if (!ArkDebuffHelper.playerHasDebuff(id)) {
            ArkDebuffHelper.applyAndLock(AbstractDungeon.player, fresh);
        } else {
            ArkDebuffHelper.lockPermanent(id);
            AbstractDungeon.player.getPower(id).updateDescription();
        }
    }

    private void show(String body) {
        imageEventText.updateBodyText(body);
        imageEventText.clearAllDialogs();
        imageEventText.setDialogOption(strings.OPTIONS[3]);
        phase = 1;
    }
}
