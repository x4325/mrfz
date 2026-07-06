package arknsfw.relics.curses.eyja;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import liesecore.api.NsfwCharacterRegistry;
import liesecore.helpers.NsfwRunStats;
import arknsfw.ArkNsfwMod;
import arknsfw.helpers.ArkCharMechanicsHelper;
import arknsfw.relics.curses.AbstractArkCurseRelic;

/** 鐏扮儸鐔旂倝锛氭瘡鍥炲悎鍏村 +12锛涙墦鍑烘敾鍑荤墝澶卞幓鐢熷懡銆傝壘闆咃細鏀诲嚮闄勫甫鐐庢伅骞舵秷鑰椾簯閲忓崌娓┿€?*/
public class AshFurnaceCurseRelic extends AbstractArkCurseRelic {
    public static final String ID = ArkNsfwMod.makeID("AshFurnaceCurseRelic");

    public AshFurnaceCurseRelic() {
        super(ID, "curse_ash_furnace.png", LandingSound.MAGICAL);
    }

    @Override
    public void atTurnStart() {
        if (NsfwCharacterRegistry.isActive()) {
            flash();
            NsfwRunStats.addExcitement(12 + ArkCharMechanicsHelper.fireMarkPowerAmount() * 2);
            ArkCharMechanicsHelper.gainCloudEnergy(1);
        }
    }

    @Override
    public void onPlayCard(AbstractCard c, AbstractMonster m) {
        if (!NsfwCharacterRegistry.isActive() || c == null || c.type != AbstractCard.CardType.ATTACK) {
            return;
        }
        int hpLoss = ArkCharMechanicsHelper.pyrobreathActive(c) ? 3 : 2;
        AbstractDungeon.player.damage(new DamageInfo(null, hpLoss, DamageInfo.DamageType.HP_LOSS));
        ArkCharMechanicsHelper.markPyrobreath(c);
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

    @Override
    public AbstractRelic makeCopy() {
        return new AshFurnaceCurseRelic();
    }
}

