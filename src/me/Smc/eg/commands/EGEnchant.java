package me.Smc.eg.commands;

import org.bukkit.inventory.ItemStack;

import me.Smc.eg.enchants.Enchant;
import me.Smc.eg.enchants.EnchantManager;

public class EGEnchant {

	public static void execute(ItemStack is, Enchant e, int level) {
		if(is != null && e != null && level != 0) {
			EnchantManager.addEnchantToItem(is, e, level);
		}
	}
	
}
