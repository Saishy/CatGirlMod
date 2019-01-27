package catgirlmod.powers;

import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.*;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.*;

import catgirlmod.CatGirlMod;

public class CatGirlPowerBuff_IncreaseClawDamage extends AbstractPower {

    public static final String POWER_ID = CatGirlMod.makeID("Gash");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    public static final String IMG = "images/powers/catgirl_claw_dmg_increase.png";

    public static final int DAMAGE_INCREASE_PER_STACK = 2;

    public CatGirlPowerBuff_IncreaseClawDamage(final AbstractCreature owner, final AbstractCreature source, final int amount) {
        name = NAME;
        ID = POWER_ID;
        this.owner = owner;
        this.amount = amount;
        updateDescription();
        type = PowerType.DEBUFF;
        isTurnBased = false;
        img = ImageMaster.loadImage(IMG);
    }

    // Update the description when you apply this power. (i.e. add or remove an "s" in keyword(s))
    @Override
    public void updateDescription() {
        description = DESCRIPTIONS[0] + DAMAGE_INCREASE_PER_STACK * amount + ".";
    }
}
