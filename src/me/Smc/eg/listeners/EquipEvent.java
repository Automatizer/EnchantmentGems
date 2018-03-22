package me.Smc.eg.listeners;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType.SlotType;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;
import org.bukkit.potion.PotionEffectType;

import me.Smc.eg.enchants.Enchant;
import me.Smc.eg.enchants.EnchantManager;

public class EquipEvent implements Listener{

	Plugin plugin;
	
	public EquipEvent(Plugin plugin){
		this.plugin = plugin;
		plugin.getServer().getPluginManager().registerEvents(this, plugin);
	}
	
	@EventHandler
	public void onRightClickArmor(PlayerInteractEvent e){
		if(e.getAction().equals(Action.RIGHT_CLICK_AIR) || e.getAction().equals(Action.RIGHT_CLICK_BLOCK))
			if(e.getPlayer().getItemOnCursor() != null) EnchantManager.callEvent(e.getPlayer().getItemOnCursor(), "onEquip", e.getPlayer(), null, 0.0, null);
	}
	
	@EventHandler
	public void onInventoryArmorEquip(InventoryClickEvent e){
		Player player = (Player) e.getWhoClicked();
		ItemStack toCheck = e.getCurrentItem();
		if(toCheck == null || toCheck.getType().equals(Material.AIR)) toCheck = player.getItemOnCursor();
		if(e.isShiftClick() && e.getCurrentItem() != null) EnchantManager.callEvent(e.getCurrentItem(), "onEquip", player, null, 0.0, null);
		else if(e.getSlotType().equals(SlotType.ARMOR)) EnchantManager.callEvent(toCheck, "onEquip", player, null, 0.0, null);
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
					}
				}
			}
		}
	}
	
}
