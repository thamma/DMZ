package me.thamma.DMZ.chests;

import me.thamma.DMZ.utils.FileManager;
import me.thamma.DMZ.utils.Utils;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.Sign;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.HashSet;

public class ChestCommands implements CommandExecutor {

    public static FileManager config = new FileManager("DMZ/config.yml");


    public boolean onCommand (CommandSender sender, Command cmd, String label, String[] args)
    {
        if ((cmd.getName().equalsIgnoreCase("chest")) &&
                ((sender instanceof Player)))
        {
            Player p = (Player)sender;
            if (Utils.matchArgs("create #int #int", args))
            {
                int amount = Integer.parseInt(args[1]);
                int time = Integer.parseInt(args[2]);
                Block target = p.getTargetBlock(new HashSet<Material>() {{
                    add(Material.AIR);
                }}, 10);

                if (((target != null) &&

                        (target.getType().equals(Material.CHEST))) ||

                        (target.getType().equals(Material.TRAPPED_CHEST)))
                {

                    if (Chest.isChest(target.getLocation()))
                    {
                        p.sendMessage("This is already a respawning chest");
                    }
                    else
                    {

                        org.bukkit.block.Chest chestBlock = (org.bukkit.block.Chest) target.getState();
                        //create chest
                        me.thamma.DMZ.chests.Chest vChest = new Chest(chestBlock.getLocation(), time, amount);
                        Location ref = vChest.getRemoteChest();

                        ref.getBlock().setType(Material.CHEST);
                        ref.getBlock().setData((byte) 5);
                        ref.getBlock().getRelative(BlockFace.EAST)
                                .setType(Material.WALL_SIGN);
                        ref.getBlock().getRelative(BlockFace.EAST)
                                .setData((byte)5);
                        Sign s = (Sign)ref.getBlock()
                                .getRelative(BlockFace.EAST).getState();
                        s.setLine(1, "id: " + vChest.getId());
                        s.setLine(2, "amount: " + vChest.getAmount());
                        s.setLine(3, "time: " + vChest.getTime());
                        s.update();

                        ((org.bukkit.block.Chest)ref.getBlock().getState())
                                .getBlockInventory()
                                .setContents(
                                        chestBlock.getBlockInventory().getContents());

                        p.sendMessage("Respawning chest created");
                    }
                }
                else
                {
                    p.sendMessage(ChatColor.RED + "You must be facing a chest.");
                }
            } else if (Utils.matchArgs("delete", args)) {
                Block target = p.getTargetBlock(new HashSet<Material>() {{
                    add(Material.AIR);
                }}, 10);
                if (((target != null) &&
                        (target.getType().equals(Material.CHEST))) ||
                        (target.getType().equals(Material.TRAPPED_CHEST))) {
                    if (Chest.isChest(target.getLocation())) {
                        Chest c = Chest.getChest(target.getLocation());
                        p.sendMessage("Respawning chest (id " + c.getId() + ") deleted.");
                        c.delete();
                    } else {
                        p.sendMessage(org.bukkit.ChatColor.RED + "This is no respawning chest.");
                    }
                } else {
                    p.sendMessage("You must be facing a chest.");
                }

            } else {
                p.sendMessage(new String[]{"/chest create [amount] [time]", "/chest delete"});

            }
        }
        return true;
    }


}
