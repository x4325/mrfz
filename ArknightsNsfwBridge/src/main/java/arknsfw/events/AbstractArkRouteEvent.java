package arknsfw.events;

import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.events.AbstractImageEvent;
import com.megacrit.cardcrawl.localization.EventStrings;
import arknsfw.helpers.ArkRunProgress;

public abstract class AbstractArkRouteEvent extends AbstractImageEvent {

    protected final EventStrings strings;
    protected final ArkRunProgress.Route route;
    protected int choice = -1;
    protected int phase = 0;

    protected AbstractArkRouteEvent(String id, String imagePath, ArkRunProgress.Route route, int initialOptions) {
        super(
                CardCrawlGame.languagePack.getEventString(id).NAME,
                CardCrawlGame.languagePack.getEventString(id).DESCRIPTIONS[0],
                imagePath
        );
        this.strings = CardCrawlGame.languagePack.getEventString(id);
        this.route = route;
        for (int i = 0; i < initialOptions; i++) {
            imageEventText.setDialogOption(strings.OPTIONS[i]);
        }
    }

    protected void showResult(int descIndex) {
        imageEventText.updateBodyText(strings.DESCRIPTIONS[descIndex]);
        imageEventText.clearAllDialogs();
        imageEventText.setDialogOption(strings.OPTIONS[strings.OPTIONS.length - 1]);
        phase = 1;
    }

    protected void lockThisRoute() {
        ArkRunProgress.lockRoute(route);
    }

    @Override
    protected void buttonEffect(int buttonUsed) {
        if (phase == 0) {
            choice = buttonUsed;
            onFirstChoice(buttonUsed);
        } else {
            openMap();
        }
    }

    protected abstract void onFirstChoice(int buttonUsed);
}
