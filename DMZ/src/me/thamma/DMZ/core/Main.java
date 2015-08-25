package me.thamma.DMZ.core;

import java.util.HashMap;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import me.thamma.DMZ.Chunky.Chunky;
import me.thamma.DMZ.Chunky.ChunkyCommands;
import me.thamma.DMZ.Listeners.BattleListener;
import me.thamma.DMZ.Listeners.ChestListener;
import me.thamma.DMZ.Listeners.ChunkyListener;
import me.thamma.DMZ.Listeners.ConnectListener;
import me.thamma.DMZ.Listeners.DataListener;
import me.thamma.DMZ.Listeners.ItemInteractListener;
import me.thamma.DMZ.Listeners.ChatTaskListener;
import me.thamma.DMZ.chests.ChestCommands;
import me.thamma.DMZ.chests.MyChest;
import me.thamma.DMZ.custom.MyItemCommands;
import me.thamma.DMZ.data.DataCommands;
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
		getServer().getPluginManager().registerEvents(new ChatTaskListener(), this);
		getServer().getPluginManager().registerEvents(new ItemInteractListener(this), this);

		initMaps();

		MyChest.respawnAll();

	}

	public void initMaps() {
		ChunkyListener.lastChunk = new HashMap<String, String>();
		ChunkyListener.settings = new HashMap<String, List<ChunkyListener.Setting>>();
		me.thamma.DMZ.utils.Cooldowns.initialize();
		me.thamma.DMZ.Chunky.Chunky.initializeMap();

		TitlesAPI.nmsver = Bukkit.getServer().getClass().getPackage().getName();
		TitlesAPI.nmsver = TitlesAPI.nmsver.substring(TitlesAPI.nmsver.lastIndexOf(".") + 1);
	}

	@Override
	public void onDisable() {
		Chunky.saveAll();
	}

}
