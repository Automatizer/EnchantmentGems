package me.auto.eg.commands;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

public class Trash{

	public static void execute(Player p) {
		Inventory inv = Bukkit.createInventory(p, 54);
		p.openInventory(inv);
	}
	
}
