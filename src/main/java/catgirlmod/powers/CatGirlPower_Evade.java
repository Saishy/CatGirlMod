package catgirlmod.powers;

import catgirlmod.CatGirlMod;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;

public class CatGirlPower_Evade extends AbstractPower {

    public static final String POWER_ID = CatGirlMod.makeID("Evade");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    public static final String IMG = "images/powers/catgirl_evade.png";

    public CatGirlPower_Evade(final AbstractCreature owner, final AbstractCreature source, final int amount) {
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
        //CatGirlMod.logger.debug("CatGirlPower_Evade::modifyBlock " + blockAmount);

        if (blockAmount <= 0.0f) {
            return blockAmount;
        }

        if (AbstractDungeon.player.hasPower("NoBlockPower")) {
            return blockAmount;
        }

        if ((blockAmount += this.amount) < 0.0F) {
            return 0.0F;
        }

        //flash();

        return blockAmount;
    }

    @Override
    public void onUseCard(final AbstractCard card, final UseCardAction action) {
        if (card.baseBlock > -1) {
            flash();
            AbstractDungeon.actionManager.addToBottom(new ReducePowerAction(this.owner, this.owner, CatGirlPower_Evade.POWER_ID, amount));
        }
    }

    @Override
    public void atEndOfTurn(boolean bIsPlayer) {
        if(!bIsPlayer) {
            return;
        }

        int reduction = amount / 2;
        if (amount == 1) {
            reduction = amount;
        }

        flash();
        AbstractDungeon.actionManager.addToBottom(new ReducePowerAction(this.owner, this.owner, CatGirlPower_Evade.POWER_ID, reduction));
    }

    // Update the description when you apply this power. (i.e. add or remove an "s" in keyword(s))
    @Override
    public void updateDescription() {
        description = DESCRIPTIONS[0] + amount + DESCRIPTIONS[1];
    }
}
