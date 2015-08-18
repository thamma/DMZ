package me.thamma.DMZ.chests;

import java.util.HashSet;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.Sign;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import me.thamma.DMZ.utils.Utils;

public class ChestCommands implements CommandExecutor {

	public static String[] helpPage(String command, String... args) {
		String title = Utils.color("&e--- &6" + command + " Helppage &e---");
		if (args.length % 2 != 0) {
			return new String[] { title };
		}
		String[] out = new String[args.length / 2 + 1];
		out[0] = title;
		for (int i = 0; i < args.length / 2; i++) {
			out[i + 1] = Utils.color("&e/" + command + " &6" + args[2 * i] + " &e - " + args[2 * i + 1]);
		}
		return out;

	}

	@SuppressWarnings({ "serial", "deprecation" })
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if ((cmd.getName().equalsIgnoreCase("chest")) && ((sender instanceof Player))) {
			Player p = (Player) sender;
			if (Utils.matchArgs("respawn", args)) {
				Chest.respawnAll();
				p.sendMessage(Utils.color("&eRespawned all respawning chests."));
			} else if (Utils.matchArgs("create #int #int", args)) {
				int amount = Integer.parseInt(args[1]);
				int time = Integer.parseInt(args[2]);
				Block target = p.getTargetBlock(new HashSet<Material>() {
					{
						add(Material.AIR);
					}
				}, 10);

				if (((target != null) && (target.getType().equals(Material.CHEST)))
						|| (target.getType().equals(Material.TRAPPED_CHEST))) {

					if (Chest.isChest(target.getLocation())) {
						p.sendMessage("This is already a respawning chest");
					} else {

						org.bukkit.block.Chest chestBlock = (org.bukkit.block.Chest) target.getState();
						boolean empty = true;
						for (ItemStack item : chestBlock.getInventory().getContents()) {
							if (item != null) {
								empty = false;
								break;
							}
						}
						if (empty) {
							p.sendMessage("This chest is empty, so it wouldn't respawn anything!");
						} else {
							// create chest
							me.thamma.DMZ.chests.Chest vChest = new Chest(chestBlock.getLocation(), time, amount);
							Location ref = vChest.getRemoteLocation();

							ref.getBlock().setType(Material.CHEST);
							ref.getBlock().setData((byte) 5);
							ref.getBlock().getRelative(BlockFace.EAST).setType(Material.WALL_SIGN);
							ref.getBlock().getRelative(BlockFace.EAST).setData((byte) 5);
							Sign s = (Sign) ref.getBlock().getRelative(BlockFace.EAST).getState();
							s.setLine(1, "id: " + vChest.getId());
							s.setLine(2, "amount: " + vChest.getAmount());
							s.setLine(3, "time: " + vChest.getTime());
							s.update();

							((org.bukkit.block.Chest) ref.getBlock().getState()).getBlockInventory()
									.setContents(chestBlock.getBlockInventory().getContents());
							vChest.fill();
							p.sendMessage("Respawning chest created");
						}
					}
				} else {
					p.sendMessage(ChatColor.RED + "You must be facing a chest.");
				}
			} else if (Utils.matchArgs("edit", args)) {
				Block target = p.getTargetBlock(new HashSet<Material>() {
					{
						add(Material.AIR);
					}
				}, 10);
				if (((target != null) && (target.getType().equals(Material.CHEST)))
						|| (target.getType().equals(Material.TRAPPED_CHEST))) {
					if (Chest.isChest(target)) {
						p.openInventory(Chest.getChest(target.getLocation()).getRemoteChest().getInventory());
					} else {
						p.sendMessage(org.bukkit.ChatColor.RED + "This is no respawning chest.");
					}
				} else {
					p.sendMessage(ChatColor.RED + "You must be facing a chest.");
				}
			} else if (Utils.matchArgs("delete", args) || Utils.matchArgs("remove", args)) {
				Block target = p.getTargetBlock(new HashSet<Material>() {
					{
						add(Material.AIR);
					}
				}, 10);
				if (((target != null) && (target.getType().equals(Material.CHEST)))
						|| (target.getType().equals(Material.TRAPPED_CHEST))) {
					if (Chest.isChest(target.getLocation())) {
						Chest c = Chest.getChest(target.getLocation());
						p.sendMessage("Respawning chest (id " + c.getId() + ") deleted.");
						c.delete();
					} else {
						p.sendMessage(org.bukkit.ChatColor.RED + "This is no respawning chest.");
					}
				} else {
					p.sendMessage("You must be facing a chest.");
				}

			} else {
				p.sendMessage(helpPage("chest", "create [amount] [time]", "Creates a respawning chest.", "delete",
						"Deletes the chest you are facing.", "edit", "Edits the chest you are facing.", "respawn",
						"Respawns all respawning chests."));

			}
		}
		return true;
	}

}
