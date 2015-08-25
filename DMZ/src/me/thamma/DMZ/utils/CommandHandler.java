package me.thamma.DMZ.utils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.bukkit.command.CommandSender;

public class CommandHandler {

	private List<Argument> patterns;
	private String cmd;

	public CommandHandler(String cmd) {
		this.patterns = new ArrayList<Argument>();
		this.cmd = cmd;
	}

	public void performCommand(CommandSender sender, String[] args) {
		for (Argument a : patterns) {
			if (a.matchPattern(args)) {
				System.out.println(a.descr());
				a.run(sender, Argument.interpreteArgs(args));
				return;
			}
		}
		if (sender != null) {
			sender.sendMessage(helpPage());
		} else {
			for (String s : helpPage())
				System.out.println(s);
		}
	}

	public CommandHandler add(Argument a) {
		this.patterns.add(a);
		return this;
	}

	public String[] helpPage() {
		String title = Utils.color("&e--------- &6" + this.cmd + "&e helppage --------");
		String[] out = new String[patterns.size() + 1];
		Collections.sort(patterns, new ArgumentSorter());
		out[0] = title;
		for (int i = 0; i < patterns.size(); i++) {
			Argument arg = patterns.get(i);
			String patstr = "";
			if (arg.patternString().length == 0) {
				for (Class<?> c : arg.pattern())
					patstr += "[" + c.getSimpleName() + "] ";
			} else {
				for (String s : arg.patternString())
					patstr += "[" + StringUtils.capitalize(s.toLowerCase()) + "] ";
			}
			out[i + 1] = Utils.color("&e/" + this.cmd + " &6" + arg.name().toLowerCase() + " " + patstr + "&e  -  "
					+ StringUtils.capitalize(arg.descr()));
		}
		return out;
	}
}

class ArgumentSorter implements Comparator<Argument> {

	@Override
	public int compare(Argument arg0, Argument arg1) {
		return arg0.name().toLowerCase().compareTo(arg1.name().toLowerCase());
	}

}