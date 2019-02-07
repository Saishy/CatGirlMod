package catgirlmod.powers;

import catgirlmod.CatGirlMod;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;

public class EvadePower extends AbstractPower {

    public static final String POWER_ID = CatGirlMod.makeID("EvadePower");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    public static final String IMG = CatGirlMod.makePath("images/powers/catgirl_evade.png");

    private int amountUsed = 0;

    public EvadePower(final AbstractCreature owner, final AbstractCreature source, final int amount) {
        name = NAME;
        ID = POWER_ID;
        this.owner = owner;
        this.amount = amount;
        updateDescription();
        type = PowerType.BUFF;
        isTurnBased = true;
        img = ImageMaster.loadImage(IMG);
    }

    @Override
    public float modifyBlock(float blockAmount) {
        //CatGirlMod.logger.debug("EvadePower::modifyBlock " + blockAmount);

        if (blockAmount <= 0.0f) {
            return blockAmount;
        }

        if (AbstractDungeon.player.hasPower("NoBlockPower")) {
            return blockAmount;
        }

        if ((blockAmount += amount) < 0.0F) {
            return 0.0F;
        }

        //flash();
        amountUsed = amount;

        return blockAmount;
    }

    @Override
    public void onAfterUseCard(AbstractCard card, UseCardAction action) {
    }

    @Override
    public void onUseCard(final AbstractCard card, final UseCardAction action) {
        if (card.baseBlock > -1) {
            flash();
            AbstractDungeon.actionManager.addToBottom(
                    //new RemoveSpecificPowerAction(owner, owner, EvadePower.POWER_ID)
                    new ReducePowerAction(owner, owner, this, amountUsed)
            );
            amountUsed = 0;
        }
    }

    @Override
    public void atEndOfTurn(boolean bIsPlayer) {
        /*if (!bIsPlayer) {
            return;
        }*/

        AbstractPower keepEvade = this.owner.getPower(EvadeDontDecayPower.POWER_ID);

        if (keepEvade != null) {
            AbstractDungeon.actionManager.addToBottom(
                    new ReducePowerAction(this.owner, this.owner, EvadeDontDecayPower.POWER_ID, 1)
            );

            return;
        }

        int reduction = amount / 2;
        if (amount == 1) {
            reduction = amount;
        }

        flash();
        AbstractDungeon.actionManager.addToBottom(
                new ReducePowerAction(this.owner, this.owner, EvadePower.POWER_ID, reduction)
        );
    }

    // Update the description when you apply this power. (i.e. add or remove an "s" in keyword(s))
    @Override
    public void updateDescription() {
        description = DESCRIPTIONS[0] + amount + DESCRIPTIONS[1] + DESCRIPTIONS[2];
    }
}
