package me.thamma.DMZ.core;

import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {
    @Override
    public void onEnable() {



        getServer().getPluginManager().registerEvents(new MyListener(this), this);

    }

    @Override
    public void onDisable(){

    }

}
