package me.thamma.DMZ.Battle;

import org.bukkit.Material;
import org.bukkit.event.Listener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by: Borlea
 * https://github.com/borlea/
 * http://thederpygolems.ca/
 */
public class EquipListener implements Listener {

    // Update config to add signs
    HashMap<String, HashMap<ArmorEquipEvent.ArmorType, Long>> lastEquip;
    List<String> blockedMaterials;

    public EquipListener() {
        lastEquip = new HashMap<String, HashMap<ArmorEquipEvent.ArmorType, Long>>();
        blockedMaterials = new ArrayList<String>();
        blockedMaterials.add(Material.SIGN_POST.name());
        blockedMaterials.add(Material.WALL_SIGN.name());
        blockedMaterials.add(Material.SIGN.name());
    }
}