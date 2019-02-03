package catgirlmod.cards.beast;

import catgirlmod.CatGirlMod;
import catgirlmod.cards.AbstractDefaultCard;
import catgirlmod.patches.AbstractCardEnum;
import catgirlmod.powers.IncreaseClawDamagePower;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class FinishingMove extends AbstractDefaultCard {

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

    public static final String ID = CatGirlMod.makeID("FinishingMove");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);

    public static final String IMG = "images/cards/FinishingMove.png";
    // This does mean that you will need to have an image with the same name as the card in your image folder for it to run correctly.

    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;

    // /TEXT DECLARATION/


    // STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.UNCOMMON; //  Up to you, I like auto-complete on these
    private static final CardTarget TARGET = CardTarget.ENEMY;  //   since they don't change much.
    private static final CardType TYPE = CardType.ATTACK;       //
    public static final CardColor COLOR = AbstractCardEnum.CATGIRL_TEAL;

    private static final int COST = 2;

    private static final int DAMAGE = 20;
    private static final int UPGRADE_PLUS_DAMAGE = 5;

    private static final int BIG_DAMAGE = 30;
    private static final int UPGRADE_PLUS_BIG_DAMAGE = 5;

    private static final int DAMAGE_BONUS_HP_PERCENTAGE = 50;
    private static final int UPGRADE_PLUS_DAMAGE_BONUS_HP_PERCENTAGE = 10;

    // /STAT DECLARATION/

    public FinishingMove() {
        super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);

        this.baseDamage = DAMAGE;
        this.magicNumber = this.baseMagicNumber = DAMAGE_BONUS_HP_PERCENTAGE;

        this.secondMagicNumber = this.baseSecondMagicNumber = BIG_DAMAGE;

        this.isEthereal = true;
    }

    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        int originalBaseDamage = baseDamage;

        calculateCardDamage(m);

        //CatGirlMod.logger.debug("CatGirl Claws used on: " + m.name + " with " + ((gashPower != null) ? gashPower.amount : 0) + " gash amount.");

        AbstractDungeon.actionManager.addToBottom(
                new DamageAction(m, new DamageInfo(p, damage, damageTypeForTurn), AbstractGameAction.AttackEffect.BLUNT_HEAVY)
        );

        baseDamage = originalBaseDamage;
    }

    @Override
    public void calculateCardDamage(AbstractMonster mo) {
        float tmp = baseDamage;

        if (((float)mo.currentHealth / (float)mo.maxHealth) <= ((float)magicNumber / 100.0f)) {
            tmp = secondMagicNumber;
        }

        if (tmp < 0.0F) {
            tmp = 0.0f;
        }

        if (baseDamage != (int)tmp) {
            isDamageModified = true;
        }

        baseDamage = MathUtils.floor(tmp);

        super.calculateCardDamage(mo);
    }

    // Upgraded stats.
    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeDamage(UPGRADE_PLUS_DAMAGE);
            upgradeSecondMagicNumber(UPGRADE_PLUS_BIG_DAMAGE);
            upgradeMagicNumber(UPGRADE_PLUS_DAMAGE_BONUS_HP_PERCENTAGE);
            initializeDescription();
        }
    }
}