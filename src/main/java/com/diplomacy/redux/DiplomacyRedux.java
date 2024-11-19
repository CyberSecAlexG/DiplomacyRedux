package com.diplomacy.redux;

import com.github.mushroommif.fabricapi.ConfigUtil;
import net.fabricmc.api.ModInitializer;


public class DiplomacyRedux implements ModInitializer {

    public static Config config;

    @Override
    public void onInitialize() {
        config = ConfigUtil.loadOrCreateConfig(
                Config.class, "diplomacy-redux", Config::createDefault
        );
        config.validate();
    }
}
