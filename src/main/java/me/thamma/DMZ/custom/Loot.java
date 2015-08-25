package me.thamma.DMZ.custom;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class Loot {

	private List<ItemStack> common;
	private List<ItemStack> uncommon;
	private List<ItemStack> rare;

	public Loot() {
		this.common = new ArrayList<ItemStack>();
		this.uncommon = new ArrayList<ItemStack>();
		this.rare = new ArrayList<ItemStack>();
	}

	public Loot(Inventory inv) {
		this();
		if (inv.getSize() != 27)
			return;
		for (int i = 0; i < 9; i++)
			if (inv.getContents()[i] != null && !inv.getContents()[i].getType().equals(Material.AIR))
				this.common.add(inv.getContents()[i]);
		for (int i = 9; i < 18; i++)
			if (inv.getContents()[i] != null && !inv.getContents()[i].getType().equals(Material.AIR))
				this.uncommon.add(inv.getContents()[i]);
		for (int i = 18; i < 27; i++)
			if (inv.getContents()[i] != null && !inv.getContents()[i].getType().equals(Material.AIR))
				this.rare.add(inv.getContents()[i]);
	}

	public List<ItemStack> getCommon() {
		return this.common;
	}

	public List<ItemStack> getUncommon() {
		return this.uncommon;
	}

	public List<ItemStack> getRare() {
		return this.rare;
	}

	public boolean empty() {
		return (common.size() == 0 && uncommon.size() == 0 && rare.size() == 0);
	}

	public List<ItemStack> getRandomLoot(int amount) {
		List<ItemStack> l = new ArrayList<ItemStack>();
		if (empty())
			return l;
		Random r = new Random();
		while (l.size() < amount) {
			int chance = r.nextInt(100);
			if (chance < 80) {
				if (common.size() > 0)
					l.add(common.get(r.nextInt(common.size())));
			} else if (chance < 95) {
				if (uncommon.size() > 0)
					l.add(uncommon.get(r.nextInt(uncommon.size())));
			} else {
				if (rare.size() > 0)
					l.add(rare.get(r.nextInt(rare.size())));
			}
		}
		return l;
	}

}
