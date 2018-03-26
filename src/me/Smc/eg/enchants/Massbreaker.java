package me.Smc.eg.enchants;

import java.util.ArrayList;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.material.MaterialData;

import me.Smc.eg.main.Main;
import me.Smc.eg.utils.Utils;

public class Massbreaker extends Enchant{

	public Massbreaker() {
		super("massbreaker");
	}

	@SuppressWarnings("deprecation")
	@Override
	public void setDefaults() {
		typesAllowed.add("pickaxe");
		displayName = "&7Massbreaker {enchantlevel}";
		maxLevel = 1;
		event = "blockBreak";
		crystal = new Crystal(this);
		crystal.displayName = "&3Massbreaker {enchantlevel}";
		crystal.material = new MaterialData(Material.EMERALD, (byte) 0);
	}

	@Override
	public void callEvent(ItemStack item, Player player, Entity entity, double value, Block block) {
		if(EnchantManager.hasEnchant(item, "massbreaker")) {
			if(isEnabled(player)) {
				for(Block b : getBlocks(block.getLocation(), EnchantManager.getEnchantLevel(item, this))) {
					if(!((b.getType().equals(Material.AIR)) || (b.getType().equals(Material.BEDROCK)) || (b.getType().equals(Material.WATER)) || (b.getType().equals(Material.LAVA)))) {
						if(item.getItemMeta().hasEnchant(Enchantment.SILK_TOUCH)) {
							b.getWorld().dropItem(b.getLocation(), new ItemStack(b.getType()));
							b.setType(Material.AIR);
						}else {
							b.breakNaturally();
						}
					}
				}
			}
		}
	}
	
	private ArrayList<Block> getBlocks(Location loc, int radius){
		ArrayList<Block> blocks = Utils.getNearbyBlocks(loc, radius);
		return blocks;
	}
	
	private boolean isEnabled(Player p) {
		if(Main.massbreakers.contains(p)) {
			return true;
		}else return false;
	}

}
