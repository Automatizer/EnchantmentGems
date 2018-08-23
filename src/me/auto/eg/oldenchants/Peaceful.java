package me.auto.eg.oldenchants;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class Peaceful extends Enchant{

	public Peaceful() {
		super("peaceful");
	}

	@Override
	public void setDefaults() {
		typesAllowed.add("chestplate");
		displayName = "&6Peaceful {enchantlevel}";
		maxLevel = 1;
		event = "entityTarget";
		crystal = new Crystal(this);
		crystal.material = Material.EMERALD;
		crystal.displayName = "&6Peaceful {enchantlevel}";
	}

	@Override
	public void callEvent(ItemStack item, Player player, Entity entity, double value, Block block) {
		return;
	}

	@Override
	public void startup() {
		// TODO Auto-generated method stub
		
	}

}
