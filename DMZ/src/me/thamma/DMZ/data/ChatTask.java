package me.thamma.DMZ.data;

import static me.thamma.DMZ.utils.Utils.msg;

import java.util.ArrayList;

import org.bukkit.entity.Player;

import me.thamma.DMZ.Listeners.TaskListener;

/**
 * Created by pc on 19.06.2015.
 */
public abstract class ChatTask implements MyRunnable {

	private Player player;

	public ChatTask(Player p) {
		this.player = p;
		taskEnabled();
		TaskListener.tasks.put(player.getName(), this);
	}

	final public void runTask(ArrayList<String> l) {
		run(l, this.player);
		taskCompleted();
	}

	final private void taskEnabled() {
		msg(this.player, "&eYour chat is now disabled due to the current task.\nType \"&6done.&e\" when you're done.");
	}

	final private void taskCompleted() {
		msg(this.player, "&eTask completed. Your chat is back to normal now."	);
	}

	public int getAmount() {
		return -1;
	}
}

interface MyRunnable {

	int getAmount();

	void run(ArrayList<String> l, Player p);
}