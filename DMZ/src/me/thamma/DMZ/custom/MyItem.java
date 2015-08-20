package me.thamma.DMZ.custom;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;

import me.thamma.DMZ.utils.NBTItem;
import me.thamma.DMZ.utils.Utils;

/**
 * Created by pc on 26.06.2015.
 */
public class MyItem {

	private ItemStack is;
	private ItemMeta im;
	private List<String> lore;
	private int level;
	private List<MyEnchantment> myenchants;
	private Map<Enchantment, Integer> enchants;
	private String name;
	private boolean unbreakable;
	private boolean hideFlags;
	private List<String> rawlore;

	public MyItem(Material arg0) {
		this(arg0, 1, (short) 0);
	}

	public MyItem(Material arg0, int arg1) {
		this(arg0, arg1, (short) 0);
	}

	public MyItem(Material arg0, int arg1, short arg2) {
		this.is = new ItemStack(arg0, arg1, arg2);
		this.im = is.getItemMeta();
		this.lore = new ArrayList<String>();
		this.level = 0;
		this.myenchants = new ArrayList<MyEnchantment>();
		this.enchants = new HashMap<Enchantment, Integer>();
		this.name = "";
		this.unbreakable = false;
		this.hideFlags = false;
		this.rawlore = new ArrayList<String>();
	}

	public MyItem(ItemStack is) {
		this.is = is;
		this.im = this.is.getItemMeta();
		this.name = "";
		this.lore = new ArrayList<String>();
		this.rawlore = new ArrayList<String>();
		this.level = 0;
		this.myenchants = new ArrayList<MyEnchantment>();
		this.enchants = is.getEnchantments();
		NBTItem n = new NBTItem(this.is);
		if (is.hasItemMeta()) {
			this.unbreakable = n.getInteger("Unbreakable") == 1;
			this.hideFlags = n.getInteger("HideFlags") > 0;
			if (is.getItemMeta().hasDisplayName())
				this.name = is.getItemMeta().getDisplayName();
			if (is.getItemMeta().hasLore())
				for (String s : is.getItemMeta().getLore()) {
					String line = ChatColor.stripColor(s);
					if (line.startsWith("Level: ")) {
						this.level = Integer.parseInt(line.replaceFirst("Level: ", ""));
					} else if (line.startsWith("+") || line.startsWith("-")) {
						int lv = Integer.parseInt(line.split(" ")[0]);
						MyEnchantmentType e = MyEnchantmentType.valueOf(line.split(" ")[1]);
						myenchants.add(new MyEnchantment(e, lv));
					} else {
						this.lore.add(line);
					}
				}
		}
	}

	public MyItem setRawLore(List<String> arg0) {
		MyItem temp = this.clone();
		temp.rawlore = arg0;
		return temp;
	}

	public MyItem setRawLore(String[] arg0) {
		return setRawLore(Arrays.asList(arg0));
	}

	public static void updateItems(Player p) {
		for (int i = 0; i < p.getInventory().getContents().length; i++)
			if (p.getInventory().getContents()[i] != null)
				p.getInventory().setItem(i, new MyItem(p.getInventory().getContents()[i]).getItemStack());

	}

	public int getEnchantmentLevel(Enchantment type) {
		for (Enchantment e : enchants.keySet()) {
			if (e.equals(type)) {
				return enchants.get(e);
			}
		}
		return 0;
	}

	public boolean isHideFlags() {
		return this.hideFlags;
	}

	public int getEnchantmentLevel(MyEnchantmentType type) {
		for (MyEnchantment e : myenchants) {
			if (e.getType().equals(type)) {
				return e.getLevel();
			}
		}
		return 0;
	}

	public boolean isUnbreakable() {
		return this.unbreakable;
	}

	public MyItem setUnbreakable(boolean arg0) {
		MyItem temp = this.clone();
		temp.unbreakable = arg0;
		return temp;
	}

	public int getLevel() {
		return this.level;
	}

	public MyItem setLevel(int i) {
		MyItem temp = this.clone();
		temp.level = i;
		return temp;
	}

	public String getName() {
		return this.name;
	}

	public MyItem setName(String arg0) {
		MyItem temp = this.clone();
		temp.name = arg0;
		return temp;
	}

	public MyItem setHideFlags(boolean arg0) {
		MyItem temp = this.clone();
		temp.hideFlags = arg0;
		return temp;
	}

	public List<String> getLore() {
		return this.lore;
	}

	public MyItem addLore(List<String> arg0) {
		MyItem temp = this.clone();
		temp.lore.addAll(arg0);
		return temp;
	}

	public MyItem addLore(String[] arg0) {
		return addLore(Arrays.asList(arg0));
	}

	public MyItem setLore(String[] arg0) {
		return setLore(Arrays.asList(arg0));
	}

	public MyItem setLore(List<String> arg0) {
		MyItem temp = this.clone();
		ArrayList<String> l = new ArrayList<String>();
		for (String s : arg0) {
			l.add(Utils.color(s));
		}
		temp.lore = l;
		return temp;
	}

	public List<MyEnchantment> getMyEnchantments() {
		return this.myenchants;
	}

	public Map<Enchantment, Integer> getEnchantments() {
		return this.enchants;
	}

	public MyItem addEnchantment(MyEnchantment e) {
		MyItem temp = this.clone();
		MyEnchantment entry = null;
		for (MyEnchantment me : temp.myenchants) {
			if (me.getType().equals(e.getType()))
				entry = me;
		}
		if (entry != null)
			temp.myenchants.remove(entry);
		temp.myenchants.add(e);
		return temp;
	}

	public MyItem addEnchantment(Enchantment e, int lv) {
		MyItem temp = this.clone();
		temp.enchants.put(e, lv);
		return temp;
	}

	public MyItem setLeatherColor(int r, int g, int b) {
		MyItem temp = this.clone();
		if (!temp.is.getType().toString().contains("LEATHER_"))
			return temp;
		LeatherArmorMeta lam = (LeatherArmorMeta) temp.im;
		lam.setColor(Color.fromRGB(r, g, b));
		return temp;
	}

	public MyItem setEnchantment(MyEnchantment e) {
		MyItem temp = this.clone();
		for (MyEnchantment i : temp.myenchants) {
			if (e.getType().equals(i.getType()))
				temp.myenchants.remove(i);
		}
		temp.myenchants.add(e);
		return temp;
	}

	@Override
	public MyItem clone() {
		return new MyItem(this.getItemStack());
	}

	public ItemStack getItemStack() {
		if (!this.name.equals(""))
			im.setDisplayName(Utils.color(this.name));
		ArrayList<String> l = new ArrayList<String>();
		if (rawlore.isEmpty()) {
			l.addAll(this.lore);
			if (level > 0)
				l.add(Utils.color("&fLevel: " + level));
			for (MyEnchantment e : this.myenchants) {
				if (e.getLevel() > 0)
					l.add(Utils.color(e.toString()));
			}
		} else {
			l.addAll(this.rawlore);
		}
		im.setLore(l);
		is.addEnchantments(enchants);
		is.setItemMeta(this.im);
		NBTItem n = new NBTItem(is);
		n.setInteger("Unbreakable", (this.unbreakable ? 1 : 0));
		n.setInteger("HideFlags", (this.hideFlags ? 6 : 0));
		this.is = n.getItem();
		return is;
	}

}
