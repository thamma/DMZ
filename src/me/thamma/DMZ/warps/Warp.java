package me.thamma.DMZ.warps;

import me.thamma.DMZ.utils.FileManager;
import me.thamma.DMZ.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.Location;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by pc on 11.06.2015.
 */
public class Warp {

    static FileManager db = new FileManager("DMZ/warps.yml");

    public static boolean exists(String name) {
        return getWarps().contains(name.toLowerCase());
    }

    public static Location getWarp(String name) {
        if (exists(name)) {
            return Utils.str2loc(db.getString(name));
        }
        return new Location(Bukkit.getWorld("world"), 0.0D, 0.0D, 0.0D);
    }

    public static List<String> getWarps() {
        List<String> out = new ArrayList();
        for (String s : db.getKeys("")) {
            out.add(s);
        }
        return out;
    }

}
