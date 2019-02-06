package catgirlmod.patches;

import catgirlmod.cards.adventurer.DefensiveStance;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.helpers.CardLibrary;

@SpirePatch(
        clz= CardLibrary.class,
        method="getCopy",
        paramtypez={
                String.class,
                int.class,
                int.class
        }
)
public class GetCopyPatch {

    public static AbstractCard Postfix(AbstractCard __instance, String key, int upgradeTime, int misc) {
        if (__instance.misc != 0) {
            if (__instance.cardID.equals(DefensiveStance.ID)) {
                __instance.baseMagicNumber = misc;
                __instance.magicNumber = misc;
                __instance.initializeDescription();
            }
        }

        return __instance;
    }
}
