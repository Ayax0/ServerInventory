package com.nextlvlup.serverInv.inventory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.json.JSONObject;

import com.nextlvlup.serverInv.Main;
import com.nextlvlup.serverInv.manager.ItemManager;
import lombok.Getter;
import net.md_5.bungee.api.ChatColor;

public class PagedInventory implements Listener {
	
	@Getter private Inventory inventory;
	@Getter private List<ItemStack> content = new ArrayList<ItemStack>();
	@Getter private int currentPage = 0;
	
	private HashMap<String, JSONObject> meta = new HashMap<String, JSONObject>();
	
	private int rows;
	private ClickEvent handler;
	
	public PagedInventory(String title, int rows) {
		this.inventory = Bukkit.createInventory(null, rows * 9, title);
		this.rows = rows;
		
		Bukkit.getPluginManager().registerEvents(this, Main.getInstance());
	}
	
	public void setHandler(ClickEvent handler) {
		this.handler = handler;
	}
	
	public void addItem(ItemStack item, JSONObject info) {
		content.add(item);
		
		if(item.getItemMeta() != null && item.getItemMeta().getLore() != null)
			meta.put(item.getItemMeta().getLore().get(0), info);
	}
	
	public void displayPage(int page) {
		currentPage = page;
		
		int displaySize = (rows - 1) * 9;
		
		int startPos = (page * displaySize) - displaySize;
		
		if(startPos > content.size()) displayPage(page - 1);
		else {
			for(int i = 0; i < displaySize; i++) {
				if(content.size() > (startPos + i))
					inventory.setItem(i, content.get(startPos + i));
				else
					inventory.setItem(i, new ItemStack(Material.AIR));
			}
		}
		
		if(getCurrentPage() > 1)
			inventory.setItem(displaySize + 6, ItemManager.getItem(Material.STONE_BUTTON, ChatColor.GOLD + "Previous Page", 1));
		else
			inventory.setItem(displaySize + 6, new ItemStack(Material.AIR));
		
		inventory.setItem(displaySize + 7, ItemManager.getItem(Material.PAPER, ChatColor.AQUA + "Page " + page, page));
		
		if((startPos + displaySize) < content.size())
			inventory.setItem(displaySize + 8, ItemManager.getItem(Material.WOOD_BUTTON, ChatColor.GOLD + "Next Page", 1));
		else
			inventory.setItem(displaySize + 8, new ItemStack(Material.AIR));
	}
	
	public void open(Player player) {
		player.openInventory(inventory);
	}
	
	public JSONObject lookup(ItemStack item) {
		if(item.getItemMeta() == null || item.getItemMeta().getLore() == null) return null;
		return meta.get(item.getItemMeta().getLore().get(0));
	}
	
	@EventHandler
	public void onInventoryClick(InventoryClickEvent e) {
		if(e.getClickedInventory() != null) {
			if(e.getClickedInventory().equals(inventory)) {
				e.setCancelled(true);
				if(e.getCurrentItem() != null) {
					handler.handle(e);
					if(e.getCurrentItem().getType() == Material.STONE_BUTTON) {
						displayPage(getCurrentPage() - 1);
					}
					if(e.getCurrentItem().getType() == Material.WOOD_BUTTON) {
						displayPage(getCurrentPage() + 1);
					}
				}
			}
		}
	}
}
