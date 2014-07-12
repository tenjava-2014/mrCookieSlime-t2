package com.tenjava.entries.mrCookieSlime.t2;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Effect;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.EnderPearl;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Fireball;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Snowball;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class WandListener implements Listener {
	
	List<UUID> opening = new ArrayList<UUID>();
	
	Map<UUID, String> infusedDamager = new HashMap<UUID, String>();
	
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
			if (wand != null) {
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
									List<Infusion> infusions = new ArrayList<Infusion>();
									
									if (!wand.getItemMeta().getLore().get(5).equalsIgnoreCase(ChatColor.GRAY + "Infused with: Nothing")) {
										for (String infusion: ChatColor.stripColor(wand.getItemMeta().getLore().get(5)).replace("Infused with: ", "").split(" & ")) {
											infusions.add(Infusion.valueOf(infusion.toUpperCase().replace(" ", "_")));
										}
									}
									
									boolean effecting = false;
									
									for (InfusionSet effect: InfusionSet.values()) {
										if (effect.isContainedin(infusions)) {
											effecting = true;
											switch(effect) {
											case WHIRLWIND:
												p.setVelocity(p.getEyeLocation().getDirection().multiply(4));
												p.getWorld().playSound(p.getLocation(), Sound.FIZZ, 1, 1);
												p.getWorld().playEffect(p.getLocation(), Effect.SMOKE, 1);
												p.setFallDistance(0.0f);
												break;
											case CANNON:
												p.launchProjectile(Arrow.class);
												break;
											case FIREBALL:
												p.launchProjectile(Fireball.class);
												break;
											case ICEBALL:
												Entity snowball = p.launchProjectile(Snowball.class);
												this.infusedDamager.put(snowball.getUniqueId(), "ice");
												break;
											case SHURIKEN:
												Entity shuriken = p.launchProjectile(Arrow.class);
												this.infusedDamager.put(shuriken.getUniqueId(), "ninja");
												break;
											case EXTINGUISH:
												p.setFireTicks(0);
												break;
											case ENDER_HAND:
												p.launchProjectile(EnderPearl.class);
												break;
											default:
												break;
											}
										}
									}
									if (!effecting) p.sendMessage(plugin.getTranslation("fail.no-combination"));
								}
							}
						}
					}
				}
			}
		}
	}
	
	@EventHandler
	public void onDamage(EntityDamageByEntityEvent e) {
		if (e.getEntity() instanceof LivingEntity && this.infusedDamager.containsKey(e.getDamager().getUniqueId())) {
			String action = this.infusedDamager.get(e.getDamager().getUniqueId());
			
			if (action.equalsIgnoreCase("ice")) ((LivingEntity) e.getEntity()).addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 100, 4));
			else if (action.equalsIgnoreCase("ninja")) ((LivingEntity) e.getEntity()).addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 200, 4));
			
			this.infusedDamager.remove(e.getDamager().getUniqueId());
		}
	}
	
	@EventHandler
	public void onNullify(ProjectileHitEvent e) {
		if (this.infusedDamager.containsKey(e.getEntity().getUniqueId())) this.infusedDamager.remove(e.getEntity().getUniqueId());
	}
	
	@EventHandler
	public void onWandClose(InventoryCloseEvent e) {
		if (opening.contains(e.getPlayer().getUniqueId())) {
			Player p = (Player) e.getPlayer();
			ItemStack wand = p.getItemInHand();
			List<Infusion> infusions = new ArrayList<Infusion>();
			
			for (ItemStack item: e.getInventory().getContents()) {
				if (item != null) {
					if (item.getType() == Material.FLINT && item.getDurability() > 16) {
						infusions.add(Infusion.valueOf(ChatColor.stripColor(item.getItemMeta().getDisplayName().replace("Meteor Shard - ", "").toUpperCase())));
					}
					else if (item.getType() != Material.AIR) {
						e.getPlayer().getWorld().playEffect(e.getPlayer().getLocation(), Effect.SMOKE, 1);
						e.getPlayer().getWorld().playSound(e.getPlayer().getLocation(), Sound.FIZZ, 1, 1);
						p.sendMessage(plugin.getTranslation("fail.wrongItems"));
						return;
					}
				}
			}
			
			String infused = "";
			
			for (int i = 0; i < infusions.size(); i++) {
				if (i > 0) infused = infused + " &7& " + infusions.get(i).getDisplayName();
				else infused = infusions.get(i).getDisplayName();
			}
			
			if (!infused.equalsIgnoreCase("")) {
				ItemMeta im = wand.getItemMeta();
				List<String> lore = im.getLore();
				lore.set(5, ChatColor.GRAY + "Infused with: " + ChatColor.translateAlternateColorCodes('&', infused));
				im.setLore(lore);
				wand.setItemMeta(im);
				p.setItemInHand(wand);
				
				e.getPlayer().getWorld().playEffect(e.getPlayer().getLocation(), Effect.ENDER_SIGNAL, 1);
				e.getPlayer().getWorld().playEffect(e.getPlayer().getLocation(), Effect.MOBSPAWNER_FLAMES, 1);
				e.getPlayer().getWorld().playSound(e.getPlayer().getLocation(), Sound.LEVEL_UP, 1, 1);
			}
			
			opening.remove(e.getPlayer().getUniqueId());
		}
	}

}
