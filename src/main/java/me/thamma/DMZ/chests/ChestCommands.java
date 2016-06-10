package me.thamma.DMZ.chests;

import static me.thamma.DMZ.utils.Utils.msg;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.Chest;
import org.bukkit.block.Sign;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import me.thamma.DMZ.utils.Argument;
import me.thamma.DMZ.utils.CommandHandler;

public class ChestCommands47 {

	final static CommandHandler ch = new CommandHandler("chest").add(new Argument() {

		@Override
		public String name() {
			return "create";
		}

		@Override
		public String descr() {
			return "creates a respawning chest";
		}

		@SuppressWarnings("deprecation")
		@Override
		public void run(Player p, List<String> args) {
			int amount = Integer.parseInt(args.get(1));
			int time = Integer.parseInt(args.get(2));

			Block target = p.getTargetBlock(transparents, 10);
			if (MyChest.isChest(target.getLocation())) {
				msg(p, "This is already a respawning chest");
			} else {

				Chest chestBlock = (Chest) target.getState();
				boolean empty = true;
				for (ItemStack item : chestBlock.getInventory().getContents()) {
					if (item != null) {
						empty = false;
						break;
					}
				}
				if (empty) {
					msg(p, "&cYou cannot make an empty chest a respawning chest!");
				} else {
					// create chest
					MyChest vChest = new MyChest(chestBlock.getLocation(), time, amount);
					Location ref = vChest.getRemoteLocation();

					ref.getBlock().setType(Material.CHEST);
					ref.getBlock().setData((byte) 5);
					ref.getBlock().getRelative(BlockFace.EAST).setType(Material.WALL_SIGN);
					ref.getBlock().getRelative(BlockFace.EAST).setData((byte) 5);
					Sign s = (Sign) ref.getBlock().getRelative(BlockFace.EAST).getState();
					s.setLine(0, "Respawning Chest");
					s.setLine(1, "id: " + vChest.getId());
					s.setLine(2, "amount: " + vChest.getAmount());
					s.setLine(3, "time: " + vChest.getTime());
					s.update();

					((org.bukkit.block.Chest) ref.getBlock().getState()).getBlockInventory()
							.setContents(chestBlock.getBlockInventory().getContents());
					vChest.fill();
					msg(p, "&eRespawning chest created");
				}
			}
		}

		@Override
		public Class<?>[] pattern() {
			return new Class<?>[] { Integer.class, Integer.class };
		}

		@Override
		public String[] patternString() {
			return new String[] { "amount", "time" };
		}

		@Override
		public String condition(Player p) {
			return lookAtChest(p);
		}

	}).add(new Argument() {

		@Override
		public String name() {
			return "edit";
		}

		@Override
		public String descr() {
			return "edits the content of a respawning chest";
		}

		@Override
		public void run(Player p, List<String> args) {

			Block target = p.getTargetBlock(transparents, 10);
			if (MyChest.isChest(target)) {
				p.openInventory(MyChest.getChest(target.getLocation()).getRemoteChest().getInventory());
			} else {
				msg(p, "&cThis is no respawning chest.");
			}
		}

		@Override
		public String condition(Player p) {
			return lookAtChest(p);
		}

	}).add(new Argument() {

		@Override
		public String name() {
			return "Remove";
		}

		@Override
		public String descr() {
			return "Removes a respawning chest";
		}

		@Override
		public void run(Player p, List<String> args) {
			Block target = p.getTargetBlock(transparents, 10);
			if (MyChest.isChest(target.getLocation())) {
				MyChest c = MyChest.getChest(target.getLocation());
				msg(p, "Respawning chest (id " + c.getId() + ") deleted.");
				c.delete();
			} else {
				msg(p, "&cThis is no respawning chest");
			}
		}

		@Override
		public String condition(Player p) {
			return lookAtChest(p);
		}

	}).add(new Argument() {

		@Override
		public String name() {
			return "respawn";
		}

		@Override
		public String descr() {
			return "Respawns all respawning chests";
		}

		@Override
		public void run(Player p, List<String> args) {
			MyChest.respawnAll();
			msg(p, "&eRespawning chests respawned.");
		}

	});

	@Override
	public boolean onCommand(CommandSender paramCommandSender, Command paramCommand, String paramString,
			String[] args) {
		if (paramCommand.getName().equalsIgnoreCase("chest"))
			ch.performCommand(paramCommandSender, args);
		return true;
	}

	protected static String lookAtChest(Player p) {
		Block target = p.getTargetBlock(transparents, 10);

		if (((target != null) && (target.getType().equals(Material.CHEST)))
				|| (target.getType().equals(Material.TRAPPED_CHEST))) {
			return "";
		}
		return "&cYou must be facing a chest!";
	}

	@SuppressWarnings("serial")
	private final static Set<Material> transparents = new HashSet<Material>() {
		{
			add(Material.AIR);
			add(Material.TORCH);
			add(Material.WATER);
			add(Material.STATIONARY_WATER);
			add(Material.LAVA);
			add(Material.STATIONARY_LAVA);
		}
	};

}
