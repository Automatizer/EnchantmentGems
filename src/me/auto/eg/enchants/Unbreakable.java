package me.auto.eg.enchants;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.material.MaterialData;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

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
		maxLevel = 3;
		event = "switchToItem";
		permission = "eg.enchant.unbreakable.#";
		crystal = new Crystal(this);
		crystal.displayName = "&3Unbreakable {enchantlevel}";
		crystal.material = new MaterialData(Material.EMERALD);
		setOption("firstMiningFatigueLevel", "1");
		setOption("miningFatigueOffPerLevel", "1");
	}

	@Override
	public void callEvent(ItemStack item, Player player, Entity target, double value, Block block){
		int level = EnchantManager.getEnchantLevel(item, this);
		if(level < 3)
			player.addPotionEffect(new PotionEffect(PotionEffectType.SLOW_DIGGING, Integer.MAX_VALUE, getIntOption("firstMiningFatigueLevel") - getIntOption("miningFatigueOffPerLevel") * (level - 1)));
	}

	@Override
	public void startup() {
		// TODO Auto-generated method stub
		
	}
	
}
