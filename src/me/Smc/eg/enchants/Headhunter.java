package me.Smc.eg.enchants;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.material.MaterialData;

import me.Smc.eg.utils.Settings;
import me.Smc.eg.utils.Utils;

public class Headhunter extends Enchant{

	public Headhunter(){
		super("headhunter");
	}

	@SuppressWarnings("deprecation")
	@Override
	public void setDefaults(){
		typesAllowed.add("sword"); typesAllowed.add("axe");
		displayName = "&7Headhunter {enchantlevel}";
		maxLevel = 3;
		event = "killedEntity";
		permission = "eg.enchant.headhunter.#";
		crystal = new Crystal(this);
		crystal.displayName = "&3Headhunter {enchantlevel}";
		crystal.material = new MaterialData(Material.EMERALD, (byte) 0);
		setOption("hitPercentage", "20");
		setOption("hitPercentageGainPerEnchantLevel", "20");
		setOption("dropSkullOnFloor", "true");
	}

	@SuppressWarnings("deprecation")
	@Override
	public void callEvent(ItemStack item, Player player, Entity entity, double value, Block block){
		if(entity instanceof Player){
			int level = EnchantManager.getEnchantLevel(item, this);
			int hitPercentage = (int) (getDoubleOption("hitPercentage") + getDoubleOption("hitPercentageGainPerEnchantLevel") * (level - 1));
			boolean canFire = Utils.randomBetween(0, 100) <= hitPercentage ? true : false;
			if(canFire){
				Player target = (Player) entity;
		    	ItemStack skull = new ItemStack(Material.SKULL_ITEM, 1, (short) 3);
		        SkullMeta meta = (SkullMeta) skull.getItemMeta();
		        meta.setDisplayName(String.format(Settings.getInstance().getMessage("Player-Head-Title-Format"), target.getName()));
		        meta.setOwner(target.getName());
		        skull.setItemMeta(meta);
		        if(getBooleanOption("dropSkullOnFloor")) target.getWorld().dropItem(target.getLocation(), skull);
		        else player.getInventory().addItem(skull);
		        Bukkit.broadcastMessage(String.format(Settings.getInstance().getMessage("Headhunter-Head-Drop"), player.getName(), target.getName()));
			}
		}
	}

}
