package arknsfw.events.highmore;

import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import liesecore.helpers.NsfwRunStats;
import arknsfw.ArkNsfwMod;
import com.megacrit.cardcrawl.events.AbstractImageEvent;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.EventStrings;

public class HighmoreDebuffSealEvent extends AbstractImageEvent {
    public static final String ID = ArkNsfwMod.makeID("HighmoreDebuffSeal");
    private static final EventStrings S = CardCrawlGame.languagePack.getEventString(ID);

    public HighmoreDebuffSealEvent() {
        super(S.NAME, S.DESCRIPTIONS[0], ArkNsfwMod.makeImagePath("events/event_highmore_debuff_seal.png"));
        imageEventText.setDialogOption(S.OPTIONS[0]);
    }

    @Override protected void buttonEffect(int buttonUsed) { NsfwRunStats.addExcitement(5); openMap(); }
}
