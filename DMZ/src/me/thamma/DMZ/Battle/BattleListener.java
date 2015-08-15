package me.thamma.DMZ.Battle;

import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

/**
 * Created by pc on 26.06.2015.
 */
public class BattleListener implements Listener {

    final int[] damageMap = {};

    public static int healthAtLevel(int level) {
        int[] wpnDamage = {1, 2, 4, 6, 9, 12, 16, 20, 25, 30, 36, 42, 49, 56, 64, 72, 81, 90, 100, 110};
        int dmg = wpnDamage[level / 6] * 5;
        int hits = getHits(level);
        return dmg * hits;
    }

    private static int getHits(int level) {
        int base = 3 + (level / 12);
        int[] res = {0, 1, 1, 2, 2, 2};
        return base + res[level % 6];
    }

    @EventHandler
    public void onDamage(EntityDamageByEntityEvent e) {
        Entity damaged = e.getEntity();
        Entity damager = e.getDamager();
        int dmg = 0;
        if (e.getDamager() instanceof LivingEntity && e.getEntity() instanceof LivingEntity) {
            MyLiving d1 = new MyLiving((LivingEntity) damaged);
            MyLiving d2 = new MyLiving((LivingEntity) damager);

            dmg = d2.attack(d1);
            
        }
        e.setDamage(dmg);
    }

}
