package me.Smc.eg.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerToggleFlightEvent;
import org.bukkit.plugin.Plugin;

import me.Smc.eg.enchants.EnchantManager;

public class FlightEvent implements Listener{

	Plugin plugin;
	
	public FlightEvent(Plugin plugin){
		this.plugin = plugin;
		plugin.getServer().getPluginManager().registerEvents(this, plugin);
	}
	
	@EventHandler
	public void onFlight(PlayerToggleFlightEvent e){
		if(MoveEvent.readyToJump.contains(e.getPlayer().getUniqueId())){
			Player player = e.getPlayer();
			if(player.getInventory().getLeggings() != null) EnchantManager.callEvent(player.getInventory().getLeggings(), "onFlight", player, null, 0.0, null);	
			e.setCancelled(true);
		}
	}
	
}
