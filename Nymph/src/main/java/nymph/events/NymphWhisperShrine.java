package nymph.events;

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
import nymph.NymphMod;
import nymph.powers.HexPower;
import nymph.powers.FearPower;
import nymph.powers.HeartLockPower;
public class NymphWhisperShrine extends AbstractImageEvent {
    public static final String ID = NymphMod.makeID("WhisperShrine");

    public NymphWhisperShrine() {
        super(
                CardCrawlGame.languagePack.getEventString(ID).NAME,
                CardCrawlGame.languagePack.getEventString(ID).DESCRIPTIONS[0],
                NymphMod.imgPath("events/WhisperShrine.png"));
        this.imageEventText.setDialogOption(CardCrawlGame.languagePack.getEventString(ID).OPTIONS[0]);
        this.imageEventText.setDialogOption(CardCrawlGame.languagePack.getEventString(ID).OPTIONS[1]);
    }

    @Override
    protected void buttonEffect(int buttonPressed) {
        EventStrings STR = CardCrawlGame.languagePack.getEventString(ID);
        switch (buttonPressed) {
            case 0:
                AbstractDungeon.player.heal(8);
                imageEventText.updateBodyText(STR.DESCRIPTIONS[1]);
                break;
            case 1:
                AbstractDungeon.actionManager.addToBottom(new GainBlockAction(AbstractDungeon.player, AbstractDungeon.player, 10));
                imageEventText.updateBodyText(STR.DESCRIPTIONS[2]);
                break;
        }
        this.imageEventText.clearRemainingOptions();
        this.openMap();
    }
}
