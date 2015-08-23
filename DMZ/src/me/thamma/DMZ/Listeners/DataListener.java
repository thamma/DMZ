package me.thamma.DMZ.Listeners;

import static me.thamma.DMZ.utils.Database.playerDb;
import static me.thamma.DMZ.utils.Utils.msg;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Creature;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityInteractEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import me.thamma.DMZ.utils.FileManager;

/**
 * Created by pc on 12.06.2015.
 */
public class DataListener implements Listener {

	@SuppressWarnings("serial")
	@EventHandler
	public void onInteract(PlayerInteractEvent e) {
		if (e.getAction().toString().contains("RIGHT"))
			if (e.getPlayer().getItemInHand() != null) {
				if (e.getPlayer().getItemInHand().hasItemMeta()) {
					if (e.getPlayer().getItemInHand().getItemMeta().hasDisplayName()) {
						if (e.getPlayer().getItemInHand().getItemMeta().getDisplayName().toLowerCase()
								.contains("powertool")) {
							e.setCancelled(true);
							if (e.getPlayer().getItemInHand().getItemMeta().getLore().get(0).toLowerCase()
									.contains("cycle")) {
								List<String> l = e.getPlayer().getItemInHand().getItemMeta().getLore();
								l.remove(0);// remove cycle
								String s = l.remove(0);
								e.getPlayer().performCommand(s);
								l.add(s);
								l.add(0, "cycle");
								ItemStack is = e.getPlayer().getItemInHand();
								ItemMeta im = is.getItemMeta();
								im.setLore(l);
								is.setItemMeta(im);
								e.getPlayer().setItemInHand(is);
							} else {
								String dat = "";
								for (String s : e.getPlayer().getItemInHand().getItemMeta().getLore())
									dat += ChatColor.stripColor(s);
								e.getPlayer().performCommand(dat);
							}
						} else if (e.getPlayer().getItemInHand().getType().equals(Material.NAME_TAG) && e.getPlayer()
								.getItemInHand().getItemMeta().getDisplayName().toLowerCase().contains("medal")) {
							e.setCancelled(true);
							List<String> l = e.getPlayer().getItemInHand().getItemMeta().getLore();
							if (l.size() != 1)
								return;
							String newname = l.get(0);

							FileManager db = playerDb(e.getPlayer());
							final String oldname = db.getString("medal", "");
							if (oldname.equals("")) {
								e.getPlayer().setItemInHand(new ItemStack(Material.AIR));
								msg(e.getPlayer(), "&eYou hang the medal around your neck.");
								db.set("medal", newname);
							} else {
								ItemStack is = e.getPlayer().getItemInHand();
								ItemMeta im = is.getItemMeta();
								im.setLore(new ArrayList<String>() {
									{
										add(oldname);
									}
								});
								is.setItemMeta(im);
								e.getPlayer().setItemInHand(is);
								db.set("medal", newname);
								msg(e.getPlayer(),
										"&eYou exchanged the medal you are wearing with the one from your pocket..");
							}
						}
					}
				}
			}
	}

	@EventHandler
	public void onPlayerTrample(PlayerInteractEvent e) {
		if (e.getAction() == Action.PHYSICAL) {
			if (e.getClickedBlock().getType().equals(Material.SOIL)) {
				e.setCancelled(true);
			}
		}
	}

	@EventHandler
	public void onEntityTrample(EntityInteractEvent e) {
		if ((e.getBlock().getType() == Material.SOIL) && (e.getEntity() instanceof Creature))
			e.setCancelled(true);
	}

}
