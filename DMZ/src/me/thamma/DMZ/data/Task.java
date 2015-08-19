package me.thamma.DMZ.data;

import org.bukkit.entity.Player;

import java.util.ArrayList;

/**
 * Created by pc on 19.06.2015.
 */
public abstract class Task implements MyRunnable {

	public int getAmount() {
		return -1;
	}
}

interface MyRunnable {

	int getAmount();

	void run(ArrayList<String> l, Player p);
}