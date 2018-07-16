package me.auto.eg.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.plugin.Plugin;

import me.auto.eg.enchants.EnchantManager;

public class HungerEvent implements Listener{

	Plugin plugin;
	
	public HungerEvent(Plugin plugin){
		this.plugin = plugin;
		plugin.getServer().getPluginManager().registerEvents(this, plugin);
	}
	
	@EventHandler
	public void hungerEvent(PlayerMoveEvent e){
		Player player = e.getPlayer();
		if(player.getFoodLevel() < 20 || player.getSaturation() < 20)
			if(player.getInventory().getChestplate() != null) EnchantManager.callEvent(player.getInventory().getChestplate(), "onHunger", player, null, 0.0, null);
	}
	
}
