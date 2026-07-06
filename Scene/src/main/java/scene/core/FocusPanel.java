package scene.core;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.ui.panels.AbstractPanel;
import scene.powers.FocusPower;

public class FocusPanel extends AbstractPanel {

    public FocusPanel() {
        super(0.0f, 0.0f, 0.0f, 0.0f, (com.badlogic.gdx.graphics.Texture) null, false);
    }

    public static int getAmount() {
        if (AbstractDungeon.player == null) return 0;
        AbstractPower p = AbstractDungeon.player.getPower(FocusPower.POWER_ID);
        return p == null ? 0 : p.amount;
    }

    public void update() {}

    @Override
    public void render(SpriteBatch sb) {}
}
