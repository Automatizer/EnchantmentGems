package me.auto.eg.oldenchants;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import me.auto.eg.utils.Utils;

public class Slowness extends Enchant{

	public Slowness(){
		super("slowness");
	}

	@Override
	public void setDefaults(){
		typesAllowed.add("sword"); typesAllowed.add("axe"); typesAllowed.add("bow");
		displayName = "&7Slowness {enchantlevel}";
		maxLevel = 3;
		event = "attackEntity";
		permission = "eg.enchant.slowness.#";
		crystal = new Crystal(this);
		crystal.displayName = "&3Slowness {enchantlevel}";
		crystal.material = Material.EMERALD;
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
				if(getBooleanOption("giveToOpponent")){addSlowness(target, effectLevel); return;}
			addSlowness(player, effectLevel);	
		}
	}
	
	/**
	 * Adds slowness to the entity
	 * 
	 * @param entity The entity to apply slowness to
	 * @param level The level of the effect
	 */
	
	public void addSlowness(Entity entity, int level){
		((LivingEntity) entity).addPotionEffect(
				new PotionEffect(PotionEffectType.SLOW, (getIntOption("duration") + getIntOption("durationGainPerEnchantLevel") * (level - 1)) * 20, level - 1));
	}

	@Override
	public void startup() {
		// TODO Auto-generated method stub
		
	}

}