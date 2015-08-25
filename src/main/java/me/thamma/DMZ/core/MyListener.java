package me.thamma.DMZ.core;

import static me.thamma.DMZ.utils.Database.config;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Chest;
import org.bukkit.entity.Item;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerFishEvent;
import org.bukkit.event.player.PlayerFishEvent.State;

import me.thamma.DMZ.custom.Loot;

public class MyListener implements Listener {

	@EventHandler
	public void onFish(PlayerFishEvent e) {
		if (e.getState().equals(State.CAUGHT_FISH)) {
			e.setExpToDrop(0);
			Location loc = config.getLocation("fishing", new Location(Bukkit.getWorld("world"), -5, 3, 1));
			if (loc.getBlock().getType().equals(Material.CHEST)) {
				Chest c = (Chest) loc.getBlock().getState();
				Loot l = new Loot(c.getInventory());
				Item i = (Item) e.getCaught();
				if (!l.empty()) {
					i.setItemStack(l.getRandomLoot(1).get(0));
				}
			}
		}
	}

}