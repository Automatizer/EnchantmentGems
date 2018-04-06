package me.Smc.eg.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerToggleFlightEvent;
import org.bukkit.plugin.Plugin;

public class FlightEvent implements Listener{

	Plugin plugin;
	
	public FlightEvent(Plugin plugin){
		this.plugin = plugin;
		plugin.getServer().getPluginManager().registerEvents(this, plugin);
	}
	
	@EventHandler
	public void onFlight(PlayerToggleFlightEvent e){
		
	}
	
}
