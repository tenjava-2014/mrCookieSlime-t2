package com.tenjava.entries.mrCookieSlime.t2;

import org.bukkit.ChatColor;
import org.bukkit.inventory.ItemStack;

public enum Infusion {
	
	WATER,
	EARTH,
	AIR,
	FIRE,
	FLUX,
	METAL;
	
	public ItemStack getShard() {
		switch(this) {
		case AIR:
			return MagicItems.AIR_SHARD;
		case EARTH:
			return MagicItems.EARTH_SHARD;
		case FIRE:
			return MagicItems.FIRE_SHARD;
		case FLUX:
			return MagicItems.FLUX_SHARD;
		case METAL:
			return MagicItems.METAL_SHARD;
		case WATER:
			return MagicItems.WATER_SHARD;
		default:
			return null;
		}
	}
	
	public String getDisplayName() {
		switch(this) {
		case AIR:
			return ChatColor.translateAlternateColorCodes('&', "&b&oAir");
		case EARTH:
			return ChatColor.translateAlternateColorCodes('&', "&4&oEarth");
		case FIRE:
			return ChatColor.translateAlternateColorCodes('&', "&c&oFire");
		case FLUX:
			return ChatColor.translateAlternateColorCodes('&', "&6&oFlux");
		case METAL:
			return ChatColor.translateAlternateColorCodes('&', "&7&oMetal");
		case WATER:
			return ChatColor.translateAlternateColorCodes('&', "&9&oWater");
		default:
			return "";
		}
	}

}
