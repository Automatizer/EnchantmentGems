package me.Smc.eg.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.Plugin;

public class JoinEvent implements Listener{

	Plugin plugin;
	
	public JoinEvent(Plugin plugin){
		this.plugin = plugin;
		plugin.getServer().getPluginManager().registerEvents(this, plugin);
	}
	
	@EventHandler
	public void onJoin(PlayerJoinEvent e){
		
	}
	
	@EventHandler
	public void onQuit(PlayerQuitEvent e) {
		
	}
	
}
