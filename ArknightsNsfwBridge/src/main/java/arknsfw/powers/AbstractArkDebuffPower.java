package arknsfw.powers;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.powers.AbstractPower;
import liesecore.helpers.TextureHelper;
import arknsfw.ArkNsfwMod;
import arknsfw.helpers.ArkDebuffHelper;

/** 方舟角色专属 debuff；可被事件「刻印」为永久无法解除。 */
public abstract class AbstractArkDebuffPower extends AbstractPower {

    protected AbstractArkDebuffPower(String id, AbstractCreature owner, int amount) {
        this.ID = id;
        this.owner = owner;
        this.amount = amount;
        this.type = PowerType.DEBUFF;
        this.isTurnBased = false;
    }

    /** CC0 裁剪图标（32×32）；缺失时回退原版 power 图集。 */
    protected void loadDebuffIcon(String iconFile, String vanillaRegion) {
        String path = ArkNsfwMod.makeImagePath("powers/" + iconFile + ".png");
        Texture texture = TextureHelper.getTexture(path);
        if (texture != null && texture.getWidth() == 32 && texture.getHeight() == 32) {
            this.img = texture;
            int w = 32;
            int h = 32;
            this.region128 = new TextureAtlas.AtlasRegion(texture, 0, 0, w, h);
            this.region128.packedWidth = w;
            this.region128.packedHeight = h;
            this.region128.originalWidth = w;
            this.region128.originalHeight = h;
            this.region48 = new TextureAtlas.AtlasRegion(texture, 0, 0, w, h);
            this.region48.packedWidth = w;
            this.region48.packedHeight = h;
            this.region48.originalWidth = w;
            this.region48.originalHeight = h;
        } else {
            loadRegion(vanillaRegion);
        }
        if (this.region48 == null) {
            loadRegion("flex");
        }
    }

    public boolean isPermanentlyLocked() {
        return ArkDebuffHelper.isLocked(ID);
    }
}
