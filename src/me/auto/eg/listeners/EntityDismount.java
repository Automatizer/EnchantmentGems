package me.auto.eg.listeners;

import java.util.HashMap;
import java.util.UUID;

import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.spigotmc.event.entity.EntityDismountEvent;

import me.auto.eg.main.Main;
import me.auto.eg.oldenchants.EnchantManager;
import me.auto.eg.oldenchants.Leaping;

public class EntityDismount implements Listener{
	
	Plugin pl;
	HashMap<UUID, Integer> jumps = new HashMap<UUID, Integer>();
	
	public EntityDismount(Plugin pl) {
		this.pl = pl;
		pl.getServer().getPluginManager().registerEvents(this, pl);
	}
	
	@EventHandler
	public void onDismount(EntityDismountEvent e) {
		Entity entity = e.getDismounted();
		Entity ent = e.getEntity();
		if(entity.getType().equals(EntityType.FALLING_BLOCK) && !entity.isDead()) {
			if(!jumps.containsKey(ent.getUniqueId())) jumps.put(ent.getUniqueId(), 1);
			if(entity.getTicksLived() < 20) {
				entity.addPassenger(ent);
			}
			else {
				if(jumps.get(ent.getUniqueId()) < Leaping.getJumps(ent)) {
					if(ent instanceof Player) {
						Player p = (Player) ent;
						EnchantManager.callEvent(p.getInventory().getLeggings(), "onLeap", p, null, 0.0, null);
						jumps.put(ent.getUniqueId(), jumps.get(ent.getUniqueId()) + 1);
					}
				}
			}
		}
		else {
			jumps.remove(ent.getUniqueId());
		}
		new BukkitRunnable() {
			public void run() {
				if(ent.isOnGround()) {
					jumps.remove(ent.getUniqueId());
					cancel();
				}
			}
		}.runTaskTimer(Main.plugin, 80, 5);
	}

}
