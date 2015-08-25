package me.thamma.DMZ.utils;

import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerInteractEvent;

import me.thamma.DMZ.custom.MyItem;

public abstract class ItemTask implements Callable {

	private MyItem mi;

	public ItemTask(MyItem arg0) {
		this.setItem(arg0);
	}

	public MyItem getItem() {
		return mi;
	}

	public void setItem(MyItem mi) {
		this.mi = mi;
	}

	@Override
	public void onBlockPlace(BlockPlaceEvent e) {
	}

	@Override
	public void onRightClick(PlayerInteractEvent e) {

	}

	@Override
	public void onLeftClick(PlayerInteractEvent e) {

	}

}

interface Callable {

	void onBlockPlace(BlockPlaceEvent e);

	void onRightClick(PlayerInteractEvent e);

	void onLeftClick(PlayerInteractEvent e);

}
