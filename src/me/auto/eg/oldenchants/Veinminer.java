package me.auto.eg.oldenchants;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

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