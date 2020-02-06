package catgirlmod.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.AbstractGameAction.ActionType;
import com.megacrit.cardcrawl.actions.GameActionManager;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.Hitbox;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.monsters.MonsterGroup;
import com.megacrit.cardcrawl.random.Random;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.unlock.UnlockTracker;
import com.megacrit.cardcrawl.vfx.combat.FlashAtkImgEffect;

public class BloodBathAction extends AbstractGameAction {
    private AbstractCreature secondaryTarget;
    private int secondaryDamage;
    private DamageInfo info;
    private static final float DURATION = 0.1F;

    public BloodBathAction(AbstractCreature target, AbstractCreature secondaryTarget, DamageInfo info, int secondaryDamage) {
        this.info = info;
        setValues(target, info);
        this.secondaryTarget = secondaryTarget;
        this.secondaryDamage = secondaryDamage;
        this.actionType = AbstractGameAction.ActionType.DAMAGE;
        this.duration = DURATION;
    }

    public void update() {
        if ((this.duration == 0.1F) && (this.target != null)) {
            AbstractDungeon.effectList.add(new FlashAtkImgEffect(this.target.hb.cX, this.target.hb.cY, AttackEffect.SLASH_VERTICAL));

            this.target.damage(this.info);

            if (((((AbstractMonster) this.target).isDying) || (this.target.currentHealth <= 0)) && (!this.target.halfDead)) {
                AbstractDungeon.actionManager.addToBottom(new VFXAction(new com.megacrit.cardcrawl.vfx.combat.OfferingEffect(), 0.15F));

                if (secondaryTarget != null) {
                    AbstractDungeon.actionManager.addToBottom(
                            new DamageAction(secondaryTarget, new DamageInfo(AbstractDungeon.player, secondaryDamage, DamageInfo.DamageType.THORNS), AttackEffect.SLASH_HEAVY)
                    );
                }
            }

            if (AbstractDungeon.getCurrRoom().monsters.areMonstersBasicallyDead()) {
                AbstractDungeon.actionManager.clearPostCombatActions();
            }
        }

        tickDuration();
    }
}