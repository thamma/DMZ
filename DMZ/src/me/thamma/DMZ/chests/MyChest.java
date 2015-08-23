package me.thamma.DMZ.chests;

import static me.thamma.DMZ.utils.Database.chestDb;	
import static me.thamma.DMZ.utils.Database.config;

import java.util.List;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;

import me.thamma.DMZ.Listeners.ChestListener;
import me.thamma.DMZ.custom.Loot;
import me.thamma.DMZ.utils.Utils;

public class MyChest {

	private Location loc;
	private int time;
	private int amount;
	private int id;
	private byte data;
	private Material material;

	public MyChest() {
		loc = new Location(Bukkit.getWorld("world"), 0, 0, 0);
		time = 0;
		amount = 0;
		id = -1;
	}

	/**
	 * @param arg0
	 *            location
	 * @param arg1
	 *            time
	 * @param arg2
	 *            amount
	 */
	@SuppressWarnings("deprecation")
	public MyChest(Location arg0, int arg1, int arg2) {
		this.material = arg0.getBlock().getType();
		this.data = arg0.getBlock().getData();
		this.loc = arg0;
		this.time = arg1;
		this.amount = arg2;
		this.id = nextId();
		save();
	}

	@SuppressWarnings("deprecation")
	public MyChest(Location arg0, int arg1, int arg2, int arg3) {
		this.material = arg0.getBlock().getType();
		this.data = arg0.getBlock().getData();
		this.loc = arg0;
		this.time = arg1;
		this.amount = arg2;
		this.id = arg3;
	}

	@SuppressWarnings("deprecation")
	private static MyChest loadChest(int id) {
		if (chestDb.contains(String.valueOf(id))) {
			Location loc = chestDb.getLocation(id + ".loc");
			loc.getBlock().setType(Material.valueOf(chestDb.getString(id + ".material")));
			loc.getBlock().setData((byte) chestDb.getInt(id + ".data"));
			return new MyChest(loc, chestDb.getInt(id + ".time"), chestDb.getInt(id + ".amount"), id);
		}
		return new MyChest();
	}

	public static MyChest getChest(Location arg0) {
		for (String s : chestDb.getKeys("")) {
			if (chestDb.getLocation(s + ".loc").distance(arg0) < 1) {
				return loadChest(Integer.parseInt(s));
			}
		}
		return new MyChest();
	}

	public static boolean isChest(Block b) {
		return isChest(b.getLocation());
	}

	public static boolean isChest(Location arg0) {
		arg0.add(0.5, 0, 0.5);
		for (String s : chestDb.getKeys("")) {
			if (chestDb.getLocation(s + ".loc").equals(arg0)) {
				// is chest
				return true;
			}
		}
		return false;
	}

	public static void respawnAll() {
		for (String s : chestDb.getKeys("")) {
			MyChest c = loadChest(Integer.parseInt(s));
			c.fill();
		}
	}

	public Location getLocation() {
		return this.loc;
	}

	public int getTime() {
		return this.time;
	}

	public void setTime(int arg0) {
		this.time = arg0;
	}

	public int getAmount() {
		return this.amount;
	}

	public void setAmount(int arg0) {
		this.amount = arg0;
	}

	public int getId() {
		return this.id;
	}

	public org.bukkit.block.Chest getRemoteChest() {
		return ((org.bukkit.block.Chest) getRemoteLocation().getBlock().getState());
	}

	public Location getRemoteLocation() {
		Location ref = Utils.str2loc(config.getString("chest.constants.reference", "world;-729;74;390"));
		int x = config.getInt("chest.constants.vector.x", -2);
		int y = config.getInt("chest.constants.vector.y", 0);
		int z = config.getInt("chest.constants.vector.z", 0);
		return ref.add(new Vector(x, y, z).multiply(this.getId()));
	}

	private int nextId() {
		int i = 0;
		while (chestDb.contains(String.valueOf(i)))
			i++;
		return i;
	}

	public void respawn() {
		Long time = (long) this.getTime();
		Random r = new Random();
		Double d = Double.valueOf(r.nextInt(50)) / 100;
		d *= this.getTime();
		time += (int) Math.round(d);
		Bukkit.getScheduler().runTaskLater(ChestListener.plugin, new Runnable() {
			@Override
			public void run() {
				fill();
			}
		}, time * 20L);
	}

	private boolean isEmpty(ItemStack[] in) {
		for (ItemStack is : in) {
			if (is != null)
				return false;
		}
		return true;
	}

	@SuppressWarnings("deprecation")
	public void fill() {
		// set chest material
		loc.getBlock().setType(this.material);
		loc.getBlock().setData(this.data);
		// empty chest, set variables
		org.bukkit.block.Chest chest = (org.bukkit.block.Chest) loc.getBlock().getState();
		chest.getInventory().clear();
		org.bukkit.block.Chest remote = (org.bukkit.block.Chest) getRemoteLocation().getBlock().getState();
		if (isEmpty(remote.getInventory().getContents()))
			return;
		// setup loot
		Loot l = new Loot(remote.getInventory());
		List<ItemStack> loot = l.getRandomLoot(this.amount);
		Random r = new Random();
		// cannot fill entirely
		if (loot.size() > 27)
			return;
		// distribute loot in chest
		for (ItemStack is : loot) {
			int dest = r.nextInt(27);
			while (chest.getInventory().getContents()[dest] != null)
				dest = r.nextInt(27);
			chest.getInventory().setItem(dest, is);
		}
	}

	public void delete() {
		org.bukkit.block.Chest chest = (org.bukkit.block.Chest) loc.getBlock().getState();
		chest.getInventory().setContents(((org.bukkit.block.Chest) (getRemoteLocation().getBlock().getState()))
				.getBlockInventory().getContents());
		org.bukkit.block.Chest remote = (org.bukkit.block.Chest) getRemoteLocation().getBlock().getState();
		remote.getInventory().clear();
		getRemoteLocation().getBlock().getRelative(BlockFace.EAST).setType(Material.AIR);
		getRemoteLocation().getBlock().setType(Material.AIR);
		chestDb.set(String.valueOf(this.id), null);
	}

	public void save() {
		chestDb.set(id + ".material", this.material.toString());
		chestDb.set(id + ".data", this.data);
		chestDb.set(id + ".loc", Utils.loc2str(loc));
		chestDb.set(id + ".time", time);
		chestDb.set(id + ".amount", amount);
	}

}