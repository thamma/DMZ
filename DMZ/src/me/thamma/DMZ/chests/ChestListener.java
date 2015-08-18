package me.thamma.DMZ.chests;

import me.thamma.DMZ.core.Main;
import me.thamma.DMZ.utils.Utils;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryType;

public class ChestListener implements Listener {

	public static Main plugin;

	public ChestListener(Main plugin) {
		ChestListener.plugin = plugin;
	}

	@EventHandler
	public void onBlockBreak(BlockBreakEvent e) {
		if (Chest.isChest(e.getBlock())) {
			e.setCancelled(true);	
			e.getPlayer().sendMessage(Utils.color("&cBe careful. You don't want to break a respawning chest."));
		}
	}

	@EventHandler
	public void onInventoryClose(InventoryCloseEvent e) {
		if (e.getInventory().getType().equals(InventoryType.CHEST)) {
			try {
				Location loc = ((org.bukkit.block.Chest) e.getInventory().getHolder()).getBlock().getLocation();
				if (Chest.isChest(loc)) {
					Chest c = Chest.getChest(loc);
					Block b = loc.getBlock();
					b.setType(Material.AIR);
					c.respawn();
				}
			} catch (Exception exc) {
			}
		}
	}

}
