package com.github.deafkite42.meow;

import com.github.deafkite42.meow.commands.*;

import co.aikar.commands.PaperCommandManager;
import org.bukkit.plugin.java.JavaPlugin;

public final class Meow extends JavaPlugin {
    private static Meow instance;
    public static Meow getInstance() {
        return instance;
    }

    @Override
    public void onEnable() {
        getLogger().info("Meow enabled");
        instance = this;

        Config.init(getConfig());
        saveConfig();

        registerCommands();
    }

    @Override
    public void onDisable() {
        getLogger().info("Meow disabled");
    }
    public void registerCommands() {
        PaperCommandManager manager = new PaperCommandManager(this);

        manager.registerCommand(new MeowCommand());
        manager.registerCommand(new PurrCommand());
    }
}
