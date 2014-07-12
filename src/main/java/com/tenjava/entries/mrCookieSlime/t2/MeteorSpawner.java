package com.tenjava.entries.mrCookieSlime.t2;

import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;

public class MeteorSpawner implements Listener {
	
	TenJava plugin;
	
	public MeteorSpawner(TenJava plugin) {
		this.plugin = plugin;
		plugin.getServer().getPluginManager().registerEvents(this, plugin);
	}
	
	@EventHandler
	public void onMeteorLand(CreatureSpawnEvent e) {
		if (e.getEntityType() == EntityType.ENDERMAN && plugin.getRandomizer().nextFloat() <= 0.1) {
			plugin.spawnMeteor(e.getLocation());
		}
	}
}
