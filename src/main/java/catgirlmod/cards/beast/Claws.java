package catgirlmod.cards.beast;

import catgirlmod.CatGirlMod;
import catgirlmod.cards.AbstractDefaultCard;
import catgirlmod.patches.AbstractCardEnum;
import catgirlmod.powers.IncreaseClawDamagePower;
import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class Claws extends AbstractDefaultCard {

    /*
     * "Hey, I wanna make a bunch of cards now." - You, probably.
     * ok cool my dude no problem here's the most convenient way to do it:
     *
     * Copy all of the code here (Ctrl+A > Ctrl+C)
     * Ctrl+Shift+A and search up "file and code template"
     * Press the + button at the top and name your template whatever it is for - "AttackCard" or "PowerCard" or something up to you.
     * Read up on the instructions at the bottom. Basically replace anywhere you'd put your cards name with Claws
     * And then you can do custom ones like $ {DAMAGE} and $ {TARGET} if you want.
     * I'll leave some comments on things you might consider replacing with what.
     *
     * Of course, delete all the comments and add anything you want (For example, if you're making a skill card template you'll
     * likely want to replace that new DamageAction with a gain Block one, and add baseBlock instead, or maybe you want a
     * universal template where you delete everything unnecessary - up to you)
     */

    // TEXT DECLARATION

    public static final String ID = CatGirlMod.makeID("Claws");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);

    public static final String IMG = "images/cards/Claws.png";
    // This does mean that you will need to have an image with the same name as the card in your image folder for it to run correctly.

    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;

    // /TEXT DECLARATION/


    // STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.COMMON; //  Up to you, I like auto-complete on these
    private static final CardTarget TARGET = CardTarget.ALL_ENEMY;  //   since they don't change much.
    private static final CardType TYPE = CardType.ATTACK;       //
    public static final CardColor COLOR = AbstractCardEnum.CATGIRL_TEAL;

    private static final int COST = 0;

    private static final int INCREASE_CLAW_DAMAGE = 2;
    private static final int UPGRADE_PLUS_INCREASE_CLAW_DAMAGE = 1;

    private static final int DAMAGE = 7;

    // /STAT DECLARATION/

    public Claws() {
        super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);

        this.baseDamage = DAMAGE;
        this.magicNumber = this.baseMagicNumber = INCREASE_CLAW_DAMAGE;
        this.tags.add(AbstractCardEnum.CLAW);
    }

    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        m = AbstractDungeon.getMonsters().getRandomMonster(null, true, AbstractDungeon.cardRandomRng);

        //AbstractPower gashPower = m.getPower(IncreaseClawDamagePower.POWER_ID);

        if (m != null) {
            AbstractDungeon.actionManager.addToBottom(new VFXAction(new com.megacrit.cardcrawl.vfx.combat.ClawEffect(m.hb.cX, m.hb.cY, Color.TEAL, Color.WHITE), 0.1F));
        }

        calculateCardDamage(m);

        //CatGirlMod.logger.debug("CatGirl Claws used on: " + m.name + " with " + ((gashPower != null) ? gashPower.amount : 0) + " gash amount.");

        AbstractDungeon.actionManager.addToBottom(
                new DamageAction(m, new DamageInfo(p, damage, damageTypeForTurn), AbstractGameAction.AttackEffect.NONE)
        );

        AbstractDungeon.actionManager.addToBottom(
                new ApplyPowerAction(
                        m, p, new IncreaseClawDamagePower(m, p, magicNumber), magicNumber
                )
        );
    }

    // Upgraded stats.
    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeMagicNumber(UPGRADE_PLUS_INCREASE_CLAW_DAMAGE);
            initializeDescription();
        }
    }
}