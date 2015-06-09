package me.thamma.DMZ.chests;

import me.thamma.DMZ.Utils.FileManager;
import me.thamma.DMZ.Utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.Location;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by pc on 07.06.2015.
 */

public class Chest {

    public static FileManager db = new FileManager("DMZ/chests.db");

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

    public Chest(Location arg0, int arg1, int arg2) {
        this.loc = arg0;
        this.time = arg1;
        this.amount = arg2;
        this.id = nextId();
        save();
    }

    public Location getLocation(){
    return loc;
}
    public int getTimeMillis() {
        return time;
    }

    public int getAmount() {
        return amount;
    }

    private Chest loadChest(int id) {
        if (db.contains(String.valueOf(id)))
            return new Chest(db.getLocation(id + ".loc"), db.getInt(id + ".time") ,db.getInt(id + ".amount"));
        return new Chest();
    }

    public static boolean isChest(Location arg0) {
        for (String s: db.getKeys("")) {

        }
        return false;
    }

    private int nextId() {
        int i = 0;
        while (db.contains(String.valueOf(i))) {
            i++;
        }
        return i;
    }

    private void save() {
        db.set(id + ".loc", Utils.loc2str(loc));
        db.set(id + ".time", time);
        db.set(id + ".time", amount);
    }

}