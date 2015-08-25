package me.thamma.DMZ.Listeners;

import static me.thamma.DMZ.utils.Utils.msg;

import java.util.ArrayList;
import java.util.HashMap;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import me.thamma.DMZ.data.ChatTask;
import me.thamma.DMZ.utils.Utils;

public class ChatTaskListener implements Listener {

	private static HashMap<String, ArrayList<String>> messages = new HashMap<String, ArrayList<String>>();
	public static HashMap<String, ChatTask> tasks = new HashMap<String, ChatTask>();

	@EventHandler
	public void onChat(AsyncPlayerChatEvent e) {
		Player p = e.getPlayer();
		if (tasks.containsKey(p.getName())) {
			e.setCancelled(true);
			if (!messages.containsKey(p.getName())) {
				messages.put(p.getName(), new ArrayList<String>());
			}
			ChatTask t = tasks.get(p.getName());
			ArrayList<String> l = messages.get(p.getName());
			if (!e.getMessage().equalsIgnoreCase("done.") && t.getAmount() != messages.get(p.getName()).size() + 1) {
				// if not ready yet
				l.add(Utils.color(e.getMessage().replaceAll("\\\\", "")));
				messages.put(p.getName(), l);
				msg(p, "&eAdded line: \"" + e.getMessage().replaceAll("\\\\", "") + "&e\"");
				return;
			}
			if (!e.getMessage().equals("done."))
				l.add(Utils.color(e.getMessage().replaceAll("\\\\", "")));
			t.runTask(l);
			messages.get(p.getName()).clear();
			tasks.remove(p.getName());
		}
	}

}
