package me.thamma.DMZ.Utils;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;

/**
 * Created by pc on 07.06.2015.
 */
public class Utils {

    public boolean matchArgs(String src, String[] args)
    {
        boolean out = true;
        String[] in = src.split(" ");
        if (in.length != args.length) {
            return false;
        }
        if (args.length == 0) {
            return out;
        }
        for (int i = 0; i < in.length; i++) {
            if (in[i].charAt(0) == '#')
            {
                String type = in[i].replaceFirst("#", "");
                if (!type.equalsIgnoreCase("string")) {
                    if (type.equalsIgnoreCase("int"))
                    {
                        if (!isNum(args[i])) {
                            out = false;
                        }
                    }
                    else if (type.equalsIgnoreCase("double"))
                    {
                        if (!isNum(args[i])) {
                            out = false;
                        }
                    }
                    else if ((type.equalsIgnoreCase("boolean")) &&
                            (!isBool(args[i]))) {
                        out = false;
                    }
                }
            }
            else if (!in[i].equalsIgnoreCase(args[i]))
            {
                return false;
            }
        }
        return out;
    }

    private boolean isNum(String string)
    {
        try
        {
            Integer.parseInt(string);
        }
        catch (NumberFormatException e)
        {
            return false;
        }
        return true;
    }

    private boolean isBool(String string)
    {
        return (string.equalsIgnoreCase("true")) ||
                (string.equalsIgnoreCase("false"));
    }

    public static Location str2loc(String in)
    {
        String[] s = in.split("\\;");
        World w = Bukkit.getWorld(s[0]);
        double x = Double.parseDouble(s[1]);
        double y = Double.parseDouble(s[2]);
        double z = Double.parseDouble(s[3]);
        if (s.length == 6)
        {
            Float yaw = Float.valueOf(Float.parseFloat(s[4]));
            Float pit = Float.valueOf(Float.parseFloat(s[5]));
            return new Location(w, x, y, z, yaw.floatValue(), pit.floatValue());
        }
        return new Location(w, x + 0.5D, y, z + 0.5D);
    }

    public static String loc2str(Location loc)
    {
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
}