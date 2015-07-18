package me.thamma.DMZ.Connection;

import me.thamma.DMZ.utils.Utils;
import me.thamma.DMZ.warps.Warp;
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

import static me.thamma.DMZ.core.Main.config;

/**
 * Created by pc on 18.07.2015.
 */
public class ConnectListener implements Listener {

    public static String levelString(Player p) {
        String op = "";
        if (p.isOp())
            op = "&f[&cA&f]&r";
        return op + "&f[&eLv. " + levelColor(p.getLevel()) + p.getLevel() + "&f] &7" + p.getName() + "&r";
    }

    public static String levelColor(int i) {
        char[] res = {'a', 'b', 'c', 'd', 'e', '3', '4', '5', '9'};
        return "&" + res[(i * 31) % res.length];
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        e.setJoinMessage(Utils.color("&e" + levelString(e.getPlayer()) + "&e joined the game"));
        if (!e.getPlayer().hasPlayedBefore()) {
            e.getPlayer().setGameMode(GameMode.ADVENTURE);
            e.getPlayer().teleport(Warp.getWarp("firstspawn"));
            Location loc = config.getLocation("firstspawn.inventory", new Location(Bukkit.getWorld("world"), -2, 1, -3));
            if (loc.getBlock().getType().equals(Material.CHEST)) {
                Chest c = (Chest) loc.getBlock().getState();
                e.getPlayer().getInventory().setContents(c.getInventory().getContents());
            }
            loc = config.getLocation("firstspawn.enderchest", new Location(Bukkit.getWorld("world"), -6, 1, -3));
            if (loc.getBlock().getType().equals(Material.CHEST)) {
                Chest c = (Chest) loc.getBlock().getState();
                e.getPlayer().getEnderChest().setContents(c.getInventory().getContents());
            }
        }
    }

    @EventHandler
    public void onChat(AsyncPlayerChatEvent e) {
        e.setFormat(Utils.color(levelString(e.getPlayer()) + ":  &f" + e.getMessage()));
    }
}
