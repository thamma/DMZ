package me.thamma.DMZ.core;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;

/**
 * Created by pc on 07.06.2015.
 */

public class MyListener implements Listener {

    private Main plugin;

    public MyListener(Main m) {
        this.plugin = m;
    }


    @EventHandler
    public void onMove(PlayerMoveEvent e) {

    }

}