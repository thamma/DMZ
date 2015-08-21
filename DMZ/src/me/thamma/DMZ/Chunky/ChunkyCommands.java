package me.thamma.DMZ.Chunky;

import static me.thamma.DMZ.utils.Utils.msg;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.thamma.DMZ.Chunky.ChunkyListener.Setting;
import me.thamma.DMZ.utils.Argument;
import me.thamma.DMZ.utils.CommandHandler;

public class ChunkyCommands implements CommandExecutor {

	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (cmd.getName().equalsIgnoreCase("chunky")) {
			CommandHandler ch = new CommandHandler(sender, "chunky", args).add(new Argument() {

				@Override
				public String name() {
					return "runset";
				}

				@Override
				public String descr() {
					return "Unsets your current runset";
				}

				@Override
				public void run(Player p, List<String> args) {
					if (ChunkyListener.settings.containsKey(p.getName())) {
						ChunkyListener.settings.remove(p.getName());
						msg(p, "&eRunset unset.");
					} else {
						msg(p, "&eNo runset setup for you. See &6/chunky&e.");
					}
				}

			}).add(new Argument() {

				@Override
				public String name() {
					return "runset";
				}

				@Override
				public Class<?>[] pattern() {
					return new Class<?>[] { String.class };
				}

				@Override
				public String[] patternString() {
					return new String[] { "\"<Attribute> <Value> ...\"" };
				}

				@Override
				public String descr() {
					return "Sets Attributes by walking though Chunkys";
				}

				@Override
				public void run(Player p, List<String> args) {
					String[] argArray = args.get(1).split(" ");
					if (argArray.length % 2 != 0) {
						msg(p, "&cSyntax error!");
						msg(p, "&cExample: &e/chunky runset \"Name Home Mobspawn false\".");
					} else {
						List<Setting> sl = new ArrayList<Setting>();
						for (int i = 0; i < argArray.length; i += 2) {
							try {
								sl.add(new Setting(Attribute.valueOf(argArray[i].replaceAll("\"", "")),
										argArray[i + 1].replaceFirst("\"", "")));
							} catch (Exception e) {
								msg(p, "&cNo such attribute: &6" + argArray[i]);
							}
						}
						ChunkyListener.settings.put(p.getName(), sl);
						msg(p, "&eRunset created with attributes:");
						for (Setting s : sl) {
							msg(p, "&e - &6" + s.getAttribute().name() + "&e:  &6" + s.getValue());
						}
						msg(p, "&eType &6/chunky runset &eto disable.");
					}
				}

			}).add(new Argument() {

				@Override
				public String name() {
					return "att";
				}

				@Override
				public String descr() {
					return "lists the Attributes available";
				}

				@Override
				public void run(Player p, List<String> args) {
					for (Attribute a : Attribute.values()) {
						p.sendMessage(" &e- &6" + a.name() + "&e,     default: " + a.getDefaultValue());
					}
				}

			}).add(new Argument() {

				@Override
				public String name() {
					return "att";
				}

				@Override
				public String descr() {
					return "Displays the attribute in the current Chunky";
				}

				@Override
				public Class<?>[] pattern() {
					return new Class<?>[] { String.class };
				}

				@Override
				public String[] patternString() {
					return new String[] { "Attribute" };
				}

				@Override
				public void run(Player p, List<String> args) {
					try {
						Attribute a = Attribute.valueOf(args.get(1));
						Chunky c = new Chunky(p.getLocation());
						msg(p, "&eThe attribute &6" + a.name() + " &efor this chunky is &6" + c.getAttribute(a)
								+ "&e.");
					} catch (Exception e) {
						p.sendMessage("&cNo such attribute: &6" + args.get(1));
					}
				}

			}).add(new Argument() {

				@Override
				public String name() {
					return "att";
				}

				@Override
				public String descr() {
					return "Sets the attribute in the current Chunky";
				}

				@Override
				public Class<?>[] pattern() {
					return new Class<?>[] { String.class, String.class };
				}

				@Override
				public String[] patternString() {
					return new String[] { "Attribute", "Value" };
				}

				@Override
				public void run(Player p, List<String> args) {
					try {
						Attribute a = Attribute.valueOf(args.get(1));
						Chunky c = new Chunky(p.getLocation());
						c.setAttribute(a, args.get(2));
						msg(p, "&eSet the attribute &6" + a.name() + " &efor this chunky to &6" + args.get(2) + "&e.");
					} catch (Exception e) {
						p.sendMessage("&cNo such attribute: &6" + args.get(1));
					}
				}

			}).add(new Argument() {

				@Override
				public String name() {
					return "map";
				}

				@Override
				public String descr() {
					return "Maps the Attribute of near Chunkys";
				}

				@Override
				public Class<?>[] pattern() {
					return new Class<?>[] { String.class, Integer.class };
				}

				@Override
				public String[] patternString() {
					return new String[] { "Attribute", "Range" };
				}

				@Override
				public void run(Player p, List<String> args) {
					try {
						Chunky c = new Chunky(p.getLocation());
						Attribute a = Attribute.valueOf(args.get(1));
						c.print(p, a, Integer.parseInt(args.get(2)));
					} catch (Exception e) {
						p.sendMessage("&cNo such attribute: &6" + args.get(1));
					}
				}

			}).add(new Argument() {

				@Override
				public String name() {
					return "save";
				}

				@Override
				public String descr() {
					return "saves all modified chunkys";
				}

				@Override
				public void run(Player p, List<String> args) {
					Chunky.saveAll();
				}

			});
			ch.perform();
		}
		return true;
	}

}
