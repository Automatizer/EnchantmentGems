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
import org.bukkit.scheduler.BukkitRunnable;

import me.Smc.eg.main.Main;

public class Nightvision extends Enchant{

	public Nightvision(){
		super("nightvision");
	}

	@SuppressWarnings("deprecation")
	@Override
	public void setDefaults(){
		typesAllowed.add("helmet");
		displayName = "&7Nightvision {enchantlevel}";
		maxLevel = 1;
		event = "onEquip";
		permission = "eg.enchant.nightvision.#";
		crystal = new Crystal(this);
		crystal.displayName = "&3Nightvision {enchantlevel}";
		crystal.material = new MaterialData(Material.EMERALD, (byte) 0);
		setOption("effectLevel", "1");
	}
	
	@Override
	public void callEvent(ItemStack item, final Player player, Entity target, double value, Block block){
		int level = EnchantManager.getEnchantLevel(item, this);
		addNightvision(player, level);
		new BukkitRunnable(){
			public void run(){
				if(player.getInventory().getHelmet() == null){player.removePotionEffect(PotionEffectType.NIGHT_VISION); cancel(); return;}
				ItemStack helmet = player.getInventory().getHelmet();
				if(EnchantManager.getEnchants(helmet).isEmpty()){player.removePotionEffect(PotionEffectType.NIGHT_VISION); cancel(); return;}
				boolean contained = false;
				for(Enchant enchant : EnchantManager.getEnchants(helmet))
					if(enchant.getName().equalsIgnoreCase(getName()))
						contained = true;
				if(!contained){player.removePotionEffect(PotionEffectType.NIGHT_VISION); cancel(); return;}
			}
		}.runTaskLater(Main.plugin, 4);
	}
	
	/**
	 * Adds nightvision to the entity
	 * 
	 * @param entity The entity to apply nightvision to
	 * @param level The level of the effect
	 */
	
	public void addNightvision(Entity entity, int level){
		((LivingEntity) entity).addPotionEffect(new PotionEffect(PotionEffectType.NIGHT_VISION, Integer.MAX_VALUE, level - 1));
	}

}
