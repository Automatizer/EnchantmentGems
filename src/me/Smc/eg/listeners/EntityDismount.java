package me.Smc.eg.listeners;

import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import org.spigotmc.event.entity.EntityDismountEvent;

public class EntityDismount implements Listener{
	
	Plugin pl;
	
	public EntityDismount(Plugin pl) {
		this.pl = pl;
		pl.getServer().getPluginManager().registerEvents(this, pl);
	}
	
	@EventHandler
	public void onDismount(EntityDismountEvent e) {
		Entity entity = e.getDismounted();
		if(entity.getType().equals(EntityType.FALLING_BLOCK)) {
			if(!entity.isDead()) {
				entity.addPassenger(e.getEntity());
			}
		}
	}

}
