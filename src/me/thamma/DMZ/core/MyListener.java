package me.thamma.DMZ.core;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

/**
 * Created by pc on 07.06.2015.
 */

public class MyListener implements Listener {

    private Main plugin;

    public MyListener(Main m) {
        this.plugin = m;
    }


    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e) {


    }

}