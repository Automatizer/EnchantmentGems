package me.auto.eg.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerChangedWorldEvent;
import org.bukkit.plugin.Plugin;

import me.auto.eg.enchants.EnchantManager;

public class WorldChangeEvent implements Listener{

	Plugin plugin;
	
	public WorldChangeEvent(Plugin plugin){
		this.plugin = plugin;
		plugin.getServer().getPluginManager().registerEvents(this, plugin);
	}
	
	@EventHandler
	public void onWorldChange(PlayerChangedWorldEvent e){
		Player player = e.getPlayer();
		if(player.getInventory().getLeggings() != null && EnchantManager.hasEnchant(player.getInventory().getLeggings(), "leaping"))
			player.setAllowFlight(true);
	}
	
}
