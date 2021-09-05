package catgirlmod.relics;

import basemod.abstracts.CustomRelic;
import catgirlmod.CatGirlMod;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.relics.AbstractRelic;

public class AmbushRelic extends CustomRelic {

    // ID, images, text.
    public static final String ID = CatGirlMod.makeID("Ambush");
    public static final String IMG = CatGirlMod.makePath("images/relics/ambush_relic.png");
    public static final String OUTLINE = CatGirlMod.makePath("images/relics/outline/ambush_relic_outline.png");

    public AmbushRelic() {
        super(ID, ImageMaster.loadImage(IMG), new Texture(OUTLINE), RelicTier.UNCOMMON, LandingSound.MAGICAL);
    }

    @Override
    public void atBattleStart() {
        for (final AbstractMonster mo : AbstractDungeon.getCurrRoom().monsters.monsters) {
            AbstractPower artifact = mo.getPower("Artifact");

            if (artifact != null) {
                AbstractDungeon.actionManager.addToTop(
                        new com.megacrit.cardcrawl.actions.common.ReducePowerAction(mo, AbstractDungeon.player, artifact, 1)
                );
            }
        }
    }

    // Description
    @Override
    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0] + 1 + this.DESCRIPTIONS[1];
    }

    // Which relic to return on making a copy of this relic.
    @Override
    public AbstractRelic makeCopy() {
        return new AmbushRelic();
    }
}
