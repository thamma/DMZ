package me.thamma.DMZ.chests;

import me.thamma.DMZ.Utils.FileManager;
import me.thamma.DMZ.Utils.Utils;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.Sign;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.metadata.LazyMetadataValue;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class ChestCommands implements CommandExecutor {

    public static FileManager config = new FileManager("DMZ/plugin.yml");


    public boolean onCommand (CommandSender sender, Command cmd, String label, String[] args)
    {
        if ((cmd.getName().equalsIgnoreCase("chest")) &&
                ((sender instanceof Player)))
        {
            Player p = (Player)sender;
            if (Utils.matchArgs("create #int #int", args))
            {
                Block target = p.getTargetBlock(new HashSet<Material>(), 10);

                if (((p.getTargetBlock(new HashSet<Material>(), 10) != null) &&

                        (p.getTargetBlock(new HashSet<Material>(), 10).getType().equals(Material.CHEST))) ||

                        (p.getTargetBlock(new HashSet<Material>(), 10).getType().equals(Material.TRAPPED_CHEST)))
                {
                    if (Chest.isChest(target.getLocation()))
                    {
                        p.sendMessage("This is already a respawning chest");
                    }
                    else
                    {
                        int amount = Integer.parseInt(args[1]);
                        int time = Integer.parseInt(args[2]);
                        org.bukkit.block.Chest chestBlock = (org.bukkit.block.Chest) target.getState();

                        //create chest
                        me.thamma.DMZ.chests.Chest vChest = new Chest(chestBlock.getLocation(), time, amount);

                        Location ref = Utils.str2loc(config.getString(
                                "chest.constants.reference", "world;-729,74,390"));

                            int x = config.getInt("chest.constants.vector.x", -2);
                            int y = config.getInt("chest.constants.vector.y", 0);
                            int z = config.getInt("chest.constants.vector.z", 0);
                            ref = ref.add(new Vector(x, y, z).multiply(vChest.getId()));

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
                    p.sendMessage("§cYou must be facing a chest.");
                }
            }
            else {
                p.sendMessage(new String[] { "/chest create [amount] [time]" });
            }
        }
        return true;
    }


}
