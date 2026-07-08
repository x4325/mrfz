package highmore.powers;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import highmore.HighmoreMod;

/** 漂流之势：每回合开始获得 amount 层收割。 */
public class DriftPower extends AbstractPower {
    public static final String POWER_ID = HighmoreMod.makeID("DriftPower");
    private static final PowerStrings ps = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);

    public DriftPower(AbstractCreature owner, int amount) {
        name = ps.NAME;
        ID = POWER_ID;
        this.owner = owner;
        this.amount = amount;
        type = PowerType.BUFF;
        isTurnBased = false;
        updateDescription();
        loadIcon();
    }

    @Override
    public void atStartOfTurn() {
        flash();
        addToBot(new ApplyPowerAction(owner, owner, new ReapPower(owner, amount), amount));
    }

    public void stackPower(int n) { super.stackPower(n); updateDescription(); }
    public void updateDescription() { description = ps.DESCRIPTIONS[0] + amount + ps.DESCRIPTIONS[1]; }

    private static com.badlogic.gdx.graphics.Texture ICON_TEX;

    private void loadIcon() {
        try {
            if (ICON_TEX == null) {
                ICON_TEX = new com.badlogic.gdx.graphics.Texture(
                        com.badlogic.gdx.Gdx.files.internal(HighmoreMod.imgPath("powers/drift_power.png")));
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
