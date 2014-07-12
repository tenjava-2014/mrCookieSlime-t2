package com.tenjava.entries.mrCookieSlime.t2;

import org.bukkit.event.Listener;

public class WandListener implements Listener {
	
	public WandListener(TenJava plugin) {
		plugin.getServer().getPluginManager().registerEvents(this, plugin);
	}

}
