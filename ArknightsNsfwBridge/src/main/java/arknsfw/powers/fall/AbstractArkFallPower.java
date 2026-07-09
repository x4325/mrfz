package arknsfw.powers.fall;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import liesecore.helpers.TextureHelper;
import arknsfw.ArkNsfwMod;

/** 堕落模式 buff/debuff 基类：读本地化 + 自绘 32×32 图标（缺失回退原版图集）。 */
public abstract class AbstractArkFallPower extends AbstractPower {

    protected final PowerStrings strings;

    protected AbstractArkFallPower(String id, AbstractCreature owner, int amount,
                                   PowerType type, String iconFile, String vanillaRegion) {
        this.ID = id;
        this.owner = owner;
        this.amount = amount;
        this.type = type;
        this.strings = CardCrawlGame.languagePack.getPowerStrings(id);
        this.name = strings != null ? strings.NAME : id;
        loadIcon(iconFile, vanillaRegion);
        updateDescription();
    }

    private void loadIcon(String iconFile, String vanillaRegion) {
        try {
            String path = ArkNsfwMod.makeImagePath("powers/" + iconFile + ".png");
            Texture texture = TextureHelper.getTexture(path);
            if (texture != null && texture.getWidth() == 32 && texture.getHeight() == 32) {
                this.img = texture;
                this.region128 = new TextureAtlas.AtlasRegion(texture, 0, 0, 32, 32);
                this.region48 = new TextureAtlas.AtlasRegion(texture, 0, 0, 32, 32);
                return;
            }
        } catch (Exception ignored) {
        }
        loadRegion(vanillaRegion);
        if (this.region48 == null) {
            loadRegion("flex");
        }
    }

    protected String str(int index) {
        if (strings == null || strings.DESCRIPTIONS == null || index >= strings.DESCRIPTIONS.length) {
            return "";
        }
        return strings.DESCRIPTIONS[index];
    }
}
