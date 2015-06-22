package me.thamma.DMZ.data;

import me.thamma.DMZ.utils.Task;
import me.thamma.DMZ.utils.Utils;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
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

            if (e.getMessage().equalsIgnoreCase("Done.")) {
                e.setCancelled(true);
                p.sendMessage("There you go.");
                if (messages.get(p.getName()).size() == 0) {
                    p.sendMessage("Task abandoned.");
                    messages.get(p.getName()).clear();
                    DataCommands.tasks.remove(p.getName());
                }
                p.sendMessage("Task completed.");
                Task t = DataCommands.tasks.get(p.getName());
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
            if (e.getPlayer().getItemInHand() != null)
                if (e.getPlayer().getItemInHand().hasItemMeta())
                    if (e.getPlayer().getItemInHand().getItemMeta().getDisplayName().toLowerCase().contains("powertool")) {
                        if (e.getPlayer().getItemInHand().getItemMeta().getLore().get(0).toLowerCase().contains("cycle")) {
                            List<String> l = e.getPlayer().getItemInHand().getItemMeta().getLore();
                            l.remove(0);//remove cycle
                            String s = l.remove(0);
                            e.getPlayer().performCommand(s);
                            l.add(s);
                            l.add(0,"cycle");
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
                    }
    }


}
