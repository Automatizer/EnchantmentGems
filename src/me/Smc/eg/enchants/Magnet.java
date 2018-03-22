package me.Smc.eg.enchants;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.ExperienceOrb;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.material.MaterialData;

import me.Smc.eg.utils.Utils;

public class Magnet extends Enchant{

	public Magnet(){
		super("magnet");
	}

	@SuppressWarnings("deprecation")
	@Override
	public void setDefaults(){
		typesAllowed.add("chestplate");
		displayName = "&7Magnet {enchantlevel}";
		maxLevel = 5;
		event = "onMove";
		permission = "eg.enchant.magnet.#";
		crystal = new Crystal(this);
		crystal.displayName = "&3Magnet {enchantlevel}";
		crystal.material = new MaterialData(Material.EMERALD, (byte) 0);
		setOption("defaultRange", "5");
		setOption("extraRangePerLevel", "1");
		setOption("speedPerLevel", "0.25");
	}

	@Override
	public void callEvent(ItemStack item, Player player, Entity target, double value, Block block){
		int level = EnchantManager.getEnchantLevel(item, this);
		int range = getIntOption("defaultRange") + (level - 1) * getIntOption("extraRangePerLevel");
		Location origin = player.getLocation();
		for(Entity entity : Utils.getNearbyEntities(player.getLocation(), range))
			if((entity instanceof Item) || (entity instanceof ExperienceOrb)){
				entity.teleport(origin);
			}
	}
}
