package com.diplomacy.redux.mixin.menus;

import aoh.kingdoms.history.mainGame.CFG;
import aoh.kingdoms.history.mainGame.Game;
import aoh.kingdoms.history.map.diplomacy.DiplomacyManager;
import aoh.kingdoms.history.menu_element.MenuElement;
import aoh.kingdoms.history.menu_element.button.ButtonDiplomacy;
import aoh.kingdoms.history.menusInGame.Civ.InGame_Civ;
import aoh.kingdoms.history.textures.ImageManager;
import aoh.kingdoms.history.textures.Images;
import aoh.kingdoms.history.mainGame.SoundsManager;
import com.badlogic.gdx.graphics.Color;
import com.diplomacy.redux.MenuManagerExtension;
import com.llamalad7.mixinextras.sugar.Local;
import org.spongepowered.asm.mixin.Debug;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;

@Mixin(InGame_Civ.class)
public abstract class InGameCivMixin {
    @Inject(method = "<init>", at = @At(
            value = "INVOKE",
            target = "java/util/List.add(Ljava/lang/Object;)Z",
            ordinal = 120
    ))
    private void addUnionButton(CallbackInfo ci, @Local(ordinal = 1) List<MenuElement> tempElements) {
        int paddingLeft = Images.boxTitleBORDERWIDTH + CFG.PADDING;
        int menuWidth = ImageManager.getImage(Images.insideTop500).getWidth();
        int buttonYPadding = CFG.PADDING*5;
        int buttonW = (menuWidth - paddingLeft * 2 - CFG.PADDING * 3) / 4;
        int buttonH = (int)(buttonW * 1.1F);
        int buttonX = paddingLeft;
        int buttonY = buttonYPadding;

        tempElements.add(new ButtonDiplomacy("Form Union", Images.heart, buttonX, buttonY, buttonW, buttonH) {
            @Override
            public void actionElement() {
                ((MenuManagerExtension) Game.menuManager).rebuildInGame_Union(InGame_Civ.iActiveCivID);
            }

            @Override
            public Color getColorHover1() {
                return new Color(0.1F, 0.6F, 0.2F, 0.15F); // Custom color for hover 1
            }

            @Override
            public Color getColorHover2() {
                return new Color(0.1F, 0.6F, 0.2F, 0.5F); // Custom color for hover 2
            }

            @Override
            public int getSFX() {
                return SoundsManager.SOUND_CLICK_TOP; // Custom sound effect
            }
        });
        // To be completed in further updates!
//        tempElements.add(new ButtonDiplomacy("Support Rebels", Images.revolutionRisk, buttonX, buttonY, buttonW, buttonH) {
//            @Override
//            public void actionElement() {
//                System.out.println("Support rebels clicked!");
//            }
//
//            @Override
//            public Color getColorHover1() {
//                return new Color(DiplomacyManager.COLOR_RED.r, DiplomacyManager.COLOR_RED.g, DiplomacyManager.COLOR_RED.b, 0.15F);
//            }
//
//            @Override
//            public Color getColorHover2() {
//                return new Color(DiplomacyManager.COLOR_RED.r, DiplomacyManager.COLOR_RED.g, DiplomacyManager.COLOR_RED.b, 0.05F);
//            }
//
//            @Override
//            public int getSFX() {
//                return SoundsManager.SOUND_CLICK_TOP; // Custom sound effect
//            }
//        });
//
//        tempElements.add(new ButtonDiplomacy("Offer Vassalisation", Images.vassal, buttonX, buttonY, buttonW, buttonH) {
//            @Override
//            public void actionElement() {
//                System.out.println("Offer vassalisation clicked!");
//            }
//
//            @Override
//            public Color getColorHover1() {
//                return new  Color(DiplomacyManager.COLOR_MILITARY_ACCESS.r, DiplomacyManager.COLOR_MILITARY_ACCESS.g, DiplomacyManager.COLOR_MILITARY_ACCESS.b, 0.15F);
//            }
//
//            @Override
//            public Color getColorHover2() {
//                return new  Color(DiplomacyManager.COLOR_MILITARY_ACCESS.r, DiplomacyManager.COLOR_MILITARY_ACCESS.g, DiplomacyManager.COLOR_MILITARY_ACCESS.b, 0.05F);
//            }
//
//            @Override
//            public int getSFX() {
//                return SoundsManager.SOUND_CLICK_TOP; // Custom sound effect
//            }
//        });
    }
}