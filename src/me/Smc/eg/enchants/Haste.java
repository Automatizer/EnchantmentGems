package me.Smc.eg.enchants;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.material.MaterialData;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class Haste extends Enchant{
	
	public Haste(){
		super("haste");
	}

	@Override
	public void setDefaults(){
		typesAllowed.add("sword"); typesAllowed.add("axe"); typesAllowed.add("hoe"); typesAllowed.add("shovel"); typesAllowed.add("pickaxe");
		displayName = "&7Haste {enchantlevel}";
		maxLevel = 3;
		event = "switchToItem";
		permission = "eg.enchant.haste.#";
		crystal = new Crystal(this);
		crystal.displayName = "&3Haste {enchantlevel}";
		crystal.material = new MaterialData(Material.EMERALD);
		setOption("firstHasteLevel", "0");
		setOption("hastePerLevel", "1");
	}

	@Override
	public void callEvent(ItemStack item, Player player, Entity target, double value, Block block){
		int level = EnchantManager.getEnchantLevel(item, this);
		player.addPotionEffect(new PotionEffect(PotionEffectType.FAST_DIGGING, Integer.MAX_VALUE, getIntOption("firstHasteLevel") + getIntOption("hastePerLevel") * (level - 1)));
	}

	@Override
	public void startup() {
		// TODO Auto-generated method stub
		
	}
	
}
