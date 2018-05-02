package me.Smc.eg.enchants;

import java.util.ArrayList;
import java.util.List;

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
						if(mat == Material.GLOWING_REDSTONE_ORE) block.setType(Material.REDSTONE_ORE);
						Location loc = block.getLocation();
						final VeinScanner v = new VeinScanner(block);
						Thread t = new Thread("Vein Scanner") {
							public void run() {
								v.start(false);
							}
						};
						t.start();
						try {
							t.join();
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
						List<Block> blocks = v.getVein();
						ArrayList<Material> mats = new ArrayList<Material>();
						mats.add(mat);
						Utils.breakCheck(blocks, player, item, loc, mats, true);
					}
				}else if(EnchantManager.getEnchantLevel(item, this) >= getIntOption("requiredlevel-for-AOE")) {
					if(value == 1.0) {
						Location loc = player.getLocation();
						List<Block> list = Utils.getNearbyBlocks(loc, (int) Math.floor(EnchantManager.getEnchantLevel(item, this) * getDoubleOption("AOE-range-multiplier")));
						for(Block b : new ArrayList<>(list)) {
							if(b.getType() == Material.GLOWING_REDSTONE_ORE) {
								b.setType(Material.REDSTONE_ORE);
							}
							if(!Utils.isOre(b)) {
								list.remove(b);
							}
						}
						final VeinScanner v = new VeinScanner(list);
						Thread t = new Thread("Vein Scanner") {
							public void run() {
								v.start(true);
							}
						};
						t.start();
						try {
							t.join();
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
						List<Block> blocks = v.getVein();
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
							if(mat == Material.GLOWING_REDSTONE_ORE) block.setType(Material.REDSTONE_ORE);
							Location loc = block.getLocation();
							final VeinScanner v = new VeinScanner(block);
							Thread t = new Thread("Vein Scanner") {
								public void run() {
									v.start(false);
								}
							};
							t.start();
							try {
								t.join();
							} catch (InterruptedException e) {
								e.printStackTrace();
							}
							List<Block> blocks = v.getVein();
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

class VeinScanner{
	
	Block initial;
	List<Block> vein = new ArrayList<>();
	
	public VeinScanner(Block b) {
		this.initial = b;
	}
	
	public VeinScanner(List<Block> blocks) {
		for(Block b : blocks) {
			vein.add(b);
		}
		
	}
	
	public void start(boolean b) {
		estimate(b);
	}
	
	public List<Block> getVein(){
		return vein;
	}
	
	private void estimate(boolean type) {
		if(!type) {
			for(Block b : Utils.getAdjacentBlocks(initial)) {
				if(b.getType() == initial.getType()) {
					vein.add(b);
				}
			}
			estimate(true);
		}else {
			int i = vein.size();
			List<Block> copy = new ArrayList<>(vein);
			for(Block b : copy) {
				for(Block bl : Utils.getAdjacentBlocks(b)) {
					if(bl.getType() == b.getType() && !vein.contains(bl)) {
						vein.add(bl);
					}
				}
			}
			if(vein.size() > i) {
				estimate(true);
			}else {
				return;
			}
		}
	}
}
