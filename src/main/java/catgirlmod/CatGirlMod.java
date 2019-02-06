package catgirlmod;

import basemod.BaseMod;
import basemod.ModLabeledToggleButton;
import basemod.ModPanel;
import basemod.abstracts.CustomUnlockBundle;
import basemod.helpers.RelicType;
import basemod.interfaces.*;
import catgirlmod.cards.basic.*;
import catgirlmod.cards.adventurer.*;
import catgirlmod.cards.beast.*;
import catgirlmod.cards.clumsy.*;
import catgirlmod.interfaces.IncrementDiscardSubscriber;
import catgirlmod.relics.*;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.evacipated.cardcrawl.mod.stslib.Keyword;
import com.evacipated.cardcrawl.modthespire.lib.SpireConfig;
import com.evacipated.cardcrawl.modthespire.lib.SpireInitializer;
import com.google.gson.Gson;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.CardHelper;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.localization.*;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.unlock.AbstractUnlock;
import com.megacrit.cardcrawl.unlock.UnlockTracker;
import catgirlmod.characters.TheCatGirl;
import catgirlmod.patches.AbstractCardEnum;
import catgirlmod.patches.TheCatGirlEnum;
import catgirlmod.variables.DefaultCustomVariable;
import catgirlmod.variables.DefaultSecondMagicNumber;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Properties;

//TODO: FIRST THINGS FIRST: RENAME YOUR PACKAGE AND ID NAMES FIRST-THING!!!
// Right click the package (folder with black dot on it. the name's at the very top) -> Refactor -> Rename, and name it whatever you wanna call your mod.
// Scroll to the very bottom of this file. Change the id string from "theDefault:" to "yourModName:" or whatever your heart desires (don't use spaces).
// Start with DefaultCommonAttack - it is the most commented card right now.
// After you sorta get the hang of how to make cards, check out the card template which will make your life easier

/*
 * With that out of the way:
 * Welcome to this mildly over-commented Slay the Spire modding base.
 * Use it to make your own mod of any type. - If you want to add any standard in-game content (Character,
 * cards, relics), this is a good starting point.
 * It features 1 character with a minimal set of things: 1 card of each type, 1 debuff, 3 types of relics, etc.
 * If you're new to modding, you basically *need* the BaseMod wiki for whatever you wish to add
 * https://github.com/daviscook477/BaseMod/wiki - work your way through with this base.
 * Feel free to use this in any way you like, of course. Happy modding!
 */

//NOTE: ASD
@SpireInitializer
public class CatGirlMod implements
        EditCardsSubscriber,
        EditRelicsSubscriber,
        EditStringsSubscriber,
        EditKeywordsSubscriber,
        EditCharactersSubscriber,
        PostInitializeSubscriber,
        PostBattleSubscriber,
        SetUnlocksSubscriber {
    // Make sure to implement the subscribers *you* are using (read basemod wiki). Editing cards? EditCardsSubscriber.
    // Making relics? EditRelicsSubscriber. etc., etc., for a full list and how to make your own, visit the basemod wiki.
    public static final Logger logger = LogManager.getLogger(CatGirlMod.class.getName());

    //This is for the in-game mod settings panel.
    private static final String MODNAME = "Catgirl Mod";
    private static final String AUTHOR = "Saishy";
    private static final String DESCRIPTION = "Adds a catgirl adventurer as a playable character.";

    // =============== INPUT TEXTURE LOCATION =================

    // Colors (RGB)
    // Character Color
    public static final Color CATGIRL_TEAL = CardHelper.getColor(44.0f, 90.0f, 104.0f);

    // Potion Colors in RGB
    public static final Color PLACEHOLDER_POTION_LIQUID = CardHelper.getColor(209.0f, 53.0f, 18.0f); // Orange-ish Red
    public static final Color PLACEHOLDER_POTION_HYBRID = CardHelper.getColor(255.0f, 230.0f, 230.0f); // Near White
    public static final Color PLACEHOLDER_POTION_SPOTS = CardHelper.getColor(100.0f, 25.0f, 10.0f); // Super Dark Red/Brown

    // Card backgrounds - The actual rectangular card.
    private static final String ATTACK_CATGIRL_TEAL = "images/512/bg_attack_catgirl.png";
    private static final String SKILL_CATGIRL_TEAL = "images/512/bg_skill_catgirl.png";
    private static final String POWER_CATGIRL_TEAL = "images/512/bg_power_catgirl.png";
    private static final String ENERGY_ORB_CATGIRL_TEAL = "images/512/card_catgirl_orb.png";
    private static final String CARD_ENERGY_ORB = "images/512/card_catgirl_small_orb.png";

    private static final String ATTACK_CATGIRL_TEAL_PORTRAIT = "images/1024/bg_attack_catgirl.png";
    private static final String SKILL_CATGIRL_TEAL_PORTRAIT = "images/1024/bg_skill_catgirl.png";
    private static final String POWER_CATGIRL_TEAL_PORTRAIT = "images/1024/bg_power_catgirl.png";
    private static final String ENERGY_ORB_CATGIRL_TEAL_PORTRAIT = "images/1024/card_catgirl_orb.png";

    // Character assets
    private static final String THE_CATGIRL_BUTTON = "images/charSelect/CatGirlCharacterButton.png";
    private static final String THE_CATGIRL_PORTRAIT = "images/charSelect/CatGirlCharacterPortraitBG.png";

    public static final String THE_CATGIRL_SHOULDER_1 = "images/char/catgirlCharacter/shoulder.png";
    public static final String THE_CATGIRL_SHOULDER_2 = "images/char/catgirlCharacter/shoulder2.png";
    public static final String THE_CATGIRL_CORPSE = "images/char/catgirlCharacter/corpse.png";

    //Mod Badge - A small icon that appears in the mod settings menu next to your mod.
    public static final String BADGE_IMAGE = "images/Badge.png";

    public static int totalDiscardedThisCombat = 0;

    private static ArrayList<IncrementDiscardSubscriber> incrementDiscardSubscribers;

    public static ArrayList<AbstractRelic> shareableRelics = new ArrayList<>();

    public static final String PROP_RELIC_SHARING = "contentSharing_relics";
    public static final String PROP_POTION_SHARING = "contentSharing_potions";
    public static final String PROP_EVENT_SHARING = "contentSharing_events";
    public static final String PROP_UNLOCK_ALL = "unlockEverything";

    public static Properties catgirlDefault = new Properties();
    public static boolean contentSharing_relics = true;
    public static boolean contentSharing_potions = true;
    public static boolean contentSharing_events = true;
    public static boolean unlockEverything = false;

    private CustomUnlockBundle unlocks0;
    private CustomUnlockBundle unlocks1;
    private CustomUnlockBundle unlocks2;
    private CustomUnlockBundle unlocks3;
    private CustomUnlockBundle unlocks4;

    // =============== /INPUT TEXTURE LOCATION/ =================


    // =============== SUBSCRIBE, CREATE THE COLOR, INITIALIZE =================

    public CatGirlMod() {
        logger.info("Subscribe to BaseMod hooks");

        BaseMod.subscribe(this);

        logger.info("Done subscribing");

        logger.info("Creating the color " + AbstractCardEnum.CATGIRL_TEAL.toString());

        BaseMod.addColor(AbstractCardEnum.CATGIRL_TEAL, CATGIRL_TEAL, CATGIRL_TEAL, CATGIRL_TEAL,
                CATGIRL_TEAL, CATGIRL_TEAL, CATGIRL_TEAL, CATGIRL_TEAL,
                ATTACK_CATGIRL_TEAL, SKILL_CATGIRL_TEAL, POWER_CATGIRL_TEAL, ENERGY_ORB_CATGIRL_TEAL,
                ATTACK_CATGIRL_TEAL_PORTRAIT, SKILL_CATGIRL_TEAL_PORTRAIT, POWER_CATGIRL_TEAL_PORTRAIT,
                ENERGY_ORB_CATGIRL_TEAL_PORTRAIT, CARD_ENERGY_ORB);

        catgirlDefault.setProperty(PROP_EVENT_SHARING, "FALSE");
        catgirlDefault.setProperty(PROP_RELIC_SHARING, "FALSE");
        catgirlDefault.setProperty(PROP_POTION_SHARING, "FALSE");
        catgirlDefault.setProperty(PROP_UNLOCK_ALL, "FALSE");

        loadConfigData();
        logger.info("Done creating the color");
    }

    @SuppressWarnings("unused")
    public static void initialize() {
        logger.info("========================= Initializing Default Mod. Hi. =========================");
        CatGirlMod catgirlmod = new CatGirlMod();
        incrementDiscardSubscribers = new ArrayList<>();
        logger.info("========================= /Default Mod Initialized. Hello World./ =========================");
    }

    private static <T> void subscribeIfInstance(ArrayList<T> list, ISubscriber sub, Class<T> clazz) {
        if (clazz.isInstance(sub)) {
            list.add(clazz.cast(sub));
        }
    }

    public static void subscribe(ISubscriber sub) {
        subscribeIfInstance(incrementDiscardSubscribers, sub, IncrementDiscardSubscriber.class);
    }

    private static <T> void unsubscribeIfInstance(ArrayList<T> list, ISubscriber sub, Class<T> clazz) {
        if (clazz.isInstance(sub)) {
            list.remove(clazz.cast(sub));
        }
    }

    public static void unsubscribe(ISubscriber sub) {
        unsubscribeIfInstance(incrementDiscardSubscribers, sub, IncrementDiscardSubscriber.class);
    }

    // ============== /SUBSCRIBE, CREATE THE COLOR, INITIALIZE/ =================


    // =============== LOAD THE CHARACTER =================

    @Override
    public void receiveEditCharacters() {
        logger.info("Beginning to edit characters. " + "Add " + TheCatGirlEnum.THE_CATGIRL.toString());

        BaseMod.addCharacter(new TheCatGirl("the CatGirl", TheCatGirlEnum.THE_CATGIRL),
                THE_CATGIRL_BUTTON, THE_CATGIRL_PORTRAIT, TheCatGirlEnum.THE_CATGIRL);

        receiveEditPotions();
        logger.info("Added " + TheCatGirlEnum.THE_CATGIRL.toString());
    }

    // =============== /LOAD THE CHARACTER/ =================


    // =============== POST-INITIALIZE =================

    @Override
    public void receivePostInitialize() {
        UIStrings configStrings = CardCrawlGame.languagePack.getUIString("catgirlConfigMenuText");

        logger.info("Loading badge image and mod options");
        // Load the Mod Badge
        Texture badgeTexture = new Texture(BADGE_IMAGE);

        // Create the Mod Menu
        ModPanel settingsPanel = new ModPanel();
        /*settingsPanel.addUIElement(new ModLabel("CatGirlMod doesn't have any settings! An example of those may come later.", 400.0f, 700.0f,
                settingsPanel, (me) -> {
        }));*/
        ModLabeledToggleButton contentSharingBtnRelics = new ModLabeledToggleButton(configStrings.TEXT[0],
                350.0f, 650.0f, Settings.CREAM_COLOR, FontHelper.charDescFont,
                contentSharing_relics, settingsPanel, (label) -> {}, (button) -> {
            contentSharing_relics = button.enabled;
            adjustRelics();
            saveData();
        });

        ModLabeledToggleButton contentSharingBtnEvents = new ModLabeledToggleButton(configStrings.TEXT[2],
                350.0f, 600.0f, Settings.CREAM_COLOR, FontHelper.charDescFont,
                contentSharing_events, settingsPanel, (label) -> {}, (button) -> {
            contentSharing_events = button.enabled;
            saveData();
        });

        ModLabeledToggleButton contentSharingBtnPotions = new ModLabeledToggleButton(configStrings.TEXT[1],
                350.0f, 550.0f, Settings.CREAM_COLOR, FontHelper.charDescFont,
                contentSharing_potions, settingsPanel, (label) -> {}, (button) -> {
            contentSharing_potions = button.enabled;
            refreshPotions();
            saveData();
        });

        ModLabeledToggleButton unlockEverythingBtn = new ModLabeledToggleButton(configStrings.TEXT[3],
                350.0f, 450.0f, Settings.CREAM_COLOR, FontHelper.charDescFont,
                unlockEverything, settingsPanel, (label) -> {}, (button) -> {
            unlockEverything = button.enabled;
            unlockEverything();
            saveData();
        });

        settingsPanel.addUIElement(unlockEverythingBtn);
        settingsPanel.addUIElement(contentSharingBtnEvents);
        settingsPanel.addUIElement(contentSharingBtnPotions);
        settingsPanel.addUIElement(contentSharingBtnRelics);

        BaseMod.registerModBadge(badgeTexture, MODNAME, AUTHOR, DESCRIPTION, settingsPanel);

        logger.info("Done loading badge Image and mod options");

        addPotions();

    }

    public void refreshPotions(){
        //BaseMod.removePotion(Potion.POTION_ID);

        addPotions();
    }

    public void addPotions(){
        if (contentSharing_potions){
            //BaseMod.addPotion(Potion.class, Color.FOREST, Color.BLACK, Color.BLACK, Potion.POTION_ID);
        } else {
            //BaseMod.addPotion(Potion.class, Color.FOREST, Color.BLACK, Color.BLACK, Potion.POTION_ID, TheCatGirlEnum.THE_CATGIRL);
        }

        //BaseMod.addPotion(NotSharedPotion.class, Color.GREEN, Color.FOREST, Color.BLACK, NotSharedPotion.POTION_ID, TheCatGirlEnum.THE_CATGIRL);
    }

    // =============== / POST-INITIALIZE/ =================


    // ================ ADD POTIONS ===================


    public void receiveEditPotions() {
        logger.info("Beginning to edit potions");

        // Class Specific Potion. If you want your potion to not be class-specific,
        // just remove the player class at the end (in this case the "TheCatGirlEnum.THE_CATGIRL".
        // Remember, you can press ctrl+P inside parentheses like addPotions)

        //BaseMod.addPotion(PlaceholderPotion.class, PLACEHOLDER_POTION_LIQUID, PLACEHOLDER_POTION_HYBRID, PLACEHOLDER_POTION_SPOTS, PlaceholderPotion.POTION_ID, TheCatGirlEnum.THE_CATGIRL);

        logger.info("Done editing potions");
    }

    // ================ /ADD POTIONS/ ===================


    // ================ ADD RELICS ===================

    @Override
    public void receiveEditRelics() {
        logger.info("Adding relics");

        // This adds a character specific relic. Only when you play with the mentioned color, will you get this relic.
        BaseMod.addRelicToCustomPool(new TrainedRelic(), AbstractCardEnum.CATGIRL_TEAL);

        BaseMod.addRelicToCustomPool(new LostAndFoundRelic(), AbstractCardEnum.CATGIRL_TEAL);
        BaseMod.addRelicToCustomPool(new MonsterGuideRelic(), AbstractCardEnum.CATGIRL_TEAL);
        BaseMod.addRelicToCustomPool(new ThrowingDaggerRelic(), AbstractCardEnum.CATGIRL_TEAL);
        //Boss Relics
        BaseMod.addRelicToCustomPool(new BeginnersLuckRelic(), AbstractCardEnum.CATGIRL_TEAL);
        BaseMod.addRelicToCustomPool(new InstinctsRelic(), AbstractCardEnum.CATGIRL_TEAL);
        BaseMod.addRelicToCustomPool(new SRankRelic(), AbstractCardEnum.CATGIRL_TEAL);

      //  BaseMod.addRelicToCustomPool(new DefaultClickableRelic(), AbstractCardEnum.CATGIRL_TEAL);

        // This adds a relic to the Shared pool. Every character can find this relic.
        //BaseMod.addRelic(new PlaceholderRelic2(), RelicType.SHARED);

        shareableRelics.add(new AmbushRelic());
        shareableRelics.add(new DriedFishRelic());

        if (unlocks2 == null){
            unlocks2 = new CustomUnlockBundle(AbstractUnlock.UnlockType.RELIC,
                    LostAndFoundRelic.ID, MonsterGuideRelic.ID, ThrowingDaggerRelic.ID
            );

            unlocks4 = new CustomUnlockBundle(AbstractUnlock.UnlockType.RELIC,
                    BeginnersLuckRelic.ID, InstinctsRelic.ID, SRankRelic.ID
            );
        }

        addSharedRelics();

        logger.info("Done adding relics!");
    }

    public void addSharedRelics(){
        if (contentSharing_relics) {
            for (AbstractRelic relic : shareableRelics) {
                BaseMod.addRelic(relic, RelicType.SHARED);
            }
        } else {
            for (AbstractRelic relic : shareableRelics) {
                BaseMod.addRelicToCustomPool(relic, AbstractCardEnum.CATGIRL_TEAL);
            }
        }
    }

    // ================ /ADD RELICS/ ===================


    // ================ ADD CARDS ===================

    @Override
    public void receiveEditCards() {
        logger.info("Adding variables");
        // Add the Custom Dynamic Variables
        logger.info("Add variables");
        // Add the Custom Dynamic variables
        BaseMod.addDynamicVariable(new DefaultCustomVariable());
        BaseMod.addDynamicVariable(new DefaultSecondMagicNumber());

        logger.info("Adding cards");
        // Add the cards
        // Basic
        BaseMod.addCard(new ClawStrike());
        BaseMod.addCard(new Strike());
        BaseMod.addCard(new Defend());
        BaseMod.addCard(new Evasion());

        // Adventurer
        BaseMod.addCard(new AllIn());
        BaseMod.addCard(new ElegantStrike());
        BaseMod.addCard(new FocusedStrike());
        BaseMod.addCard(new Parry());
        BaseMod.addCard(new PlayAround());
        BaseMod.addCard(new PoisedAttack());
        BaseMod.addCard(new QuickStrike());
        BaseMod.addCard(new SpinningStrike());

        BaseMod.addCard(new AdaptivePacing());
        BaseMod.addCard(new ChangeOfPlans());
        BaseMod.addCard(new DefensiveStance());
        BaseMod.addCard(new EmergencyParry());
        BaseMod.addCard(new KeepAlert());
        BaseMod.addCard(new LearnThePattern());
        BaseMod.addCard(new PerfectBlock());
        BaseMod.addCard(new Prepared());
        BaseMod.addCard(new TwoStep());

        BaseMod.addCard(new AdventurersInsight());
        BaseMod.addCard(new Counterattack());
        BaseMod.addCard(new PoiseStance());

        // Beast
        BaseMod.addCard(new BloodBath());
        BaseMod.addCard(new BloodyClaws());
        BaseMod.addCard(new Claws());
        BaseMod.addCard(new ClawScratch());
        BaseMod.addCard(new ComboAttack());
        BaseMod.addCard(new FinishingMove());
        BaseMod.addCard(new HungerClaw());
        BaseMod.addCard(new RageClaws());
        BaseMod.addCard(new UnrelentingAssault());

        BaseMod.addCard(new Growl());
        BaseMod.addCard(new Howl());
        BaseMod.addCard(new Insane());
        BaseMod.addCard(new SevenLives());
        BaseMod.addCard(new SmellFear());
        BaseMod.addCard(new Stalk());

        BaseMod.addCard(new Beastification());
        BaseMod.addCard(new BleedingWounds());
        BaseMod.addCard(new BloodScent());
        BaseMod.addCard(new Enrage());

        // Clumsy
        BaseMod.addCard(new AccidentalSlap());
        BaseMod.addCard(new BodyBlow());
        BaseMod.addCard(new FumblingBlow());
        BaseMod.addCard(new LuckyBlow());
        BaseMod.addCard(new Misfire());
        BaseMod.addCard(new RecoveryBlow());
        BaseMod.addCard(new RunningWithDaggers());
        BaseMod.addCard(new TripLunge());
        BaseMod.addCard(new TryingMyBest());

        BaseMod.addCard(new AccidentalHeadbutt());
        BaseMod.addCard(new ClenchYourTeeth());
        BaseMod.addCard(new DecliningDefenses());
        BaseMod.addCard(new LearnByMistake());
        BaseMod.addCard(new LostTrinket());
        BaseMod.addCard(new Meow());
        BaseMod.addCard(new SearchBackpack());
        BaseMod.addCard(new Slip());
        BaseMod.addCard(new ThatWasntIt());
        BaseMod.addCard(new TrainWreck());

        BaseMod.addCard(new GoddessOfMisfortune());
        BaseMod.addCard(new NeverGivingUp());
        BaseMod.addCard(new PilingErrors());
        BaseMod.addCard(new ShockedWitness());

        BaseMod.addCard(new Stumble());

        // Test
        //BaseMod.addCard(new MultiHitStrikenTest());
        //BaseMod.addCard(new MultiHitGashTest());
        //BaseMod.addCard(new AllEnemyStrike());
        //BaseMod.addCard(new AllEnemyClaw());

        /*BaseMod.addCard(new DefaultSecondMagicNumberSkill());
        //BaseMod.addCard(new DefaultCommonAttack());
        BaseMod.addCard(new DefaultAttackWithVariable());
        //BaseMod.addCard(new DefaultCommonSkill());
        BaseMod.addCard(new DefaultCommonPower());
        BaseMod.addCard(new DefaultUncommonSkill());
        BaseMod.addCard(new DefaultUncommonAttack());
        BaseMod.addCard(new DefaultUncommonPower());
        BaseMod.addCard(new DefaultRareAttack());
        BaseMod.addCard(new DefaultRareSkill());
        BaseMod.addCard(new DefaultRarePower());*/

        logger.info("Making sure the basic cards are unlocked.");
        // Unlock the cards
        // Basic
        UnlockTracker.unlockCard(ClawStrike.ID);
        UnlockTracker.unlockCard(Strike.ID);
        UnlockTracker.unlockCard(Defend.ID);
        UnlockTracker.unlockCard(Evasion.ID);

        unlocks0 = new CustomUnlockBundle(
                FocusedStrike.ID, RageClaws.ID, TripLunge.ID
        );

        unlocks1 = new CustomUnlockBundle(
                Counterattack.ID, Enrage.ID, PilingErrors.ID
        );

        unlocks3 = new CustomUnlockBundle(
                AdaptivePacing.ID, BloodyClaws.ID, NeverGivingUp.ID
        );

        logger.info("Done adding cards!");
    }

    public void unlockEverything(){
        UnlockTracker.unlockCard(AllIn.ID);
        UnlockTracker.unlockCard(ElegantStrike.ID);
        UnlockTracker.unlockCard(FocusedStrike.ID);
        UnlockTracker.unlockCard(Parry.ID);
        UnlockTracker.unlockCard(PlayAround.ID);
        UnlockTracker.unlockCard(PoisedAttack.ID);
        UnlockTracker.unlockCard(QuickStrike.ID);
        UnlockTracker.unlockCard(SpinningStrike.ID);

        UnlockTracker.unlockCard(AdaptivePacing.ID);
        UnlockTracker.unlockCard(ChangeOfPlans.ID);
        UnlockTracker.unlockCard(DefensiveStance.ID);
        UnlockTracker.unlockCard(EmergencyParry.ID);
        UnlockTracker.unlockCard(KeepAlert.ID);
        UnlockTracker.unlockCard(LearnThePattern.ID);
        UnlockTracker.unlockCard(PerfectBlock.ID);
        UnlockTracker.unlockCard(Prepared.ID);
        UnlockTracker.unlockCard(TwoStep.ID);

        UnlockTracker.unlockCard(AdventurersInsight.ID);
        UnlockTracker.unlockCard(Counterattack.ID);
        UnlockTracker.unlockCard(PoiseStance.ID);

        // Beast
        UnlockTracker.unlockCard(BloodBath.ID);
        UnlockTracker.unlockCard(BloodyClaws.ID);
        UnlockTracker.unlockCard(Claws.ID);
        UnlockTracker.unlockCard(ClawScratch.ID);
        UnlockTracker.unlockCard(ComboAttack.ID);
        UnlockTracker.unlockCard(FinishingMove.ID);
        UnlockTracker.unlockCard(HungerClaw.ID);
        UnlockTracker.unlockCard(RageClaws.ID);
        UnlockTracker.unlockCard(UnrelentingAssault.ID);

        UnlockTracker.unlockCard(Growl.ID);
        UnlockTracker.unlockCard(Howl.ID);
        UnlockTracker.unlockCard(Insane.ID);
        UnlockTracker.unlockCard(SevenLives.ID);
        UnlockTracker.unlockCard(SmellFear.ID);
        UnlockTracker.unlockCard(Stalk.ID);

        UnlockTracker.unlockCard(Beastification.ID);
        UnlockTracker.unlockCard(BleedingWounds.ID);
        UnlockTracker.unlockCard(BloodScent.ID);
        UnlockTracker.unlockCard(Enrage.ID);

        // Clumsy
        UnlockTracker.unlockCard(AccidentalSlap.ID);
        UnlockTracker.unlockCard(BodyBlow.ID);
        UnlockTracker.unlockCard(FumblingBlow.ID);
        UnlockTracker.unlockCard(LuckyBlow.ID);
        UnlockTracker.unlockCard(Misfire.ID);
        UnlockTracker.unlockCard(RecoveryBlow.ID);
        UnlockTracker.unlockCard(RunningWithDaggers.ID);
        UnlockTracker.unlockCard(TripLunge.ID);
        UnlockTracker.unlockCard(TryingMyBest.ID);

        UnlockTracker.unlockCard(AccidentalHeadbutt.ID);
        UnlockTracker.unlockCard(ClenchYourTeeth.ID);
        UnlockTracker.unlockCard(DecliningDefenses.ID);
        UnlockTracker.unlockCard(LearnByMistake.ID);
        UnlockTracker.unlockCard(LostTrinket.ID);
        UnlockTracker.unlockCard(Meow.ID);
        UnlockTracker.unlockCard(SearchBackpack.ID);
        UnlockTracker.unlockCard(Slip.ID);
        UnlockTracker.unlockCard(ThatWasntIt.ID);
        UnlockTracker.unlockCard(TrainWreck.ID);

        UnlockTracker.unlockCard(GoddessOfMisfortune.ID);
        UnlockTracker.unlockCard(NeverGivingUp.ID);
        UnlockTracker.unlockCard(PilingErrors.ID);
        UnlockTracker.unlockCard(ShockedWitness.ID);

        UnlockTracker.unlockCard(Stumble.ID);

        //UnlockTracker.addScore(SlimeboundEnum.SLIMEBOUND, 1000000);

        clearUnlockBundles();
    }

    @Override
    public void receiveSetUnlocks() {
        if (!unlockEverything) {
            BaseMod.addUnlockBundle(unlocks0, TheCatGirlEnum.THE_CATGIRL, 0);

            BaseMod.addUnlockBundle(unlocks1, TheCatGirlEnum.THE_CATGIRL, 1);

            BaseMod.addUnlockBundle(unlocks2, TheCatGirlEnum.THE_CATGIRL, 2);

            BaseMod.addUnlockBundle(unlocks3, TheCatGirlEnum.THE_CATGIRL, 3);

            BaseMod.addUnlockBundle(unlocks4, TheCatGirlEnum.THE_CATGIRL, 4);


            UnlockTracker.addCard(FocusedStrike.ID);
            UnlockTracker.addCard(RageClaws.ID);
            UnlockTracker.addCard(TripLunge.ID);

            UnlockTracker.addCard(Counterattack.ID);
            UnlockTracker.addCard(Enrage.ID);
            UnlockTracker.addCard(PilingErrors.ID);

            UnlockTracker.addCard(AdaptivePacing.ID);
            UnlockTracker.addCard(BloodyClaws.ID);
            UnlockTracker.addCard(NeverGivingUp.ID);


            UnlockTracker.addRelic(LostAndFoundRelic.ID);
            UnlockTracker.addRelic(MonsterGuideRelic.ID);
            UnlockTracker.addRelic(ThrowingDaggerRelic.ID);

            UnlockTracker.addRelic(BeginnersLuckRelic.ID);
            UnlockTracker.addRelic(InstinctsRelic.ID);
            UnlockTracker.addRelic(SRankRelic.ID);
        }
    }

    public void clearUnlockBundles(){
        BaseMod.removeUnlockBundle(TheCatGirlEnum.THE_CATGIRL,0);
        BaseMod.removeUnlockBundle(TheCatGirlEnum.THE_CATGIRL,1);
        BaseMod.removeUnlockBundle(TheCatGirlEnum.THE_CATGIRL,2);
        BaseMod.removeUnlockBundle(TheCatGirlEnum.THE_CATGIRL,3);
        BaseMod.removeUnlockBundle(TheCatGirlEnum.THE_CATGIRL,4);
        receiveSetUnlocks();
    }

    // There are better ways to do this than listing every single individual card, but I do not want to complicate things
    // in a "tutorial" mod. This will do and it's completely ok to use. If you ever want to clean up and
    // shorten all the imports, go look take a look at other mods, such Conspire or Hubris.

    // ================ /ADD CARDS/ ===================

    // ================ HOOKS =====================

    public static void incrementDiscardHook(boolean endOfTurn) {
        if (endOfTurn) {
            return;
        }

        totalDiscardedThisCombat++;

        boolean bNullExist = false;
        for (IncrementDiscardSubscriber sub : incrementDiscardSubscribers) {
            if (sub != null) {
                sub.receiveIncrementDiscard(endOfTurn);
            } else {
                bNullExist = true;
            }
        }

        // Powers don't have an universal hook for when they are destroyed, so we have to clean our list
        if (bNullExist) {
            incrementDiscardSubscribers.removeAll(Collections.singleton(null));
        }
    }

    public static void clearHook() {
        totalDiscardedThisCombat = 0;
    }

    @Override
    public void receivePostBattle(AbstractRoom battleRoom) {
    }

    // ================ /HOOKS/ ===================

    // ================ LOAD THE TEXT ===================

    @Override
    public void receiveEditStrings() {
        logger.info("Beginning to edit strings");

        // CardStrings
        BaseMod.loadCustomStringsFile(CardStrings.class,
                "localization/eng/CatGirlMod-Card-Strings.json");

        // PowerStrings
        BaseMod.loadCustomStringsFile(PowerStrings.class,
                "localization/eng/CatGirlMod-Power-Strings.json");

        // RelicStrings
        BaseMod.loadCustomStringsFile(RelicStrings.class,
                "localization/eng/CatGirlMod-Relic-Strings.json");

        // PotionStrings
        BaseMod.loadCustomStringsFile(PotionStrings.class,
                "localization/eng/CatGirlMod-Potion-Strings.json");

        // CharacterStrings
        BaseMod.loadCustomStringsFile(CharacterStrings.class,
                "localization/eng/CatGirlMod-Character-Strings.json");

        // OrbStrings
        BaseMod.loadCustomStringsFile(OrbStrings.class,
                "localization/eng/CatGirlMod-Orb-Strings.json");

        // UIStrings
        BaseMod.loadCustomStringsFile(UIStrings.class,
                "localization/eng/CatGirlMod-UI-Strings.json");

        logger.info("Done edittting strings");
    }

    // ================ /LOAD THE TEXT/ ===================

    // ================ SAVE LOAD CONFIGS ===================

    public static void clearData() {
        saveData();
    }

    public static void saveData() {
        try {
            SpireConfig config = new SpireConfig("CatGirlMod", "CatgirlSaveData", catgirlDefault);
            config.setBool(PROP_EVENT_SHARING, contentSharing_events);
            config.setBool(PROP_RELIC_SHARING, contentSharing_relics);
            config.setBool(PROP_POTION_SHARING, contentSharing_potions);
            config.setBool(PROP_UNLOCK_ALL, unlockEverything);

            config.save();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void adjustRelics(){
        // remove all shareable relics wherever they are, then re-add them.
        // assuming right now that there are no overheated expansion relics shared by other characters.
        for (AbstractRelic relic : shareableRelics){
            BaseMod.removeRelic(relic);
            BaseMod.removeRelicFromCustomPool(relic, AbstractCardEnum.CATGIRL_TEAL);
        }

        addSharedRelics();
    }

    public static void loadConfigData() {
        try {
            logger.info("CatGirlMod | Loading Config Preferences...");
            SpireConfig config = new SpireConfig("CatGirlMod", "CatgirlSaveData", catgirlDefault);
            config.load();
            contentSharing_events = config.getBool(PROP_EVENT_SHARING);
            contentSharing_relics = config.getBool(PROP_RELIC_SHARING);
            contentSharing_potions = config.getBool(PROP_POTION_SHARING);
            unlockEverything = config.getBool(PROP_UNLOCK_ALL);
        } catch(Exception e) {
            e.printStackTrace();
            clearData();
        }
    }

    // ================ /SAVE LOAD CONFIGS/ =================

    // ================ LOAD THE KEYWORDS ===================

    @Override
    public void receiveEditKeywords() {
        Gson gson = new Gson();
        String json = Gdx.files.internal("localization/eng/CatGirlMod-Keyword-Strings.json").readString(String.valueOf(StandardCharsets.UTF_8));
        com.evacipated.cardcrawl.mod.stslib.Keyword[] keywords = gson.fromJson(json, com.evacipated.cardcrawl.mod.stslib.Keyword[].class);

        if (keywords != null) {
            for (Keyword keyword : keywords) {
                BaseMod.addKeyword(keyword.NAMES, keyword.DESCRIPTION);
            }
        }
    }

    // ================ /LOAD THE KEYWORDS/ ===================    

    // this adds "ModName:" before the ID of any card/relic/power etc.
    // in order to avoid conflicts if any other mod uses the same ID.
    public static String makeID(String idText) {
        return "theCatGirl:" + idText;
    }

}