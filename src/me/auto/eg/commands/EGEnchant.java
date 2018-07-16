package me.auto.eg.commands;

import org.bukkit.inventory.ItemStack;

import me.auto.eg.enchants.Enchant;
import me.auto.eg.enchants.EnchantManager;

public class EGEnchant {

	public static void execute(ItemStack is, Enchant e, int level) {
		if(is != null && e != null && level != 0) {
			EnchantManager.addEnchantToItem(is, e, level);
		}
	}
	
}
