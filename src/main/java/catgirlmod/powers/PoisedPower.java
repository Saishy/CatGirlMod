package catgirlmod.powers;

import catgirlmod.CatGirlMod;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;

public class PoisedPower extends AbstractPower {
    public AbstractCreature source;

    public static final String POWER_ID = CatGirlMod.makeID("PoisedBuff");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    public static final String IMG = "images/powers/catgirl_poised.png";

    public PoisedPower(final AbstractCreature owner, final AbstractCreature source, final int amount) {
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
    public void atStartOfTurn() { // At the start of your turn
        AbstractDungeon.actionManager.addToBottom(
                new ApplyPowerAction(
                        source, source, new EvadePower(source, source, amount), amount
                )
        );
    }

    @Override
    public void updateDescription() {
        this.description = String.format(DESCRIPTIONS[0], amount);
    }

}
