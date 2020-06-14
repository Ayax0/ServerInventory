package com.nextlvlup.serverInv.listener;

import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.json.JSONObject;

import com.nextlvlup.serverInv.inventory.PagedInventory;
import com.nextlvlup.serverInv.manager.ItemManager;
import com.nextlvlup.serverInv.manager.ServerManager;
import com.nextlvlup.serverInv.rest.ReturnHook;

public class ItemListener implements Listener{
	
	@EventHandler
	public void openInv(PlayerInteractEvent e) {
		if(e.getAction() == Action.RIGHT_CLICK_AIR || e.getAction() == Action.RIGHT_CLICK_BLOCK) {
			if(e.getPlayer().getInventory().getItemInHand() != null) {
				if(e.getPlayer().getInventory().getItemInHand().getType() == Material.NETHER_STAR) {
					e.setCancelled(true);
					
					PagedInventory inventory = new PagedInventory(ChatColor.BOLD + "Other players' servers", 5);
					
					inventory.getInventory().setItem(0, ItemManager.getItem(Material.CLAY, ChatColor.AQUA + ChatColor.BOLD.toString() + "Loading", 1, ChatColor.GRAY + "Scanning for Online Servers..."));
					
					inventory.open(e.getPlayer());
					
					new ReturnHook("/server").getList((server) -> {
						if(!server.getString("name").equalsIgnoreCase("lobby")) {
							try {
								OfflinePlayer oPlayer = Bukkit.getOfflinePlayer(UUID.fromString(server.getString("name")));
								inventory.addItem(ItemManager.getSkull(
										oPlayer.getName(),																				//Player
										ChatColor.translateAlternateColorCodes('&', server.getString("motd")), 							//Title
										server.getJSONArray("player").length() > 0 ? server.getJSONArray("player").length() : 1,		//Amount
										ChatColor.DARK_GRAY + ChatColor.ITALIC.toString() + "Created by " + oPlayer.getName(),			//Lore1
										" ",																							//Lore2
										ChatColor.RED + "version: " + server.getString("version")										//Lore3
								), server);
							}catch(IllegalArgumentException ex) {}
						}
						
						inventory.displayPage(1);
					});
					
					inventory.setHandler((event) -> {
						if(event != null) {
							if(event.getCurrentItem().getType() == Material.SKULL_ITEM) {
								JSONObject server = inventory.lookup(event.getCurrentItem());
								if(server != null)
									ServerManager.sendPlayer(e.getPlayer(), server.getString("name"));
							}
						}
					});
				}
			}
		}
	}

}
