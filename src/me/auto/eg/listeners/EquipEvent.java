package me.auto.eg.listeners;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;

import com.codingforcookies.armorequip.ArmorEquipEvent;

import me.auto.eg.enchants.EnchantManager;

public class EquipEvent implements Listener{

	Plugin plugin;
	
	public EquipEvent(Plugin plugin){
		this.plugin = plugin;
		plugin.getServer().getPluginManager().registerEvents(this, plugin);
	}
	
	@EventHandler
	public void onArmorEquip(ArmorEquipEvent e) {
		ItemStack is = e.getNewArmorPiece();
		ItemStack old = e.getOldArmorPiece();
		Player player = e.getPlayer();
		if(is != null && is.getType() != Material.AIR) {
			EnchantManager.callEvent(is, "onEquip", player, null, 0.0, null);
			if(EnchantManager.hasEnchant(is, "leaping")) EnchantManager.callEvent(is, "onLeap", player, null, 1.0, null);
		}else if(old != null && old.getType() != Material.AIR){
			EnchantManager.callEvent(old, "onEquip", player, null, 1.0, null);
			if(EnchantManager.hasEnchant(old, "leaping")) EnchantManager.callEvent(old, "onLeap", player, null, 2.0, null);
		}
	}
	
}
