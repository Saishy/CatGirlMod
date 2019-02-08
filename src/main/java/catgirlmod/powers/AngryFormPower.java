package catgirlmod.powers;

import catgirlmod.CatGirlMod;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.actions.unique.LoseEnergyAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;

public class AngryFormPower extends AbstractPower {
    public AbstractCreature source;

    public static final String POWER_ID = CatGirlMod.makeID("AngryFormPower");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    public static final String IMG = CatGirlMod.makePath("images/powers/catgirl_angryform.png");

    public AngryFormPower(final AbstractCreature owner, final AbstractCreature source) {
        this.name = NAME;
        this.ID = POWER_ID;
        this.owner = owner;
        updateDescription();
        this.type = PowerType.BUFF;
        this.img = ImageMaster.loadImage(IMG);
        this.source = source;
    }

    @Override
    public void updateDescription() {
        description = DESCRIPTIONS[0];
    }

}
