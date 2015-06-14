package me.thamma.DMZ.Chunkys;

import me.thamma.DMZ.utils.FileManager;
import org.bukkit.Location;

import java.util.HashMap;

/**
 * Created by pc on 13.06.2015.
 */
public class Chunky {

    public static FileManager db = new FileManager("DMZ/chunky.yml");

    private HashMap<Attribute, String> att = new HashMap<Attribute, String>();
    private int x;
    private int z;

    public Chunky(int arg0, int arg1) {
        loadMap();
        this.x = arg0;
        this.z = arg1;
        save();
    }

    public Chunky(Location loc) {
        this(loc.getChunk().getX(), loc.getChunk().getZ());
    }

    public static boolean isChunky(Location loc) {
        int x = loc.getChunk().getX();
        int z = loc.getChunk().getZ();
        String src = "(" + x + "," + z + ")";
        return db.contains(src);
    }

    private void loadMap() {
        initializeMap();
        String src = "(" + this.x + "," + this.z + ")";
        if (db.contains(src))
            for (Attribute a : Attribute.values()) {
                att.put(a, db.getString(src + "." + a.toString()));
            }
    }

    private void initializeMap() {
        String src = "(" + this.x + "," + this.z + ")";
        for (Attribute a : Attribute.values()) {
            if (db.contains(src + "." + a.toString())) {
                att.put(a, db.getString(src + "." + a.toString()));
            } else {
                att.put(a, a.getDefaultValue());
            }
        }
    }

    public void save() {
        String src = "(" + this.x + "," + this.z + ")";
        for (Attribute a : att.keySet()) {
            String val = att.get(a);
            db.set(src + "." + a.toString(), val);
        }
    }

    public void setAttribute(Attribute a, String val) {
        att.put(a, val);
        save();
    }

    public String getAttribute(Attribute a) {
        return att.get(a);
    }
}
