package me.thamma.DMZ.core;

import java.util.HashMap;
import java.util.List;

import org.bukkit.plugin.java.JavaPlugin;

import me.thamma.DMZ.chests.ChestCommands;
import me.thamma.DMZ.chests.MyChest;
import me.thamma.DMZ.chunky.Chunky;
import me.thamma.DMZ.chunky.ChunkyCommands;
import me.thamma.DMZ.custom.MyItemCommands;
import me.thamma.DMZ.data.DataCommands;
import me.thamma.DMZ.listeners.BattleListener;
import me.thamma.DMZ.listeners.ChestListener;
import me.thamma.DMZ.listeners.ChunkyListener;
import me.thamma.DMZ.listeners.ConnectListener;
import me.thamma.DMZ.listeners.DataListener;
import me.thamma.DMZ.listeners.ItemInteractListener;
import me.thamma.DMZ.listeners.TaskListener;
import me.thamma.DMZ.utils.Cooldowns;
import me.thamma.DMZ.utils.TitlesAPI;
import me.thamma.DMZ.warps.WarpCommands;

public class Main extends JavaPlugin {

	@Override
	public void onEnable() {

		getCommand("chest").setExecutor(new ChestCommands());
		getCommand("warp").setExecutor(new WarpCommands());
		getCommand("data").setExecutor(new DataCommands());
		getCommand("chunky").setExecutor(new ChunkyCommands());
		getCommand("myitem").setExecutor(new MyItemCommands());

		getServer().getPluginManager().registerEvents(new MyListener(), this);
		getServer().getPluginManager().registerEvents(new ChestListener(this), this);
		getServer().getPluginManager().registerEvents(new DataListener(), this);
		getServer().getPluginManager().registerEvents(new ChunkyListener(), this);
		getServer().getPluginManager().registerEvents(new BattleListener(), this);
		getServer().getPluginManager().registerEvents(new ConnectListener(), this);
		getServer().getPluginManager().registerEvents(new ColorSign(), this);
		getServer().getPluginManager().registerEvents(new TaskListener(), this);
		getServer().getPluginManager().registerEvents(new ItemInteractListener(this), this);

		initMaps();

		MyChest.respawnAll();

	}

	public void initMaps() {
		ChunkyListener.lastChunk = new HashMap<String, String>();
		ChunkyListener.settings = new HashMap<String, List<ChunkyListener.Setting>>();
		Cooldowns.initialize();
		Chunky.initializeMap();
		TitlesAPI.initialize();
	}

	@Override
	public void onDisable() {
		Chunky.saveAll();
	}

}
