package me.auto.eg.listeners;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockFromToEvent;
import org.bukkit.plugin.Plugin;

public class BlockChange implements Listener{

	Plugin pl;
	
	public BlockChange(Plugin plugin) {
		pl = plugin;
		Bukkit.getServer().getPluginManager().registerEvents(this, pl);
	}
	
	@EventHandler
	public void onChange(BlockFromToEvent e) {
		
	}
	
}
