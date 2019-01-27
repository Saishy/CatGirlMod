package catgirlmod.patches;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePostfixPatch;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.blue.Claw;

@SpirePatch(
        clz= Claw.class,
        method=SpirePatch.CONSTRUCTOR
)
public class CatGirlPatch_AddClawTag {


    public static void Postfix(Claw __instance) {
        __instance.tags.add(AbstractCardEnum.CLAW);
    }
}
