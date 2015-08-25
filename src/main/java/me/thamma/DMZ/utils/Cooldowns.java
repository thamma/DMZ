package me.thamma.DMZ.utils;

import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;

public class Cooldowns {
    private static Map<String, Long> map;
    private String index;

    public Cooldowns(Player p) {
        this.index = "" + p.getUniqueId();

    }

    public Cooldowns(String arg0) {
        this.index = arg0;
    }

    public Cooldowns() {
        this.index = "main";
    }

    public static void initialize() {
        map = new HashMap<String, Long>();
    }

    public boolean readyAndUse(String key, int millis) {
        Boolean out = ready(key);
        if (ready(key)) {
            add(key, millis);
        }
        return out;
    }

    public boolean ready(String key) {
        return remaining(key) <= 0;
    }

    public boolean exists(String key) {
        return map.containsKey(index + "." + key);
    }

    public void add(String key, int millis) {
        if ((ready(key)) || (!exists(key))) {
            map.put(index + "." + key, System.currentTimeMillis() + millis);
        } else {
            long rem = remaining(key);
            map.put(index + "." + key, rem + millis);
        }
    }

    public void reset(String key) {
        map.put(index + "." + key, System.currentTimeMillis());
    }

    public long remaining(String key) {
        if (exists(key)) {
            long diff = map.get(index + "." + key) - System.currentTimeMillis();
            // <= 0 if ready,  >0 if on cooldown
            return diff;
        }
        return 0;
    }
}