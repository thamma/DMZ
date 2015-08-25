package me.thamma.DMZ.warps;

import static me.thamma.DMZ.utils.Database.warpDb;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Location;

import me.thamma.DMZ.utils.Utils;

/**
 * Created by pc on 11.06.2015.
 */
public class Warp {

	public static boolean exists(String name) {
		return getWarps().contains(name.toLowerCase());
	}

	public static Location getWarp(String name) {
		if (exists(name)) {
			return Utils.str2loc(warpDb.getString(name));
		}
		return new Location(Bukkit.getWorld("world"), 0, 64, 0);
	}

	public static List<String> getWarps() {
		List<String> out = new ArrayList<String>();
		for (String s : warpDb.getKeys("")) {
			out.add(s);
		}
		return out;
	}

}