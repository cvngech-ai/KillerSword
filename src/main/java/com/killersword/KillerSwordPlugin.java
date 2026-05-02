package com.killersword;

import org.bukkit.plugin.java.JavaPlugin;

public class KillerSwordPlugin extends JavaPlugin {

    private static KillerSwordPlugin instance;

    @Override
    public void onEnable() {
        instance = this;
        getServer().getPluginManager().registerEvents(new KillerSwordListener(this), this);
        getCommand("killersword").setExecutor(new KillerSwordCommand(this));
        getLogger().info("KillerSword Plugin enabled! ⚔️");
    }

    @Override
    public void onDisable() {
        getLogger().info("KillerSword Plugin disabled!");
    }

    public static KillerSwordPlugin getInstance() {
        return instance;
    }
}
