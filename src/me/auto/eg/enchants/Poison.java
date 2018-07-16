package me.auto.eg.enchants;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.material.MaterialData;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import me.auto.eg.utils.Utils;

public class Poison extends Enchant{

	public Poison(){
		super("poison");
	}

	@Override
	public void setDefaults(){
		typesAllowed.add("sword"); typesAllowed.add("axe");
		displayName = "&7Poison {enchantlevel}";
		maxLevel = 3;
		event = "attackEntity";
		permission = "eg.enchant.poison.#";
		crystal = new Crystal(this);
		crystal.displayName = "&3Poison {enchantlevel}";
		crystal.material = new MaterialData(Material.EMERALD);
		setOption("duration", "5");
		setOption("durationGainPerEnchantLevel", "0");
		setOption("firstEffectLevel", "1");
		setOption("effectLevelGainPerEnchantLevel", "1");
		setOption("giveToOpponent", "true");
		setOption("hitPercentage", "100");
		setOption("hitPercentageGainPerEnchantLevel", "0");
	}

	@Override
	public void callEvent(ItemStack item, Player player, Entity target, double value, Block block){
		int level = EnchantManager.getEnchantLevel(item, this);
		int hitPercentage = (int) (getDoubleOption("hitPercentage") + getDoubleOption("hitPercentageGainPerEnchantLevel") * (level - 1));
		boolean canFire = Utils.randomBetween(0, 100) <= hitPercentage ? true : false;
		if(canFire){
			int effectLevel = getIntOption("firstEffectLevel") + (level - 1) * getIntOption("effectLevelGainPerEnchantLevel");
			if(event.equalsIgnoreCase("attackEntity"))
				if(getBooleanOption("giveToOpponent")){addPoison(target, effectLevel); return;}
			addPoison(player, effectLevel);	
		}
	}
	
	/**
	 * Adds poison to the entity
	 * 
	 * @param entity The entity to apply poison to
	 * @param level The level of the effect
	 */
	
	public void addPoison(Entity entity, int level){
		((LivingEntity) entity).addPotionEffect(
				new PotionEffect(PotionEffectType.POISON, (getIntOption("duration") + getIntOption("durationGainPerEnchantLevel") * (level - 1)) * 20, level - 1));
	}

	@Override
	public void startup() {
		// TODO Auto-generated method stub
		
	}
	
}
