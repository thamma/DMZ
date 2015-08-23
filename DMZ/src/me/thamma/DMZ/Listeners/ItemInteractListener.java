package me.thamma.DMZ.Listeners;

import java.util.Random;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftEntity;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.Villager;
import org.bukkit.entity.Zombie;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import me.thamma.DMZ.core.Main;
import me.thamma.DMZ.custom.MyItem;
import me.thamma.DMZ.utils.ItemTask;
import me.thamma.DMZ.utils.SelfCancellingTask;
import me.thamma.DMZ.utils.TitlesAPI;
import net.minecraft.server.v1_8_R3.EntityLiving;
import net.minecraft.server.v1_8_R3.NBTTagCompound;

public class ItemInteractListener implements Listener {

	private Main plugin;

	public ItemInteractListener(Main arg0) {
		this.plugin = arg0;
	}

	@EventHandler
	public void onInteract(PlayerInteractEvent e) {
		Player p = e.getPlayer();
		if (p.getItemInHand() != null && !p.getItemInHand().getType().equals(Material.AIR)) {
			MyItem mi = new MyItem(p.getItemInHand());
			ItemTask it = getTask(mi);
			if (it != null) {
				if (e.getAction().equals(Action.RIGHT_CLICK_AIR) || e.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
					it.onRightClick(e);
				} else
					if (e.getAction().equals(Action.LEFT_CLICK_AIR) || e.getAction().equals(Action.LEFT_CLICK_BLOCK)) {
					it.onLeftClick(e);
				}
			}
		}
	}

	@EventHandler
	public void onPlace(BlockPlaceEvent e) {
		if (e.getItemInHand() != null && !e.getItemInHand().getType().equals(Material.AIR)) {
			MyItem mi = new MyItem(e.getItemInHand());
			ItemTask it = getTask(mi);
			if (it != null) {
				it.onBlockPlace(e);
			}
		}
	}

	public final ItemTask[] tasks = new ItemTask[] { new ItemTask(new MyItem(Material.SKULL_ITEM).setName("Goldbag")) {
		@Override
		public void onRightClick(PlayerInteractEvent e) {
			Player p = e.getPlayer();
			e.setCancelled(true);
			int amount = goldBagAmount(new MyItem(p.getItemInHand()));
			giveGold(p, amount);
			consume(p);
			TitlesAPI.sendActionBar(p, "&eThe goldbag contained &6" + amount + " &egold.");
		}
	}, new ItemTask(new MyItem(Material.REDSTONE_TORCH_ON).setName("Flare")) {
		@SuppressWarnings("deprecation")
		@Override
		public void onBlockPlace(final BlockPlaceEvent e) {
			TitlesAPI.sendActionBar(e.getPlayer(), "&6Flare&e activated. It will attract nearby zombies!");
			SelfCancellingTask sct = new SelfCancellingTask(8) {

				Villager v;

				@Override
				public void runBefore() {
					v = (Villager) e.getBlock().getWorld().spawnCreature(e.getBlock().getLocation().add(0.5, 0, 0.5),
							EntityType.VILLAGER);
					v.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, Integer.MAX_VALUE, 0, false));
					v.setNoDamageTicks(Integer.MAX_VALUE);
					setNoAI(v);
				}

				@Override
				public void runAfter() {
					v.remove();
					e.getBlock().setType(Material.AIR);
				}

				@Override
				public void myrun() {
					for (Entity en : v.getNearbyEntities(20D, 20D, 20D)) {
						if (en instanceof Zombie) {
							Zombie z = (Zombie) en;
							z.setTarget(v);
						}
					}

				}
			};
			sct.runTaskTimerAsynchronously(plugin, 0L, 20L);
		}
	} };

	public void setNoAI(Entity e) {

		net.minecraft.server.v1_8_R3.Entity nmsEntity = ((CraftEntity) e).getHandle();

		NBTTagCompound tag = new NBTTagCompound();

		nmsEntity.c(tag);

		tag.setBoolean("NoAI", true);

		EntityLiving el = (EntityLiving) nmsEntity;
		el.a(tag);
	}

	public ItemTask getTask(MyItem arg0) {
		for (ItemTask it : tasks) {
			if (arg0.isSimilar(it.getItem()))
				return it;
		}
		return null;
	}

	private void giveGold(Player p, int amount) {
		int gold4 = amount / 6561;
		amount %= 6561;
		int gold3 = amount / 729;
		amount %= 729;
		int gold2 = amount / 81;
		amount %= 81;
		int gold1 = amount / 9;
		amount %= 9;
		int gold0 = amount;
		if (gold0 != 0)
			p.getInventory().addItem(new MyItem(Material.GOLD_NUGGET, gold0).setName("&bGold")
					.setLore(new String[] { "&eValue: 1" }).getItemStack());
		if (gold1 != 0)
			p.getInventory().addItem(new MyItem(Material.GOLD_INGOT, gold1).setName("&bGold")
					.setLore(new String[] { "&eValue: 9" }).getItemStack());
		if (gold2 != 0)
			p.getInventory().addItem(new MyItem(Material.GOLD_BLOCK, gold2).setName("&bGold")
					.setLore(new String[] { "&eValue: 81" }).getItemStack());
		if (gold3 != 0)
			p.getInventory().addItem(new MyItem(Material.EMERALD, gold3).setName("&bEmerald")
					.setLore(new String[] { "&eValue: 729" }).getItemStack());
		if (gold4 != 0)
			p.getInventory().addItem(new MyItem(Material.EMERALD_BLOCK, gold4).setName("&bEmerald")
					.setLore(new String[] { "&eValue: 6561" }).getItemStack());
	}

	private int goldBagAmount(MyItem mi) {
		String size = ChatColor.stripColor(mi.getLore().get(0)).replaceAll("Size: ", "");
		Random r = new Random();
		if (size.equals("S")) {
			return r.nextInt(7) + 1;
		} else if (size.equals("M")) {
			return r.nextInt(11) + 1;
		} else if (size.equals("L")) {
			return r.nextInt(16) + 1;
		}
		return 0;
	}

	private void consume(Player p) {
		MyItem mi = new MyItem(p.getItemInHand());
		p.setItemInHand(mi.setAmount(mi.getAmount() - 1).getItemStack());
	}

}
