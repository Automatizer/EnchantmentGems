package me.auto.eg.listeners;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.world.ChunkLoadEvent;
import org.bukkit.plugin.Plugin;

public class ChunkLoad implements Listener{

	public ChunkLoad(Plugin pl) {
		this.plugin = pl;
		Bukkit.getPluginManager().registerEvents(this, pl);
	}
	
	Plugin plugin;
	
	@EventHandler
	public void onChunkLoad(ChunkLoadEvent e) {
		
	}
	
}
