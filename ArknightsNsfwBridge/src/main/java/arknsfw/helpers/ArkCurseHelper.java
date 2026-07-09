package arknsfw.helpers;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.CardLibrary;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import liesecore.helpers.CurseHelper;

import java.util.ArrayList;

public final class ArkCurseHelper {

    private static final String[] EYJA_CURSES = {
            "arknsfw:WombEmberCard",
            "arknsfw:FeverContractCard",
            "arknsfw:AshCollarCard",
            "arknsfw:MagmaEchoCard",
    };

    private static final String[] MUEL_CURSES = {
            "arknsfw:CloneResidueCard",
            "arknsfw:FloodMarkCard",
            "arknsfw:BubbleMuteCard",
            "arknsfw:SeedWombCard",
    };

    private static String[] fiveCharCurses(String prefix) {
        return new String[]{
                "arknsfw:" + prefix + "WombMarkCard",
                "arknsfw:" + prefix + "ShameContractCard",
                "arknsfw:" + prefix + "BindCollarCard",
                "arknsfw:" + prefix + "EchoSeedCard",
        };
    }

    private static String[] currentCharacterCurses() {
        if (ArkCharacterSetup.isEyjaRun()) return EYJA_CURSES;
        if (ArkCharacterSetup.isMuelsyseRun()) return MUEL_CURSES;
        if (ArkCharacterSetup.isSceneRun()) return fiveCharCurses("Scene");
        if (ArkCharacterSetup.isHighmoreRun()) return fiveCharCurses("Highmore");
        if (ArkCharacterSetup.isArchettoRun()) return fiveCharCurses("Archetto");
        if (ArkCharacterSetup.isHarukaRun()) return fiveCharCurses("Haruka");
        if (ArkCharacterSetup.isNymphRun()) return fiveCharCurses("Nymph");
        return null;
    }

    private ArkCurseHelper() {
    }

    public static void addRandomCurseForCurrentCharacter() {
        String[] pool = currentCharacterCurses();
        if (pool != null) {
            addCurse(randomFrom(pool));
        } else {
            CurseHelper.addRandomCurseToDeck();
        }
    }

    /** 战后诅咒卡奖励：按当前 Ark 角色返回对应诅咒池。 */
    public static AbstractCard randomCurseForCurrentCharacter() {
        String[] pool = currentCharacterCurses();
        return pool != null ? copyOrNull(randomFrom(pool)) : null;
    }

    public static void addCurseById(String id) {
        AbstractCard copy = CardLibrary.getCopy(id);
        if (copy != null) {
            addCurse(copy);
        }
    }

    public static void addCurse(AbstractCard card) {
        if (AbstractDungeon.player == null || card == null) {
            return;
        }
        AbstractDungeon.player.masterDeck.addToTop(card.makeCopy());
    }

    public static AbstractRelic randomUnownedCurseRelicForCurrentCharacter() {
        if (AbstractDungeon.player == null || AbstractDungeon.cardRandomRng == null) {
            return null;
        }
        ArrayList<AbstractRelic> pool = new ArrayList<>();
        if (ArkCharacterSetup.isEyjaRun()) {
            addIfMissing(pool, new arknsfw.relics.curses.eyja.VolcanoBrandCurseRelic());
            addIfMissing(pool, new arknsfw.relics.curses.eyja.AshFurnaceCurseRelic());
            addIfMissing(pool, new arknsfw.relics.curses.eyja.BreedingAltarCurseRelic());
        } else if (ArkCharacterSetup.isMuelsyseRun()) {
            addIfMissing(pool, new arknsfw.relics.curses.muel.CloneLoopCurseRelic());
            addIfMissing(pool, new arknsfw.relics.curses.muel.RhineFilthCurseRelic());
            addIfMissing(pool, new arknsfw.relics.curses.muel.OverflowCoreCurseRelic());
        } else if (ArkCharacterSetup.isSceneRun()) {
            addIfMissing(pool, new arknsfw.relics.curses.scene.SceneBrandCurseRelic());
            addIfMissing(pool, new arknsfw.relics.curses.scene.SceneAltarCurseRelic());
            addIfMissing(pool, new arknsfw.relics.curses.scene.SceneLoopCurseRelic());
        } else if (ArkCharacterSetup.isHighmoreRun()) {
            addIfMissing(pool, new arknsfw.relics.curses.highmore.HighmoreBrandCurseRelic());
            addIfMissing(pool, new arknsfw.relics.curses.highmore.HighmoreAltarCurseRelic());
            addIfMissing(pool, new arknsfw.relics.curses.highmore.HighmoreLoopCurseRelic());
        } else if (ArkCharacterSetup.isArchettoRun()) {
            addIfMissing(pool, new arknsfw.relics.curses.archetto.ArchettoBrandCurseRelic());
            addIfMissing(pool, new arknsfw.relics.curses.archetto.ArchettoAltarCurseRelic());
            addIfMissing(pool, new arknsfw.relics.curses.archetto.ArchettoLoopCurseRelic());
        } else if (ArkCharacterSetup.isHarukaRun()) {
            addIfMissing(pool, new arknsfw.relics.curses.haruka.HarukaBrandCurseRelic());
            addIfMissing(pool, new arknsfw.relics.curses.haruka.HarukaAltarCurseRelic());
            addIfMissing(pool, new arknsfw.relics.curses.haruka.HarukaLoopCurseRelic());
        } else if (ArkCharacterSetup.isNymphRun()) {
            addIfMissing(pool, new arknsfw.relics.curses.nymph.NymphBrandCurseRelic());
            addIfMissing(pool, new arknsfw.relics.curses.nymph.NymphAltarCurseRelic());
            addIfMissing(pool, new arknsfw.relics.curses.nymph.NymphLoopCurseRelic());
        }
        if (pool.isEmpty()) {
            return CurseHelper.randomUnownedCurseRelic();
        }
        return pool.get(AbstractDungeon.cardRandomRng.random(pool.size() - 1)).makeCopy();
    }

    private static AbstractCard randomFrom(String[] ids) {
        if (ids == null || ids.length == 0 || AbstractDungeon.cardRandomRng == null) {
            return null;
        }
        // Random.random(n) 是闭区间 [0, n]，必须用 length - 1
        String id = ids[AbstractDungeon.cardRandomRng.random(ids.length - 1)];
        return CardLibrary.getCopy(id);
    }

    private static AbstractCard copyOrNull(AbstractCard card) {
        return card != null ? card.makeCopy() : null;
    }

    private static void addIfMissing(ArrayList<AbstractRelic> pool, AbstractRelic relic) {
        if (!AbstractDungeon.player.hasRelic(relic.relicId)) {
            pool.add(relic);
        }
    }
}
