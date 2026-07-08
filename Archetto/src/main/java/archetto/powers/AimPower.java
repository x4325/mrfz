package archetto.powers;

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
import archetto.ArchettoMod;

public class AimPower extends AbstractPower {
    public static final String POWER_ID = ArchettoMod.makeID("AimPower");
    private static final PowerStrings ps = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);

    public AimPower(AbstractCreature owner, int amount) {
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
    /** 箭无虚发：下一次攻击不消耗瞄准。 */
    public static boolean preserveNext = false;

    public float atDamageGive(float damage, DamageInfo.DamageType type, AbstractCard card) {
        if (amount > 0 && card.type == AbstractCard.CardType.ATTACK) {
            int per = 2 + (owner != null && owner.hasPower(HawkEyePower.POWER_ID) ? 1 : 0);
            return damage + amount * per;
        }
        return damage;
    }

    /** 同一张攻击牌只结算一次消耗；AOE 的每个目标都吃满加成后再统一清层。 */
    private boolean pendingConsume = false;

    public void onAttack(DamageInfo info, int damageAmount, AbstractCreature target) {
        if (preserveNext && info.owner == this.owner && info.type != DamageInfo.DamageType.THORNS) {
            preserveNext = false;
            return;
        }
        if (info.owner == this.owner && info.type != DamageInfo.DamageType.THORNS
                && amount > 0 && !pendingConsume) {
            pendingConsume = true;
            final int toRemove = amount;
            addToBot(new ReducePowerAction(owner, owner, POWER_ID, toRemove));
            addToBot(new AbstractGameAction() {
                @Override
                public void update() {
                    pendingConsume = false;
                    this.isDone = true;
                }
            });
        }
    }

    @Override
    public void atEndOfTurn(boolean isPlayer) {
        pendingConsume = false;
    }
    public void stackPower(int n) { super.stackPower(n); updateDescription(); }
    public void updateDescription() { description = ps.DESCRIPTIONS[0] + (amount * 2) + ps.DESCRIPTIONS[1]; }

    private static com.badlogic.gdx.graphics.Texture ICON_TEX;

    private void loadIcon() {
        try {
            if (ICON_TEX == null) {
                ICON_TEX = new com.badlogic.gdx.graphics.Texture(
                        com.badlogic.gdx.Gdx.files.internal(ArchettoMod.imgPath("powers/aim_power.png")));
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
