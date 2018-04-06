package me.Smc.eg.listeners;

import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerToggleSneakEvent;
import org.bukkit.plugin.Plugin;

import me.Smc.eg.enchants.EnchantManager;

public class SneakEvent implements Listener{

	Plugin plugin;
	
	public SneakEvent(Plugin pl) {
		this.plugin = pl;
		plugin.getServer().getPluginManager().registerEvents(this, plugin);
	}
	
	@EventHandler
	public static void onSneak(PlayerToggleSneakEvent e) {
		Player p = e.getPlayer();
		
		if(e.isSneaking()) {
			EnchantManager.callEvent(p.getInventory().getLeggings(), "onSneak", p, null, 0.0, p.getLocation().getBlock().getRelative(BlockFace.DOWN));
		}else {
			EnchantManager.callEvent(p.getInventory().getLeggings(), "onSneak", p, null, 1.0, p.getLocation().getBlock().getRelative(BlockFace.DOWN));
		}
	}
	
}
