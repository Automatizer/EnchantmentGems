package me.Smc.eg.enchants;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.material.MaterialData;

import me.Smc.eg.utils.Utils;

public class Veinminer extends Enchant{

	public Veinminer() {
		super("veinminer");
	}

	@Override
	public void setDefaults() {
		typesAllowed.add("pickaxe");
		displayName = "&7Veinminer {enchantlevel}";
		maxLevel = 5;
		event = "veinMine";
		crystal = new Crystal(this);
		crystal.displayName = "&3Veinminer {enchantlevel}";
		crystal.material = new MaterialData(Material.EMERALD);
	}

	@Override
	public void callEvent(ItemStack item, Player player, Entity entity, double value, Block block) {
		if(EnchantManager.hasEnchant(item, this.name)) {
			Material mat = block.getType();
			if(mat == Material.GLOWING_REDSTONE_ORE) mat = Material.REDSTONE_ORE;
			if(EnchantManager.getEnchantLevel(item, this) < 5) {
				if(Utils.isOre(block)) {
					Location loc = block.getLocation();
					for(Block b : Utils.getNearbyBlocks(loc, EnchantManager.getEnchantLevel(item, this))) {
						if(Utils.isOre(b) && b.getType() == mat) {
							Utils.breakCheck(b, player, item, loc);
						}
					}
				}
			}else if(EnchantManager.getEnchantLevel(item, this) >= 5) {
				if(value == 1.0) {
					Location loc = player.getLocation();
					for(Block b : Utils.getNearbyBlocks(loc, (int) Math.floor(EnchantManager.getEnchantLevel(item, this) * 1.5))) {
						if(Utils.isOre(b)) {
							Utils.breakCheck(b, player, item, loc);
						}
					}
				}else {
					if(Utils.isOre(block)) {
						Location loc = block.getLocation();
						for(Block b : Utils.getNearbyBlocks(loc, EnchantManager.getEnchantLevel(item, this))) {
							if(Utils.isOre(b) && b.getType() == mat) {
								Utils.breakCheck(b, player, item, loc);
							}
						}
					}
				}
			}
		}
	}
}
