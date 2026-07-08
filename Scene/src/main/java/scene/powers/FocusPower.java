package scene.powers;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.actions.common.HealAction;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.StrengthPower;
import scene.SceneMod;

public class FocusPower extends AbstractPower {
    public static final String POWER_ID = SceneMod.makeID("FocusPower");
    private static final PowerStrings ps = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);

    public FocusPower(AbstractCreature owner, int amount) {
        name = ps.NAME;
        ID = POWER_ID;
        this.owner = owner;
        this.amount = amount;
        type = PowerType.BUFF;
        isTurnBased = false;
        updateDescription();
        loadIcon();
    }

    public static int get(AbstractCreature o) {
        AbstractPower p = o.getPower(POWER_ID);
        return p == null ? 0 : p.amount;
    }
    public static boolean spend(AbstractCreature o, int n) {
        AbstractPower p = o.getPower(POWER_ID);
        if (p == null || p.amount < n) return false;
        AbstractDungeon.actionManager.addToBottom(new ReducePowerAction(o, o, POWER_ID, n));
        return true;
    }
    public void stackPower(int n) { super.stackPower(n); updateDescription(); }
    public void updateDescription() { description = ps.DESCRIPTIONS[0] + amount + ps.DESCRIPTIONS[1]; }

    private static com.badlogic.gdx.graphics.Texture ICON_TEX;

    private void loadIcon() {
        try {
            if (ICON_TEX == null) {
                ICON_TEX = new com.badlogic.gdx.graphics.Texture(
                        com.badlogic.gdx.Gdx.files.internal(SceneMod.imgPath("powers/focus_power.png")));
            }
            int w = ICON_TEX.getWidth();
            int h = ICON_TEX.getHeight();
            this.region128 = new com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion(ICON_TEX, 0, 0, w, h);
            this.region48 = new com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion(ICON_TEX, 0, 0, w, h);
        } catch (Exception ignored) {
        }
        if (this.region48 == null) {
            loadRegion("flex");
        }
    }
}
