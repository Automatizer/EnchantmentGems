package me.Smc.eg.enchants;

import java.util.ArrayList;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.material.MaterialData;
import org.bukkit.scheduler.BukkitRunnable;

import me.Smc.eg.main.Main;
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
		setOption("required-level-for-AOE", "5");
		setOption("AOE-range-multiplier", "1.5");
		setOption("AOE-materials", "COAL_ORE, DIAMOND_ORE, EMERALD_ORE, GLOWING_REDSTONE_ORE, GOLD_ORE, IRON_ORE, LAPIS_ORE, QUARTZ_ORE, REDSTONE_ORE");
	}

	@Override
	public void callEvent(ItemStack item, Player player, Entity entity, double value, Block block) {
		if(!player.isSneaking()) {
			if(EnchantManager.hasEnchant(item, this.name)) {
				if(EnchantManager.getEnchantLevel(item, this) < getIntOption("required-level-for-AOE")) {
					if(Utils.isOre(block)) {
						Material mat = block.getType();
						if(mat == Material.GLOWING_REDSTONE_ORE) mat = Material.REDSTONE_ORE;
						Location loc = block.getLocation();
						ArrayList<Block> blocks = Utils.getNearbyBlocks(loc, EnchantManager.getEnchantLevel(item, this));
						ArrayList<Material> mats = new ArrayList<Material>();
						mats.add(mat);
						Utils.breakCheck(blocks, player, item, loc, mats, true);
					}
				}else if(EnchantManager.getEnchantLevel(item, this) >= getIntOption("requiredlevel-for-AOE")) {
					if(value == 1.0) {
						Location loc = player.getLocation();
						ArrayList<Block> blocks = Utils.getNearbyBlocks(loc, (int) Math.floor(EnchantManager.getEnchantLevel(item, this) * getDoubleOption("AOE-range-multiplier")));
						ArrayList<Material> mats = new ArrayList<Material>();
						String[] s = getOption("AOE-materials").split(", ");
						for(String name : s) {
							mats.add(Material.getMaterial(name));
						}
						Utils.breakCheck(blocks, player, item, loc, mats, true);
						new BukkitRunnable() {
							public void run() {
								for(Entity e : Utils.getNearbyEntities(player.getLocation(), 15)) {
									if(e.getType().equals(EntityType.DROPPED_ITEM)) {
										e.teleport(loc);
									}
								}
							}
						}.runTaskLater(Main.plugin, 10);
					}else {
						if(Utils.isOre(block)) {
							Material mat = block.getType();
							if(mat == Material.GLOWING_REDSTONE_ORE) mat = Material.REDSTONE_ORE;
							Location loc = block.getLocation();
							ArrayList<Block> blocks = Utils.getNearbyBlocks(loc, EnchantManager.getEnchantLevel(item, this));
							ArrayList<Material> mats = new ArrayList<Material>();
							mats.add(mat);
							Utils.breakCheck(blocks, player, item, loc, mats, true);
						}
					}
				}
			}
		}
	}
}
