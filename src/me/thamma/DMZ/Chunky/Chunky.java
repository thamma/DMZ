package me.thamma.DMZ.Chunky;

import me.thamma.DMZ.utils.FileManager;
import org.bukkit.Bukkit;
import org.bukkit.Location;

import java.util.HashMap;

/**
 * Created by pc on 13.06.2015.
 */
public class Chunky {

    public static FileManager db = new FileManager("DMZ/chunky.yml");
    private static HashMap<String, HashMap<Attribute, String>> cache;

    private int x;
    private int z;

    public Chunky(int arg0, int arg1) {
        this.x = arg0;
        this.z = arg1;

        if (!cache.containsKey(this.path()))
            newEntry();

    }

    public Chunky(Location loc) {
        this(loc.getChunk().getX(), loc.getChunk().getZ());
    }

    public HashMap<Attribute, String> getMap() {
        return cache.get(this.path());
    }

    public void newEntry() {
        HashMap<Attribute, String> temp = new HashMap<Attribute, String>();
        for (Attribute a : Attribute.values()) {
            temp.put(a, a.getDefaultValue());
        }
        cache.put(this.path(), temp);
        save();
    }

    public String path() {
        return this.x + "," + this.z;
    }

    public static void initializeMap() {
        Bukkit.broadcastMessage("Chunky loading from database, please wait...");
        cache = new HashMap<String, HashMap<Attribute, String>>();
        for (String key : db.getKeys("")) {
            HashMap<Attribute, String> temp = new HashMap<Attribute, String>();
            for (Attribute a : Attribute.values()) {
                String s = db.getString(key + "." + a.toString(), db.getString(key + "." + a.toString()));
                temp.put(a, s);
            }
            cache.put(key, temp);
        }
        Bukkit.broadcastMessage("Done.");
    }

    public void save() {
        for (Attribute a : this.getMap().keySet()) {
            String val = cache.get(this.path()).get(a);
            db.set(this.path() + "." + a.toString(), val);
        }
    }

    public void setAttribute(Attribute a, String val) {
        HashMap<Attribute, String> temp = cache.get(this.path());
        temp.put(a, val);
        cache.put(this.path(), temp);
        save();
    }

    public String getAttribute(Attribute a) {
        return cache.get(this.path()).get(a);
    }
}
