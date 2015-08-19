package me.thamma.DMZ.utils;

import org.bukkit.entity.Player;

public class Database {

	public static FileManager config = new FileManager("DMZ/config.yml");
	public static FileManager chestDb = new FileManager("DMZ/chests.yml");
	public static FileManager chunkyDb = new FileManager("DMZ/chunky.yml");
	public static FileManager warpDb = new FileManager("DMZ/warps.yml");

	public static FileManager playerDb(Player p) {
		return new FileManager("DMZ/players/" + p.getName().toLowerCase() + ".yml");
	}

}
