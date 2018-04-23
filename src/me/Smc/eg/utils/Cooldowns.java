package me.Smc.eg.utils;

import java.util.HashMap;

import org.bukkit.entity.Player;

public class Cooldowns {

	public static HashMap<Player, String> enchants = new HashMap<Player, String>();
	public static HashMap<Player, Integer> moveListener = new HashMap<Player, Integer>();
	
	public static void addEnchantCooldown(String enchantName, Player player) {
		enchants.put(player, enchantName);
	}
	
	public static void removeEnchantCooldown(String enchantName, Player player) {
		enchants.remove(player, enchantName);
	}
	
	public static boolean isEnchantOnCooldown(String enchantName, Player player) {
		if(enchants.containsKey(player)) {
			if(enchants.get(player).contains(enchantName)) return true;
		}
		return false;
	}
	
	public static void addMoveListenerCooldown(Player p, int i) {
		moveListener.put(p, i);
	}
	
	public static boolean isMoveListenerOnCooldown(Player p) {
		if(moveListener.containsKey(p)) { return true; }
		return false;
	}
	
}
