package me.Smc.eg.listeners;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityChangeBlockEvent;
import org.bukkit.plugin.Plugin;

public class EntityChangeBlock implements Listener{
	
	Plugin pl;
	
	public EntityChangeBlock(Plugin plugin) {
		this.pl = plugin;
		Bukkit.getServer().getPluginManager().registerEvents(this, pl);
	}
	
	@EventHandler
	public void onBlockChange(EntityChangeBlockEvent e) {
		
	}
	
}
