package catgirlmod.relics;

import basemod.abstracts.CustomRelic;
import catgirlmod.CatGirlMod;
import catgirlmod.powers.EvadePower;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.actions.utility.LoseBlockAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.PoisonPower;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.rooms.AbstractRoom;

public class NepetasHat extends CustomRelic {

    // ID, images, text.
    public static final String ID = CatGirlMod.makeID("NepetasHat");
    public static final String IMG = CatGirlMod.makePath("images/relics/placeholder_relic2.png");
    public static final String OUTLINE = CatGirlMod.makePath("images/relics/outline/placeholder_relic2.png");

    public static final int BLOCK_REMOVAL = 2;
    public static final int POISON_APPLY = 2;

    public NepetasHat() {
        super(ID, ImageMaster.loadImage(IMG), new Texture(OUTLINE), RelicTier.RARE, LandingSound.FLAT);
    }

    @Override
    public void onAttack(DamageInfo info, int damageAmount, AbstractCreature target) {
        //CatGirlMod.logger.info("Catgirl info owner: " + info.owner);
        //CatGirlMod.logger.info("Catgirl type output: " + info.type);
        //CatGirlMod.logger.info("Catgirl target: " + target);

        if (AbstractDungeon.getCurrRoom() == null
                || AbstractDungeon.getCurrRoom().phase != AbstractRoom.RoomPhase.COMBAT
                || (info.owner == null)) {
            return;
        }

        if (info.type != DamageInfo.DamageType.NORMAL) {
            return;
        }

        if (target == AbstractDungeon.player) {
            return;
        }

        AbstractMonster m = target instanceof AbstractMonster ? (AbstractMonster)target : null;

        if (m == null) {
            return;
        }

        if (m.currentBlock > 0) {
            AbstractDungeon.actionManager.addToBottom(
                    new LoseBlockAction(
                            m, AbstractDungeon.player, BLOCK_REMOVAL
                    )
            );
            AbstractDungeon.actionManager.addToBottom(
                    new ApplyPowerAction(
                            m, AbstractDungeon.player, new PoisonPower(m, AbstractDungeon.player, POISON_APPLY), POISON_APPLY
                    )
            );
        }
    }

    // Description
    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

    // Which relic to return on making a copy of this relic.
    @Override
    public AbstractRelic makeCopy() {
        return new NepetasHat();
    }
}
