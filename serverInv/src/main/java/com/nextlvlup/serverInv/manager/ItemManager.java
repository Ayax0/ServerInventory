package com.nextlvlup.serverInv.manager;

import java.util.Arrays;

import org.bukkit.Material;
import org.bukkit.SkullType;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

public class ItemManager {
	
	public static ItemStack getItem(Material mat, String name, int amount) {
		ItemStack item = new ItemStack(mat, amount);
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName(name);
		item.setItemMeta(meta);
		return item;
	}
	
	public static ItemStack getItem(Material mat, String name, int amount, String... lore) {
		ItemStack item = new ItemStack(mat, amount);
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName(name);
		meta.setLore(Arrays.asList(lore));
		item.setItemMeta(meta);
		return item;
	}
	
	public static ItemStack getSkull(String player, String name, int amount) {
		ItemStack skull = new ItemStack(Material.SKULL_ITEM, amount, (short) SkullType.PLAYER.ordinal());
		SkullMeta meta = (SkullMeta) skull.getItemMeta();
		meta.setOwner(player);
		meta.setDisplayName(name);
		skull.setItemMeta(meta);
		return skull;
	}
	
	public static ItemStack getSkull(String player, String name, int amount, String... lore) {
		ItemStack skull = new ItemStack(Material.SKULL_ITEM, amount, (short) SkullType.PLAYER.ordinal());
		SkullMeta meta = (SkullMeta) skull.getItemMeta();
		meta.setOwner(player);
		meta.setDisplayName(name);
		meta.setLore(Arrays.asList(lore));
		skull.setItemMeta(meta);
		return skull;
	}

}
