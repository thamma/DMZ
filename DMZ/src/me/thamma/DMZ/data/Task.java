package me.thamma.DMZ.data;

import org.bukkit.entity.Player;

import me.thamma.DMZ.utils.Utils;

import java.util.ArrayList;

/**
 * Created by pc on 19.06.2015.
 */
public abstract class Task implements MyRunnable {

	private Player player;

	public Task(Player p) {
		this.player = p;
		taskEnabled();
		TaskListener.tasks.put(player.getName(), this);
	}

	final public void runTask(ArrayList<String> l) {
		run(l, this.player);
		taskCompleted();
	}

	final private void taskEnabled() {
		this.player.sendMessage(Utils
				.color("&eYour chat is now disabled due to the current task.\nType \"&6done.&e\" when you're done."));
	}

	final private void taskCompleted() {
		this.player.sendMessage(Utils.color("&eTask completed. Your chat is back to normal now."));
	}

	public int getAmount() {
		return -1;
	}
}

interface MyRunnable {

	int getAmount();

	void run(ArrayList<String> l, Player p);
}