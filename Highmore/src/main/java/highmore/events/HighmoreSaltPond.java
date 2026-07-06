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
public class HighmoreSaltPond extends AbstractImageEvent {
    public static final String ID = HighmoreMod.makeID("SaltPond");

    public HighmoreSaltPond() {
        super(
                CardCrawlGame.languagePack.getEventString(ID).NAME,
                CardCrawlGame.languagePack.getEventString(ID).DESCRIPTIONS[0],
                HighmoreMod.imgPath("events/SaltPond.png"));
        this.imageEventText.setDialogOption(CardCrawlGame.languagePack.getEventString(ID).OPTIONS[0]);
        this.imageEventText.setDialogOption(CardCrawlGame.languagePack.getEventString(ID).OPTIONS[1]);
    }

    @Override
    protected void buttonEffect(int buttonPressed) {
        EventStrings STR = CardCrawlGame.languagePack.getEventString(ID);
        switch (buttonPressed) {
            case 0:
                AbstractDungeon.player.gainGold(70);
                imageEventText.updateBodyText(STR.DESCRIPTIONS[1]);
                break;
            case 1:
                AbstractDungeon.player.damage(new DamageInfo(AbstractDungeon.player, 5, DamageInfo.DamageType.HP_LOSS));
                imageEventText.updateBodyText(STR.DESCRIPTIONS[2]);
                break;
        }
        this.imageEventText.clearRemainingOptions();
        this.openMap();
    }
}
