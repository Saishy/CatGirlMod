package catgirlmod.patches;

import catgirlmod.CatGirlMod;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.actions.GameActionManager;

@SpirePatch(
        cls="com.megacrit.cardcrawl.actions.GameActionManager",
        method="clear"
)
public class ClearPatch {

    public static void Prefix(GameActionManager __instance) {
        CatGirlMod.clearHook();
    }
}
