package me.thamma.DMZ.data;

import static me.thamma.DMZ.utils.Utils.msg;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Chest;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.Villager;
import org.bukkit.entity.Zombie;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import me.thamma.DMZ.custom.MyItem;
import me.thamma.DMZ.utils.Argument;
import me.thamma.DMZ.utils.CommandHandler;
import me.thamma.DMZ.utils.Utils;

/**
 * Created by pc on 11.06.2015.
 */
public class DataCommands implements CommandExecutor, TabExecutor {

	final static CommandHandler ch = new CommandHandler("data").add(new Argument() {

		@Override
		public String name() {
			return "setname";
		}

		@Override
		public String descr() {
			return "sets the name of your held item";
		}

		@Override
		public String condition(Player p) {
			return inHandCondition(p);
		}

		@Override
		public void run(Player p, List<String> args) {
			new ChatTask(p) {
				@Override
				public int getAmount() {
					return 1;
				}

				@Override
				public void run(ArrayList<String> l, Player p) {
					MyItem mi = new MyItem(p.getItemInHand());
					p.setItemInHand(mi.setName(l.get(0)).getItemStack());

				}
			};
		}

	}).add(new Argument() {

		@Override
		public String name() {
			return "barr";
		}

		@Override
		public String descr() {
			return "makes barriers around you visible";
		}

		@Override
		public String[] patternString() {
			return new String[] { "radius", "visible" };
		}

		@Override
		public Class<?>[] pattern() {
			return new Class[] { Integer.class, Boolean.class };
		}

		@SuppressWarnings("deprecation")
		@Override
		public void run(Player p, List<String> args) {
			int range = Integer.valueOf(args.get(1));
			Boolean show = Boolean.valueOf(args.get(2));
			Location loc = p.getLocation();
			for (int x = (int) (loc.getX() - range / 2); x < loc.getX() + range / 2; x++)
				for (int y = (int) (loc.getY() - range / 2); y < loc.getY() + range / 2; y++)
					for (int z = (int) (loc.getZ() - range / 2); z < loc.getZ() + range / 2; z++)
						if ((new Location(loc.getWorld(), x, y, z)).getBlock().getType() != null
								&& (new Location(loc.getWorld(), x, y, z)).getBlock().getType()
										.equals(Material.BARRIER))
							if (!show) {
								p.sendBlockChange((new Location(loc.getWorld(), x, y, z)), Material.BARRIER, (byte) 0);
							} else {
								p.sendBlockChange((new Location(loc.getWorld(), x, y, z)), Material.BEDROCK, (byte) 0);
							}
		}
	}).add(new Argument() {

		@Override
		public String name() {
			return "ac";
		}

		@Override
		public String descr() {
			return "opens the remote admin chest";
		}

		@Override
		public void run(Player p, List<String> args) {
			Location loc = new Location(Bukkit.getWorld("world"), 0, 0, 0);
			if (loc.getBlock().getType().equals(Material.CHEST)) {
				Chest c = (Chest) ((loc.getBlock().getState()));
				p.openInventory(c.getBlockInventory());
			} else {
				msg(p, "&cNo chest Located at (world, 0, 0, 0)");
			}
		}

	}).add(new Argument() {

		@Override
		public String name() {
			return "powertool";
		}

		@Override
		public String descr() {
			return "makes your held item a powertool";
		}

		@Override
		public String condition(Player p) {
			return inHandCondition(p);
		}

		@Override
		public void run(Player p, List<String> args) {
			new ChatTask(p) {
				@Override
				public void run(ArrayList<String> l, Player p) {
					MyItem mi = new MyItem(p.getItemInHand());
					p.setItemInHand(mi.setName("&6Powertool").setLore(l).getItemStack());
				}
			};
		}

	}).add(new Argument() {

		@Override
		public String name() {
			return "powertool cycle";
		}

		@Override
		public String descr() {
			return "makes your held item a cycling powertool";
		}

		@Override
		public String condition(Player p) {
			return inHandCondition(p);
		}

		@Override
		public void run(Player p, List<String> args) {
			new ChatTask(p) {
				@Override
				public void run(ArrayList<String> l, Player p) {
					MyItem mi = new MyItem(p.getItemInHand());
					p.setItemInHand(
							mi.setName("&6Powertool").addLore(new String[] { "cycle" }).addLore(l).getItemStack());
				}
			};
		}

	}).add(new Argument() {

		@Override
		public String name() {
			return "setlore";
		}

		@Override
		public String descr() {
			return "sets the lore of your item";
		}

		@Override
		public String condition(Player p) {
			return inHandCondition(p);
		}

		@Override
		public void run(Player p, List<String> args) {
			new ChatTask(p) {
				@Override
				public void run(ArrayList<String> l, Player p) {
					MyItem mi = new MyItem(p.getItemInHand());
					p.setItemInHand(mi.setRawLore(l).getItemStack());
				}
			};
		}

	}).add(new Argument() {

		@Override
		public String name() {
			return "addlore";
		}

		@Override
		public String descr() {
			return "Adds to the lore of your item";
		}

		@Override
		public String condition(Player p) {
			return inHandCondition(p);
		}

		@Override
		public void run(Player p, List<String> args) {
			new ChatTask(p) {
				@Override
				public void run(ArrayList<String> l, Player p) {
					MyItem mi = new MyItem(p.getItemInHand());
					p.setItemInHand(mi.addLore(l).getItemStack());
				}
			};
		}

	}).add(new Argument() {

		@Override
		public String name() {
			return "medal";
		}

		@Override
		public String descr() {
			return "creates a medal";
		}

		@Override
		public void run(Player p, List<String> args) {
			new ChatTask(p) {
				@Override
				public int getAmount() {
					return 1;
				}

				@Override
				public void run(ArrayList<String> l, Player p) {
					MyItem mi = new MyItem(Material.NAME_TAG);
					p.getInventory().addItem(mi.setName(Utils.color("&6Medal")).setLore(l).getItemStack());
				}
			};
		}

	}).add(new Argument() {

		@Override
		public String name() {
			return "test";
		}

		@Override
		public String descr() {
			return "Testing purposes. Do not use.";
		}

		@Override
		public void run(Player p, List<String> args) {
			Location loc = p.getLocation();
			Zombie z = (Zombie) p.getWorld().spawnEntity(loc, EntityType.ZOMBIE);
			Villager v = (Villager) p.getWorld().spawnEntity(loc.add(0, 0, 20), EntityType.VILLAGER);
			v.setNoDamageTicks(Integer.MAX_VALUE);
			v.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, Integer.MAX_VALUE, 150));
			v.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, Integer.MAX_VALUE, 0));
			z.setTarget(v);
		}

	});

	protected static String inHandCondition(Player p) {
		if (p.getItemInHand() != null && !p.getItemInHand().getType().equals(Material.AIR))
			return "";
		return "&cYou must be holding an item to modify!";
	}

	@Override
	public boolean onCommand(CommandSender paramCommandSender, Command paramCommand, String paramString,
			String[] args) {
		if (paramCommand.getName().toLowerCase().equalsIgnoreCase(ch.getCommand()))
			ch.performCommand(paramCommandSender, args);
		return true;
	}

	@Override
	public List<String> onTabComplete(CommandSender arg0, Command arg1, String arg2, String[] arg3) {
		return ch.tabComplete(arg3);
	}
}
