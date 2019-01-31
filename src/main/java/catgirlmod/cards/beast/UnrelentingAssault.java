package catgirlmod.cards.beast;

import catgirlmod.CatGirlMod;
import catgirlmod.actions.DrawCardsAndDoAction;
import catgirlmod.cards.AbstractDefaultCard;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import catgirlmod.patches.AbstractCardEnum;

public class UnrelentingAssault extends AbstractDefaultCard implements DrawCardsAndDoAction.IDrawCardsAndDoCallback {

    /*
     * "Hey, I wanna make a bunch of cards now." - You, probably.
     * ok cool my dude no problem here's the most convenient way to do it:
     *
     * Copy all of the code here (Ctrl+A > Ctrl+C)
     * Ctrl+Shift+A and search up "file and code template"
     * Press the + button at the top and name your template whatever it is for - "AttackCard" or "PowerCard" or something up to you.
     * Read up on the instructions at the bottom. Basically replace anywhere you'd put your cards name with UnrelentingAssault
     * And then you can do custom ones like $ {DAMAGE} and $ {TARGET} if you want.
     * I'll leave some comments on things you might consider replacing with what.
     *
     * Of course, delete all the comments and add anything you want (For example, if you're making a skill card template you'll
     * likely want to replace that new DamageAction with a gain Block one, and add baseBlock instead, or maybe you want a
     * universal template where you delete everything unnecessary - up to you)
     */

    // TEXT DECLARATION

    public static final String ID = CatGirlMod.makeID("UnrelentingAssault");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);

    public static final String IMG = "images/cards/UnrelentingAssault.png";
    // This does mean that you will need to have an image with the same name as the card in your image folder for it to run correctly.

    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;

    // /TEXT DECLARATION/


    // STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.UNCOMMON; //  Up to you, I like auto-complete on these
    private static final CardTarget TARGET = CardTarget.ENEMY;  //   since they don't change much.
    private static final CardType TYPE = CardType.ATTACK;       //
    public static final CardColor COLOR = AbstractCardEnum.CATGIRL_TEAL;

    private static final int COST = 3;

    private static final int DAMAGE = 9;
    private static final int UPGRADE_PLUS_DMG = 3;

    private static final int DRAW_CARDS = 6;

    //private int attackDraws = 0;

    private AbstractMonster lastTarget;

    // /STAT DECLARATION/

    public UnrelentingAssault() {
        super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);

        this.baseDamage = DAMAGE;
        this.magicNumber = baseMagicNumber = DRAW_CARDS;
    }

    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        //attackDraws = 0;

        if (AbstractDungeon.player.drawPile.size() < DRAW_CARDS) {
            AbstractDungeon.actionManager.addToBottom(
                    new com.megacrit.cardcrawl.actions.common.EmptyDeckShuffleAction()
            );
        }

        lastTarget = m;

        AbstractDungeon.actionManager.addToBottom(
                new DrawCardsAndDoAction(this, DRAW_CARDS)
        );

        /*CatGirlMod.logger.debug("Finished Drawing, attacks draw is: " + attackDraws);

        for (int i = 0; i < attackDraws; i++) {
            AbstractDungeon.actionManager.addToBottom(
                    new DamageAction(m, new DamageInfo(p, damage, damageTypeForTurn), (i % 2 == 0) ? AbstractGameAction.AttackEffect.SLASH_VERTICAL : AbstractGameAction.AttackEffect.SLASH_HORIZONTAL)
            );
        }*/
    }

    @Override
    public void Execute(AbstractCard cardDraw) {
        //CatGirlMod.logger.debug("CardDraw is: " + cardDraw.type);

        if (cardDraw.type == CardType.ATTACK && lastTarget != null) {
            //attackDraws++;
            //CatGirlMod.logger.debug("Attack Draws is: " + attackDraws);
            AbstractDungeon.actionManager.addToBottom(
                    new DamageAction(lastTarget, new DamageInfo(AbstractDungeon.player, damage, damageTypeForTurn), (Math.random() < 0.5f) ? AbstractGameAction.AttackEffect.SLASH_VERTICAL : AbstractGameAction.AttackEffect.SLASH_HORIZONTAL)
            );
        }
    }

    // Upgraded stats.
    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeDamage(UPGRADE_PLUS_DMG);
            initializeDescription();
        }
    }
}