package me.Smc.eg.commands;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import me.Smc.eg.enchants.Enchant;
import me.Smc.eg.enchants.EnchantManager;
import me.Smc.eg.utils.Utils;

public class EGAdd{

	@SuppressWarnings("deprecation")
	public static void runCommand(Player player, String[] args){
		
		Enchant enchant = null;
		int level = Utils.stringToInt(args[2]);
		
		switch(args[1]){
		case "unbreakable": enchant = EnchantManager.getEnchant("unbreakable"); break;
		default: break;
		}
		
		if(enchant != null && level != -1) EnchantManager.addEnchantToItem(player.getItemInHand(), enchant, level);
		else player.sendMessage(ChatColor.RED + "Invalid arguments!");
		
	}
	
}
