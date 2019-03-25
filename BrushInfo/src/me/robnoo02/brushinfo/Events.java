package me.robnoo02.brushinfo;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerItemHeldEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.inventory.ItemStack;

@SuppressWarnings("deprecation")
public class Events implements Listener {

	private Main plugin;
	private ConfigManager config;

	public Events() {
		this.plugin = Main.getInstance();
		this.config = new ConfigManager();
	}

	@EventHandler
	public void onJoin(PlayerJoinEvent e) {
		if (e.getPlayer().hasPermission("brushinfo.toolinfo"))
			InventoryUpdater.resetOnJoin(e.getPlayer());
	}

	@EventHandler
	public void onWorldJoin(PlayerTeleportEvent e) {
		if (!e.getFrom().getWorld().getUID().equals(e.getTo().getWorld().getUID()))
			Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(plugin, () -> {
				if (e.getPlayer().hasPermission("brushinfo.toolinfo"))
					InventoryUpdater.resetOnJoin(e.getPlayer());
			}, 300);
	}

	@EventHandler(priority = EventPriority.MONITOR)
	public void onInvClick(InventoryClickEvent e) {
		if (e.getWhoClicked() instanceof Player) {
			Player p = (Player) e.getWhoClicked();
			ItemStack cursor = e.getCursor();
			if (p.hasPermission("brushinfo.toolinfo") && e.getInventory().getType().equals(InventoryType.CRAFTING)
					&& e.getSlot() >= 0 && cursor != null && (cursor).getType() != Material.AIR
					&& !cursor.getType().isBlock() && ToolUtil.isBrushTool(p, cursor))
				e.setCursor(MetaManager.ChangeMeta(p, cursor));
		}
	}

	@EventHandler
	public void onScroll(PlayerItemHeldEvent e) {
		if (e.getPlayer().hasPermission("brushinfo.toolinfo")) {
			if (config.updateOnScroll()) {
				Player p = e.getPlayer();
				ItemStack previousItem = p.getInventory().getItem(e.getPreviousSlot());
				ItemStack nextItem = p.getInventory().getItem(e.getNewSlot());
				if (ToolUtil.hasBrushInName(previousItem)) {
					updateWithDelay(p);
					return;
				}
				if (ToolUtil.hasBrushInName(nextItem))
					updateWithDelay(p);
			}
		}
	}

	public void updateWithDelay(Player p) {
		Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(plugin, () -> {
			if (p.hasPermission("brushinfo.toolinfo"))
				InventoryUpdater.updateInventory(p);
		}, 5);
	}

	@EventHandler
	public void onCommand(PlayerCommandPreprocessEvent e) {
		Player p = e.getPlayer();
		String message = e.getMessage();
		if (p.hasPermission("brushinfo.toolinfo")) {
			String[] unequip = { "/none ", "//none ", "/br none", "//br none", "/brush none", "//brush none",
					"/br info", "//br info", "/brush info", "//brush info", "/br boulder", "//br boulder",
					"/brush boulder", "//brush boudler" };
			for (int i = 0; i < unequip.length; i++) {
				String space = message + " ";
				if (space.startsWith(unequip[i])) {
					ItemStack item = p.getItemInHand();
					if (ToolUtil.hasBrushInName(item))
						InventoryUpdater.updateAndReset(p, item);
					return;
				}
			}

			String[] startingWith = { "/brush ", "//brush ", "/br ", "//br ", "/mask ", "//mask ", "/size ", "//smask ",
					"/smask ", "/material ", "/mat " };
			for (int i = 0; i < startingWith.length; i++) {
				String space = message + " ";
				if (space.startsWith(startingWith[i])) {
					// Task executed 1.5s later, to make sure FaWe's command-method is finished
					Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
						public void run() {
							InventoryUpdater.updateInventory(p);
						}
					}, 30);
				}
			}
		}
	}

	@EventHandler
	public void onDrop(PlayerDropItemEvent e) {
		ItemStack item = e.getItemDrop().getItemStack();
		if (ToolUtil.hasBrushInName(item))
			e.getItemDrop().setItemStack(new ItemStack(item.getType(), item.getAmount()));
	}

	@EventHandler
	public void onPickup(PlayerPickupItemEvent e) {
		ItemStack item = e.getItem().getItemStack();
		Player p = e.getPlayer();
		if (p.hasPermission("brushinfo.toolinfo") && item != null)
			e.getItem().setItemStack(MetaManager.ChangeMeta(p, item));
	}
}
