package me.thamma.DMZ.data;

import me.thamma.DMZ.Battle.MyItem;
import me.thamma.DMZ.utils.FileManager;
import me.thamma.DMZ.utils.Task;
import me.thamma.DMZ.utils.Utils;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Creature;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityInteractEvent;
import org.bukkit.event.player.PlayerChatEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by pc on 12.06.2015.
 */
public class DataListener implements Listener {

    private static HashMap<String, ArrayList<String>> messages = new HashMap<String, ArrayList<String>>();

    @EventHandler
    public void onChat(PlayerChatEvent e) {
        Player p = e.getPlayer();
        if (DataCommands.tasks.containsKey(p.getName())) {
            if (!messages.containsKey(p.getName())) {
                messages.put(p.getName(), new ArrayList<String>());
            }

            Task t = DataCommands.tasks.get(p.getName());

            if (t.getAmount() == messages.get(p.getDisplayName()).size() + 1) {
                e.setCancelled(true);
                p.sendMessage(Utils.color("Added line: \"" + e.getMessage() + "\""));
                p.sendMessage("There you go.");
                ArrayList<String> l = messages.get(p.getName());
                l.add(Utils.color(e.getMessage()));
                messages.put(p.getName(), l);
                t.run(messages.get(p.getName()), p);
                messages.get(p.getName()).clear();
                DataCommands.tasks.remove(p.getName());
            } else if (e.getMessage().equalsIgnoreCase("Done.")) {
                e.setCancelled(true);
                p.sendMessage("There you go.");
                if (messages.get(p.getName()).size() == 0) {
                    p.sendMessage("Task abandoned.");
                    messages.get(p.getName()).clear();
                    DataCommands.tasks.remove(p.getName());
                }
                p.sendMessage("Task completed.");
                t.run(messages.get(p.getName()), p);
                messages.get(p.getName()).clear();
                DataCommands.tasks.remove(p.getName());
            } else {
                p.sendMessage(Utils.color("Added line: \"" + e.getMessage() + "\""));
                e.setCancelled(true);
                ArrayList<String> l = messages.get(p.getName());
                l.add(Utils.color(e.getMessage()));
                messages.put(p.getName(), l);
            }
        }
    }

    @EventHandler
    public void onInteract(PlayerInteractEvent e) {
        if (e.getAction().toString().contains("RIGHT"))
            if (e.getPlayer().getItemInHand() != null) {
                if (e.getPlayer().getItemInHand().hasItemMeta()) {
                    if (e.getPlayer().getItemInHand().getItemMeta().hasDisplayName()) {
                        if (e.getPlayer().getItemInHand().getItemMeta().getDisplayName().toLowerCase().contains("powertool")) {
                            e.setCancelled(true);
                            if (e.getPlayer().getItemInHand().getItemMeta().getLore().get(0).toLowerCase().contains("cycle")) {
                                List<String> l = e.getPlayer().getItemInHand().getItemMeta().getLore();
                                l.remove(0);//remove cycle
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
                        } else if (e.getPlayer().getItemInHand().getType().equals(Material.NAME_TAG) && e.getPlayer().getItemInHand().getItemMeta().getDisplayName().toLowerCase().contains("medal")) {
                            e.setCancelled(true);
                            List<String> l = e.getPlayer().getItemInHand().getItemMeta().getLore();
                            if (l.size() != 1) return;
                            String newname = l.get(0);
                            FileManager db = new FileManager("DMZ/players/" + e.getPlayer().getName().toLowerCase() + ".yml");
                            final String oldname = db.getString("medal", "");
                            if (oldname.equals("")) {
                                e.getPlayer().setItemInHand(new ItemStack(Material.AIR));
                                e.getPlayer().sendMessage(Utils.color("&eYou hang the medal around your neck."));
                                db.set("medal", newname);
                            } else {
                                ItemStack is = e.getPlayer().getItemInHand();
                                ItemMeta im = is.getItemMeta();
                                im.setLore(new ArrayList<String>() {{
                                    add(oldname);
                                }});
                                is.setItemMeta(im);
                                e.getPlayer().setItemInHand(is);
                                db.set("medal", newname);
                                e.getPlayer().sendMessage(Utils.color("&eYou exchanged the medal you are wearing with the one from your pocket.."));
                            }
                        }
                    }
                }
            }
    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent e) {
        if (e.getAction() == Action.PHYSICAL) {
            if (e.getClickedBlock().getType().equals(Material.SOIL)) {
                e.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onEntityInteract(EntityInteractEvent e) {
        if ((e.getBlock().getType() == Material.SOIL) &&
                (e.getEntity() instanceof Creature))
            e.setCancelled(true);
    }

}
