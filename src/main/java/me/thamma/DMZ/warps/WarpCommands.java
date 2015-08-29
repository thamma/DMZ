package me.thamma.DMZ.warps;

import static me.thamma.DMZ.utils.Utils.*;

import me.thamma.DMZ.utils.Argument;
import me.thamma.DMZ.utils.CommandHandler;
import me.thamma.DMZ.utils.Utils;

import static me.thamma.DMZ.utils.Database.warpDb;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class WarpCommands implements CommandExecutor {

	final static CommandHandler ch = new CommandHandler("warp").add(new Argument() {

		@Override
		public String name() {
			return "list";
		}

		@Override
		public String descr() {
			return "lists the available warps";
		}

		@Override
		public void run(Player p, List<String> args) {
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
		}

	}).add(new Argument() {

		@Override
		public String name() {
			return "set";
		}

		@Override
		public String descr() {
			return "sets/moves a new warp";
		}

		@Override
		public Class<?>[] pattern() {
			return new Class<?>[] { String.class };
		}

		@Override
		public String[] patternString() {
			return new String[] { "warpname" };
		}

		@Override
		public void run(Player p, List<String> args) {
			if (Warp.getWarps().contains(args.get(1).toLowerCase())) {
				msg(p, "&eWarp &6" + args.get(1).toLowerCase() + "&e moved.");
			} else {
				msg(p, "&eWarp &6" + args.get(1).toLowerCase() + "&e set.");
			}
			warpDb.set(args.get(1).toLowerCase(), Utils.loc2str(p.getLocation()));
		}

	}).add(new Argument() {

		@Override
		public String name() {
			return "del";
		}

		@Override
		public String descr() {
			return "deletes a warp";
		}

		@Override
		public Class<?>[] pattern() {
			return new Class<?>[] { String.class };
		}

		@Override
		public String[] patternString() {
			return new String[] { "warpname" };
		}

		@Override
		public void run(Player p, List<String> args) {
			if (Warp.getWarps().contains(args.get(1).toLowerCase())) {
				warpDb.set(args.get(1).toLowerCase(), null);
				msg(p, "&eWarp &6" + args.get(1) + "&e deleted.");
			} else {
				msg(p, "&eThe warp &6" + args.get(1) + "&e does not exist.");
			}
		}

	}).add(new Argument() {

		@Override
		public String name() {
			return "to";
		}

		@Override
		public String descr() {
			return "Teleports you to a warp";
		}

		@Override
		public Class<?>[] pattern() {
			return new Class<?>[] { String.class };
		}

		@Override
		public String[] patternString() {
			return new String[] { "warpname" };
		}

		@Override
		public void run(Player p, List<String> args) {
			if (Warp.getWarps().contains(args.get(1).toLowerCase())) {
				Location loc = Utils.str2loc(warpDb.getString(args.get(1)));
				p.teleport(loc);
			} else {
				msg(p, "&eThe warp &6" + args.get(1) + "&e does not exist.");
			}
		}

	}).add(new Argument() {

		@Override
		public String name() {
			return "to";
		}

		@Override
		public String descr() {
			return "Teleports a player to a warp";
		}

		@Override
		public Class<?>[] pattern() {
			return new Class<?>[] { String.class };
		}

		@Override
		public String[] patternString() {
			return new String[] { "player", "warpname" };
		}

		@Override
		public void run(Player p, List<String> args) {
			run((CommandSender) p, args);
		}

		@Override
		public void run(CommandSender p, List<String> args) {
			if (Warp.getWarps().contains(args.get(2))) {
				Location loc = Utils.str2loc(warpDb.getString(args.get(2)));
				Player q = Bukkit.getPlayer(args.get(1));
				if (q != null) {
					q.teleport(loc);
					msg(p, "&eTeleported &6" + q.getName() + " &eto warp &6" + args.get(2) + "&e.");
				} else {
					msg(p, "&6" + args.get(1) + "&e is not online.");
				}
			} else {
				msg(p, "&eThe warp &6" + args.get(2) + "&e does not exist.");
			}
		}

	}).add(new Argument() {

		@Override
		public String name() {
			return "random";
		}

		@Override
		public String descr() {
			return "Teleports a player to a warp";
		}

		@Override
		public Class<?>[] pattern() {
			return new Class<?>[] { String.class, String.class };
		}

		@Override
		public String[] patternString() {
			return new String[] { "player", "warp[]" };
		}

		@Override
		public void run(Player p, List<String> args) {
			run((CommandSender) p, args);
		}

		@Override
		public void run(CommandSender p, List<String> args) {
			args.remove(0); // "random"
			String qname = args.remove(0);
			Player q = Bukkit.getPlayer(qname);
			if (q != null) {
				List<String> warps = new ArrayList<String>(Arrays.asList(args.get(0).split(" ")));
				for (String s : warps)
					if (!Warp.exists(s)) {
						msg(p, "&cWarp &6" + s + " &cdoes not exist.");
						return;
					}
				Collections.shuffle(warps);
				q.teleport(Warp.getWarp(warps.get(0)));
				msg(p, "&eTeleported &6" + q.getName() + " &eto &6" + warps.get(0) + "&e.");
			} else {
				p.sendMessage("&6" + qname + " &eis not online.");
			}
		}

	});

	@Override
	public boolean onCommand(CommandSender arg0, Command arg1, String arg2, String[] arg3) {
		if (arg1.getName().equalsIgnoreCase("warp"))
			ch.performCommand(arg0, arg3);
		return true;
	}

}