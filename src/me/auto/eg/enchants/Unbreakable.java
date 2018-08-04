package me.auto.eg.enchants;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.material.MaterialData;

public class Unbreakable extends Enchant{

	public Unbreakable(){
		super("unbreakable");
	}

	@Override
	public void setDefaults(){
		typesAllowed.add("sword"); typesAllowed.add("axe"); typesAllowed.add("hoe"); typesAllowed.add("shovel"); typesAllowed.add("pickaxe"); typesAllowed.add("bow"); typesAllowed.add("fishing");
		typesAllowed.add("helmet"); typesAllowed.add("chestplate"); typesAllowed.add("leggings"); typesAllowed.add("boots");
		typesAllowed.add("other");
		
		displayName = "&7Unbreakable {enchantlevel}";
		maxLevel = 1;
		event = "switchToItem";
		permission = "eg.enchant.unbreakable.#";
		crystal = new Crystal(this);
		crystal.displayName = "&3Unbreakable {enchantlevel}";
		crystal.material = new MaterialData(Material.EMERALD);
	}

	@Override
	public void callEvent(ItemStack item, Player player, Entity target, double value, Block block){
		if(EnchantManager.hasEnchant(item, this.name)) {
			if(EnchantManager.getEnchantLevel(item, this) > 1) {
				EnchantManager.removeEnchant(this, item);
				EnchantManager.addEnchantToItem(item, this, 1);
			}
		}
	}

	@Override
	public void startup() {
		// TODO Auto-generated method stub
		
	}
	
}
