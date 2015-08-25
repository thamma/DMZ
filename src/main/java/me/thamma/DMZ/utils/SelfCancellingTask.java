package me.thamma.DMZ.utils;

import org.bukkit.scheduler.BukkitRunnable;

public abstract class SelfCancellingTask extends BukkitRunnable implements MyRun {

	private int counter;
	private final int cap;

	public SelfCancellingTask(int cap) {
		this.counter = 0;
		if (cap < 1) {
			this.cap = 1;
		} else {
			this.cap = cap;
		}
	}

	@Override
	public void runBefore() {

	}

	@Override
	public void runAfter() {

	}

	@Override
	public void run() {
		if (counter == 0)
			runBefore();
		if (counter < cap) {
			counter++;
			this.myrun();
		} else {
			runAfter();
			this.cancel();
		}
	}
}

interface MyRun {
	void myrun();

	void runBefore();

	void runAfter();
}