package me.thamma.DMZ.chunky;

import static me.thamma.DMZ.utils.Utils.broadcast;
import static me.thamma.DMZ.utils.Utils.color;
import static me.thamma.DMZ.utils.Utils.msg;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import me.thamma.DMZ.utils.Database;

/**
 * Created by pc on 13.06.2015.
 */
public class Chunky {

	public static List<String> updated;
	private static HashMap<String, HashMap<Attribute, String>> cache;
	private int x;
	private int z;

	public Chunky(int arg0, int arg1) {
		this.x = arg0;
		this.z = arg1;

		if (!cache.containsKey(this.coordinate())) {
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
		for (String key : Database.chunkyDb.getKeys("")) {
			HashMap<Attribute, String> temp = new HashMap<Attribute, String>();
			for (Attribute a : Attribute.values()) {
				String s = Database.chunkyDb.getString(key + "." + a.toString(), a.getDefaultValue());
				temp.put(a, s);
			}
			cache.put(key, temp);
		}
		Bukkit.broadcastMessage("Done.");
		updated = new ArrayList<String>();
	}

	public static void saveAll() {
		broadcast("&6Saving chunkys. This might take some time. &7[" + updated.size() + "]");
		for (String s : updated) {
			Chunky c = new Chunky(s);
			c.save();
		}
		updated.clear();
		broadcast("&6Saving complete!");
	}

	public HashMap<Attribute, String> getMap() {
		return cache.get(this.coordinate());
	}

	public void newEntry() {
		HashMap<Attribute, String> temp = new HashMap<Attribute, String>();
		for (Attribute a : Attribute.values()) {
			temp.put(a, a.getDefaultValue());
		}
		cache.put(this.coordinate(), temp);
		// save();
	}

	public String coordinate() {
		return this.x + "," + this.z;
	}

	private void save() {
		for (Attribute a : this.getMap().keySet()) {
			String val = cache.get(this.coordinate()).get(a);
			Database.chunkyDb.set(this.coordinate() + "." + a.toString(), val);
		}
	}

	public void setAttribute(Attribute a, String val) {
		HashMap<Attribute, String> temp = cache.get(this.coordinate());
		temp.put(a, val);
		cache.put(this.coordinate(), temp);
		if (!updated.contains(this.coordinate()))
			updated.add(this.coordinate());
		save();
	}

	public void print(Player p, Attribute a, int cap) {
		Chunk chunk = p.getLocation().getChunk();

		List<String> out = new ArrayList<String>();
		List<String> keys = new ArrayList<String>();
		char[] res = { 'a', 'b', 'c', 'd', 'e', '3', '4', '5', '9' };
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
					if (temp.getAttribute(a).equals("true")) {
						m += "&a";
					} else if (temp.getAttribute(a).equals("false")) {
						m += "&c";
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
				p.sendMessage(color("&" + res[Math.abs(s.hashCode()) % res.length]) + s);
		for (String m : out)
			msg(p, m);
	}

	public void print(Player p, Attribute a) {
		print(p, a, 5);
	}

	public String getAttribute(Attribute a) {
		return cache.get(this.coordinate()).get(a);
	}
}
