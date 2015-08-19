package me.thamma.DMZ.utils;

import org.bukkit.inventory.ItemStack;

public class NBTItem {
	private ItemStack bukkititem;

	public NBTItem(ItemStack Item) {
		this.bukkititem = Item.clone();
	}

	public ItemStack getItem() {
		return this.bukkititem;
	}

	public void setString(String Key, String Text) {
		this.bukkititem = NBTReflectionutil.setString(this.bukkititem, Key, Text);
	}

	public String getString(String Key) {
		return NBTReflectionutil.getString(this.bukkititem, Key);
	}

	public void setInteger(String key, Integer Int) {
		this.bukkititem = NBTReflectionutil.setInt(this.bukkititem, key, Int);
	}

	public Integer getInteger(String key) {
		return NBTReflectionutil.getInt(this.bukkititem, key);
	}

	public void setDouble(String key, Double d) {
		this.bukkititem = NBTReflectionutil.setDouble(this.bukkititem, key, d);
	}

	public Double getDouble(String key) {
		return NBTReflectionutil.getDouble(this.bukkititem, key);
	}

	public void setBoolean(String key, Boolean b) {
		this.bukkititem = NBTReflectionutil.setBoolean(this.bukkititem, key, b);
	}

	public Boolean getBoolean(String key) {
		return NBTReflectionutil.getBoolean(this.bukkititem, key);
	}

	public Boolean hasKey(String key) {
		return NBTReflectionutil.hasKey(this.bukkititem, key);
	}
}
