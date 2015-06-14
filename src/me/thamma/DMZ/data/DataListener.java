package me.thamma.DMZ.data;

import me.thamma.DMZ.utils.Utils;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerChatEvent;

import java.util.ArrayList;
import java.util.HashMap;

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
                DataCommands.Task t = DataCommands.tasks.get(p.getName());
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

}
