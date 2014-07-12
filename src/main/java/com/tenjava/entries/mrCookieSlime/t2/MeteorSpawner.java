package com.tenjava.entries.mrCookieSlime.t2;

import java.util.Random;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Chest;
import org.bukkit.entity.FallingBlock;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;

public class MeteorSpawner implements Listener {
	
	TenJava plugin;
	
	public MeteorSpawner(TenJava plugin) {
		this.plugin = plugin;
		plugin.getServer().getPluginManager().registerEvents(this, plugin);
	}
	
	@SuppressWarnings("deprecation")
	@EventHandler
	public void onMeteorLand(PlayerInteractEvent e) {
		if (e.getItem() != null) {
			Location base = e.getPlayer().getLocation();
			
			base.getWorld().createExplosion(base, new Random().nextInt(3) + 6);
			
			for (int i = 0; i < this.plugin.getConfig().getInt("meteor.blocks-per-meteor"); i++) {
				FallingBlock block;
				Location l = base.getBlock().getRelative(new Random().nextInt(4) - new Random().nextInt(8), new Random().nextInt(2) - new Random().nextInt(4), new Random().nextInt(4) - new Random().nextInt(8)).getLocation();
				
				if (plugin.getRandomizer().nextFloat() <=  this.plugin.getConfig().getDouble("meteor.quartz-chance")) {
					block = l.getWorld().spawnFallingBlock(l, Material.QUARTZ_ORE, (byte) 0);
				}
				else {
					block = l.getWorld().spawnFallingBlock(l, Material.NETHERRACK, (byte) 0);
				}
				block.setVelocity(new Vector(new Random().nextInt(2) - new Random().nextInt(4), 0, new Random().nextInt(2) - new Random().nextInt(4)).multiply(0.6));
				if (plugin.getRandomizer().nextFloat() <=  0.99) l.getBlock().setType(Material.NETHERRACK);
			}
			
			base.getBlock().setType(Material.CHEST);
			Chest chest = (Chest) base.getBlock().getState();
			ItemStack[] loot = new ItemStack[] {MagicItems.AIR_SHARD, MagicItems.EARTH_SHARD, MagicItems.FIRE_SHARD, MagicItems.FLUX_SHARD, MagicItems.METAL_SHARD, MagicItems.WATER_SHARD};
			for (int i = 0; i < 1 + new Random().nextInt(4); i++) {
				chest.getInventory().addItem(loot[plugin.getRandomizer().nextInt(loot.length)]);
			}
		}
	}

}
