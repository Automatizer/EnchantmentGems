package me.Smc.eg.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;

import me.Smc.eg.enchants.EnchantManager;
import me.Smc.eg.utils.ListUtils;

public class LoginEvent implements Listener{
	
	Plugin plugin;

	public LoginEvent(Plugin plugin){
		this.plugin = plugin;
		plugin.getServer().getPluginManager().registerEvents(this, plugin);
	}
	
	@EventHandler
	public void onLogin(PlayerLoginEvent e){
		Player p = e.getPlayer();
		ItemStack is = p.getInventory().getBoots();
		if(is != null){
			if(EnchantManager.hasEnchant(is, "flash")){
				ListUtils.flashed.add(p);
			}
		}
	}
	
	
}
