package catgirlmod.relics;

import basemod.abstracts.CustomRelic;
import catgirlmod.CatGirlMod;
import com.badlogic.gdx.graphics.Texture;
import com.evacipated.cardcrawl.mod.stslib.relics.OnReceivePowerRelic;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.*;
import com.megacrit.cardcrawl.relics.AbstractRelic;

public class CatGirlRelic_MonsterGuide extends CustomRelic implements OnReceivePowerRelic {

    // ID, images, text.
    public static final String ID = CatGirlMod.makeID("MonsterGuide");
    public static final String IMG = "images/relics/placeholder_relic2.png";
    public static final String OUTLINE = "images/relics/outline/placeholder_relic2.png";

    private boolean bHasTriggered = false;

    public CatGirlRelic_MonsterGuide() {
        super(ID, ImageMaster.loadImage(IMG), new Texture(OUTLINE), RelicTier.RARE, LandingSound.FLAT);

        this.bHasTriggered = false;
    }

    @Override
    public void atBattleStart() {
        bHasTriggered = false;
    }

    @Override
    public void onVictory() {
        bHasTriggered = false;
    }

    @Override
    public boolean onReceivePower(AbstractPower abstractPower, AbstractCreature abstractCreature) {
        if (bHasTriggered) {
            return true;
        }

        if (abstractPower.type == AbstractPower.PowerType.DEBUFF) {
            this.flash();

            if (abstractCreature instanceof AbstractMonster) {
                abstractPower.owner = abstractCreature;
                AbstractDungeon.actionManager.addToBottom(
                        new ApplyPowerAction(
                                abstractCreature, AbstractDungeon.player, abstractPower, abstractPower.amount
                        )
                );
            }
            /*switch ((abstractPower.ID)) {
                case VulnerablePower.POWER_ID:
                case WeakPower.POWER_ID:
                case StrengthPower.POWER_ID:
                case PoisonPower.POWER_ID:
                case LockOnPower.POWER_ID:
                case ChokePower.POWER_ID:
                    AbstractDungeon.actionManager.addToBottom(
                            new ApplyPowerAction(
                                    abstractCreature, AbstractDungeon.player, abstractPower, abstractPower.amount
                            )
                    );
                    break;
            }*/

            bHasTriggered = true;

            return false;
        }

        return true;
    }

    // Description
    @Override
    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0];
    }

    // Which relic to return on making a copy of this relic.
    @Override
    public AbstractRelic makeCopy() {
        return new CatGirlRelic_MonsterGuide();
    }
}
