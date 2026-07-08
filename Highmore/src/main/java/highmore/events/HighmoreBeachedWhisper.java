package highmore.events;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.events.AbstractImageEvent;
import com.megacrit.cardcrawl.localization.EventStrings;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.relics.Circlet;
import highmore.HighmoreMod;
import highmore.powers.ReapPower;
public class HighmoreBeachedWhisper extends AbstractImageEvent {
    public static final String ID = HighmoreMod.makeID("BeachedWhisper");

    public HighmoreBeachedWhisper() {
        super(
                CardCrawlGame.languagePack.getEventString(ID).NAME,
                CardCrawlGame.languagePack.getEventString(ID).DESCRIPTIONS[0],
                HighmoreMod.imgPath("events/BeachedWhisper.png"));
        this.imageEventText.setDialogOption(CardCrawlGame.languagePack.getEventString(ID).OPTIONS[0]);
        this.imageEventText.setDialogOption(CardCrawlGame.languagePack.getEventString(ID).OPTIONS[1]);
    }

    @Override
    protected void buttonEffect(int buttonPressed) {
        EventStrings STR = CardCrawlGame.languagePack.getEventString(ID);
        switch (buttonPressed) {
            case 0:
                AbstractDungeon.player.increaseMaxHp(4, true);
                imageEventText.updateBodyText(STR.DESCRIPTIONS[1]);
                break;
            case 1:
                // 海的低语属于潮汐治疗，绕过禁疗削减
                highmore.characters.Highmore.tideHealChannel = true;
                AbstractDungeon.player.heal(10);
                highmore.characters.Highmore.tideHealChannel = false;
                imageEventText.updateBodyText(STR.DESCRIPTIONS[2]);
                break;
        }
        this.imageEventText.clearRemainingOptions();
        this.openMap();
    }
}
