package me.thamma.DMZ.Chunky;

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

                Chunky c = new Chunky(p.getLocation());
                if (Utils.matchArgs("runset", args)) {
                    if (ChunkyListener.settings.containsKey(p.getName())) {
                        ChunkyListener.settings.remove(p.getName());
                        p.sendMessage("Runset unset.");
                    } else {
                        p.sendMessage("No runset setup for you. Use /chunky runset <Attribute> <Value> to do so.");
                    }
                } else if (Utils.matchArgs("runset #String #String", args)) {
                    try {
                        Attribute a = Attribute.valueOf(args[1]);
                        ChunkyListener.Setting s = new ChunkyListener.Setting(a, args[2]);
                        ChunkyListener.settings.put(p.getName(), s);
                        p.sendMessage(Utils.color("&eRunset created:\nAttribute:&6" + a.name() + "   &eValue: &6" + args[2] + "\n&eType &6/chunky runset &eto disable."));
                    } catch (Exception e) {
                        p.sendMessage("No such attribute");
                    }
                } else if (Utils.matchArgs("att", args)) {
                    for (Attribute a : Attribute.values()) {
                        p.sendMessage(" - " + a.name());
                    }
                } else if (Utils.matchArgs("att #String", args)) {
                    try {
                        Attribute a = Attribute.valueOf(args[1]);
                        p.sendMessage(Utils.color("&eThe attribute &6" + a.name() + " &efor this chunky is &6" + c.getAttribute(a) + "&e."));
                    } catch (Exception e) {
                        p.sendMessage("No such attribute");
                    }
                } else if (Utils.matchArgs("att #String #String", args)) {
                    try {
                        Attribute a = Attribute.valueOf(args[1]);
                        c.setAttribute(a, args[2]);
                        p.sendMessage(Utils.color("&eSet the attribute &6" + a.name() + " &efor this chunky to &6" + args[2] + "&e."));
                    } catch (Exception e) {
                        p.sendMessage("No such attribute");
                    }
                } else if (Utils.matchArgs("help", args) || Utils.matchArgs("", args)) {
                    p.sendMessage("att, att <attribute>, att <attribute> <value>, map <attribute>, save" +
                            "");
                } else if (Utils.matchArgs("save", args)) {
                    Chunky.saveAll();
                } else if (Utils.matchArgs("map #String", args)) {
                    try {
                        Attribute a = Attribute.valueOf(args[1]);
                        c.print(p, a);
                    } catch (Exception e) {
                        p.sendMessage("No such attribute");
                    }
                } else if (Utils.matchArgs("map #String #int", args)) {
                    try {
                        Attribute a = Attribute.valueOf(args[1]);
                        c.print(p, a, Integer.parseInt(args[2]));
                    } catch (Exception e) {
                        p.sendMessage("No such attribute");
                }
                }
            }
        }
        return true;
    }

}
