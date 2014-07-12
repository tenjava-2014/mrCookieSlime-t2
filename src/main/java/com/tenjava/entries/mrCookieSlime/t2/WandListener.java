package com.tenjava.entries.mrCookieSlime.t2;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Effect;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class WandListener implements Listener {
	
	List<UUID> opening = new ArrayList<UUID>();
	TenJava plugin;
	
	public WandListener(TenJava plugin) {
		this.plugin = plugin;
		plugin.getServer().getPluginManager().registerEvents(this, plugin);
	}
	
	@EventHandler
	public void onWandUse(PlayerInteractEvent e) {
		if (e.getAction() == Action.RIGHT_CLICK_AIR || e.getAction() == Action.RIGHT_CLICK_BLOCK) {
			Player p = e.getPlayer();
			ItemStack wand = e.getItem();
			if (wand.getType() == Material.STICK && wand.getDurability() == 16) {
				if (wand.hasItemMeta()) {
					if (wand.getItemMeta().hasLore()) {
						if (wand.getItemMeta().getLore().size() >= MagicItems.INFUSABLE_WAND.getItemMeta().getLore().size()) {
							if (p.isSneaking()) {
								
								List<Infusion> infusions = new ArrayList<Infusion>();
								
								if (!wand.getItemMeta().getLore().get(5).equalsIgnoreCase(ChatColor.GRAY + "Infused with: Nothing")) {
									for (String infusion: ChatColor.stripColor(wand.getItemMeta().getLore().get(5)).replace("Infused with: ", "").split(" & ")) {
										infusions.add(Infusion.valueOf(infusion.toUpperCase().replace(" ", "_")));
									}
								}
								
								ItemMeta im = wand.getItemMeta();
								List<String> lore = im.getLore();
								lore.set(5, ChatColor.GRAY + "Infused with: Nothing");
								im.setLore(lore);
								wand.setItemMeta(im);
								p.setItemInHand(wand);
								
								Inventory shardmenu = Bukkit.createInventory(null, 9, "Put Meteor Shards in me ");
								
								for (Infusion infusion: infusions) {
									shardmenu.addItem(infusion.getShard());
								}
								
								p.openInventory(shardmenu);
								opening.add(p.getUniqueId());
							}
							else {
								//do stuff
							}
						}
					}
				}
			}
		}
	}
	
	@EventHandler
	public void onWandClose(InventoryCloseEvent e) {
		if (opening.contains(e.getPlayer().getUniqueId())) {
			Player p = (Player) e.getPlayer();
			List<Infusion> infusions = new ArrayList<Infusion>();
			
			for (ItemStack item: e.getInventory().getContents()) {
				if (item != null) {
					if (item.getType() == Material.FLINT && item.getDurability() > 16) {
						infusions.add(Infusion.valueOf(ChatColor.stripColor(item.getItemMeta().getDisplayName().replace("Meteor Shard - ", ""))));
					}
					else {
						e.getPlayer().getWorld().playEffect(e.getPlayer().getLocation(), Effect.SMOKE, 1);
						e.getPlayer().getWorld().playSound(e.getPlayer().getLocation(), Sound.FIZZ, 1, 1);
						p.sendMessage(plugin.getTranslation("fail.wrongItems"));
						return;
					}
				}
			}
			
			e.getPlayer().getWorld().playEffect(e.getPlayer().getLocation(), Effect.ENDER_SIGNAL, 1);
			e.getPlayer().getWorld().playEffect(e.getPlayer().getLocation(), Effect.MOBSPAWNER_FLAMES, 1);
			e.getPlayer().getWorld().playSound(e.getPlayer().getLocation(), Sound.LEVEL_UP, 1, 1);
			
			opening.remove(e.getPlayer().getUniqueId());
		}
	}

}
