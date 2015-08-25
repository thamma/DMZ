package me.thamma.DMZ.core;

import me.thamma.DMZ.utils.Utils;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.SignChangeEvent;

/**
 * Created by pc on 18.07.2015.
 */
public class ColorSign implements Listener {

    @EventHandler
    public void onSignChange(SignChangeEvent e) {
        String[] lines = e.getLines();
        for (int i = 0; i < lines.length; i++) {
            e.setLine(i, Utils.color(lines[i]));
        }
    }
}
