package me.thamma.DMZ.Battle;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Chest;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Zombie;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;

import me.thamma.DMZ.utils.FileManager;

/**
 * Created by pc on 09.08.2015.
 */
public class MyLiving {

	public FileManager config = new FileManager("DMZ/config.yml");

	private LivingEntity entity;

	public MyLiving(LivingEntity en) {
		this.entity = en;
	}

	public LivingEntity getEntity() {
		return this.entity;
	}

	public int getEnchantmentSum(MyItem.MyEnchantmentType ench) {
		int out = 0;
		for (ItemStack is : this.entity.getEquipment().getArmorContents()) {
			MyItem mi = new MyItem(is);
			if (this.entity instanceof Player) {
				if (((Player) this.entity).getLevel() >= mi.getLevel())
					out += mi.getEnchantmentLevel(ench);
			} else
				out += mi.getEnchantmentLevel(ench);
		}
		return out;
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
				return Integer.valueOf(z.getCustomName().replaceFirst("Zombie lv.", ""));
		}
		return 0;
	}

	public Loot getLoot() {
		if (this.entity instanceof Zombie) {
			Location ref = config.getLocation("loot.coordinate", new Location(Bukkit.getWorld("world"), 0, 10, 0));
			int level = getLevel();
			int x = config.getInt("loot.vector.x", 1);
			int y = config.getInt("loot.vector.x", 0);
			int z = config.getInt("loot.vector.x", 0);
			ref.add(new Vector(x, y, z).multiply(level));
			if (ref.getBlock() != null && ref.getBlock().getType().equals(Material.CHEST)) {
				Chest c = (Chest) ref.getBlock().getState();
				return new Loot(c.getInventory());
			}
		}
		return new Loot();
	}

	public int getEnchantmentSum(Enchantment ench) {
		int out = 0;
		for (ItemStack is : this.entity.getEquipment().getArmorContents()) {
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
				- target.getEnchantmentSum(MyItem.MyEnchantmentType.Armor))) * 5;
	}

}