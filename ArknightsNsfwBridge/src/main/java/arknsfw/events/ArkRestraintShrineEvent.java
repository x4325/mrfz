package arknsfw.events;

import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.events.AbstractImageEvent;
import com.megacrit.cardcrawl.localization.EventStrings;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import liesecore.helpers.NsfwRunStats;
import arknsfw.ArkNsfwMod;

import java.util.ArrayList;

/**
 * 拘束祭坛：戴上一件随机拘束装备换取奖励，或付钱挣脱一件已戴上的。
 * 全角色通用事件（不写入 EVENT_META，走原版事件池）。
 */
public class ArkRestraintShrineEvent extends AbstractImageEvent {

    public static final String ID = ArkNsfwMod.makeID("RestraintShrine");
    private static final EventStrings STR = CardCrawlGame.languagePack.getEventString(ID);

    private static final String[] EQUIPMENT_IDS = {
            arknsfw.relics.equipment.RestraintCuffsRelic.ID,
            arknsfw.relics.equipment.LeashRelic.ID,
            arknsfw.relics.equipment.ChastityBeltRelic.ID,
            arknsfw.relics.equipment.LaceGarterRelic.ID,
            arknsfw.relics.equipment.BellTagRelic.ID,
            arknsfw.relics.equipment.ClothGagRelic.ID,
            arknsfw.relics.equipment.RingGagRelic.ID,
            arknsfw.relics.equipment.LaceBlindfoldRelic.ID,
            arknsfw.relics.equipment.VibeEggRelic.ID,
            arknsfw.relics.equipment.BodyCrestRelic.ID,
    };

    private static final int REMOVE_COST = 60;
    private int screen = 0;

    public ArkRestraintShrineEvent() {
        super(STR.NAME, STR.DESCRIPTIONS[0], "images/events/theLab.jpg");
        ArrayList<String> missing = missingEquipment();
        if (missing.isEmpty()) {
            this.imageEventText.setDialogOption(STR.OPTIONS[3]);
        } else {
            this.imageEventText.setDialogOption(STR.OPTIONS[0]);
        }
        if (ownedEquipment().isEmpty() || AbstractDungeon.player.gold < REMOVE_COST) {
            this.imageEventText.setDialogOption(STR.OPTIONS[4]);
        } else {
            this.imageEventText.setDialogOption(STR.OPTIONS[1]);
        }
        this.imageEventText.setDialogOption(STR.OPTIONS[2]);
    }

    private static ArrayList<String> missingEquipment() {
        ArrayList<String> out = new ArrayList<>();
        for (String id : EQUIPMENT_IDS) {
            if (!AbstractDungeon.player.hasRelic(id)) {
                out.add(id);
            }
        }
        return out;
    }

    private static ArrayList<String> ownedEquipment() {
        ArrayList<String> out = new ArrayList<>();
        for (String id : EQUIPMENT_IDS) {
            if (AbstractDungeon.player.hasRelic(id)) {
                out.add(id);
            }
        }
        return out;
    }

    @Override
    protected void buttonEffect(int buttonPressed) {
        if (this.screen != 0) {
            openMap();
            return;
        }
        switch (buttonPressed) {
            case 0: {
                ArrayList<String> missing = missingEquipment();
                if (!missing.isEmpty()) {
                    String id = missing.get(AbstractDungeon.miscRng.random(missing.size() - 1));
                    AbstractRelic relic = com.megacrit.cardcrawl.helpers.RelicLibrary.getRelic(id).makeCopy();
                    AbstractDungeon.getCurrRoom().spawnRelicAndObtain(
                            com.megacrit.cardcrawl.core.Settings.WIDTH / 2.0F,
                            com.megacrit.cardcrawl.core.Settings.HEIGHT / 2.0F, relic);
                    AbstractDungeon.player.increaseMaxHp(4, true);
                    NsfwRunStats.addExcitement(10);
                    this.imageEventText.updateBodyText(STR.DESCRIPTIONS[1]);
                } else {
                    this.imageEventText.updateBodyText(STR.DESCRIPTIONS[0]);
                }
                finishScreen();
                break;
            }
            case 1: {
                ArrayList<String> owned = ownedEquipment();
                if (!owned.isEmpty() && AbstractDungeon.player.gold >= REMOVE_COST) {
                    AbstractDungeon.player.loseGold(REMOVE_COST);
                    String id = owned.get(AbstractDungeon.miscRng.random(owned.size() - 1));
                    AbstractDungeon.player.loseRelic(id);
                    this.imageEventText.updateBodyText(STR.DESCRIPTIONS[2]);
                } else {
                    this.imageEventText.updateBodyText(STR.DESCRIPTIONS[0]);
                }
                finishScreen();
                break;
            }
            default:
                openMap();
                break;
        }
    }

    private void finishScreen() {
        this.screen = 1;
        this.imageEventText.clearAllDialogs();
        this.imageEventText.setDialogOption(STR.OPTIONS[2]);
    }
}
