/*package catgirlmod.patches;

import com.evacipated.cardcrawl.modthespire.lib.*;
import com.evacipated.cardcrawl.modthespire.patcher.PatchingException;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import javassist.CannotCompileException;
import javassist.CtBehavior;

import java.util.ArrayList;

@SpirePatch(
        clz= AbstractCard.class,
        method="calculateCardDamage"
)
public class CalculateCardDamageEvadePatch {

    @SpireInsertPatch(
            locator = CalculateCardDamageEvadePatch.LocatorSingleEnemy.class
    )
    public static void CalculateCardDamage(AbstractCard __instance, AbstractMonster mo) {

    }

    private static class LocatorSingleEnemy extends SpireInsertLocator {
        public int[] Locate(CtBehavior ctMethodToPatch) throws CannotCompileException, PatchingException {
            Matcher finalMatcher = new Matcher.MethodCallMatcher(
                    "com.badlogic.gdx.math.MathUtils", "floor");
            return LineFinder.findInOrder(ctMethodToPatch, new ArrayList<Matcher>(), finalMatcher);
        }
    }
}
*/