package me.thamma.DMZ.core;

import me.thamma.DMZ.chests.ChestCommands;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {

    @Override
    public void onEnable() {

        getCommand("chest").setExecutor(new ChestCommands());

        getServer().getPluginManager().registerEvents(new MyListener(this), this);

        me.thamma.DMZ.utils.Cooldowns.initialize();

    }

    @Override
    public void onDisable(){

    }

}
