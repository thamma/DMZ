package me.thamma.DMZ.Chunky;

import org.bukkit.Location;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.Zombie;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.player.PlayerMoveEvent;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by pc on 14.06.2015.
 */
public class ChunkyListener implements Listener {

    public static List<EntityType> hostiles = new ArrayList<EntityType>() {{
        add(EntityType.ZOMBIE);
        add(EntityType.SPIDER);
        add(EntityType.CREEPER);
        add(EntityType.ENDERMAN);
        add(EntityType.CAVE_SPIDER);
        add(EntityType.WITCH);
        add(EntityType.PIG_ZOMBIE);
        add(EntityType.BLAZE);
        add(EntityType.GHAST);
        add(EntityType.GIANT);
        add(EntityType.GUARDIAN);
        add(EntityType.ENDERMITE);
        add(EntityType.MAGMA_CUBE);
        add(EntityType.SILVERFISH);
        add(EntityType.SKELETON);
        add(EntityType.SLIME);
    }};
    public static HashMap<String, Setting> settings;
    public static HashMap<String, String> lastChunk;

    @EventHandler
    public void onSpawn(CreatureSpawnEvent e) {
        Location loc = e.getLocation();
        Chunky c = new Chunky(e.getLocation());
        if (c.getAttribute(Attribute.Mobspawn).equals("false")) {
            if (hostiles.contains(e.getEntity().getType())) {
                e.setCancelled(true);
            }
        } else {
            if (!e.getEntityType().equals(EntityType.ZOMBIE)) {
                e.setCancelled(true);
            } else {
                Zombie z = (Zombie) e.getEntity();
                z.setCustomName("Zombie Lv." + c.getAttribute(Attribute.Level));
                z.setCustomNameVisible(true);
            }
        }
    }

    @EventHandler
    public void onMove(PlayerMoveEvent e) {
        Player p = e.getPlayer();
        Chunky c = new Chunky(p.getLocation());
        if (lastChunk.containsKey(p.getName())) {
            if (!lastChunk.get(p.getName()).equals(c.path())) {
                onChunkyChange(new ChunkyChangeEvent(p, new Chunky(lastChunk.get(p.getName())), c));
                lastChunk.put(p.getName(), c.path());
            }
        } else {
            lastChunk.put(p.getName(), c.path());
        }
    }

    public void onChunkyChange(ChunkyChangeEvent e) {
        Player p = e.getPlayer();
        if (settings.containsKey(p.getName())) {
            Setting s = settings.get(p.getName());
            if (!e.getTo().getAttribute(s.getAttribute()).equals(s.getValue())) {
                e.getTo().setAttribute(s.getAttribute(), s.getValue());
                p.sendMessage("Set attribute " + s.getAttribute().name() + " to " + s.getValue() + " for this chunky");
            }
            p.sendMessage("Remember to turn the runset off once youre done using /chunky runset");
            e.getTo().print(p, s.getAttribute());
        }
    }

    public static class Setting {
        private String value;
        private Attribute attribute;

        public Setting(Attribute arg0, String arg1) {
            this.attribute = arg0;
            this.value = arg1;
        }

        public String getValue() {
            return this.value;
        }

        public Attribute getAttribute() {
            return this.attribute;
        }
    }

    private class ChunkyChangeEvent {

        private Player player;
        private Chunky from;
        private Chunky to;

        public ChunkyChangeEvent(Player arg0, Chunky from, Chunky to) {
            this.player = arg0;
            this.from = from;
            this.to = to;
        }

        public Chunky getFrom() {
            return this.from;
        }

        public Chunky getTo() {
            return this.to;
        }

        public Player getPlayer() {
            return this.player;
        }
    }

}