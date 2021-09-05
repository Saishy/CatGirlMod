package catgirlmod.powers;

import catgirlmod.CatGirlMod;
import com.evacipated.cardcrawl.mod.stslib.patches.core.AbstractCreature.TempHPField;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;

public class SevenLivesPower extends AbstractPower {

    public static final String POWER_ID = CatGirlMod.makeID("SevenLivesPower");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    public static final String IMG = CatGirlMod.makePath("images/powers/catgirl_sevenlives.png");

    public SevenLivesPower(final AbstractCreature owner, int amount) {
        this.name = NAME;
        this.ID = POWER_ID;
        this.owner = owner;
        this.amount = amount;
        updateDescription();
        this.type = PowerType.BUFF;
        this.isTurnBased = false;
        this.img = ImageMaster.loadImage(IMG);
    }

    @Override
    public int onLoseHp(int damageAmount) {
        if (damageAmount <= 0) {
            return damageAmount;
        }

        AbstractDungeon.actionManager.addToTop(
                new ReducePowerAction(owner, owner, this, 1)
        );

        AbstractDungeon.actionManager.addToTop(
                new ApplyPowerAction(owner, owner, new EvadePower(owner, owner, damageAmount), damageAmount)
        );

        damageAmount = 0;

        return damageAmount;
    }

    @Override
    public void updateDescription() {
        if (amount <= 1) {
            description = DESCRIPTIONS[0];
        } else {
            description = String.format(DESCRIPTIONS[1], amount);
        }
    }
}
