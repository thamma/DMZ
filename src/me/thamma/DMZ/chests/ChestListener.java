package me.thamma.DMZ.chests;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;


public class ChestListener implements Listener {


    @EventHandler
    public void onOpenChest(PlayerInteractEvent e) {
        if (e.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
            if (e.getClickedBlock().getType() != null && e.getClickedBlock().getType().toString().contains("CHEST")) {
                System.out.println(e.getClickedBlock().getType());
                org.bukkit.block.Chest chestBlock = (org.bukkit.block.Chest) e.getClickedBlock().getState();
                if (Chest.isChest(chestBlock.getLocation())) {
                    Chest vChest = Chest.getChest(chestBlock.getLocation());
                    if (vChest.willRefill()) {
                        vChest.fill();
                    }

                }
            }
        }
    }

}
