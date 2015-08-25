package me.thamma.DMZ.custom;

import org.apache.commons.lang.StringUtils;
import org.bukkit.ChatColor;

public enum MyEnchantmentType {
	Damage(ChatColor.GOLD), Armor(ChatColor.GOLD), Poison(ChatColor.GREEN), Swiftness(ChatColor.AQUA);

	private ChatColor c;

	MyEnchantmentType(ChatColor arg0) {
		this.c = arg0;
	}

	public ChatColor getColor() {
		return this.c;
	}

	public String getDisplayName() {
		return StringUtils.capitalize(this.name());
	}

	public static MyEnchantmentType fromString(String s) {
		for (MyEnchantmentType et : MyEnchantmentType.values())
			if (s.equalsIgnoreCase(et.name()))
				return et;
		return null;
	}
}
