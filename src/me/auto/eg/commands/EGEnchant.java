package me.auto.eg.commands;

import org.bukkit.inventory.ItemStack;

import me.auto.eg.oldenchants.Enchant;
import me.auto.eg.oldenchants.EnchantManager;

public class EGEnchant {

	public static void execute(ItemStack is, Enchant e, int level) {
		if(is != null && e != null && level != 0) {
			EnchantManager.addEnchantToItem(is, e, level);
		}
	}
	
}
