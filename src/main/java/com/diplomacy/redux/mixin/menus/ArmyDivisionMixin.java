package com.diplomacy.redux.mixin.menus;

import aoh.kingdoms.history.map.army.ArmyDivision;
import com.diplomacy.redux.ArmyDivisionExtension;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(ArmyDivision.class)
public class ArmyDivisionMixin implements ArmyDivisionExtension {

    @Shadow
    public int civID;

    public void updateCivID(int newCivID) {
        this.civID = newCivID;
    }
}
