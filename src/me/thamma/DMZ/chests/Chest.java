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
        fill();
    }

    public Chest(Location arg0, int arg1, int arg2, int arg3) {
        this.loc = arg0;
        this.time = arg1;
        this.amount = arg2;
        this.id = arg3;

    }

    private static Chest loadChest(int id) {
        if (db.contains(String.valueOf(id)))
            return new Chest(db.getLocation(id + ".loc"), db.getInt(id + ".time"), db.getInt(id + ".amount"), id);
        return new Chest();
    }

    public static Chest getChest(Location arg0) {
        for (String s : db.getKeys("")) {
            System.out.println("Key found: " + s);
            if (db.getLocation(s + ".loc").distance(arg0) < 1) {
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
        org.bukkit.block.Chest chest = (org.bukkit.block.Chest) loc.getBlock().getState();
        org.bukkit.block.Chest remote = (org.bukkit.block.Chest) getRemoteChest().getBlock().getState();
        Random r = new Random();

        List<ItemStack> filling = new ArrayList<ItemStack>();
        for (int i = 0; i < amount; i++) {
            List<ItemStack> temp = new ArrayList<ItemStack>();
            int chance = r.nextInt(100);
            //interprete rarity
            if (chance < 70) { //common
                for (int j = 0; j < 18; j++) {
                    temp.add(remote.getInventory().getItem(j));
                }
            } else if (chance < 95) { //uncommon
                for (int j = 18; j < 24; j++) {
                    temp.add(remote.getInventory().getItem(j));
                }
            } else {//rare
                for (int j = 24; j < 27; j++) {
                    temp.add(remote.getInventory().getItem(j));
                }
            }
            //select one itemstack from rarity class
            filling.add(temp.get(r.nextInt(temp.size())));
        }
        for (ItemStack is : filling) { //fill chest
            if (chest.getInventory().firstEmpty() == -1) break;
            int dest = r.nextInt(27);
            //search free spot
            while (chest.getInventory().getItem(dest).getType() != Material.AIR || chest.getInventory().getItem(dest).getType() != null)
                dest = r.nextInt(27);
            chest.getInventory().setItem(dest, is);
        }
    }


    public void delete() {
        System.out.println("Delete called.");
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