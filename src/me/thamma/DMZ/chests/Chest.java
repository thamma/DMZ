package me.thamma.DMZ.chests;

import me.thamma.DMZ.utils.Cooldowns;
import me.thamma.DMZ.utils.FileManager;
import me.thamma.DMZ.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Chest {

    public static FileManager db = new FileManager("DMZ/chests.yml");
    public static FileManager config = new FileManager("DMZ/config.yml");
    public static Cooldowns cd = new Cooldowns("respawningchest");

    private Location loc;
    private int time;
    private int amount;
    private int id;

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
        this.loc = arg0;
        this.time = arg1;
        this.amount = arg2;
        this.id = nextId();

        save();
    }

    public Chest(Location arg0, int arg1, int arg2, int arg3) {
        this.loc = arg0;
        this.time = arg1;
        this.amount = arg2;
        this.id = arg3;

    }

    private static Chest loadChest(int id) {
        if (db.contains(String.valueOf(id))) {
            System.out.println("loading chest, id: " + id);
            return new Chest(db.getLocation(id + ".loc"), db.getInt(id + ".time"), db.getInt(id + ".amount"), id);
        }
        return new Chest();
    }

    public static Chest getChest(Location arg0) {
        for (String s : db.getKeys("")) {
            if (db.getLocation(s + ".loc").distance(arg0) < 1) {
                System.out.println("matching key found: " + s);
                return loadChest(Integer.parseInt(s));
            }
        }
        return new Chest();
    }

    public static boolean isChest(Location arg0) {
        for (String s : db.getKeys("")) {
            if (db.getLocation(s + ".loc").distance(arg0) < 1) {
                //is chest
                return true;
            }
        }
        return false;
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

    public Location getRemoteChest() {
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

    public boolean willRefill() {
        return cd.ready(id + "");
    }

    public void fill() {
        cd.add(id + "", this.time * 1000);

        org.bukkit.block.Chest chest = (org.bukkit.block.Chest) loc.getBlock().getState();

        chest.getInventory().clear();

        System.out.println(getRemoteChest().getBlock().getType());
        org.bukkit.block.Chest remote = (org.bukkit.block.Chest) getRemoteChest().getBlock().getState();
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

            //select one itemstack from rarity class
        for (int i = 0; i < this.amount; i++) { //fill chest
            if (chest.getInventory().firstEmpty() == -1) break; //cheat already full

            int rarity = r.nextInt(100);
            ItemStack item;
            if (rarity < 70) {
                item = common.get(r.nextInt(common.size()));
            } else if (rarity < 95) {
                item = uncommon.get(r.nextInt(common.size()));
            } else {
                item = rare.get(r.nextInt(common.size()));
            }

            int dest = r.nextInt(27);
            //search free spot
            System.out.println(chest.getInventory().getContents()[dest] == null);
            while (chest.getInventory().getContents()[dest] != null)
                dest = r.nextInt(27);
            chest.getInventory().setItem(dest, item);
        }
    }


    public void delete() {
        org.bukkit.block.Chest chest = (org.bukkit.block.Chest) loc.getBlock().getState();
        chest.getInventory().setContents(((org.bukkit.block.Chest) (getRemoteChest().getBlock().getState())).getBlockInventory().getContents());
        getRemoteChest().getBlock().setType(Material.AIR);
        cd.reset(id + "");
        db.set(String.valueOf(this.id), null);
    }

    public void save() {
        db.set(id + ".loc", Utils.loc2str(loc));
        db.set(id + ".time", time);
        db.set(id + ".amount", amount);
    }


}