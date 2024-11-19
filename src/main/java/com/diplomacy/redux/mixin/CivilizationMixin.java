package com.diplomacy.redux.mixin;

import aoh.kingdoms.history.map.civilization.Civilization;
import aoh.kingdoms.history.textures.Image;
import com.diplomacy.redux.CivilizationExtension;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(Civilization.class)
public abstract class CivilizationMixin implements CivilizationExtension {

    @Shadow
    private Image civFlag;

    @Override
    public final void setFlag(Image newFlag) {
        civFlag = newFlag;
    }
}
