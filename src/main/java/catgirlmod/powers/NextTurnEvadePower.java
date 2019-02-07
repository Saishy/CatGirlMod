package catgirlmod.powers;

import catgirlmod.CatGirlMod;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;

public class NextTurnEvadePower extends AbstractPower {

    public static final String POWER_ID = CatGirlMod.makeID("NextTurnEvadePower");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    public static final String IMG = CatGirlMod.makePath("images/powers/catgirl_next_turn_evade.png");

    public NextTurnEvadePower(AbstractCreature owner, int evadeAmt) {
        this.name = NAME;
        this.ID = POWER_ID;
        this.owner = owner;
        this.amount = evadeAmt;
        updateDescription();
        this.type = PowerType.BUFF;
        this.isTurnBased = false;
        this.img = ImageMaster.loadImage(IMG);
    }

    // Update the description when you apply this power.
    @Override
    public void updateDescription() {
        description = (DESCRIPTIONS[0] + amount + DESCRIPTIONS[1]);
    }

    @Override
    public void atStartOfTurn() {
        flash();
        AbstractDungeon.actionManager.addToBottom(
                new ApplyPowerAction(
                        owner, owner, new EvadePower(owner, owner, amount), amount
                )
        );
        AbstractDungeon.actionManager.addToBottom(
                new RemoveSpecificPowerAction(
                        owner, owner, POWER_ID
                )
        );
    }
}
