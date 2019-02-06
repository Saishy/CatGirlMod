package catgirlmod.cards.beast;

import catgirlmod.CatGirlMod;
import catgirlmod.actions.BloodBathAction;
import catgirlmod.cards.AbstractDefaultCard;
import catgirlmod.patches.AbstractCardEnum;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class BloodBath extends AbstractDefaultCard {

    /*
     * "Hey, I wanna make a bunch of cards now." - You, probably.
     * ok cool my dude no problem here's the most convenient way to do it:
     *
     * Copy all of the code here (Ctrl+A > Ctrl+C)
     * Ctrl+Shift+A and search up "file and code template"
     * Press the + button at the top and name your template whatever it is for - "AttackCard" or "PowerCard" or something up to you.
     * Read up on the instructions at the bottom. Basically replace anywhere you'd put your cards name with ComboAttack
     * And then you can do custom ones like $ {DAMAGE} and $ {TARGET} if you want.
     * I'll leave some comments on things you might consider replacing with what.
     *
     * Of course, delete all the comments and add anything you want (For example, if you're making a skill card template you'll
     * likely want to replace that new DamageAction with a gain Block one, and add baseBlock instead, or maybe you want a
     * universal template where you delete everything unnecessary - up to you)
     */

    // TEXT DECLARATION

    public static final String ID = CatGirlMod.makeID("BloodBath");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);

    public static final String IMG = "images/cards/BloodBath.png";
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

    private static final int DAMAGE = 15;
    private static final int UPGRADE_PLUS_DMG = 5;

    private static final int SECONDARY_DAMAGE = 20;
    private static final int UPGRADE_PLUS_SECONDARY_DAMAGE = 5;

    // /STAT DECLARATION/

    public BloodBath() {
        super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);

        this.magicNumber = this.baseMagicNumber = SECONDARY_DAMAGE;
        this.baseDamage = DAMAGE;
    }

    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        int secondDamageToApply = magicNumber;
        int savedBaseDamage = baseDamage;
        int firstDamage = damage;

        AbstractMonster secondTarget = AbstractDungeon.getMonsters().getRandomMonster(m, true, AbstractDungeon.cardRandomRng);

        if (secondTarget != null) {
            baseDamage = secondDamageToApply;
            calculateCardDamage(secondTarget);
        }

        AbstractDungeon.actionManager.addToBottom(
                new BloodBathAction(m, secondTarget, new DamageInfo(p, firstDamage, damageTypeForTurn), damage)
        );

        baseDamage = savedBaseDamage;
    }

    // Upgraded stats.
    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeDamage(UPGRADE_PLUS_DMG);
            upgradeMagicNumber(UPGRADE_PLUS_SECONDARY_DAMAGE);
            initializeDescription();
        }
    }
}