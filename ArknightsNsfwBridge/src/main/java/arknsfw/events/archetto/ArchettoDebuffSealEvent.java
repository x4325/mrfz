package arknsfw.events.archetto;

import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import liesecore.helpers.NsfwRunStats;
import arknsfw.ArkNsfwMod;
import com.megacrit.cardcrawl.events.AbstractImageEvent;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.EventStrings;

public class ArchettoDebuffSealEvent extends AbstractImageEvent {
    public static final String ID = ArkNsfwMod.makeID("ArchettoDebuffSeal");
    private static final EventStrings S = CardCrawlGame.languagePack.getEventString(ID);

    public ArchettoDebuffSealEvent() {
        super(S.NAME, S.DESCRIPTIONS[0], ArkNsfwMod.makeImagePath("events/event_archetto_debuff_seal.png"));
        imageEventText.setDialogOption(S.OPTIONS[0]);
    }

    @Override protected void buttonEffect(int buttonUsed) { NsfwRunStats.addExcitement(5); openMap(); }
}
