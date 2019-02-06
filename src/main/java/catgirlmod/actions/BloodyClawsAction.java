package catgirlmod.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.unique.VampireDamageAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.vfx.combat.ScrapeEffect;

public class BloodyClawsAction extends AbstractGameAction {

    private DamageInfo info;
    private int times;

    public BloodyClawsAction(AbstractCreature target, DamageInfo info, int times) {
        this.info = info;
        setValues(target, info);
        this.times = times;
        this.duration = Settings.ACTION_DUR_XFAST;
    }

    @Override
    public void update() {
        if (duration == Settings.ACTION_DUR_XFAST) {
            if (AbstractDungeon.getCurrRoom().monsters.areMonstersBasicallyDead()) {
                AbstractDungeon.actionManager.clearPostCombatActions();
                isDone = true;
                return;
            }

            if (target == null || target.currentHealth <= 0) {
                target = AbstractDungeon.getMonsters().getRandomMonster(null, true, AbstractDungeon.cardRandomRng);
            }

            AbstractDungeon.actionManager.addToBottom(new VFXAction(new ScrapeEffect(target.hb.cX, target.hb.cY), 0.1F));

            AbstractDungeon.actionManager.addToBottom(
                    new VampireDamageAction(target, info, AbstractGameAction.AttackEffect.NONE)
            );

            times--;

            if (times > 0) {
                AbstractDungeon.actionManager.addToBottom(
                        new BloodyClawsAction(target, info, times)
                );
            }
        }

        if (times == 0) {
            tickDuration();
        } else {
            isDone = true;
        }
    }
}
