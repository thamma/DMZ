package me.thamma.DMZ.data;

import me.thamma.DMZ.Battle.MyItem;
import me.thamma.DMZ.core.Main;
import me.thamma.DMZ.utils.Task;
import me.thamma.DMZ.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Chest;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by pc on 11.06.2015.
 */
public class DataCommands implements CommandExecutor {


    public static HashMap<String, Task> tasks = new HashMap<String, Task>();
    private static List<String> active = new ArrayList<String>();
    private static Main plugin;

    private static HashMap<Integer, Location> movemap;
    private static int count = 0;
    private static int task = 0;

    public DataCommands(Main m) {
        plugin = m;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (cmd.getName().equalsIgnoreCase("data")) {
            if ((sender instanceof Player)) {
                Player p = (Player) sender;
                if (Utils.matchArgs("enchant", args)) {
                    for (MyItem.MyEnchantmentType et : MyItem.MyEnchantmentType.values()) {
                        p.sendMessage("-" + et.getColor() + et.toString());
                    }
                } else if (Utils.matchArgs("enchant #string #int", args)) {
                    try {
                        MyItem.MyEnchantmentType et = MyItem.MyEnchantmentType.valueOf(args[1]);
                        MyItem.MyEnchantment myench = new MyItem.MyEnchantment(et, Integer.parseInt(args[2]));
                        if (p.getItemInHand() != null) {
                            MyItem mi = new MyItem(p.getItemInHand());
                            mi.addEnchantment(myench);
                            p.setItemInHand(mi.getItemStack());
                        } else {
                            p.sendMessage("No item in hand!");
                        }
                    } catch (Exception e) {
                        p.sendMessage("No such enchant");
                    }
                } else if (Utils.matchArgs("ac", args)) {
                    Chest c = (Chest) ((new Location(Bukkit.getWorld("world"), 0, 0, 0).getBlock().getState()));
                    p.openInventory(c.getBlockInventory());
                } else if (Utils.matchArgs("update", args)) {
                    p.setHealthScaled(true);
                    p.setMaxHealth(100 + p.getLevel() * 10);
                    p.setHealthScale(p.getMaxHealth() / 5);
                    p.setHealth(p.getMaxHealth());
                    MyItem is = new MyItem(Material.WOOD_SWORD);
                    is.setName("&aKokiri Sword");
                    is.addEnchantment(new MyItem.MyEnchantment(MyItem.MyEnchantmentType.Damage, 3));
                    is.addEnchantment(new MyItem.MyEnchantment(MyItem.MyEnchantmentType.Poison, 2));
                    is.setLevel(3);
                    is.setLore(new String[]{"Totally not stolen from Link."});
                    p.getInventory().addItem(is.getItemStack());
                } else if (Utils.matchArgs("toolbar", args)) {
                    IconMenu menu = new IconMenu("Admin toolbar", 27, new IconMenu.OptionClickEventHandler() {
                        @Override
                        public void onOptionClick(IconMenu.OptionClickEvent e) {
                            switch (e.getPosition()) {
                                case 0:
                                    e.getPlayer().setGameMode(GameMode.SURVIVAL);
                                    break;
                                case 9:
                                    e.getPlayer().setGameMode(GameMode.CREATIVE);
                                    break;
                                case 18:
                                    e.getPlayer().setGameMode(GameMode.SPECTATOR);
                                    break;
                                case 12:
                                    e.getPlayer().setHealth(e.getPlayer().getMaxHealth());
                                    break;
                                case 13:
                                    e.getPlayer().setFoodLevel(20);
                                    e.getPlayer().setExhaustion(1F);
                                    break;
                                case 14:
                                    Bukkit.broadcastMessage("Called");
                                    if (e.getPlayer().hasPotionEffect(PotionEffectType.NIGHT_VISION)) {
                                        e.getPlayer().removePotionEffect(PotionEffectType.NIGHT_VISION);
                                        Bukkit.broadcastMessage("removed");
                                    } else {
                                        e.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.NIGHT_VISION, 500000, 0));
                                        Bukkit.broadcastMessage("added");
                                    }
                                    break;
                            }
                        }
                    }, plugin)
                            .setOption(0, new ItemStack(Material.STONE_SWORD, 1), "Survival", "Puts you into survival gamemode")
                            .setOption(9, new ItemStack(Material.DIAMOND_BLOCK, 1), "Go creative!", "Puts you into creative gamemode")
                            .setOption(18, new ItemStack(Material.COMPASS, 1), "Spectate", "Puts you into spectator gamemode")
                            .setOption(12, new ItemStack(Material.POTION, 1, (short) 8197), "Heal", "Heals your hearts")
                            .setOption(13, new ItemStack(Material.BREAD, 1), "Feed", "Satisfies your appetite")
                            .setOption(14, new ItemStack(Material.GOLDEN_CARROT, 1), "Nightvision", "Toggles Nightvision effect");
                    menu.open(p);
                } else if (Utils.matchArgs("barr", args)) {
                    Location loc = p.getLocation();
                    int cap = 200;
                    for (int x = (int) (loc.getX() - cap / 2); x < loc.getX() + cap / 2; x++) {
                        for (int y = (int) (loc.getY() - cap / 2); y < loc.getY() + cap / 2; y++) {
                            for (int z = (int) (loc.getZ() - cap / 2); z < loc.getZ() + cap / 2; z++) {
                                if ((new Location(loc.getWorld(), x, y, z)).getBlock().getType() != null && (new Location(loc.getWorld(), x, y, z)).getBlock().getType().equals(Material.BARRIER)) {
                                    if (active.contains(p.getName())) {
                                        p.sendBlockChange((new Location(loc.getWorld(), x, y, z)), Material.BARRIER, (byte) 0);
                                    } else {
                                        p.sendBlockChange((new Location(loc.getWorld(), x, y, z)), Material.BEDROCK, (byte) 0);
                                    }

                                }
                            }
                        }
                    }
                } else if (p.getItemInHand() != null) {
                    if (Utils.matchArgs("powertool", args)) {
                        p.sendMessage("Your chat is now disabled due to the current task. Type \'Done.\' when you're done. (1)");
                        tasks.put(p.getName(), new Task() {
                            @Override
                            public int getAmount() {
                                return -1;
                            }

                            @Override
                            public void run(ArrayList<String> l, Player p) {
                                ItemStack is = p.getItemInHand();
                                if (is != null) {
                                    ItemMeta im = is.getItemMeta();
                                    im.setDisplayName(Utils.color("&6Powertool"));
                                    im.setLore(l);
                                    is.setItemMeta(im);
                                    p.setItemInHand(is);
                                }
                            }
                        });
                    } else if (Utils.matchArgs("powertool cycle", args)) {
                        p.sendMessage("Your chat is now disabled due to the current task. Type \'Done.\' when you're done. (2)");
                        tasks.put(p.getName(), new Task() {
                            @Override
                            public int getAmount() {
                                return -1;
                            }

                            @Override
                            public void run(ArrayList<String> l, Player p) {
                                ItemStack is = p.getItemInHand();
                                if (is != null) {
                                    ItemMeta im = is.getItemMeta();
                                    im.setDisplayName(Utils.color("&6Powertool"));
                                    l.add(0, "cycle");
                                    im.setLore(l);
                                    is.setItemMeta(im);
                                    p.setItemInHand(is);
                                }
                            }
                        });
                    } else if (Utils.matchArgs("setname", args)) {
                        p.sendMessage("Your chat is now disabled due to the current task. Type \'Done.\' when you're done.");
                        tasks.put(p.getName(), new Task() {
                            @Override
                            public int getAmount() {
                                return 1;
                            }

                            @Override
                            public void run(ArrayList<String> l, Player p) {
                                ItemStack is = p.getItemInHand();
                                if (is != null) {
                                    ItemMeta im = is.getItemMeta();
                                    im.setDisplayName(l.get(0));
                                    is.setItemMeta(im);
                                    p.setItemInHand(is);
                                }
                            }
                        });
                    } else if (Utils.matchArgs("setlore", args)) {
                        p.sendMessage("Your chat is now disabled due to the current task. Type \'Done.\' when you're done.");
                        tasks.put(p.getName(), new Task() {
                            @Override
                            public int getAmount() {
                                return -1;
                            }

                            @Override
                            public void run(ArrayList<String> l, Player p) {
                                ItemStack is = p.getItemInHand();
                                if (is != null) {
                                    ItemMeta im = is.getItemMeta();
                                    im.setLore(l);
                                    is.setItemMeta(im);
                                    p.setItemInHand(is);
                                }
                            }
                        });
                    } else if (Utils.matchArgs("medal", args)) {
                        p.sendMessage("Your chat is now disabled due to the current task. Type \'Done.\' when you're done.");
                        tasks.put(p.getName(), new Task() {
                            @Override
                            public int getAmount() {
                                return -1;
                            }

                            @Override
                            public void run(ArrayList<String> l, Player p) {
                                ItemStack is = new ItemStack(Material.NAME_TAG);
                                ItemMeta im = is.getItemMeta();
                                im.setDisplayName(Utils.color(l.remove(0)));
                                im.setLore(l);
                                is.setItemMeta(im);
                                p.getInventory().addItem(is);
                            }
                        });
                    } else {
                        p.sendMessage("ac, barr, powertool, setname, setlore, medal");
                    }
                    if (active.contains(p.getName())) {
                        active.remove(p.getName());
                    } else {
                        active.add(p.getName());
                    }
                } else {
                    p.sendMessage(Utils.color("&cYou must be holding an item to modify"));
                }
            } else {
                sender.sendMessage("Data commands are no console commands!");
            }
        }

        return true;
    }
}
