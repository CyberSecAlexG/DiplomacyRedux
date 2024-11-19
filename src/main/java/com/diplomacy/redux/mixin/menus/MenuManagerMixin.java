package com.diplomacy.redux.mixin.menus;

import aoh.kingdoms.history.mainGame.Game;
import aoh.kingdoms.history.menu.Menu;
import aoh.kingdoms.history.menu.MenuManager;
import com.diplomacy.redux.InGame_Union;
import com.diplomacy.redux.MenuManagerExtension;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

import java.util.List;

@Mixin(MenuManager.class)
public abstract class MenuManagerMixin implements MenuManagerExtension {

    @Shadow
    private List<List<Menu>> menus;

    @Shadow
    public abstract void setOrderOfMenu(int menuID);

    @Shadow
    public static int IN_GAME_POP_UP_MENU_ID;

    @Shadow
    public int IN_GAME;

    @Shadow
    public int IN_GAME_POP_UP;

    @Override
    public final void rebuildInGame_Union(int iCivID) {
        ((List<Menu>) this.menus.get(IN_GAME)).set(IN_GAME_POP_UP, new InGame_Union(iCivID));
        ((Menu) ((List<Menu>) this.menus.get(IN_GAME)).get(IN_GAME_POP_UP)).setVisible(true);
        this.setOrderOfMenu(IN_GAME_POP_UP);
        IN_GAME_POP_UP_MENU_ID = 999; // Unique ID for Union menu
    }
}

