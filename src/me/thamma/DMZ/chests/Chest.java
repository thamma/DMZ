package me.thamma.DMZ.chests;

import me.thamma.DMZ.utils.FileManager;
import me.thamma.DMZ.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static me.thamma.DMZ.core.Main.config;

public class Chest {

    public static FileManager db = new FileManager("DMZ/chests.yml");

    private Location loc;
    private int time;
    private int amount;
    private int id;
    private byte data;
    private Material material;

    public Chest() {
        loc = new Location(Bukkit.getWorld("world"), 0, 0, 0);
        time = 0;
        amount = 0;
        id = -1;
    }


    /**
     * @param arg0 location
     * @param arg1 time
     * @param arg2 amount
     */
    public Chest(Location arg0, int arg1, int arg2) {
        this.material = arg0.getBlock().getType();
        this.data = arg0.getBlock().getData();
        this.loc = arg0;
        this.time = arg1;
        this.amount = arg2;
        this.id = nextId();
        save();
    }

    public Chest(Location arg0, int arg1, int arg2, int arg3) {
        this.material = arg0.getBlock().getType();
        this.data = arg0.getBlock().getData();
        this.loc = arg0;
        this.time = arg1;
        this.amount = arg2;
        this.id = arg3;
    }

    private static Chest loadChest(int id) {
        if (db.contains(String.valueOf(id))) {
            Location loc = db.getLocation(id + ".loc");
            loc.getBlock().setType(Material.valueOf(db.getString(id + ".material")));
            loc.getBlock().setData((byte) db.getInt(id + ".data"));
            return new Chest(loc, db.getInt(id + ".time"), db.getInt(id + ".amount"), id);
        }
        return new Chest();
    }

    public static Chest getChest(Location arg0) {
        for (String s : db.getKeys("")) {
            if (db.getLocation(s + ".loc").distance(arg0) < 1) {
                return loadChest(Integer.parseInt(s));
            }
        }
        return new Chest();
    }

    public static boolean isChest(Block b) {
        return isChest(b.getLocation());
    }

    public static boolean isChest(Location arg0) {
        arg0.add(0.5, 0, 0.5);
        for (String s : db.getKeys("")) {
            if (db.getLocation(s + ".loc").equals(arg0)) {
                //is chest
                return true;
            }
        }
        return false;
    }

    public static void respawnAll() {
        for (String s : db.getKeys("")) {
            Chest c = loadChest(Integer.parseInt(s));
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
        Location ref = Utils.str2loc(config.getString(
                "chest.constants.reference", "world;-729;74;390"));
        int x = config.getInt("chest.constants.vector.x", -2);
        int y = config.getInt("chest.constants.vector.y", 0);
        int z = config.getInt("chest.constants.vector.z", 0);
        return ref.add(new Vector(x, y, z).multiply(this.getId()));
    }

    private int nextId() {
        int i = 0;
        while (db.contains(String.valueOf(i))) {
            i++;
        }
        return i;
    }

    public void respawn() {
        Bukkit.getScheduler().scheduleSyncDelayedTask(ChestListener.plugin, new BukkitRunnable() {
            @Override
            public void run() {
                fill();
            }
        }, this.getTime() * 20L);
    }

    private boolean isEmpty(ItemStack[] in) {
        for (ItemStack is : in) {
            if (is != null)
                return false;
        }
        return true;
    }

    public void fill() {
        loc.getBlock().setType(this.material);
        loc.getBlock().setData(this.data);

        org.bukkit.block.Chest chest = (org.bukkit.block.Chest) loc.getBlock().getState();
        chest.getInventory().clear();
        org.bukkit.block.Chest remote = (org.bukkit.block.Chest) getRemoteLocation().getBlock().getState();

        if (isEmpty(remote.getInventory().getContents()))
            return;

        Random r = new Random();

        List<ItemStack> common = new ArrayList<ItemStack>();
        List<ItemStack> uncommon = new ArrayList<ItemStack>();
        List<ItemStack> rare = new ArrayList<ItemStack>();


        for (int j = 0; j < 8; j++) {
            if (remote.getInventory().getContents()[j] != null)
                common.add(remote.getInventory().getItem(j));
        }

        for (int j = 9; j < 17; j++) {
            if (remote.getInventory().getContents()[j] != null)
                uncommon.add(remote.getInventory().getItem(j));
        }

        for (int j = 18; j < 26; j++) {
            if (remote.getInventory().getContents()[j] != null)
                rare.add(remote.getInventory().getItem(j));
        }

        List<ItemStack> loot = new ArrayList<ItemStack>();
        while (loot.size() < this.getAmount()) {
            int rarity = r.nextInt(100);
            if (rarity < 70) {
                if (common.size() > 0) {
                    loot.add(common.get(r.nextInt(common.size())));
                }
            } else if (rarity < 95) {
                if (uncommon.size() > 0) {
                    loot.add(uncommon.get(r.nextInt(uncommon.size())));
                }
            } else {
                if (rare.size() > 0) {
                    loot.add(rare.get(r.nextInt(rare.size())));
                }
            }
        }
        for (ItemStack is : loot) {
            int dest = r.nextInt(27);
            while (chest.getInventory().getContents()[dest] != null)
                dest = r.nextInt(27);
            chest.getInventory().setItem(dest, is);
        }
    }


    public void delete() {
        org.bukkit.block.Chest chest = (org.bukkit.block.Chest) loc.getBlock().getState();
        chest.getInventory().setContents(((org.bukkit.block.Chest) (getRemoteLocation().getBlock().getState())).getBlockInventory().getContents());
        getRemoteLocation().getBlock().setType(Material.AIR);
        db.set(String.valueOf(this.id), null);
    }

    public void save() {
        db.set(id + ".material", this.material.toString());
        db.set(id + ".data", this.data);
        db.set(id + ".loc", Utils.loc2str(loc));
        db.set(id + ".time", time);
        db.set(id + ".amount", amount);
    }


}