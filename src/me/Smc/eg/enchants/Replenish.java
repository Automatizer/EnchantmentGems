package me.Smc.eg.enchants;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.material.MaterialData;

public class Replenish extends Enchant{

	public Replenish(){
		super("replenish");
	}

	@Override
	public void setDefaults(){
		typesAllowed.add("chestplate");
		displayName = "&7Replenish {enchantlevel}";
		maxLevel = 1;
		event = "onHunger";
		permission = "eg.enchant.replenish.#";
		crystal = new Crystal(this);
		crystal.displayName = "&3Replenish {enchantlevel}";
		crystal.material = new MaterialData(Material.EMERALD);
	}

	@Override
	public void callEvent(ItemStack item, final Player player, Entity target, double value, Block block){
		if(player.getInventory().getChestplate() == null){return;}
		if(EnchantManager.getEnchants(player.getInventory().getChestplate()).isEmpty()){return;}
		if(EnchantManager.hasEnchant(item, "replenish")) {
			player.setFoodLevel(20);
			player.setSaturation(20f);
		}
	}
	
}
