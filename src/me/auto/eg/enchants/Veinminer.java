package me.auto.eg.enchants;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.material.MaterialData;
import org.bukkit.scheduler.BukkitRunnable;

import me.auto.eg.main.Main;
import me.auto.eg.utils.Utils;

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
		setOption("maximum-range", "35");
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
						final VeinScanner v = new VeinScanner(block, block.getLocation(), getIntOption("maximum-range"));
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
						final VeinScanner v = new VeinScanner(list, player.getLocation(), getIntOption("maximum-range"));
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
						List<Material> mats = new ArrayList<Material>();
						String[] s = getOption("AOE-materials").split(", ");
						for(String name : s) {
							Material m = Material.getMaterial(name);
							mats.add(m);
						}
						Utils.breakCheck(blocks, player, item, loc, mats, true);
						new BukkitRunnable() {
							public void run() {
								Magnet.getInstance().magnetize(player, getIntOption("maximum-range") + 5);
							}
						}.runTaskLater(Main.plugin, 10);
					}else {
						if(Utils.isOre(block)) {
							Material mat = block.getType();
							if(mat == Material.GLOWING_REDSTONE_ORE) block.setType(Material.REDSTONE_ORE);
							Location loc = block.getLocation();
							final VeinScanner v = new VeinScanner(block, block.getLocation(), getIntOption("maximum-range"));
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

	@Override
	public void startup() {
		
	}
}

class VeinScanner{
	
	Block initial;
	Location initialLoc;
	int range;
	List<Block> vein = new ArrayList<>();
	
	public VeinScanner(Block b, Location loc, int range) {
		this.initial = b;
		this.initialLoc = loc;
		this.range = range;
	}
	
	public VeinScanner(List<Block> blocks, Location loc, int range) {
		for(Block b : blocks) {
			vein.add(b);
		}
		this.initialLoc = loc;
		this.range = range;
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
				Material mat = b.getType();
				Material iniMat = initial.getType();
				if(mat == Material.GLOWING_REDSTONE_ORE) mat = Material.REDSTONE_ORE;
				if(iniMat == Material.GLOWING_REDSTONE_ORE) iniMat = Material.REDSTONE_ORE;
				if(mat == iniMat) {
					vein.add(b);
				}
			}
			estimate(true);
		}else {
			int i = vein.size();
			List<Block> copy = new ArrayList<>(vein);
			for(Block b : copy) {
				for(Block bl : Utils.getAdjacentBlocks(b)) {
					Material mat = bl.getType();
					if(mat == Material.GLOWING_REDSTONE_ORE) mat = Material.REDSTONE_ORE;
					if(bl.getType() == b.getType() && !vein.contains(bl) && (bl.getLocation().distance(initialLoc) < range)) {
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
