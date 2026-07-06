package arknsfw.events.eyja;

import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.events.AbstractImageEvent;
import com.megacrit.cardcrawl.localization.EventStrings;
import liesecore.helpers.NsfwRunStats;
import arknsfw.ArkNsfwMod;
import arknsfw.helpers.ArkDebuffHelper;
import arknsfw.helpers.ArkEventImages;
import arknsfw.powers.eyja.AshShamePower;
import arknsfw.powers.eyja.CoreStrainPower;
import arknsfw.powers.eyja.VolcanicFlushPower;

public class EyjaDebuffSealEvent extends AbstractImageEvent {
    public static final String ID = ArkNsfwMod.makeID("EyjaDebuffSealEvent");
    private final EventStrings strings;
    private int choice = -1;
    private int phase = 0;

    public EyjaDebuffSealEvent() {
        super(
                CardCrawlGame.languagePack.getEventString(ID).NAME,
                CardCrawlGame.languagePack.getEventString(ID).DESCRIPTIONS[0],
                ArkEventImages.path(ArkEventImages.EYJA_DEBUFF_SEAL)
        );
        strings = CardCrawlGame.languagePack.getEventString(ID);
        imageEventText.setDialogOption(strings.OPTIONS[0]);
        imageEventText.setDialogOption(strings.OPTIONS[1]);
        imageEventText.setDialogOption(strings.OPTIONS[2]);
    }

    @Override
    protected void buttonEffect(int buttonUsed) {
        if (phase == 0) {
            choice = buttonUsed;
            if (buttonUsed == 0) {
                seal(VolcanicFlushPower.POWER_ID, new VolcanicFlushPower(AbstractDungeon.player, 2));
                NsfwRunStats.addExcitement(15);
                show(strings.DESCRIPTIONS[1]);
            } else if (buttonUsed == 1) {
                seal(AshShamePower.POWER_ID, new AshShamePower(AbstractDungeon.player, 2));
                AbstractDungeon.player.gainGold(50);
                show(strings.DESCRIPTIONS[2]);
            } else {
                seal(CoreStrainPower.POWER_ID, new CoreStrainPower(AbstractDungeon.player, 2));
                NsfwRunStats.addFertility(0, 12, false);
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
