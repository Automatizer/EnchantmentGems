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
		/*if(e.getEntityType().equals(EntityType.FALLING_BLOCK)) {
			FallingBlock fb = (FallingBlock) e.getEntity();
			if(fb.getMaterial().equals(Material.BARRIER)) {
				if(e.getBlock().isEmpty()) {
					e.setCancelled(true);
					Location loc = fb.getLocation();
					fb.eject();
					fb.remove();
					EntityDismount.entities.remove(fb.getUniqueId());
					for(Entity ent : Utils.getNearbyEntities(loc, 2)) {
						if(ent.getType().equals(EntityType.DROPPED_ITEM)) {
							Item item = (Item) ent;
							if(item.getItemStack().getType().equals(Material.BARRIER)) {
								ent.remove();
							}
						}
					}
				}else {
					e.setCancelled(true);
					bounce(fb);
				}
			}
		}*/
	}
	
}
