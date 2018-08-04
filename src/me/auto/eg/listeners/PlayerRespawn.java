package me.auto.eg.listeners;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.plugin.Plugin;

public class PlayerRespawn implements Listener{
	
	Plugin pl;
	
	private static PlayerRespawn instance = null;

	public PlayerRespawn(Plugin plugin) {
		this.pl = plugin;
		Bukkit.getServer().getPluginManager().registerEvents(this, pl);
	}
	
	public PlayerRespawn() {}
	
	public static PlayerRespawn getInstance() {
		if(instance == null) {
			instance = new PlayerRespawn();
		}
		return instance;
	}
	
	@EventHandler
	public void onPlayerRespawn(PlayerRespawnEvent e) {
		
	}
	
}
