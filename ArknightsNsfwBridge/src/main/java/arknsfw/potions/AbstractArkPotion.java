package arknsfw.potions;

import basemod.abstracts.CustomPotion;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.PowerTip;
import com.megacrit.cardcrawl.localization.PotionStrings;
import com.megacrit.cardcrawl.potions.AbstractPotion;
import arknsfw.ArkNsfwMod;

public abstract class AbstractArkPotion extends CustomPotion {

    protected final PotionStrings potionStrings;

    protected AbstractArkPotion(
            String id,
            PotionRarity rarity,
            PotionSize size,
            PotionColor color
    ) {
        super("", id, rarity, size, color);
        potionStrings = CardCrawlGame.languagePack.getPotionString(id);
        if (potionStrings != null) {
            name = potionStrings.NAME;
        }
        refreshDescription();
    }

    protected void refreshDescription() {
        if (potionStrings == null) {
            return;
        }
        potency = getPotency();
        description = buildDescription(potency);
        tips.clear();
        tips.add(new PowerTip(name, description));
    }

    protected String buildDescription(int amount) {
        if (potionStrings.DESCRIPTIONS.length == 1) {
            return potionStrings.DESCRIPTIONS[0];
        }
        if (potionStrings.DESCRIPTIONS.length == 2) {
            return potionStrings.DESCRIPTIONS[0] + amount + potionStrings.DESCRIPTIONS[1];
        }
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < potionStrings.DESCRIPTIONS.length; i++) {
            sb.append(potionStrings.DESCRIPTIONS[i]);
            if (i % 2 == 0 && i + 1 < potionStrings.DESCRIPTIONS.length) {
                sb.append(amount);
            }
        }
        return sb.toString();
    }

    @Override
    public void initializeData() {
        super.initializeData();
        refreshDescription();
    }

    protected void addToBot(AbstractGameAction action) {
        AbstractDungeon.actionManager.addToBottom(action);
    }

    @Override
    public abstract AbstractPotion makeCopy();
}
