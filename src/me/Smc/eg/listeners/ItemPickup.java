package me.Smc.eg.listeners;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityPickupItemEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;

import me.Smc.eg.enchants.EnchantManager;

public class ItemPickup implements Listener{

	Plugin pl;
	
	public ItemPickup(Plugin plugin) {
		this.pl = plugin;
		Bukkit.getServer().getPluginManager().registerEvents(this, pl);
	}
	
	@EventHandler
	public void onItemPickup(EntityPickupItemEvent e) {
		if(e.getEntity() instanceof Player) {
			Player p = (Player) e.getEntity();
			ItemStack is = e.getItem().getItemStack();
			if(is.getType().equals(Material.BARRIER)) {
				if(EnchantManager.hasEnchant(p.getInventory().getLeggings(), "leaping")) {
					e.setCancelled(true);
					e.getItem().remove();
				}
			}
		}
	}
	
}
