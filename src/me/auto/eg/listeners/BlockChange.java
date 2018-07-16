package me.auto.eg.listeners;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockFromToEvent;
import org.bukkit.plugin.Plugin;

public class BlockChange implements Listener{

	Plugin pl;
	
	public BlockChange(Plugin plugin) {
		pl = plugin;
		Bukkit.getServer().getPluginManager().registerEvents(this, pl);
	}
	
	@EventHandler
	public void onChange(BlockFromToEvent e) {
		Block to = e.getToBlock();
		if(to.getType() == Material.GLOWING_REDSTONE_ORE) {
			to.setType(Material.REDSTONE_ORE);
		}
	}
	
}
