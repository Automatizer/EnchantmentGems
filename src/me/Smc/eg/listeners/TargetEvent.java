package me.Smc.eg.listeners;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityTargetEvent;
import org.bukkit.event.entity.EntityTargetEvent.TargetReason;
import org.bukkit.plugin.Plugin;

import me.Smc.eg.enchants.EnchantManager;

public class TargetEvent implements Listener{

	Plugin pl;
	
	public TargetEvent(Plugin plugin) {
		this.pl = plugin;
		Bukkit.getServer().getPluginManager().registerEvents(this, pl);
	}
	
	@EventHandler
	public void onEntityTarget(EntityTargetEvent e) {
		if(e.getTarget() instanceof Player) {
			Player p = (Player) e.getTarget();
			if(EnchantManager.hasEnchant(p.getInventory().getChestplate(), "peaceful") && e.getReason() != TargetReason.TARGET_ATTACKED_ENTITY) {
				e.setCancelled(true);
			}
		}
	}
	
}
