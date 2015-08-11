package me.thamma.DMZ.Chunky;

import me.thamma.DMZ.utils.FileManager;
import me.thamma.DMZ.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by pc on 13.06.2015.
 */
public class Chunky {

    public static FileManager db = new FileManager("DMZ/chunky.yml");
    public static List<String> updated;
    private static HashMap<String, HashMap<Attribute, String>> cache;
    private int x;
    private int z;

    public Chunky(int arg0, int arg1) {
        this.x = arg0;
        this.z = arg1;

        if (!cache.containsKey(this.path())) {
            newEntry();
        }

    }

    public Chunky(String in) {
        this(Integer.valueOf(in.split(",")[0]), Integer.valueOf(in.split(",")[1]));
    }

    public Chunky(Location loc) {
        this(loc.getChunk().getX(), loc.getChunk().getZ());
    }

    public static void initializeMap() {
        Bukkit.broadcastMessage("Chunky loading from database, please wait...");
        cache = new HashMap<String, HashMap<Attribute, String>>();
        for (String key : db.getKeys("")) {
            HashMap<Attribute, String> temp = new HashMap<Attribute, String>();
            for (Attribute a : Attribute.values()) {
                String s = db.getString(key + "." + a.toString(), a.getDefaultValue());
                temp.put(a, s);
            }
            cache.put(key, temp);
        }
        Bukkit.broadcastMessage("Done.");
        updated = new ArrayList<String>();
    }

    public static void saveAll() {
        Bukkit.broadcastMessage("Saving chunkys. This might take some time. [" + updated.size() + "]");
        for (String s : updated) {
            Chunky c = new Chunky(s);
            c.save();
        }
        updated.clear();
        Bukkit.broadcastMessage("Saving complete!");
    }

    public HashMap<Attribute, String> getMap() {
        return cache.get(this.path());
    }

    public void newEntry() {
        HashMap<Attribute, String> temp = new HashMap<Attribute, String>();
        for (Attribute a : Attribute.values()) {
            temp.put(a, a.getDefaultValue());
        }
        //updated.add(this.path());
        cache.put(this.path(), temp);
        //save();
    }

    public String path() {
        return this.x + "," + this.z;
    }

    private void save() {
        for (Attribute a : this.getMap().keySet()) {
            String val = cache.get(this.path()).get(a);
            db.set(this.path() + "." + a.toString(), val);
        }
    }

    public void setAttribute(Attribute a, String val) {
        HashMap<Attribute, String> temp = cache.get(this.path());
        temp.put(a, val);
        cache.put(this.path(), temp);
        if (!updated.contains(this.path()))
            updated.add(this.path());
        save();
    }

    public void print(Player p, Attribute a, int cap) {
        Chunk chunk = p.getLocation().getChunk();

        List<String> out = new ArrayList<String>();
        List<String> keys = new ArrayList<String>();
        char[] res = {'a', 'b', 'c', 'd', 'e', '3', '4', '5', '9'};
        for (int i = chunk.getZ() - cap + 1; i < chunk.getZ() + cap; i++) {
            String m = "";
            if (i == chunk.getZ() - cap + 1) {
                m = "N";
            } else if (i == chunk.getZ() + cap - 1) {
                m = "S";
            } else {
                m = "...";
            }

            for (int j = chunk.getX() - cap + 1; j < chunk.getX() + cap; j++) {
                Chunky temp = new Chunky(j, i);
                if (a.getDefaultValue().equals("true") || a.getDefaultValue().equals("false")) {
                    boolean bool = false;
                    if (temp.getAttribute(a).equals("true")) {
                        m += "&a";
                        bool = true;
                    } else if (temp.getAttribute(a).equals("false")) {
                        m += "&c";
                        bool = true;
                    }
                    if (j == chunk.getX() && i == chunk.getZ()) {
                        m += "II";
                    } else
                        m += "[]";
                } else {
                    if (!keys.contains(temp.getAttribute(a)))
                        keys.add(temp.getAttribute(a));

                    String color = "&" + res[(Math.abs(temp.getAttribute(a).hashCode()) % res.length)];
                    if (j == chunk.getX() && i == chunk.getZ()) {
                        m += color + "II";
                    } else
                        m += color + "[]";
                }
            }
            out.add(m);
        }
        if (!(a.getDefaultValue().equals("true") || a.getDefaultValue().equals("false")))
            for (String s : keys)
                p.sendMessage(Utils.color("&" + res[Math.abs(s.hashCode()) % res.length]) + s);
        for (String m : out)
            p.sendMessage(Utils.color(m));
    }

    public void print(Player p, Attribute a) {
        print(p, a, 5);
    }

    public String getAttribute(Attribute a) {
        return cache.get(this.path()).get(a);
    }
}
