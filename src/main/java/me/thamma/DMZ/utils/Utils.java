package me.thamma.DMZ.utils;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Utils {

	public static void msg(CommandSender p, String s) {
		p.sendMessage(Utils.color(s));
	}

	public static void msg(CommandSender p, String[] s) {
		for (String t : s)
			msg(p, t);
	}

	public static void broadcast(String s) {
		for (Player p : Bukkit.getOnlinePlayers())
			if (p.isOp())
				msg(p, s);
	}

	public static String[] helpPage(String command, String... args) {
		String title = Utils.color("&e--- &6" + command + " Helppage &e---");
		if (args.length % 2 != 0) {
			return new String[] { title };
		}
		String[] out = new String[args.length / 2 + 1];
		out[0] = title;
		for (int i = 0; i < args.length / 2; i++) {
			out[i + 1] = Utils.color("&e/" + command + " &6" + args[2 * i] + "&e  -  " + args[2 * i + 1]);
		}
		return out;
	}

	public static boolean matchArgs(String src, String[] args) {
		String[] in = src.split(" ");
		if (in.length != args.length)
			return false;
		if (args.length == 0 && in.length == 0)
			return true;
		for (int i = 0; i < in.length; i++) {
			if (in[i].charAt(0) == '#') {
				String type = in[i].replaceFirst("#", "");
				if (!type.equalsIgnoreCase("string")) {
					if (type.equalsIgnoreCase("int")) {
						if (!isNum(args[i])) {
							return false;
						}
					} else if (type.equalsIgnoreCase("double")) {
						if (!isNum(args[i])) {
							return false;
						}
					} else if ((type.equalsIgnoreCase("boolean")) && (!isBool(args[i]))) {
						return false;
					}
				}
			} else if (!in[i].equalsIgnoreCase(args[i])) {
				return false;
			}
		}
		return true;
	}

	private static boolean isNum(String string) {
		try {
			Integer.parseInt(string);
		} catch (NumberFormatException e) {
			return false;
		}
		return true;
	}

	private static boolean isBool(String string) {
		return (string.equalsIgnoreCase("true")) || (string.equalsIgnoreCase("false"));
	}

	public static Location str2loc(String in) {
		String[] s = in.split("\\;");
		World w = Bukkit.getWorld(s[0]);
		double x = Double.parseDouble(s[1]);
		double y = Double.parseDouble(s[2]);
		double z = Double.parseDouble(s[3]);
		if (s.length == 6) {
			Float yaw = Float.valueOf(Float.parseFloat(s[4]));
			Float pit = Float.valueOf(Float.parseFloat(s[5]));
			return new Location(w, x, y, z, yaw.floatValue(), pit.floatValue());
		}
		return new Location(w, x + 0.5D, y, z + 0.5D);
	}

	public static String loc2str(Location loc) {
		String w = loc.getWorld().getName();
		double x = loc.getX();
		double y = loc.getY();
		double z = loc.getZ();
		if ((loc.getYaw() == 0.0F) && (loc.getPitch() == 0.0F)) {
			return w + ";" + x + ";" + y + ";" + z;
		}
		float yaw = loc.getYaw();
		float pit = loc.getPitch();
		return w + ";" + x + ";" + y + ";" + z + ";" + yaw + ";" + pit;
	}

	public static String color(String text) {
		return ChatColor.translateAlternateColorCodes('&', text);
	}
}