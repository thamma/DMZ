package me.thamma.DMZ.Chunkys;

import me.thamma.DMZ.utils.Utils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * Created by pc on 14.06.2015.
 */
public class ChunkyCommands implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (cmd.getName().equalsIgnoreCase("chunky")) {
            if ((sender instanceof Player)) {
                Player p = (Player) sender;


                if (Utils.matchArgs("create", args)) {
                    Chunky c = new Chunky(p.getLocation());
                    p.sendMessage("Chunky created. No need to but still");
                } else if (Utils.matchArgs("nomobs", args)) {
                    Chunky c = new Chunky(p.getLocation());
                    c.setAttribute(Attribute.Mobspawn, "false");
                }
            }
        }
        return true;
    }

}
