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

public class Starvation extends Enchant{

	public Starvation(){
		super("starvation");
	}

	@Override
	public void setDefaults(){
		typesAllowed.add("sword"); typesAllowed.add("axe"); typesAllowed.add("bow");
		displayName = "&7Starvation {enchantlevel}";
		maxLevel = 3;
		event = "attackEntity";
		permission = "eg.enchant.starvation.#";
		crystal = new Crystal(this);
		crystal.displayName = "&3Starvation {enchantlevel}";
		crystal.material = Material.EMERALD;
		setOption("duration", "5");
		setOption("durationGainPerEnchantLevel", "0");
		setOption("firstEffectLevel", "1");
		setOption("effectLevelGainPerEnchantLevel", "1");
		setOption("giveToOpponent", "true");
		setOption("hitPercentage", "2");
		setOption("hitPercentageGainPerEnchantLevel", "2");
	}

	@Override
	public void callEvent(ItemStack item, Player player, Entity target, double value, Block block){
		int level = EnchantManager.getEnchantLevel(item, this);
		int hitPercentage = (int) (getDoubleOption("hitPercentage") + getDoubleOption("hitPercentageGainPerEnchantLevel") * (level - 1));
		boolean canFire = Utils.randomBetween(0, 100) <= hitPercentage ? true : false;
		if(canFire){
			int effectLevel = getIntOption("firstEffectLevel") + (level - 1) * getIntOption("effectLevelGainPerEnchantLevel");
			if(event.equalsIgnoreCase("attackEntity"))
				if(getBooleanOption("giveToOpponent")){addHunger(target, effectLevel); return;}
			addHunger(player, effectLevel);	
		}
	}
	
	/**
	 * Adds hunger to the entity
	 * 
	 * @param entity The entity to apply starvation to
	 * @param level The level of the effect
	 */
	
	public void addHunger(Entity entity, int level){
		((LivingEntity) entity).addPotionEffect(
				new PotionEffect(PotionEffectType.HUNGER, (getIntOption("duration") + getIntOption("durationGainPerEnchantLevel") * (level - 1)) * 20, level - 1));
	}

	@Override
	public void startup() {
		// TODO Auto-generated method stub
		
	}

}
