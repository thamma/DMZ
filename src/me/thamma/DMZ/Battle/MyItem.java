package me.thamma.DMZ.Battle;

import me.thamma.DMZ.utils.Utils;
import org.apache.commons.lang.StringUtils;
import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by pc on 26.06.2015.
 */
public class MyItem extends ItemStack {

    private ItemStack is;
    private ItemMeta im;
    private List<String> lore;
    private int level;
    private List<MyEnchantment> enchants;
    private String name;

    public MyItem(Material arg0, int arg1, short arg2) {
        is = new ItemStack(arg0, arg1, arg2);
        if (im != null)
            im = is.getItemMeta();
    }

    private void addEnchantment(MyEnchantment e) {
        enchants.add(e);
    }

    public void setLeatherColor(int r, int g, int b) {
        if (!this.is.getType().toString().contains("LEATHER_"))
            return;
        LeatherArmorMeta lam = (LeatherArmorMeta) im;
        lam.setColor(Color.fromRGB(r, g, b));
    }

    public void setEnchantment(MyEnchantment e) {
        boolean b = false;
        for (MyEnchantment i : enchants) {
            if (e.getType().equals(i.getType()))
                enchants.remove(i);
        }
        enchants.add(e);
    }

    public void setLore(String[] arg0) {
        ArrayList<String> l = new ArrayList<String>();
        for (String s : arg0) {
            l.add(Utils.color(s));
        }
        this.lore = l;
    }

    public void setLore(List<String> arg0) {
        ArrayList<String> l = new ArrayList<String>();
        for (String s : arg0) {
            l.add(Utils.color(s));
        }
        this.lore = l;
    }

    public void setName(String arg0) {
        this.name = arg0;
    }

    public ItemStack getItemStack() {
        im.setDisplayName(Utils.color(this.name));
        ArrayList<String> l = new ArrayList<String>();
        l.addAll(this.lore);
        if (level > 0)
            l.add(Utils.color("&fLevel: " + level));
        for (MyEnchantment e : this.enchants) {
            l.add(Utils.color(e.toString()));
        }
        return is;
    }

    public enum EnchantmentType {
        DAMAGE(ChatColor.GOLD), ARMOR(ChatColor.GOLD), POISON(ChatColor.GREEN), SWIFTNESS(ChatColor.AQUA);

        private ChatColor c;

        EnchantmentType(ChatColor arg0) {
            this.c = arg0;
        }

        public ChatColor getColor() {
            return this.c;
        }

        public String getDisplayName() {
            return StringUtils.capitalize(this.name());
        }
    }

    public static class MyEnchantment {

        private EnchantmentType e;
        private int l;

        public MyEnchantment(EnchantmentType arg0, int arg1) {
            this.e = arg0;
            this.l = arg1;
        }

        @Override
        public String toString() {
            return "" + this.getType().getColor() + (this.getLevel() >= 0 ? "+" : "-") + this.getLevel() + this.getType().getDisplayName();
        }

        public EnchantmentType getType() {
            return this.e;
        }

        public int getLevel() {
            return this.l;
        }

    }

}
