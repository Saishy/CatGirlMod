package catgirlmod.powers;

import catgirlmod.CatGirlMod;
import catgirlmod.cards.clumsy.Stumble;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;

public class GoddessOfMisfortunePower extends AbstractPower {

    public static final String POWER_ID = CatGirlMod.makeID("GoddessOfMisfortunePower");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    public static final String IMG = "images/powers/catgirl_goddessofmisfortune.png";

    public static final int LOST_GOLD = 2;

    public GoddessOfMisfortunePower(final AbstractCreature owner, final int amount) {
        name = NAME;
        ID = POWER_ID;
        this.owner = owner;
        this.amount = amount;
        updateDescription();
        type = PowerType.BUFF;
        isTurnBased = false;
        img = ImageMaster.loadImage(IMG);
    }

    @Override
    public void atStartOfTurn() {
        AbstractDungeon.actionManager.addToBottom(
                new MakeTempCardInHandAction(new Stumble(), amount)
        );
        owner.loseGold(LOST_GOLD * amount);
    }

    // Update the description when you apply this power. (i.e. add or remove an "s" in keyword(s))
    @Override
    public void updateDescription() {
        this.description = String.format(DESCRIPTIONS[0], amount, amount * LOST_GOLD);
    }
}
