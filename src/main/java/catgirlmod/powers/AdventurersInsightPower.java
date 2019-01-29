package catgirlmod.powers;

import catgirlmod.CatGirlMod;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;

public class AdventurersInsightPower extends AbstractPower {

    public static final String POWER_ID = CatGirlMod.makeID("AdventurersInsightBuff");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    public static final String IMG = "images/powers/catgirl_adventurersinsight.png";

    public AdventurersInsightPower(final AbstractCreature owner, final AbstractCreature source, final int amount) {
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
    public void onAttack(DamageInfo info, int damageAmount, AbstractCreature target) {
        super.onAttack(info, damageAmount, target);

        AbstractMonster mo = (target instanceof AbstractMonster ? (AbstractMonster)target : null);

        if (mo == null) {
            return;
        }

        if (info.type != DamageInfo.DamageType.NORMAL) {
            return;
        }

        if ( mo.intent == AbstractMonster.Intent.ATTACK ||
        mo.intent == AbstractMonster.Intent.ATTACK_BUFF ||
        mo.intent == AbstractMonster.Intent.ATTACK_DEBUFF ||
        mo.intent == AbstractMonster.Intent.ATTACK_DEFEND ) {
            AbstractDungeon.actionManager.addToBottom(
                    new ApplyPowerAction(
                            owner, owner, new EvadePower(owner, owner, amount), amount
                    )
            );
        }
    }

    @Override
    public void updateDescription() {
        this.description = String.format(DESCRIPTIONS[0], amount);
    }
}
