package me.auto.eg.enchants;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.material.MaterialData;

import me.auto.eg.utils.Utils;

@SuppressWarnings("deprecation")
public class Headhunter extends Enchant{

	public Headhunter(){
		super("headhunter");
	}

	@Override
	public void setDefaults(){
		typesAllowed.add("sword"); typesAllowed.add("axe");
		displayName = "&7Headhunter {enchantlevel}";
		maxLevel = 3;
		event = "killedEntity";
		permission = "eg.enchant.headhunter.#";
		crystal = new Crystal(this);
		crystal.displayName = "&3Headhunter {enchantlevel}";
		crystal.material = new MaterialData(Material.EMERALD);
		setOption("hitPercentage", "10");
		setOption("hitPercentageGainPerEnchantLevel", "10");
	}

	@Override
	public void callEvent(ItemStack item, Player player, Entity entity, double value, Block block){
		if((item != null) && (EnchantManager.hasEnchant(item, this.name))) {
			if(entity != null) {
				int i = Utils.randomBetween(0, 100);
				int chance = getIntOption("hitPercentage") + (getIntOption("hitPercentageGainPerEnchantLevel") * (EnchantManager.getEnchantLevel(item, this)));
				if(i <= chance) {
					EntityType et = entity.getType();
					ItemStack is = null;
					switch(et) {
					case ZOMBIE: 
					case ZOMBIE_VILLAGER: is = new ItemStack(Material.ZOMBIE_HEAD); break;
					case SKELETON: is = new ItemStack(Material.SKELETON_SKULL); break;
					case WITHER_SKELETON: is = new ItemStack(Material.WITHER_SKELETON_SKULL); break;
					default: break;
					}
					if(is != null) {
						entity.getWorld().dropItem(entity.getLocation(), is);
					}
				}
			}
		}
	}

	@Override
	public void startup() {
		// TODO Auto-generated method stub
		
	}

}
