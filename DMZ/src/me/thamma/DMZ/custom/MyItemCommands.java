package me.thamma.DMZ.custom;

import static me.thamma.DMZ.utils.Utils.helpPage;

import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.thamma.DMZ.utils.Utils;

public class MyItemCommands implements CommandExecutor {

	String[] helpPage = helpPage("myitem", "enchant", "Lists the available MyEnchantments", "enchant [enchant] [level]",
			"Enchants your held item", "level [level]", "Sets the current MyItem's level", "unbr(eakable)",
			"Toggles the MyItem's unbreakability", "hide(flags)", "Toggles the MyItem's HideFlags attribute");

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (sender instanceof Player) {
			Player p = (Player) sender;
			if (cmd.getName().equalsIgnoreCase("myitem")) {
				if (p.getItemInHand() != null && !p.getItemInHand().getType().equals(Material.AIR)) {
					MyItem mi = new MyItem(p.getItemInHand());
					// whether to update the item afterwards

					if (Utils.matchArgs("enchant", args)) {
						p.sendMessage(Utils.color("&eThe following &6MyEnchantments &eare available:"));
						for (MyEnchantmentType et : MyEnchantmentType.values()) {
							p.sendMessage(Utils.color("&e-" + et.getColor() + et.toString()));
						}
					} else if (Utils.matchArgs("enchant #string #int", args)) {
						try {
							MyEnchantmentType et = MyEnchantmentType.fromString(args[1]);
							MyEnchantment myench = new MyEnchantment(et, Integer.parseInt(args[2]));
							mi = mi.addEnchantment(myench);
							p.sendMessage(Utils.color("&eAdded MyEnchantment " + et + "&e."));
						} catch (Exception e) {
							p.sendMessage(Utils.color("&cNo such enchant"));
						}
					} else if (Utils.matchArgs("setlevel #int", args)) {
						int i = Integer.parseInt(args[1]);
						mi = mi.setLevel(i);
						p.sendMessage(Utils.color("&eLevel set to &6" + i + "&e."));
					} else if (Utils.matchArgs("unbreakable", args) || Utils.matchArgs("unbr", args)) {
						boolean unbr = !mi.isUnbreakable();
						mi = mi.setUnbreakable(unbr);
						p.sendMessage(Utils.color("&eUnbreakability set to &6" + unbr + "&e."));
					} else if (Utils.matchArgs("hideflags", args) || Utils.matchArgs("hide", args)) {
						boolean hide = !mi.isHideFlags();
						mi = mi.setHideFlags(hide);
						p.sendMessage(Utils.color("&eHideFlags attribute set to &6" + hide + "&e."));
					} else {
						p.sendMessage(helpPage);
					}
					p.setItemInHand(mi.getItemStack());
				} else {
					p.sendMessage(Utils.color("Error: &cYou must hold an item in your hands"));
					p.sendMessage(helpPage);
				}
			}
		} else {
			sender.sendMessage("Ingame only command. You need to be holding an item.");
		}
		return true;
	}

}
