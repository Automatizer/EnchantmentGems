package me.Smc.eg.utils;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;

import me.Smc.eg.enchants.EnchantManager;
import me.Smc.eg.main.Main;


//stands for Global Tick Loops
public class GTL {
	
	final static Plugin pl = Main.plugin;
	
	public static void startLoops() {
		fiveTicks();
	}

	private static void fiveTicks() {
		new BukkitRunnable() {
			public void run() {
				for(Player p : Bukkit.getServer().getOnlinePlayers()) {
					if(p.getInventory().getChestplate() != null && EnchantManager.hasEnchant(p.getInventory().getChestplate(), "magnet")) {
						EnchantManager.callEvent(p.getInventory().getChestplate(), "onMove", p, null, 0.0, null);
					}
				}
			}
		}.runTaskTimer(pl, 0, 5);
	}
}
