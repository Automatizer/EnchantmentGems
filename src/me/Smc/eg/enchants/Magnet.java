package me.Smc.eg.enchants;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.ExperienceOrb;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.material.MaterialData;

import de.tr7zw.itemnbtapi.NBTItem;
import me.Smc.eg.utils.Utils;

public class Magnet extends Enchant{
	
	private static Magnet instance = null;

	public Magnet(){
		super("magnet");
	}
	
	public static Magnet getInstance() {
		if(instance == null) {
			instance = new Magnet();
		}
		return instance;
	}

	@Override
	public void setDefaults(){
		typesAllowed.add("chestplate");
		displayName = "&7Magnet {enchantlevel}";
		maxLevel = 10;
		event = "magnet";
		permission = "eg.enchant.magnet.#";
		crystal = new Crystal(this);
		crystal.displayName = "&3Magnet {enchantlevel}";
		crystal.material = new MaterialData(Material.EMERALD);
		setOption("defaultRange", "5");
		setOption("extraRangePerLevel", "1");
		setOption("speedPerLevel", "0.25");
		setOption("toggleMaterial", Material.LEVER.name());
	}

	@Override
	public void callEvent(ItemStack item, Player player, Entity target, double value, Block block){
		int level = EnchantManager.getEnchantLevel(item, this);
		int range = getIntOption("defaultRange") + (level - 1) * getIntOption("extraRangePerLevel");
		magnetize(player, range);
	}

	@Override
	public void startup() {
		
	}
	
	private boolean hasToggle(Player p) {
			for(ItemStack item : p.getInventory().getContents()) {
				if((item != null) && (!item.getType().equals(Material.AIR)) && (item.getItemMeta().hasLore()) && (item.getItemMeta().getLore().contains("Magnet Toggler"))) {
					return true;
				}
			}
		return false;
	}
	
	public ItemStack getToggle() {
		ItemStack is = new ItemStack(Material.getMaterial(getOption("toggleMaterial")));
		ItemMeta im = is.getItemMeta();
		List<String> lore = new ArrayList<String>();
		lore.add("Magnet Toggler");
		im.setLore(lore);
		is.setItemMeta(im);
		NBTItem nbti = new NBTItem(is);
		nbti.setString("toggler", "magnet");
		nbti.setBoolean("status", true);
		is = nbti.getItem();
		return is;
	}
	
	public ItemStack getToggle(Player p) {
		for(ItemStack item : p.getInventory().getContents()) {
			if((item != null) && (!item.getType().equals(Material.AIR)) && (item.getItemMeta().hasLore()) && (item.getItemMeta().getLore().contains("Magnet Toggler"))) {
				return item;
			}
		}
		return null;
	}
	
	private boolean isOn(Player p, ItemStack is) {
		NBTItem nbti = new NBTItem(is);
		return nbti.getBoolean("status");
	}
	
	public void magnetize(Player p, int range) {
		if(hasToggle(p)) {
			ItemStack is = getToggle(p);
			if(!isOn(p, is)) {
				return;
			}
		}
		Location loc = p.getLocation();
		for(Entity entity : Utils.getNearbyEntities(loc, range)) {
			if((entity instanceof Item) || (entity instanceof ExperienceOrb)){
				entity.teleport(loc);
				if(entity instanceof Item) {
					Item i = (Item) entity;
					i.setPickupDelay(0);
				}
			}
		}
	}
}
