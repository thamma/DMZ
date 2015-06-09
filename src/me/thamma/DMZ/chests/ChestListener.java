package me.thamma.DMZ.chests;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;


public class ChestListener implements Listener {


    @EventHandler
    public void onOpenChest(PlayerInteractEvent e) {

    }


    void open() {
        Chest c = null;
        if (c.willRefill()) {
            c.fill();
        }
    }

}
