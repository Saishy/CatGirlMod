package catgirlmod.powers;

import catgirlmod.CatGirlMod;
import com.evacipated.cardcrawl.mod.stslib.powers.interfaces.OnReceivePowerPower;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.vfx.combat.CleaveEffect;

public class CounterattackPower extends AbstractPower implements OnReceivePowerPower {
    public AbstractCreature source;

    public static final String POWER_ID = CatGirlMod.makeID("CounterattackPower");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    public static final String IMG = "images/powers/catgirl_counterattack.png";

    public CounterattackPower(final AbstractCreature owner, final AbstractCreature source, final int amount) {
        this.name = NAME;
        this.ID = POWER_ID;
        this.owner = owner;
        this.amount = amount;
        updateDescription();
        this.type = PowerType.BUFF;
        this.isTurnBased = false;
        this.img = ImageMaster.loadImage(IMG);
        this.source = source;
    }

    @Override
    public boolean onReceivePower(AbstractPower power, AbstractCreature target, AbstractCreature source) {

        if (power.ID.equals(EvadePower.POWER_ID)) {
            flash();
            AbstractDungeon.actionManager.addToBottom(new com.megacrit.cardcrawl.actions.utility.SFXAction("ATTACK_HEAVY"));

            if (com.megacrit.cardcrawl.core.Settings.FAST_MODE) {
                AbstractDungeon.actionManager.addToBottom(
                        new VFXAction(new CleaveEffect())
                );
            } else {
                AbstractDungeon.actionManager.addToBottom(
                        new VFXAction(this.owner, new CleaveEffect(), 0.2F)
                );
            }
            AbstractDungeon.actionManager.addToBottom(
                    new DamageAllEnemiesAction(
                            this.owner,
                            DamageInfo.createDamageMatrix(this.amount, true),
                            DamageInfo.DamageType.THORNS,
                            AbstractGameAction.AttackEffect.NONE,
                            true
                    )
            );
        }

        return true;
    }

    @Override
    public void updateDescription() {
        this.description = (DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[1]);
    }

}
