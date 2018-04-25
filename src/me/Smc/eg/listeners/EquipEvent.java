package me.Smc.eg.listeners;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;

import com.codingforcookies.armorequip.ArmorEquipEvent;

import me.Smc.eg.enchants.EnchantManager;

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
	
	/*@EventHandler
	public void onRightClickArmor(PlayerInteractEvent e){
		if(e.getAction().equals(Action.RIGHT_CLICK_AIR) || e.getAction().equals(Action.RIGHT_CLICK_BLOCK))
			if(e.getPlayer().getItemOnCursor() != null) EnchantManager.callEvent(e.getPlayer().getInventory().getItemInMainHand(), "onEquip", e.getPlayer(), null, 0.0, null);
	}
	
	@EventHandler
	public void onArmorEquip(InventoryClickEvent e){
		Player player = (Player) e.getWhoClicked();
		ItemStack toCheck = e.getCurrentItem();
		if(toCheck == null || toCheck.getType().equals(Material.AIR)) return;
		if(!e.isShiftClick()) {
			EnchantManager.callEvent(toCheck, "onEquip", player, null, 0.0, null);
			if(EnchantManager.hasEnchant(toCheck, "leaping")) EnchantManager.callEvent(toCheck, "onLeap", player, null, 1.0, null);
		}
	}
	
	@EventHandler
	public void onArmorUnequip(InventoryClickEvent e) {
		Player player = (Player) e.getWhoClicked();
		ItemStack is = e.getCurrentItem();
		SlotType st = e.getSlotType();
		if(st == SlotType.ARMOR) {
			if(!(is == null || is.getType().equals(Material.AIR))){
				for(Enchant en : EnchantManager.getEnchants(is)){
					if(en.getName().equalsIgnoreCase("nightvision")){
						if(player.hasPotionEffect(PotionEffectType.NIGHT_VISION)) player.removePotionEffect(PotionEffectType.NIGHT_VISION);
					}else if(en.getName().equalsIgnoreCase("flash")) {
						if(player.hasPotionEffect(PotionEffectType.SPEED)) player.removePotionEffect(PotionEffectType.SPEED);
					}else if(en.getName().equalsIgnoreCase("leaping")) {
						if(player.hasPotionEffect(PotionEffectType.JUMP)) player.removePotionEffect(PotionEffectType.JUMP);
					}
				}
			}
		}
	}*/
	
}
