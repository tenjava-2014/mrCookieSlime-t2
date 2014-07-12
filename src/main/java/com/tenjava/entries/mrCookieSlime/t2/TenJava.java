package com.tenjava.entries.mrCookieSlime.t2;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;

public class TenJava extends JavaPlugin {
	
	File localFile;
	FileConfiguration localConfig;
	Random random;
	
	/**
	 * 	TenJava 2014 Submission by mrCookieSlime
	 * 	
	 * 	Theme: How can energy be harnessed and used in the Minecraft world?
	 * 	Name: InfusedSticks
	 * 
	 * 	Description/Story: 
	 * 		Meteors are coming down! But as you look inside you start
	 * 		finding weird looking Crystals which contain unearthly
	 * 		Energy...
	 * 		As you experiment more and more with these you begin to
	 * 		craft powerful Gadgets to use this unknown Energy.
	 * 		It has a strange behaviour but you discover slowly but
	 * 		surely how to use them for your advantage!
	 * 
	 * 	Proper Description:
	 * 		This Plugin adds Meteors which contain Crystal inside, combined
	 * 		with a craftable Wand, some Crystal Combinations can have good/bad Effects
	 * 
	 * 
	 */
	
	@Override
	public void onEnable() {
		System.out.println("[InfusedSticks] InfusedSticks v" + getDescription().getVersion() + " has been enabled");
		
		this.loadConfig();
		this.random = new Random();
		
		this.localFile = new File("plugins/" + getDescription().getName().replace(" ", "_") + "/messages.yml");
		this.localConfig = YamlConfiguration.loadConfiguration(localFile);
		
		try {
			setupLocalizations(
					new String[] {"fail.wrongItems"}, 
					new String[] {"&4Your Wand did not accept your Items and exterminated these"}
			);
		} catch (IOException e) {
		}
		
		this.loadItems();
		this.setupRecipe();
		
		new MeteorSpawner(this);
		new WandListener(this);
	}
	
	@Override
	public void onDisable() {
		System.out.println("tenJava v" + getDescription().getVersion() + " has been disabled");
	}
	
	public void setupLocalizations(String[] keys, String[] values) throws IOException {
		if (!localFile.exists()) localFile.createNewFile();
		
		for (int i = 0; i < keys.length; i++) {
			if (!localConfig.contains(keys[i])) localConfig.set(keys[i], values[i]);
		}
	}
	
	public String getTranslation(String input) {
		return ChatColor.translateAlternateColorCodes('&', "&6&l[InfusedSticks] &7" + String.valueOf(this.localConfig.get(input)));
	}
	
	public void loadConfig() {
		FileConfiguration cfg = this.getConfig();
		cfg.options().copyDefaults(true);
		this.saveConfig();
	}
	
	public void loadItems() {
		if (true) {
			ItemStack item = new ItemStack(Material.STICK);
			item.setDurability((short) 16);
			ItemMeta im = item.getItemMeta();
			im.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&aMagic Wand"));
			List<String> lore = new ArrayList<String>();
			lore.add("");
			lore.add("This powerful Gadget can be");
			lore.add("infused with unearthly Energy");
			lore.add("to show you its mighty Power");
			lore.add("");
			lore.add(ChatColor.GRAY + "Infused with: Nothing");
			im.setLore(lore);
			item.setItemMeta(im);
			
			MagicItems.INFUSABLE_WAND = item;
		}
		if (true) {
			ItemStack item = new ItemStack(Material.FLINT);
			item.setDurability((short) 17);
			ItemMeta im = item.getItemMeta();
			im.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&aMeteor Shard - &9&oWater"));
			List<String> lore = new ArrayList<String>();
			lore.add("");
			lore.add("Unearthly magical Power");
			lore.add("flows through the Atoms of this");
			lore.add("powerful Crystal. Maybe you can use it somehow");
			im.setLore(lore);
			item.setItemMeta(im);
			
			MagicItems.WATER_SHARD = item;
		}
		if (true) {
			ItemStack item = new ItemStack(Material.FLINT);
			item.setDurability((short) 18);
			ItemMeta im = item.getItemMeta();
			im.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&aMeteor Shard - &4&oEarth"));
			List<String> lore = new ArrayList<String>();
			lore.add("");
			lore.add("Unearthly magical Power");
			lore.add("flows through the Atoms of this");
			lore.add("powerful Crystal. Maybe you can use it somehow");
			im.setLore(lore);
			item.setItemMeta(im);
			
			MagicItems.EARTH_SHARD = item;
		}
		if (true) {
			ItemStack item = new ItemStack(Material.FLINT);
			item.setDurability((short) 19);
			ItemMeta im = item.getItemMeta();
			im.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&aMeteor Shard - &b&oAir"));
			List<String> lore = new ArrayList<String>();
			lore.add("");
			lore.add("Unearthly magical Power");
			lore.add("flows through the Atoms of this");
			lore.add("powerful Crystal. Maybe you can use it somehow");
			im.setLore(lore);
			item.setItemMeta(im);
			
			MagicItems.AIR_SHARD = item;
		}
		if (true) {
			ItemStack item = new ItemStack(Material.FLINT);
			item.setDurability((short) 20);
			ItemMeta im = item.getItemMeta();
			im.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&aMeteor Shard - &c&oFire"));
			List<String> lore = new ArrayList<String>();
			lore.add("");
			lore.add("Unearthly magical Power");
			lore.add("flows through the Atoms of this");
			lore.add("powerful Crystal. Maybe you can use it somehow");
			im.setLore(lore);
			item.setItemMeta(im);
			
			MagicItems.FIRE_SHARD = item;
		}
		if (true) {
			ItemStack item = new ItemStack(Material.FLINT);
			item.setDurability((short) 21);
			ItemMeta im = item.getItemMeta();
			im.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&aMeteor Shard - &5&oFlux"));
			List<String> lore = new ArrayList<String>();
			lore.add("");
			lore.add("Unearthly magical Power");
			lore.add("flows through the Atoms of this");
			lore.add("powerful Crystal. Maybe you can use it somehow");
			im.setLore(lore);
			item.setItemMeta(im);
			
			MagicItems.FLUX_SHARD = item;
		}
		if (true) {
			ItemStack item = new ItemStack(Material.FLINT);
			item.setDurability((short) 22);
			ItemMeta im = item.getItemMeta();
			im.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&aMeteor Shard - &7&oMetal"));
			List<String> lore = new ArrayList<String>();
			lore.add("");
			lore.add("Unearthly magical Power");
			lore.add("flows through the Atoms of this");
			lore.add("powerful Crystal. Maybe you can use it somehow");
			im.setLore(lore);
			item.setItemMeta(im);
			
			MagicItems.METAL_SHARD = item;
		}
	}
	
	public void setupRecipe() {
		if (true) {
			ShapedRecipe r = new ShapedRecipe(MagicItems.INFUSABLE_WAND);
			r.shape(new String[] {" ld", "ldl", " dl"});
			r.setIngredient('l', Material.LOG);
			r.setIngredient('d', Material.DIAMOND);
			getServer().addRecipe(r);
		}
		if (true) {
			ShapedRecipe r = new ShapedRecipe(MagicItems.INFUSABLE_WAND);
			r.shape(new String[] {" ld", "ldl", " dl"});
			r.setIngredient('l', Material.LOG_2);
			r.setIngredient('d', Material.DIAMOND);
			getServer().addRecipe(r);
		}
	}
	
	public Random getRandomizer()		{		return this.random;		}

}
