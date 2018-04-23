package me.Smc.eg.enchants;

import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.FallingBlock;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.material.MaterialData;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class Leaping extends Enchant{
	
	public Leaping(){
		super("leaping");
	}

	@Override
	public void setDefaults(){
		typesAllowed.add("leggings");
		displayName = "&7Leaping {enchantlevel}";
		maxLevel = 20;
		event = "onLeap";
		permission = "eg.enchant.leaping.#";
		crystal = new Crystal(this);
		crystal.displayName = "&3Leaping {enchantlevel}";
		crystal.material = new MaterialData(Material.EMERALD);
		setOption("velocity-divider", "2");
	}

	@Override
	public void callEvent(ItemStack item, Player player, Entity target, double value, Block block){
		if(EnchantManager.hasEnchant(item, this.name)) {
			int level = EnchantManager.getEnchantLevel(item, this);
			jump(player, level);
		}
	}
	
	public static void addEffect(Player p, ItemStack is) {
		int level = EnchantManager.getEnchantLevel(is, EnchantManager.getEnchant("leaping"));
		int i;
		switch(level) {
		case 1: 
		case 2:
		case 3:
		case 4:
		case 5: i = 1; break;
		case 6:
		case 7:
		case 8:
		case 9:
		case 10: i = 2; break;
		case 11:
		case 12:
		case 13:
		case 14:
		case 15: i = 3; break;
		case 16:
		case 17:
		case 18:
		case 19:
		case 20: i = 4; break;
		default: i = 1;
		}
		p.addPotionEffect(new PotionEffect(PotionEffectType.JUMP, Integer.MAX_VALUE, i - 1));
	}
	
	private void jump(Player p, int i) {
		FallingBlock fb = p.getWorld().spawnFallingBlock(p.getEyeLocation(), new MaterialData(Material.PISTON_MOVING_PIECE));
		fb.setVelocity(p.getLocation().getDirection().multiply(i / getDoubleOption("velocity-divider")));
		fb.addPassenger(p);
		p.playSound(p.getLocation(), Sound.ENTITY_GENERIC_EXPLODE, 1, 1);
	}
	
}
