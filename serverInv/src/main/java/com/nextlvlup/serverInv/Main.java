package com.nextlvlup.serverInv;

import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import com.nextlvlup.serverInv.listener.ItemListener;
import com.nextlvlup.serverInv.rest.RestAPI;

import lombok.Getter;

public class Main extends JavaPlugin {
	
	@Getter private static JavaPlugin instance;
	@Getter private static RestAPI restAPI;
	
	@Override
	public void onEnable() {
		saveDefaultConfig();
		
		instance = this;
		restAPI = new RestAPI(getConfig().getString("api"));
		
		PluginManager pm = Bukkit.getPluginManager();
		pm.registerEvents(new ItemListener(), this);
		
		getServer().getMessenger().registerOutgoingPluginChannel(this, "BungeeCord");
	}

}
