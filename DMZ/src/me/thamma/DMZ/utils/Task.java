package me.thamma.DMZ.utils;

import org.bukkit.entity.Player;

import java.util.ArrayList;

/**
 * Created by pc on 19.06.2015.
 */
public interface Task {

    int getAmount();

    void run(ArrayList<String> l, Player p);

}