package me.thamma.DMZ.Chunky;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;

import java.util.ArrayList;
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


    @EventHandler
    public void onSpawn(CreatureSpawnEvent e) {
        Location loc = e.getLocation();
        Chunky c = new Chunky(loc.getChunk().getX(), loc.getChunk().getZ());

        if (c.getAttribute(Attribute.Mobspawn) == "false") {

            if (hostiles.contains(e.getEntity().getType())) {
                e.setCancelled(true);
            }
        }
    }
}