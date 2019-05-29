package me.auto.eg.oldenchants;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import me.auto.eg.utils.SmeltResult;
import me.auto.eg.utils.Utils;

public class AutoSmelt extends Enchant{

	public AutoSmelt() {
		super("autosmelt");
	}

	@Override
	public void setDefaults() {
		typesAllowed.add("pickaxe"); typesAllowed.add("shovel"); typesAllowed.add("axe"); typesAllowed.add("hoe"); typesAllowed.add("sword");
		displayName = "&7Autosmelt {enchantlevel}";
		maxLevel = 5;
		event = "autosmelt";
		crystal = new Crystal(this);
		crystal.material = Material.EMERALD;
		crystal.displayName = "&3Autosmelt {enchantlevel}";
		registerOptions();
		setOption("chance-percent-per-level", "20");
	}

	@Override
	public void callEvent(ItemStack item, Player player, Entity entity, double value, Block block) {
		if(EnchantManager.hasEnchant(item, this.name)) {
			if(chance(EnchantManager.getEnchantLevel(item, this))) {
				int i = (int) Math.floor(value);
				switch(i) {
				case 0: processBlock(player, block, item); break;
				case 1: processEntity(player, entity, item); break;
				default: break;
				}
			}
		}
	}

	@Override
	public void startup() {
		
	}
	
	public boolean isEnabled(Material mat) {
		return getBooleanOption("materials." + mat.name() + ".enabled");
	}
	
	private boolean chance(int level) {
		int i = Utils.randomBetween(0, 100);
		return i < (getIntOption("chance-percent-per-level") * level);
	}
	
	private void processBlock(Player p, Block b, ItemStack is) {
		
	}
	
	private void processEntity(Player p, Entity e, ItemStack is) {
		
	}
	
	private void registerOptions() {
		String s = "materials.";
		for(Material m : Material.values()) {
			if(SmeltResult.get(m) != null) {
				setOption(s + m.name() + ".enabled", "true");
			}
		}
	}

}
