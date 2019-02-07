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

public class SRankRelic extends CustomRelic {

    // ID, images, text.
    public static final String ID = CatGirlMod.makeID("SRank");
    public static final String IMG = CatGirlMod.makePath("images/relics/placeholder_relic2.png");
    public static final String OUTLINE = CatGirlMod.makePath("images/relics/outline/placeholder_relic2.png");

    public static final int AMOUNT = 2;

    public SRankRelic() {
        super(ID, ImageMaster.loadImage(IMG), new Texture(OUTLINE), RelicTier.BOSS, LandingSound.MAGICAL);
    }

    /*@Override
    public void obtain() {
        //CatGirlMod.logger.debug("Starting obtain()");
        if (AbstractDungeon.player.hasRelic(TrainedRelic.ID)) {
            //CatGirlMod.logger.debug("Has TrainedRelic, amount of relics: " + AbstractDungeon.player.relics.size());
            for (int i = 0; i < AbstractDungeon.player.relics.size(); i++) {
                //CatGirlMod.logger.debug("i = " + i);
                if (AbstractDungeon.player.relics.get(i).relicId.equals(TrainedRelic.ID)) {
                    //CatGirlMod.logger.debug("Found TrainedRelic at slot: " + i);
                    this.counter = AbstractDungeon.player.getRelic(TrainedRelic.ID).counter;
                    instantObtain(AbstractDungeon.player, i, true);

                    break;
                }
            }
        } else {
            super.obtain();
        }
    }*/

    /*@Override
    public boolean canSpawn() {
        return AbstractDungeon.player.hasRelic(TrainedRelic.ID);
    }*/

    @Override
    public int onAttacked(DamageInfo info, int damageAmount) {
        //CatGirlMod.logger.info("Catgirl info output: " + info.output);
        //CatGirlMod.logger.info("Catgirl type output: " + info.type);
        if (AbstractDungeon.getCurrRoom() == null || AbstractDungeon.getCurrRoom().phase != AbstractRoom.RoomPhase.COMBAT
                || (info.owner == null) || (info.owner == AbstractDungeon.player)) {
            return super.onAttacked(info, damageAmount);
        }

        if (info.type == DamageInfo.DamageType.NORMAL) {
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
        return DESCRIPTIONS[0] + AMOUNT + DESCRIPTIONS[1];
    }

    // Which relic to return on making a copy of this relic.
    @Override
    public AbstractRelic makeCopy() {
        return new SRankRelic();
    }
}
