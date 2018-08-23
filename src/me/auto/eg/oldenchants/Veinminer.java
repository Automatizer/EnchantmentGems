package me.auto.eg.oldenchants;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import me.auto.eg.main.Main;
import me.auto.eg.utils.Utils;

public class Veinminer extends Enchant{
	
	private static Veinminer instance = null;
	
	public static Veinminer getInstance() {
		if(instance == null) {
			instance = new Veinminer();
		}
		return instance;
	}

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
		crystal.material = Material.EMERALD;
		setOption("required-level-for-AOE", "5");
		setOption("AOE-range-multiplier", "1.5");
		setOption("maximum-blocks", "500");
		setOption("AOE-materials", "COAL_ORE, DIAMOND_ORE, EMERALD_ORE, GLOWING_REDSTONE_ORE, GOLD_ORE, IRON_ORE, LAPIS_ORE, NETHER_QUARTZ_ORE, REDSTONE_ORE");
		setOption("veinmine-sneak-mode", "true");
	}
	
	private static List<Material> mats = new ArrayList<Material>();

	@Override
	public void callEvent(ItemStack item, Player player, Entity entity, double value, Block block) {
		if(EnchantManager.hasEnchant(item, this.name)) {
			if(EnchantManager.getEnchantLevel(item, this) < getIntOption("required-level-for-AOE")) {
				if(works(player)) {
					if(Utils.isOre(block)) {
						Material mat = block.getType();
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
						} catch (InterruptedException ex) {
							ex.printStackTrace();
						}
						List<Block> blocks = v.getVein();
						ArrayList<Material> mats = new ArrayList<Material>();
						mats.add(mat);
						Utils.breakCheck(blocks, player, item, loc, mats, true);
					}
				}
			}else if(EnchantManager.getEnchantLevel(item, this) >= getIntOption("requiredlevel-for-AOE")) {
				if(value == 1.0) {
					Location loc = player.getLocation();
					List<Block> list = Utils.getNearbyBlocks(loc, (int) Math.floor(EnchantManager.getEnchantLevel(item, this) * getDoubleOption("AOE-range-multiplier")));
					for(Block b : new ArrayList<>(list)) {
						if(!mats.contains(b.getType())) {
							list.remove(b);
						}
					}
					final VeinScanner v = new VeinScanner(list, player.getLocation(), getIntOption("maximum-blocks"));
					Thread t = new Thread("Vein Scanner") {
						public void run() {
							v.start(true);
						}
					};
					t.start();
					try {
						t.join();
					} catch (InterruptedException ex) {
						ex.printStackTrace();
					}
					List<Block> blocks = v.getVein();
					Utils.breakCheck(blocks, player, item, loc, mats, true);
					new BukkitRunnable() {
						public void run() {
							Magnet.getInstance().magnetize(player, getIntOption("maximum-range") + 5);
						}
					}.runTaskLater(Main.plugin, 10);
				}else {
					if(getBooleanOption("veinmine-sneak-mode") == player.isSneaking()) {
						if(Utils.isOre(block)) {
							Material mat = block.getType();
							Location loc = block.getLocation();
							final VeinScanner v = new VeinScanner(block, block.getLocation(), getIntOption("maximum-blocks"));
							Thread t = new Thread("Vein Scanner") {
								public void run() {
									v.start(false);
								}
							};
							t.start();
							try {
								t.join();
							} catch (InterruptedException ex) {
								ex.printStackTrace();
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
		String[] s = getOption("AOE-materials").split(", ");
		for(String name : s) { mats.add(Material.getMaterial(name)); }
	}
	
	public static List<Material> getMats() {
		return mats;
	}
	
	public boolean works(Player p) {
		return p.isSneaking() == getBooleanOption("veinmine-sneak-mode");
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
				if(mat == iniMat) {
					vein.add(b);
				}
			}
			estimate(true);
		}else {
			int i = vein.size();
			for(Block b : new ArrayList<>(vein)) {
				for(Block bl : Utils.getAdjacentBlocks(b)) {
					if(bl.getType() == b.getType() && !vein.contains(bl) && (i < range)) {
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