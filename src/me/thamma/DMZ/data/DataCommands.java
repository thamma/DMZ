package me.thamma.DMZ.data;

import me.thamma.DMZ.utils.Utils;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by pc on 11.06.2015.
 */
public class DataCommands implements CommandExecutor {


    public static HashMap<String, Task> tasks = new HashMap<String, Task>();
    private static List<String> active = new ArrayList<String>();

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (cmd.getName().equalsIgnoreCase("data")) {
            if ((sender instanceof Player)) {
                Player p = (Player) sender;

                if (p.getItemInHand() != null) {

                    if (Utils.matchArgs("setname", args)) {
                        p.sendMessage("Your chat is now disabled due to the current task. Type \'Done.\' when you're done.");
                        tasks.put(p.getName(), new Task() {
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
                            public void run(ArrayList<String> l, Player p) {
                                ItemStack is = new ItemStack(Material.NAME_TAG);
                                ItemMeta im = is.getItemMeta();
                                im.setDisplayName(Utils.color(l.get(0)));
                                l.remove(0);
                                im.setLore(l);
                                is.setItemMeta(im);
                                p.getInventory().addItem(is);
                            }
                        });
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
                        if (active.contains(p.getName())) {
                            active.remove(p.getName());
                        } else {
                            active.add(p.getName());
                        }
                    }
                } else {
                    p.sendMessage("You must be holding an item to modify");
                }
            }
        }

        return true;
    }

    public interface Task {

        void run(ArrayList<String> l, Player p);

    }
}
