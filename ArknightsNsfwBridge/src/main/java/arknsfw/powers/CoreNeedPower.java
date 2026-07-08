package arknsfw.powers;

import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import liesecore.helpers.NsfwRunStats;
import arknsfw.ArkNsfwMod;
import arknsfw.helpers.ArkCharMechanicsHelper;

/** 核心渴求：每回合开始兴奋 +5/层，并获得 1 层本角色资源/层。 */
public class CoreNeedPower extends AbstractPower {
    public static final String POWER_ID = ArkNsfwMod.makeID("CoreNeedPower");
    private static final PowerStrings STR = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);

    public CoreNeedPower(AbstractCreature owner, int amount) {
        name = STR.NAME;
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
        NsfwRunStats.addExcitement(5 * amount);
        if (owner instanceof AbstractPlayer) {
            AbstractPlayer p = (AbstractPlayer) owner;
            // 每个 apply 内部都会检查当前角色，只有匹配的会生效
            ArkCharMechanicsHelper.applyReapPower(p, amount);
            ArkCharMechanicsHelper.applyFocusPower(p, amount);
            ArkCharMechanicsHelper.applyAimPower(p, amount);
            ArkCharMechanicsHelper.applyPyroPower(p, amount);
            ArkCharMechanicsHelper.applyHexPower(p, amount);
        }
    }

    public void stackPower(int n) { super.stackPower(n); updateDescription(); }
    public void updateDescription() { description = STR.DESCRIPTIONS[0] + (5 * amount) + STR.DESCRIPTIONS[1] + amount + STR.DESCRIPTIONS[2]; }

    private static com.badlogic.gdx.graphics.Texture ICON_TEX;

    private void loadIcon() {
        try {
            if (ICON_TEX == null) {
                ICON_TEX = new com.badlogic.gdx.graphics.Texture(
                        com.badlogic.gdx.Gdx.files.internal(ArkNsfwMod.makeImagePath("powers/core_need.png")));
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
