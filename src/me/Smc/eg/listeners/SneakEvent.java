package me.Smc.eg.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerToggleSneakEvent;
import org.bukkit.plugin.Plugin;

public class SneakEvent implements Listener{

	Plugin plugin;
	
	public SneakEvent(Plugin pl) {
		this.plugin = pl;
		plugin.getServer().getPluginManager().registerEvents(this, plugin);
	}
	
	@EventHandler
	public static void onSneak(PlayerToggleSneakEvent e) {
		
	}
	
}
