package com.nextlvlup.serverInv.manager;

import org.bukkit.entity.Player;

import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import com.nextlvlup.serverInv.Main;

public class ServerManager {
	
	public static void sendPlayer(Player player, String server) {
		ByteArrayDataOutput out = ByteStreams.newDataOutput();
		out.writeUTF("Connect");
		out.writeUTF(server);
		player.sendPluginMessage(Main.getInstance(), "BungeeCord", out.toByteArray());
	}

}
