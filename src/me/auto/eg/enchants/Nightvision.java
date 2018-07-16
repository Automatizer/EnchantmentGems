package me.auto.eg.enchants;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.material.MaterialData;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

import me.auto.eg.main.Main;

public class Nightvision extends Enchant{

	public Nightvision(){
		super("nightvision");
	}

	@Override
	public void setDefaults(){
		typesAllowed.add("helmet");
		displayName = "&7Nightvision {enchantlevel}";
		maxLevel = 1;
		event = "onEquip";
		permission = "eg.enchant.nightvision.#";
		crystal = new Crystal(this);
		crystal.displayName = "&3Nightvision {enchantlevel}";
		crystal.material = new MaterialData(Material.EMERALD);
		setOption("effectLevel", "1");
	}
	
	@Override
	public void callEvent(ItemStack item, final Player player, Entity target, double value, Block block){
		if(EnchantManager.hasEnchant(item, this.name)) {
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
					if(!contained){removeNightvision(player); cancel(); return;}
				}
			}.runTaskLater(Main.plugin, 4);
			if(value == 1.0) {
				removeNightvision(player);
			}
		}
	}
	
	/**
	 * Adds nightvision to the entity
	 * 
	 * @param entity The entity to apply nightvision to
	 * @param level The level of the effect
	 */
	
	public void addNightvision(Player p, int level){
		p.addPotionEffect(new PotionEffect(PotionEffectType.NIGHT_VISION, Integer.MAX_VALUE, level - 1));
	}
	
	private void removeNightvision(Player p) {
		p.removePotionEffect(PotionEffectType.NIGHT_VISION);
	}

	@Override
	public void startup() {
		// TODO Auto-generated method stub
		
	}

}
