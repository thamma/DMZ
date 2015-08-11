package me.thamma.DMZ.Battle;


import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

/**
 * Created by pc on 09.08.2015.
 */
public class MyLiving {


    private LivingEntity entity;

    public MyLiving(LivingEntity en) {
        this.entity = en;
    }

    public LivingEntity getEntity() {
        return this.entity;
    }

    public int getEnchantmentSum(MyItem.MyEnchantmentType ench) {
        int out = 0;
        for (ItemStack is : this.entity.getEquipment().getArmorContents()) {
            MyItem mi = new MyItem(is);
            if (this.entity instanceof Player) {
                if (((Player) this.entity).getLevel() >= mi.getLevel())
                    out += mi.getEnchantmentLevel(ench);
            } else
                out += mi.getEnchantmentLevel(ench);
        }
        return out;
    }

    public int getEnchantmentSum(Enchantment ench) {
        int out = 0;
        for (ItemStack is : this.entity.getEquipment().getArmorContents()) {
            MyItem mi = new MyItem(is);
            if (this.entity instanceof Player) {
                if (((Player) this.entity).getLevel() >= mi.getLevel())
                    out += mi.getEnchantmentLevel(ench);
            } else
                out += mi.getEnchantmentLevel(ench);
        }
        return out;
    }

    /**
     * @param target: The attacked target
     * @return: The damage dealt
     */
    public int attack(MyLiving target) {
        //no decent calculation!
        return (Math.max(0, this.getEnchantmentSum(MyItem.MyEnchantmentType.Damage) - target.getEnchantmentSum(MyItem.MyEnchantmentType.Armor))) * 5;
    }

}