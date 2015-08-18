package me.thamma.DMZ.Battle;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Chest;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Zombie;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;

import me.thamma.DMZ.utils.Database;

/**
 * Created by pc on 09.08.2015.
 */
public class MyLiving {

	private LivingEntity entity;

	public MyLiving(LivingEntity en) {
		this.entity = en;
	}

	public LivingEntity getEntity() {
		return this.entity;
	}

	public MyItem getItemInHand() {
		return new MyItem(this.entity.getEquipment().getItemInHand());
	}

	public int getLevel() {
		if (this.entity instanceof Player) {
			return ((Player) this.entity).getLevel();
		} else if (this.entity instanceof Zombie) {
			Zombie z = (Zombie) this.entity;
			if (z.getCustomName().contains("Zombie Lv."))
				return Integer.valueOf(z.getCustomName().replaceFirst("Zombie Lv.", ""));
		}
		return 0;
	}

	public Loot getLoot() {
		if (this.entity instanceof Zombie) {
			Location ref = Database.config.getLocation("loot.coordinate",
					new Location(Bukkit.getWorld("world"), 0, 10, 0));
			int level = getLevel();
			int x = Database.config.getInt("loot.vector.x", 2);
			int y = Database.config.getInt("loot.vector.y", 0);
			int z = Database.config.getInt("loot.vector.z", 0);
			ref = ref.add(new Vector(x, y, z).multiply(level));
			if (ref.getBlock() != null && ref.getBlock().getType().equals(Material.CHEST)) {
				Chest c = (Chest) ref.getBlock().getState();
				return new Loot(c.getInventory());
			}
		}
		return new Loot();
	}

	public void setNoEquipDrops() {
		this.entity.getEquipment().setBootsDropChance(0F);
		this.entity.getEquipment().setChestplateDropChance(0F);
		this.entity.getEquipment().setHelmetDropChance(0F);
		this.entity.getEquipment().setLeggingsDropChance(0F);
		this.entity.getEquipment().setItemInHandDropChance(0F);
	}

	// public int getEnchantmentSum(Enchantment ench) {
	// int out = 0;
	// List<ItemStack> l = new
	// ArrayList<ItemStack>(Arrays.asList(this.entity.getEquipment().getArmorContents()));
	// l.add(this.entity.getEquipment().getItemInHand());
	// for (ItemStack is : l) {
	// MyItem mi = new MyItem(is);
	// if (this.entity instanceof Player) {
	// if (((Player) this.entity).getLevel() >= mi.getLevel())
	// out += mi.getEnchantmentLevel(ench);
	// } else
	// out += mi.getEnchantmentLevel(ench);
	// }
	// return out;
	// }

	public int getEnchantmentSum(MyItem.MyEnchantmentType ench) {
		int out = 0;
		List<ItemStack> l = new ArrayList<ItemStack>(Arrays.asList(this.entity.getEquipment().getArmorContents()));
		l.add(this.entity.getEquipment().getItemInHand());
		for (ItemStack is : l) {
			MyItem mi = new MyItem(is);
			if (this.entity instanceof Player) {
				if (((Player) this.entity).getLevel() >= mi.getLevel())
					out += mi.getEnchantmentLevel(ench);
			} else
				out += mi.getEnchantmentLevel(ench);
		}
		return out;
	}

	/**
	 * @param target:
	 *            The attacked target
	 * @return: The damage dealt
	 */
	public int attack(MyLiving target) {
		// no decent calculation!
		return (Math.max(0, this.getEnchantmentSum(MyItem.MyEnchantmentType.Damage)
				- target.getEnchantmentSum(MyItem.MyEnchantmentType.Armor))) * 2;
	}

}