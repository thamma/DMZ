package me.thamma.DMZ.core;

import me.thamma.DMZ.Chunkys.ChunkyCommands;
import me.thamma.DMZ.Chunkys.ChunkyListener;
import me.thamma.DMZ.chests.ChestCommands;
import me.thamma.DMZ.chests.ChestListener;
import me.thamma.DMZ.data.DataCommands;
import me.thamma.DMZ.data.DataListener;
import me.thamma.DMZ.warps.WarpCommands;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {

    @Override
    public void onEnable() {

        getCommand("chest").setExecutor(new ChestCommands());
        getCommand("warp").setExecutor(new WarpCommands());
        getCommand("data").setExecutor(new DataCommands());
        getCommand("chunky").setExecutor(new ChunkyCommands());

        getServer().getPluginManager().registerEvents(new MyListener(this), this);
        getServer().getPluginManager().registerEvents(new ChestListener(), this);
        getServer().getPluginManager().registerEvents(new DataListener(), this);
        getServer().getPluginManager().registerEvents(new ChunkyListener(), this);

        me.thamma.DMZ.utils.Cooldowns.initialize();

    }

    @Override
    public void onDisable(){

    }

}
