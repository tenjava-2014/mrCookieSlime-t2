package com.tenjava.entries.mrCookieSlime.t2;

import java.util.Random;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.FallingBlock;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
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
		Location l = e.getPlayer().getLocation();
		
		l.getWorld().createExplosion(l, new Random().nextInt(3) + 6);
		
		for (int i = 0; i < this.plugin.getConfig().getInt("meteor.blocks-per-meteor"); i++) {
			
			FallingBlock block;
			
			if (plugin.getRandomizer().nextFloat() <=  this.plugin.getConfig().getDouble("meteor.quartz-chance")) {
				block = l.getWorld().spawnFallingBlock(l, Material.QUARTZ_ORE, (byte) 0);
			}
			else {
				block = l.getWorld().spawnFallingBlock(l, Material.NETHERRACK, (byte) 0);
			}
			block.setVelocity(new Vector(new Random().nextInt(1) - new Random().nextInt(4), 0, new Random().nextInt(2) - new Random().nextInt(4)).multiply(0.6));
		}
	}

}
