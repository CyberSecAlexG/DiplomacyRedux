package com.diplomacy.redux.mixin.menus;

import aoh.kingdoms.history.menusInGame.Civ.InGame_Civ;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(InGame_Civ.class)
public abstract class InGameCivDebugMixin {

    private int addCallCount = -1; // Counter for add calls

    @Inject(method = "<init>", at = @At(
            value = "INVOKE",
            target = "java/util/List.add(Ljava/lang/Object;)Z"
    ))
    private void logAddCallIndex(CallbackInfo ci) {
        addCallCount++;
        System.out.println("List.add call at ordinal: " + addCallCount);
    }
}