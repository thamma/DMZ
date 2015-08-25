package me.thamma.DMZ.listeners;

import static me.thamma.DMZ.utils.Database.config;
import static me.thamma.DMZ.utils.Database.playerDb;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Chest;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import me.thamma.DMZ.chunky.Chunky;
import me.thamma.DMZ.utils.Utils;
import me.thamma.DMZ.warps.Warp;

/**
 * Created by pc on 18.07.2015.
 */
public class ConnectListener implements Listener {

	public static String levelString(Player p) {
		String medal = playerDb(p).getString("medal", "");
		if (!medal.equals(""))
			medal = "&f[" + medal + "&f] ";
		String op = "";
		if (p.isOp())
			op = "&f[&cA&f]&r";
		return op + "&f[&eLv. " + levelColor(p.getLevel()) + p.getLevel() + "&f]" + medal + " &7" + p.getName() + "&r";
	}

	public static String levelColor(int i) {
		char[] res = "abcde3459".toCharArray();
		return "&" + res[(i * 31) % res.length];
	}

	@EventHandler
	public void onJoin(PlayerJoinEvent e) {
		e.setJoinMessage(Utils.color("&e" + levelString(e.getPlayer()) + "&e joined the game"));
		if (!e.getPlayer().hasPlayedBefore())
			setupNewPlayer(e);
	}

	private void setupNewPlayer(PlayerJoinEvent e) {
		e.getPlayer().setLevel(1);
		e.getPlayer().setGameMode(GameMode.ADVENTURE);
		e.getPlayer().teleport(Warp.getWarp("spawn"));
		// setup inventory
		Location loc = config.getLocation("firstspawn.inventory", new Location(Bukkit.getWorld("world"), -2, 1, -3));
		if (loc.getBlock().getType().equals(Material.CHEST)) {
			e.getPlayer().getInventory().setContents(((Chest) loc.getBlock().getState()).getInventory().getContents());
		}
		// setup enderchest
		loc = config.getLocation("firstspawn.enderchest", new Location(Bukkit.getWorld("world"), -6, 1, -3));
		if (loc.getBlock().getType().equals(Material.CHEST)) {
			e.getPlayer().getEnderChest().setContents(((Chest) loc.getBlock().getState()).getInventory().getContents());
		}
	}

	@EventHandler
	public void onLeave(PlayerQuitEvent e) {
		e.setQuitMessage(Utils.color("&e" + levelString(e.getPlayer()) + "&e left the game"));
		ChunkyListener.lastChunk.put(e.getPlayer().getName(),
				new Chunky(Integer.MAX_VALUE, Integer.MAX_VALUE).coordinate());
	}

	@EventHandler
	public void onChat(AsyncPlayerChatEvent e) {
		String out = levelString(e.getPlayer()) + ": " + e.getMessage();
		e.setFormat(Utils.color(out));
	}
}
