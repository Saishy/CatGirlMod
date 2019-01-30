package catgirlmod.powers;

import catgirlmod.CatGirlMod;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.actions.unique.LoseEnergyAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;

public class BeastificationPower extends AbstractPower {
    public AbstractCreature source;

    public static final String POWER_ID = CatGirlMod.makeID("BeastificationPower");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    public static final String IMG = "images/powers/catgirl_beastification.png";

    public BeastificationPower(final AbstractCreature owner, final AbstractCreature source, final int amount) {
        this.name = NAME;
        this.ID = POWER_ID;
        this.owner = owner;
        this.amount = amount;
        updateDescription();
        this.type = PowerType.DEBUFF;
        this.isTurnBased = true;
        this.img = ImageMaster.loadImage(IMG);
        this.source = source;
    }

    @Override
    public void atStartOfTurn() { // At the start of your turn
        AbstractDungeon.actionManager.addToBottom(
                new LoseEnergyAction(amount)
        );

        AbstractDungeon.actionManager.addToBottom(
                new RemoveSpecificPowerAction(owner, owner, this)
        );
    }

    @Override
    public void updateDescription() {
        this.description = String.format(DESCRIPTIONS[0], amount);
    }

}
