package me.thamma.DMZ.Battle;

import me.thamma.DMZ.utils.Utils;
import org.apache.commons.lang.StringUtils;
import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by pc on 26.06.2015.
 */
public class MyItem {

    private ItemStack is;
    private ItemMeta im;
    private List<String> lore;
    private int level;
    private List<MyEnchantment> myenchants;
    private Map<Enchantment, Integer> enchants;
    private String name;
    private boolean unbreakable;

    public MyItem(Material arg0) {
        this(arg0, 1, (short) 0);
    }

    public MyItem(Material arg0, int arg1) {
        this(arg0, arg1, (short) 0);
    }

    public MyItem(Material arg0, int arg1, short arg2) {
        this.is = new ItemStack(arg0, arg1, arg2);
        this.im = is.getItemMeta();
        this.lore = new ArrayList<String>();
        this.level = 0;
        this.myenchants = new ArrayList<MyEnchantment>();
        this.enchants = new HashMap<Enchantment, Integer>();
        this.name = "";
        this.unbreakable = false;
    }

    public MyItem(ItemStack is) {
        this.is = is;
        this.im = this.is.getItemMeta();
        this.name = "";
        this.lore = new ArrayList<String>();
        this.level = 0;
        this.myenchants = new ArrayList<MyEnchantment>();
        this.enchants = is.getEnchantments();
        if (is.hasItemMeta()) {
            this.unbreakable = false;
            if (is.getItemMeta().hasDisplayName())
                this.name = is.getItemMeta().getDisplayName();
            if (is.getItemMeta().hasLore())
                for (String s : is.getItemMeta().getLore()) {
                    String line = ChatColor.stripColor(s);
                    if (line.startsWith("Level: ")) {
                        this.level = Integer.parseInt(line.replaceFirst("Level: ", ""));
                    } else if (line.startsWith("+") || line.startsWith("-")) {
                        int lv = Integer.parseInt(line.split(" ")[0]);
                        MyEnchantmentType e = MyEnchantmentType.valueOf(line.split(" ")[1]);
                        myenchants.add(new MyEnchantment(e, lv));
                    } else {
                        this.lore.add(line);
                    }

                }
        }
    }

    public static void updateItems(Player p) {
        for (int i = 0; i < p.getInventory().getContents().length; i++) {
            if (p.getInventory().getContents()[i] != null) {
                p.getInventory().setItem(i, new MyItem(p.getInventory().getContents()[i]).getItemStack());
            }
        }
    }

    public int getEnchantmentLevel(Enchantment type) {
        for (Enchantment e : enchants.keySet()) {
            if (e.equals(type)) {
                return enchants.get(e);
            }
        }
        return 0;
    }

    public int getEnchantmentLevel(MyEnchantmentType type) {
        for (MyEnchantment e : myenchants) {
            if (e.getType().equals(type)) {
                return e.getLevel();
            }
        }
        return 0;
    }

    public boolean isUnbreakable() {
        return this.unbreakable;
    }

    public void setUnbreakable(boolean arg0) {
        this.unbreakable = arg0;
    }

    public int getLevel() {
        return this.level;
    }

    public void setLevel(int i) {
        this.level = i;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String arg0) {
        this.name = arg0;
    }

    public List<String> getLore() {
        return this.lore;
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

    public List<MyEnchantment> getMyEnchantments() {
        return this.myenchants;
    }

    public Map<Enchantment, Integer> getEnchantments() {
        return this.enchants;
    }

    public void addEnchantment(MyEnchantment e) {
        myenchants.add(e);
    }

    public void addEnchantment(Enchantment e, int lv) {
        this.enchants.put(e, lv);
    }

    public void setLeatherColor(int r, int g, int b) {
        if (!this.is.getType().toString().contains("LEATHER_"))
            return;
        LeatherArmorMeta lam = (LeatherArmorMeta) im;
        lam.setColor(Color.fromRGB(r, g, b));
    }

    public void setEnchantment(MyEnchantment e) {
        boolean b = false;
        for (MyEnchantment i : myenchants) {
            if (e.getType().equals(i.getType()))
                myenchants.remove(i);
        }
        myenchants.add(e);
    }

    public ItemStack getItemStack() {
        if (!this.name.equals(""))
            im.setDisplayName(Utils.color(this.name));
        ArrayList<String> l = new ArrayList<String>();
        l.addAll(this.lore);
        if (level > 0)
            l.add(Utils.color("&fLevel: " + level));
        for (MyEnchantment e : this.myenchants) {
            l.add(Utils.color(e.toString()));
        }
        //im.spigot().setUnbreakable(this.unbreakable);
        im.setLore(l);
        is.addEnchantments(enchants);
        is.setItemMeta(this.im);
        return is;
    }

    public enum MyEnchantmentType {
        Damage(ChatColor.GOLD), Armor(ChatColor.GOLD), Poison(ChatColor.GREEN), Swiftness(ChatColor.AQUA);

        private ChatColor c;

        MyEnchantmentType(ChatColor arg0) {
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

        private MyEnchantmentType e;
        private int l;

        public MyEnchantment(MyEnchantmentType arg0, int arg1) {
            this.e = arg0;
            this.l = arg1;
        }

        @Override
        public String toString() {
            return "" + this.getType().getColor() + (this.getLevel() >= 0 ? "+" : "-") + this.getLevel() + " " + this.getType().getDisplayName();
        }

        public MyEnchantmentType getType() {
            return this.e;
        }

        public int getLevel() {
            return this.l;
        }

    }

}
