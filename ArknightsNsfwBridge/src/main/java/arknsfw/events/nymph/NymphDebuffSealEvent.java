package arknsfw.events.nymph;

import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import liesecore.helpers.NsfwRunStats;
import arknsfw.ArkNsfwMod;
import com.megacrit.cardcrawl.events.AbstractImageEvent;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.EventStrings;

public class NymphDebuffSealEvent extends AbstractImageEvent {
    public static final String ID = ArkNsfwMod.makeID("NymphDebuffSeal");
    private static final EventStrings S = CardCrawlGame.languagePack.getEventString(ID);

    public NymphDebuffSealEvent() {
        super(S.NAME, S.DESCRIPTIONS[0], ArkNsfwMod.makeImagePath("events/event_nymph_debuff_seal.png"));
        imageEventText.setDialogOption(S.OPTIONS[0]);
    }

    @Override protected void buttonEffect(int buttonUsed) { NsfwRunStats.addExcitement(5); openMap(); }
}
