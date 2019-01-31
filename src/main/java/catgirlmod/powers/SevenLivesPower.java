package catgirlmod.powers;

import catgirlmod.CatGirlMod;
import com.evacipated.cardcrawl.mod.stslib.patches.core.AbstractCreature.TempHPField;
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
    public static final String IMG = "images/powers/catgirl_sevenlives.png";

    public SevenLivesPower(final AbstractCreature owner) {
        this.name = NAME;
        this.ID = POWER_ID;
        this.owner = owner;
        updateDescription();
        this.type = PowerType.BUFF;
        this.isTurnBased = false;
        this.img = ImageMaster.loadImage(IMG);
    }

    @Override
    public float atDamageFinalReceive(float damage, DamageInfo.DamageType type) {
        float damageDealt = damage - owner.currentBlock;

        if (owner.currentHealth + TempHPField.tempHp.get(owner) <= damageDealt) {
            damage = 0;
        }

        AbstractDungeon.actionManager.addToBottom(
                new RemoveSpecificPowerAction(owner, owner, this)
        );

        return damage;
    }

    @Override
    public void updateDescription() {
        description = DESCRIPTIONS[0];
    }
}
