package me.Smc.eg.listeners;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.plugin.Plugin;

import me.Smc.eg.enchants.EnchantManager;
import me.Smc.eg.enchants.Leaping;

public class DamageEvent implements Listener{
	
	Plugin plugin;
	
	public DamageEvent(Plugin plugin){
		this.plugin = plugin;
		plugin.getServer().getPluginManager().registerEvents(this, plugin);
	}
	
	@EventHandler
	public void attackEntityEvent(EntityDamageByEntityEvent e){
		Entity entDamager = e.getDamager();
		Entity entDamaged = e.getEntity();
		if(entDamager instanceof Projectile){
			Projectile proj = (Projectile) entDamager;
			if(proj.getShooter() instanceof Player) entDamager = (Entity) proj.getShooter();
		}
		if(entDamager instanceof Player){
			Player damager = (Player) entDamager;
			if(damager.getItemOnCursor() != null) EnchantManager.callEvent(damager.getItemOnCursor(), "attackEntity", damager, e.getEntity(), e.getDamage(), null);
			if(damager.getInventory().getBoots() != null) EnchantManager.callEvent(damager.getInventory().getBoots(), "attackEntity", damager, e.getEntity(), e.getDamage(), null);
		}
		if(entDamaged instanceof Player){
			Player damaged = (Player) entDamaged;
			if(damaged.getItemOnCursor() != null) EnchantManager.callEvent(damaged.getItemOnCursor(), "hitByEntity", damaged, e.getDamager(), e.getDamage(), null);
		}
	}
	
	@EventHandler
	public void takeDamageEvent(EntityDamageEvent e){
		if(e.getEntity() instanceof Player){
			Player player = (Player) e.getEntity();
			if(Leaping.protectedFromFall.contains(player.getUniqueId())){
				if(e.getCause().equals(DamageCause.FALL)){
					Leaping.protectedFromFall.remove(player.getUniqueId());
					e.setDamage(0.0);
					e.setCancelled(true);
				}
			}
		}
	}

}
