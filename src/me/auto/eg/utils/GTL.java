package me.auto.eg.utils;

import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;

import me.auto.eg.enchants.EnchantManager;
import me.auto.eg.main.Main;


//stands for Global Tick Loops
public class GTL {
	
	final static Plugin pl = Main.plugin;
	
	public static void startLoops() {
		oneTick();
		fiveTicks();
	}

	private static void fiveTicks() {
		new BukkitRunnable() {
			public void run() {
				for(Player p : Bukkit.getServer().getOnlinePlayers()) {
					if(p.getInventory().getChestplate() != null && EnchantManager.hasEnchant(p.getInventory().getChestplate(), "magnet")) {
						EnchantManager.callEvent(p.getInventory().getChestplate(), "magnet", p, null, 0.0, null);
					}
				}
			}
		}.runTaskTimer(pl, 0, 5);
	}
	
	private static void oneTick() {
		HashMap<Player, Integer> map = Cooldowns.moveListener;
		new BukkitRunnable() {
			public void run() {
				for(Player p: map.keySet()) {
					if(map.get(p) > 0) {
						map.put(p, map.get(p) - 1);
					}
					else {
						map.remove(p);
					}
				}
			}
		}.runTaskTimer(pl, 0, 1);
	}
}
