package me.auto.eg.oldenchants;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

import me.auto.eg.main.Main;

public class Flash extends Enchant{

	public Flash(){
		super("flash");
	}

	@Override
	public void setDefaults(){
		typesAllowed.add("boots");
		displayName = "&7Flash {enchantlevel}";
		maxLevel = 2;
		event = "onEquip";
		permission = "eg.enchant.flash.#";
		crystal = new Crystal(this);
		crystal.displayName = "&3Flash {enchantlevel}";
		crystal.material = Material.EMERALD;
		setOption("basePercentage", "10");
		setOption("percentageGainPerEnchantLevel", "15");
	}

	@Override
	public void callEvent(ItemStack item, final Player player, Entity target, double value, Block block){
		if(EnchantManager.hasEnchant(item, this.name)) {
			int level = EnchantManager.getEnchantLevel(item, this);
			addFlash(player, level);
			new BukkitRunnable(){
				public void run(){
					if(player.getInventory().getBoots() == null){player.removePotionEffect(PotionEffectType.SPEED); cancel(); return;}
					ItemStack boots = player.getInventory().getBoots();
					if(EnchantManager.getEnchants(boots).isEmpty()){player.removePotionEffect(PotionEffectType.SPEED); cancel(); return;}
					boolean contained = false;
					for(Enchant enchant : EnchantManager.getEnchants(boots))
						if(enchant.getName().equalsIgnoreCase(getName()))
							contained = true;
					if(!contained){player.removePotionEffect(PotionEffectType.SPEED); cancel(); return;}
				}
			}.runTaskLater(Main.plugin, 4);
			if(value == 1.0) {
				removeFlash(player);
			}
		}
	}
	
	/**
	 * Adds flash to the entity
	 * 
	 * @param entity The entity to apply flash to
	 * @param level The level of the effect
	 */
	
	public void addFlash(Player player, int level){
		player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, Integer.MAX_VALUE, level - 1));
	}
	
	public static void removeFlash(Player p){
		p.removePotionEffect(PotionEffectType.SPEED);
	}

	@Override
	public void startup() {
		// TODO Auto-generated method stub
		
	}

	
}
