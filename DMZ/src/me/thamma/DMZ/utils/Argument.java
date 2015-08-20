package me.thamma.DMZ.utils;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public abstract class Argument implements CommandRunnable {

	public boolean matchPattern(String[] args) {
		Class<?>[] pattern = this.pattern();
		int arg0 = 0;
		try {
			while (arg0 < this.name().split(" ").length && arg0 < args[arg0].length()
					&& this.name().split(" ")[arg0].equalsIgnoreCase(args[arg0]))
				arg0++;
			if (args.length - arg0 != pattern.length)
				return false;
			int pat0 = 0;
			for (int i = arg0; i < args.length; i++) {
				if (("" + args[i]).startsWith("\"")) {
					if (!pattern[pat0].equals(String.class))
						return false;
					while (!args[i].endsWith("\"")) {
						i++;
					}
				} else if (pattern[pat0].equals(String.class)) {
					System.out.println("String, okay");
				} else if (pattern[pat0].equals(Boolean.class)) {
					if (!args[i].equalsIgnoreCase("false") && !args[i].equalsIgnoreCase("true"))
						return false;
				} else if (pattern[pat0].equals(Character.class)) {
					if (args[i].length() != 1)
						return false;
				} else {
					Class<?> c = pattern[pat0];
					try {
						c.getMethod("valueOf", String.class).invoke(c, args[i]);
					} catch (Exception e) {
						return false;
					}
				}
				System.out.println("parsed " + i + "  " + pattern[pat0]);
				pat0++;
			}
		} catch (Exception e) {
			return false;
		}
		return true;
	}

	public String[] patternString() {
		return new String[] {};
	}

	public String condition(Player p) {
		return "";
	}

	public void run(CommandSender sender) {
		if (sender instanceof Player) {
			Player p = (Player) sender;
			if (condition(p).equals("")) {
				run(p);
			} else {
				p.sendMessage(Utils.color(condition(p)));
			}
		} else {
			sender.sendMessage("This is an ingame-only command!");
		}
	}

	public Class<?>[] pattern() {
		return new Class[] {};
	}

}

interface CommandRunnable {

	String name();

	String descr();

	String[] patternString();

	Class<?>[] pattern();

	String condition(Player p);

	void run(CommandSender sender);

	void run(Player p);

}