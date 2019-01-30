package catgirlmod.relics;

import basemod.abstracts.CustomRelic;
import catgirlmod.CatGirlMod;
import catgirlmod.powers.EvadePower;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.rooms.AbstractRoom;

public class TrainedRelic extends CustomRelic {

    // ID, images, text.
    public static final String ID = CatGirlMod.makeID("TrainedRelic");
    public static final String IMG = "images/relics/placeholder_relic.png";
    public static final String OUTLINE = "images/relics/outline/placeholder_relic.png";

    public static final int AMOUNT = 2;

    public TrainedRelic() {
        super(ID, ImageMaster.loadImage(IMG), new Texture(OUTLINE), RelicTier.STARTER, LandingSound.MAGICAL);
    }

    @Override
    public int onAttacked(DamageInfo info, int damageAmount) {
        //CatGirlMod.logger.info("Catgirl info output: " + info.output);
        //CatGirlMod.logger.info("Catgirl type output: " + info.type);
        if (AbstractDungeon.getCurrRoom() == null || AbstractDungeon.getCurrRoom().phase != AbstractRoom.RoomPhase.COMBAT
                || (info.owner == null) || (info.owner == AbstractDungeon.player)) {
            return super.onAttacked(info, damageAmount);
        }

        if (info.output > 0 && info.type == DamageInfo.DamageType.NORMAL && (damageAmount > AbstractDungeon.player.currentBlock)) {
            AbstractDungeon.actionManager.addToBottom(
                new ApplyPowerAction(
                        AbstractDungeon.player, AbstractDungeon.player, new EvadePower(AbstractDungeon.player, AbstractDungeon.player, AMOUNT), AMOUNT
                )
            );
        }

        return super.onAttacked(info, damageAmount);
    }

    // Description
    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

    // Which relic to return on making a copy of this relic.
    @Override
    public AbstractRelic makeCopy() {
        return new TrainedRelic();
    }
}
