package me.thamma.DMZ.warps;

import static me.thamma.DMZ.utils.Utils.*;
import me.thamma.DMZ.utils.Utils;

import static me.thamma.DMZ.utils.Database.warpDb;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Collections;
import java.util.List;
import java.util.Random;

public class WarpCommands implements CommandExecutor {

	@SuppressWarnings("deprecation")
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (cmd.getName().equalsIgnoreCase("warp")) {
			if ((sender instanceof Player)) {
				Player p = (Player) sender;
				if (Utils.matchArgs("list", args)) {
					int size = Warp.getWarps().size();
					if (size == 0) {
						msg(p, "&eNo warps available.");
					} else {
						List<String> al = Warp.getWarps();
						Collections.sort(al);
						String out = "";
						for (String s : al) {
							out = out + "&7, &e" + s;
						}
						out = out.replaceFirst("&7, ", "");
						msg(p, "&6Available warps:");
						msg(p, out);
					}
				} else if (Utils.matchArgs("set #string", args)) {
					if (Warp.getWarps().contains(args[1].toLowerCase())) {
						msg(p, "&eWarp &6" + args[1].toLowerCase() + "&e moved.");
					} else {
						msg(p, "&eWarp &6" + args[1].toLowerCase() + "&e set.");
					}
					warpDb.set(args[1].toLowerCase(), Utils.loc2str(p.getLocation()));
				} else if (Utils.matchArgs("del #string", args)) {
					if (Warp.getWarps().contains(args[1].toLowerCase())) {
						warpDb.set(args[1].toLowerCase(), null);
						msg(p, "&eWarp &6" + args[1] + "&e deleted.");
					} else {
						msg(p, "&eThe warp &6" + args[1] + "&e does not exist.");
					}
				} else if ((Utils.matchArgs("#string", args)) && (!Utils.matchArgs("help", args))) {
					if (Warp.getWarps().contains(args[0].toLowerCase())) {
						Location loc = Utils.str2loc(warpDb.getString(args[0]));
						p.teleport(loc);
					} else {
						msg(p, "&eThe warp &6" + args[0] + "&e does not exist.");
					}
				} else if (Utils.matchArgs("#string #string", args)) {
					if (Warp.getWarps().contains(args[1])) {
						Location loc = Utils.str2loc(warpDb.getString(args[1]));

						Player q = Bukkit.getPlayer(args[+0]);
						if (q != null) {
							q.teleport(loc);
							msg(p, "&eTeleported &6" + q.getName() + " &eto warp &6" + args[1] + "&e.");
						} else {
							msg(p, "&6" + args[0] + "&e is not online.");
						}
					} else {
						msg(p, "&eThe warp &6" + args[1] + "&e does not exist.");
					}
				} else {
					msg(p, new String[] { "&e--- &6Warp Helppage &e---",
							"&e/warp &6list &e- Shows the available warps.", "&e/warp &6set [name] &e- Sets a warp.",
							"&e/warp &6del [name] &e- Deletes a warp.", "&e/warp &6[name] &e- Teleports you to a warp.",
							"&e/warp &6[player] [name] &e- Teleports a player to a warp.",
							"&e/warp &6random [player] [name] [name] &e- Teleports a player to a random warp." });
				}
			} else {
				if (Utils.matchArgs("#string #string", args)) {
					if (Warp.getWarps().contains(args[1])) {
						Location loc = Utils.str2loc(warpDb.getString(args[1]));

						Player q = Bukkit.getPlayer(args[0]);
						if (q != null) {
							q.teleport(loc);
							sender.sendMessage("Teleported " + q.getName() + " to warp " + args[1] + ".");
						} else {
							sender.sendMessage("" + args[0] + " is not online.");
						}
					} else {
						sender.sendMessage("The warp " + args[1] + " does not exist.");
					}
				} else if (Utils.matchArgs("random #string #string #string", args)) {
					if (Warp.getWarps().contains(args[2]) && Warp.getWarps().contains(args[2])) {
						Location warp1 = Utils.str2loc(warpDb.getString(args[2]));
						Location warp2 = Utils.str2loc(warpDb.getString(args[3]));
						Player q = Bukkit.getPlayer(args[1]);
						if (q != null) {
							Random r = new Random();
							if (r.nextBoolean()) {
								q.teleport(warp1);
								sender.sendMessage("Teleported " + q.getName() + " to warp " + args[2] + ".");
							} else {
								q.teleport(warp2);
								sender.sendMessage("Teleported " + q.getName() + " to warp " + args[3] + ".");
							}
						} else {
							sender.sendMessage("" + args[1] + " is not online.");
						}
					} else {
						sender.sendMessage("The warp " + args[2] + " does not exist.");
					}
				} else {
					sender.sendMessage("This is an ingame-only command");
				}
			}
		}
		return true;
	}

}