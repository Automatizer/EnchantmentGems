package me.Smc.eg.enchants;

import java.util.ArrayList;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.material.MaterialData;

import de.tr7zw.itemnbtapi.NBTItem;
import me.Smc.eg.utils.Utils;

public class Massbreaker extends Enchant{

	public Massbreaker() {
		super("massbreaker");
	}

	@Override
	public void setDefaults() {
		typesAllowed.add("pickaxe");
		displayName = "&7Massbreaker {enchantlevel}";
		maxLevel = 1;
		event = "blockBreak";
		crystal = new Crystal(this);
		crystal.displayName = "&3Massbreaker {enchantlevel}";
		crystal.material = new MaterialData(Material.EMERALD);
	}

	@Override
	public void callEvent(ItemStack item, Player player, Entity entity, double value, Block block) {
		if(EnchantManager.hasEnchant(item, "massbreaker")) {
			if(isEnabled(player)) {
				ArrayList<Material> blacklist = new ArrayList<Material>();
				blacklist.add(Material.AIR);
				blacklist.add(Material.BEDROCK);
				blacklist.add(Material.WATER);
				blacklist.add(Material.STATIONARY_WATER);
				blacklist.add(Material.LAVA);
				blacklist.add(Material.STATIONARY_LAVA);
				Utils.breakCheck(getBlocks(block.getLocation(), EnchantManager.getEnchantLevel(item, this)), player, item, block.getLocation(), blacklist, false);
			}
		}
	}
	
	private ArrayList<Block> getBlocks(Location loc, int radius){
		ArrayList<Block> blocks = Utils.getNearbyBlocks(loc, radius);
		return blocks;
	}
	
	private boolean isEnabled(Player p) {
		boolean b = false;
		NBTItem nbti = new NBTItem(p.getInventory().getItemInMainHand());
		if(nbti.hasKey("massbreaker")) {
			b = nbti.getBoolean("massbreaker");
		}
		return b;
	}

	@Override
	public void startup() {
		// TODO Auto-generated method stub
		
	}

}
