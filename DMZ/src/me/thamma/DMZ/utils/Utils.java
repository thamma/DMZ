package me.thamma.DMZ.utils;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;

public class Utils {

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
                    } else if ((type.equalsIgnoreCase("boolean")) &&
                            (!isBool(args[i]))) {
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
        return (string.equalsIgnoreCase("true")) ||
                (string.equalsIgnoreCase("false"));
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
        /*
        text = text.replaceAll("&0", String.valueOf(ChatColor.BLACK));
        text = text.replaceAll("&1", String.valueOf(ChatColor.DARK_BLUE));
        text = text.replaceAll("&2", String.valueOf(ChatColor.DARK_GREEN));
        text = text.replaceAll("&3", String.valueOf(ChatColor.DARK_AQUA));
        text = text.replaceAll("&4", String.valueOf(ChatColor.DARK_RED));
        text = text.replaceAll("&5", String.valueOf(ChatColor.DARK_PURPLE));
        text = text.replaceAll("&6", String.valueOf(ChatColor.GOLD));
        text = text.replaceAll("&7", String.valueOf(ChatColor.GRAY));
        text = text.replaceAll("&8", String.valueOf(ChatColor.DARK_GRAY));
        text = text.replaceAll("&9", String.valueOf(ChatColor.BLUE));
        text = text.replaceAll("&A", String.valueOf(ChatColor.GREEN));
        text = text.replaceAll("&B", String.valueOf(ChatColor.AQUA));
        text = text.replaceAll("&C", String.valueOf(ChatColor.RED));
        text = text.replaceAll("&D", String.valueOf(ChatColor.LIGHT_PURPLE));
        text = text.replaceAll("&E", String.valueOf(ChatColor.YELLOW));
        text = text.replaceAll("&F", String.valueOf(ChatColor.WHITE));
        text = text.replaceAll("&a", String.valueOf(ChatColor.GREEN));
        text = text.replaceAll("&b", String.valueOf(ChatColor.AQUA));
        text = text.replaceAll("&c", String.valueOf(ChatColor.RED));
        text = text.replaceAll("&d", String.valueOf(ChatColor.LIGHT_PURPLE));
        text = text.replaceAll("&e", String.valueOf(ChatColor.YELLOW));
        text = text.replaceAll("&f", String.valueOf(ChatColor.WHITE));
        text = text.replaceAll("&u", String.valueOf(ChatColor.UNDERLINE));
        text = text.replaceAll("&U", String.valueOf(ChatColor.UNDERLINE));
        text = text.replaceAll("&n", String.valueOf(ChatColor.BOLD));
        text = text.replaceAll("&N", String.valueOf(ChatColor.BOLD));
        text = text.replaceAll("&i", String.valueOf(ChatColor.ITALIC));
        text = text.replaceAll("&I", String.valueOf(ChatColor.ITALIC));
        text = text.replaceAll("&m", String.valueOf(ChatColor.MAGIC));
        text = text.replaceAll("&M", String.valueOf(ChatColor.MAGIC));
        text = text.replaceAll("&r", String.valueOf(ChatColor.RESET));
        text = text.replaceAll("&R", String.valueOf(ChatColor.RESET));
        return text;
        */
    }
}