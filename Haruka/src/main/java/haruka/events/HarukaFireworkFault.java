package haruka.events;

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
import haruka.HarukaMod;
import haruka.powers.PyroPower;
public class HarukaFireworkFault extends AbstractImageEvent {
    public static final String ID = HarukaMod.makeID("FireworkFault");

    public HarukaFireworkFault() {
        super(
                CardCrawlGame.languagePack.getEventString(ID).NAME,
                CardCrawlGame.languagePack.getEventString(ID).DESCRIPTIONS[0],
                HarukaMod.imgPath("events/FireworkFault.png"));
        this.imageEventText.setDialogOption(CardCrawlGame.languagePack.getEventString(ID).OPTIONS[0]);
        this.imageEventText.setDialogOption(CardCrawlGame.languagePack.getEventString(ID).OPTIONS[1]);
    }

    @Override
    protected void buttonEffect(int buttonPressed) {
        EventStrings STR = CardCrawlGame.languagePack.getEventString(ID);
        switch (buttonPressed) {
            case 0:
                AbstractDungeon.player.damage(new DamageInfo(AbstractDungeon.player, 6, DamageInfo.DamageType.HP_LOSS));
                imageEventText.updateBodyText(STR.DESCRIPTIONS[1]);
                break;
            case 1:
                AbstractDungeon.player.gainGold(65);
                imageEventText.updateBodyText(STR.DESCRIPTIONS[2]);
                break;
        }
        this.imageEventText.clearRemainingOptions();
        this.openMap();
    }
}
