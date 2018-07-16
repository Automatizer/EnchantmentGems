package me.auto.eg.enchants;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.material.MaterialData;

import me.auto.eg.utils.Utils;

public class Lifesteal extends Enchant{

	public Lifesteal(){
		super("lifesteal");
	}

	@Override
	public void setDefaults(){
		typesAllowed.add("sword"); typesAllowed.add("axe");
		displayName = "&7Lifesteal {enchantlevel}";
		maxLevel = 3;
		event = "attackEntity";
		permission = "eg.enchant.lifesteal.#";
		crystal = new Crystal(this);
		crystal.displayName = "&3Lifesteal {enchantlevel}";
		crystal.material = new MaterialData(Material.EMERALD);
		setOption("baseHealAmount", "2");
		setOption("healAmountGainPerEnchantLevel", "1");
		setOption("hitPercentage", "2");
		setOption("hitPercentageGainPerEnchantLevel", "2");
	}

	@SuppressWarnings("deprecation")
	@Override
	public void callEvent(ItemStack item, Player player, Entity target, double value, Block block){
		int level = EnchantManager.getEnchantLevel(item, this);
		int hitPercentage = (int) (getDoubleOption("hitPercentage") + getDoubleOption("hitPercentageGainPerEnchantLevel") * (level - 1));
		boolean canFire = Utils.randomBetween(0, 100) <= hitPercentage ? true : false;
		if(canFire){
			double healAmount = getIntOption("baseHealAmount") + getIntOption("healAmountGainPerEnchantLevel") * (level - 1);
			double healthWithHeal = player.getHealth() + healAmount;
			if(healthWithHeal >= player.getMaxHealth()) healthWithHeal = player.getMaxHealth();
			player.setHealth(healthWithHeal);
		}
	}

	@Override
	public void startup() {
		// TODO Auto-generated method stub
		
	}
	
}
