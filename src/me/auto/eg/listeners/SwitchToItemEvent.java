package me.auto.eg.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerItemHeldEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;
import org.bukkit.potion.PotionEffectType;

import me.auto.eg.oldenchants.EnchantManager;

public class SwitchToItemEvent implements Listener{

	Plugin plugin;
	
	public SwitchToItemEvent(Plugin plugin){
		this.plugin = plugin;
		plugin.getServer().getPluginManager().registerEvents(this, plugin);
	}
	
	@EventHandler
	public void switchToItemEvent(PlayerItemHeldEvent e){
		if(e.getPreviousSlot() == e.getNewSlot()) return;
		Player player = e.getPlayer();
		if(player.hasPotionEffect(PotionEffectType.FAST_DIGGING)) player.removePotionEffect(PotionEffectType.FAST_DIGGING);
		ItemStack item = player.getInventory().getItem(e.getNewSlot());
		if(item != null) EnchantManager.callEvent(item, "switchToItem", player, null, 0.0, null);
	}
	
}
