package me.auto.eg.listeners;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.FallingBlock;
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
		if(e.getEntity() instanceof FallingBlock) {
			FallingBlock fb = (FallingBlock) e.getEntity();
			if(fb.getBlockData().getMaterial().equals(Material.MOVING_PISTON)) {
				e.setCancelled(true);
				fb.eject();
				fb.remove();
			}
		}
	}
	
}
