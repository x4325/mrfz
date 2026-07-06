package haruka.core;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.ui.panels.AbstractPanel;
import haruka.powers.PyroPower;

public class PyroPanel extends AbstractPanel {

    public PyroPanel() {
        super(0.0f, 0.0f, 0.0f, 0.0f, (com.badlogic.gdx.graphics.Texture) null, false);
    }

    public static int getAmount() {
        if (AbstractDungeon.player == null) return 0;
        AbstractPower p = AbstractDungeon.player.getPower(PyroPower.POWER_ID);
        return p == null ? 0 : p.amount;
    }

    public void update() {}

    @Override
    public void render(SpriteBatch sb) {}
}
