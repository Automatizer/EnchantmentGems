package me.Smc.eg.listeners;

import java.util.ArrayList;
import java.util.UUID;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.plugin.Plugin;

public class MoveEvent implements Listener{

	Plugin plugin;
	ArrayList<UUID> list = new ArrayList<UUID>();
	
	public MoveEvent(Plugin plugin){
		this.plugin = plugin;
		plugin.getServer().getPluginManager().registerEvents(this, plugin);
	}
	
	@EventHandler
	public void onPlayerMove(PlayerMoveEvent e){
		
	}
	
}
