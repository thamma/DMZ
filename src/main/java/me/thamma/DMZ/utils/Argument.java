package me.thamma.DMZ.utils;

import static me.thamma.DMZ.utils.Utils.msg;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public abstract class Argument implements CommandRunnable {

	public boolean matchPattern(String[] args) {
		if (args.length == 0)
			return false;
		Class<?>[] pattern = this.pattern();
		List<String> args2 = interpreteArgs(args);

		List<String> toremove = new ArrayList<String>();

		for (int i = 0; i < args2.size(); i++) {
			String s = args2.get(i);
			if (i < this.name().split(" ").length && s.equalsIgnoreCase(this.name().split(" ")[i])) {
				toremove.add(args2.get(i));
			} else {
				break;
			}
		}
		if (name().length() != 0 && toremove.size() == 0)
			return false;
		args2.removeAll(toremove);
		if (args2.size() != pattern.length)
			return false;
		for (int i = 0; i < pattern.length; i++) {
			if (pattern[i].equals(Character.class)) {
				if (args2.get(i).length() != 1)
					return false;
			} else if (pattern[i].equals(Boolean.class)) {
				if (!args2.get(i).equalsIgnoreCase("true") && !args2.get(i).equalsIgnoreCase("false"))
					return false;
			} else if (pattern[i].equals(String.class)) {
			} else {
				Class<?> c = pattern[i];
				try {
					c.getMethod("valueOf", String.class).invoke(c, args2.get(i));
				} catch (Exception e) {
					return false;
				}
			}
		}
		return true;
	}

	@Override
	public boolean hasPermission(Player p) {
		return p.isOp();
	}

	@Override
	public String[] patternString() {
		return new String[] {};
	}

	@Override
	public String condition(Player p) {
		return "";
	}

	@Override
	public void run(CommandSender sender, List<String> args) {
		if (sender instanceof Player) {
			Player p = (Player) sender;
			if (!hasPermission(p)) {
				msg(p, "&cYou don't have permission to use that!");
				return;
			}
			if (condition(p).equals("")) {
				run(p, args);
			} else {
				msg(p, condition(p));
			}
		} else {
			sender.sendMessage("This is an ingame-only command!");
		}
	}

	@Override
	public Class<?>[] pattern() {
		return new Class[] {};
	}

	public static List<String> interpreteArgs(String[] args) {
		List<String> l = new ArrayList<String>();
		for (int i = 0; i < args.length; i++) {
			String s = args[i];
			if (s.startsWith("\"")) {
				String temp = s.replaceFirst("\"", "");
				while (!args[i].endsWith("\"")) {
					i++;
					temp += " " + args[i].replaceAll("\"", "");
				}
				l.add(temp);
			} else {
				l.add(s);
			}
		}
		return l;
	}

	public String possibleTabCompletition(String[] args) {
		
		return null;
	}

}

interface CommandRunnable {

	boolean hasPermission(Player p);

	String name();

	String descr();

	String[] patternString();

	Class<?>[] pattern();

	String condition(Player p);

	void run(CommandSender sender, List<String> args);

	void run(Player p, List<String> args);

}