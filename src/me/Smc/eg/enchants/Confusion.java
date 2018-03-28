package me.Smc.eg.enchants;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.material.MaterialData;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import me.Smc.eg.utils.Utils;

public class Confusion extends Enchant{

	public Confusion(){
		super("confusion");
	}

	@Override
	public void setDefaults(){
		typesAllowed.add("sword"); typesAllowed.add("axe"); typesAllowed.add("bow");
		displayName = "&7Confusion {enchantlevel}";
		maxLevel = 1;
		event = "attackEntity";
		permission = "eg.enchant.confusion.#";
		crystal = new Crystal(this);
		crystal.displayName = "&3Confusion {enchantlevel}";
		crystal.material = new MaterialData(Material.EMERALD);
		setOption("duration", "20");
		setOption("durationGainPerEnchantLevel", "0");
		setOption("firstEffectLevel", "1");
		setOption("effectLevelGainPerEnchantLevel", "1");
		setOption("giveToOpponent", "true");
		setOption("hitPercentage", "5");
		setOption("hitPercentageGainPerEnchantLevel", "5");
	}

	@Override
	public void callEvent(ItemStack item, Player player, Entity target, double value, Block block){
		int level = EnchantManager.getEnchantLevel(item, this);
		int hitPercentage = (int) (getDoubleOption("hitPercentage") + getDoubleOption("hitPercentageGainPerEnchantLevel") * (level - 1));
		boolean canFire = Utils.randomBetween(0, 100) <= hitPercentage ? true : false;
		if(canFire){
			int effectLevel = getIntOption("firstEffectLevel") + (level - 1) * getIntOption("effectLevelGainPerEnchantLevel");
			if(event.equalsIgnoreCase("attackEntity"))
				if(getBooleanOption("giveToOpponent")){addConfusion(target, effectLevel); return;}
			addConfusion(player, effectLevel);	
		}
	}
	
	/**
	 * Adds confusion to the entity
	 * 
	 * @param entity The entity to apply confusion to
	 * @param level The level of the effect
	 */
	
	public void addConfusion(Entity entity, int level){
		((LivingEntity) entity).addPotionEffect(
				new PotionEffect(PotionEffectType.CONFUSION, (getIntOption("duration") + getIntOption("durationGainPerEnchantLevel") * (level - 1)) * 20, level - 1));
	}
	
}
