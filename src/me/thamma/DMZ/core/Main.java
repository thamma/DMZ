package me.thamma.DMZ.core;

import me.thamma.DMZ.Battle.BattleListener;
import me.thamma.DMZ.Battle.EquipListener;
import me.thamma.DMZ.Chunky.Chunky;
import me.thamma.DMZ.Chunky.ChunkyCommands;
import me.thamma.DMZ.Chunky.ChunkyListener;
import me.thamma.DMZ.Connection.ConnectListener;
import me.thamma.DMZ.chests.Chest;
import me.thamma.DMZ.chests.ChestCommands;
import me.thamma.DMZ.chests.ChestListener;
import me.thamma.DMZ.data.DataCommands;
import me.thamma.DMZ.data.DataListener;
import me.thamma.DMZ.utils.FileManager;
import me.thamma.DMZ.warps.WarpCommands;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;

public class Main extends JavaPlugin {

    public static FileManager config = new FileManager("DMZ/config.yml");

    @Override
    public void onEnable() {

        getCommand("chest").setExecutor(new ChestCommands());
        getCommand("warp").setExecutor(new WarpCommands());
        getCommand("data").setExecutor(new DataCommands(this));
        getCommand("chunky").setExecutor(new ChunkyCommands());

        getServer().getPluginManager().registerEvents(new MyListener(this), this);
        getServer().getPluginManager().registerEvents(new ChestListener(this), this);
        getServer().getPluginManager().registerEvents(new DataListener(), this);
        getServer().getPluginManager().registerEvents(new ChunkyListener(), this);
        getServer().getPluginManager().registerEvents(new BattleListener(), this);
        getServer().getPluginManager().registerEvents(new EquipListener(), this);
        getServer().getPluginManager().registerEvents(new ConnectListener(), this);
        getServer().getPluginManager().registerEvents(new ColorSign(), this);


        ChunkyListener.lastChunk = new HashMap<String, String>();
        ChunkyListener.settings = new HashMap<String, ChunkyListener.Setting>();
        me.thamma.DMZ.utils.Cooldowns.initialize();
        me.thamma.DMZ.Chunky.Chunky.initializeMap();
        Chest.respawnAll();

    }

    @Override
    public void onDisable() {
        Chunky.saveAll();
    }

}
