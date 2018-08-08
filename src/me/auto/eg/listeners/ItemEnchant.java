package me.auto.eg.listeners;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.enchantment.EnchantItemEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;

import me.auto.eg.enchants.EnchantManager;
import me.auto.eg.utils.Utils;

public class ItemEnchant implements Listener{

	public ItemEnchant(Plugin pl) {
		this.plugin = pl;
		Bukkit.getPluginManager().registerEvents(this, pl);
	}
	
	Plugin plugin;
	
	@EventHandler
	public void onItemEnchant(EnchantItemEvent e) {
		int i = Utils.randomBetween(0, 100);
		int level = 0;
		ItemStack is = e.getItem();
		if(i <= 30) {
			level = 1;
			if(i <= 20) {
				level = 2;
				if(i <= 10) {
					level = 3;
				}
			}
			EnchantManager.addEnchantToItem(is, EnchantManager.getEnchant("headhunter"), level);
		}
	}
	
}
