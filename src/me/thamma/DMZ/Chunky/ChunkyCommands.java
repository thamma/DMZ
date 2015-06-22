package me.thamma.DMZ.Chunky;

import me.thamma.DMZ.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.w3c.dom.Attr;

/**
 * Created by pc on 14.06.2015.
 */
public class ChunkyCommands implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (cmd.getName().equalsIgnoreCase("chunky")) {
            if ((sender instanceof Player)) {
                Player p = (Player) sender;

                Chunky c = new Chunky(p.getLocation());
                if (Utils.matchArgs("mobs #bool", args)) {
                    c.setAttribute(Attribute.Mobspawn, args[1]);
                    p.sendMessage("Mobspawn is now: " + args[1]);
                } else if (Utils.matchArgs("level #int", args)) {
                    c.setAttribute(Attribute.Level, args[1]);
                    p.sendMessage("Level is now: " + args[1]);
                } else if (Utils.matchArgs("attributes", args)) {
                    for (Attribute a : Attribute.values()) {
                        p.sendMessage(" - " + a.name());
                    }
                } else if (Utils.matchArgs("map #String", args)) {
                    try {
                        Attribute a = Attribute.valueOf(args[1]);
                        p.sendMessage("     NORTH");
                        int cap = 5;
                        Chunk chunk = p.getLocation().getChunk();
                        String m = "";
                        for (int i = chunk.getZ() - cap + 1; i < chunk.getZ() + cap; i++) {
                            m = "";
                            for (int j = chunk.getX() - cap + 1; j < chunk.getX() + cap; j++) {
                                Chunky temp = new Chunky(j, i);
                                boolean bool = false;
                                if (temp.getAttribute(a).equals("true")) {
                                    m += "&a";
                                    bool = true;
                                } else if (temp.getAttribute(a).equals("false")) {
                                    m += "&c";
                                    bool = true;
                                }
                                if (j == chunk.getX() && i == chunk.getZ()) {
                                    m += "II";
                                } else {
                                    m += "[" + (!bool ? temp.getAttribute(a) : "") + "]";
                                }
                            }
                            p.sendMessage(Utils.color(m));
                        }
                    } catch (Exception e) {
                        p.sendMessage("No such attribute");
                    }
                }
            }
        }
        return true;
    }

}
