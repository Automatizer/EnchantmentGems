package me.Smc.eg.enchants;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import me.Smc.eg.main.Main;
import me.Smc.eg.utils.Utils;

/**
 * This class manages all enchants.
 * 
 * @author Smc
 */

public class EnchantManager{
	
	/**
	 * Loads/generates all enchants' configuration files
	 */
	
	public static void loadConfFiles(){
		for(Enchant enchant : getAllEnchants())
			enchant.save();
	}
	
	/**
	 * Returns all the enchants on an item
	 * 
	 * @param item The ItemStack to check for enchants on
	 * @return The enchants found on the ItemStack
	 */
	
	public static ArrayList<Enchant> getEnchants(ItemStack item){
		ArrayList<Enchant> enchants = new ArrayList<Enchant>();
		if(item.hasItemMeta()){
			ItemMeta im = item.getItemMeta();
			if(im.hasLore())
				for(String lorePiece : im.getLore())
					for(Enchant enchant : getAllEnchants())
						if(enchant.compareNames(lorePiece)){
							enchants.add(enchant);
							break;
						}
		}
		return enchants;
	}
	
	public static boolean hasEnchant(ItemStack item, String enchantName){
		ArrayList<Enchant> enchants = getEnchants(item);
		if(!enchants.isEmpty())
			for(Enchant enchant : enchants)
				if(enchant.getName().equalsIgnoreCase(enchantName)) return true;
		return false;
	}
	
	/**
	 * Removes the enchant off the ItemStack
	 * 
	 * @param enchant The enchant to remove
	 * @param item The ItemStack to remove it from
	 */
	
	public static ItemStack removeEnchant(Enchant enchant, ItemStack item){
		if(item.hasItemMeta()){
			ItemMeta im = item.getItemMeta();
			if(im.hasLore()){
				List<String> lore = im.getLore();
				List<String> newLore = new ArrayList<String>();
				for(String lorePiece : lore){
					if(!enchant.compareNames(lorePiece))
						newLore.add(lorePiece);
				}
				im.setLore(newLore);
			}
			item.setItemMeta(im);
		}
		return item;
	}
	
	/**
	 * Checks if the ItemStack is a Crystal
	 * 
	 * @param item The ItemStack to check for
	 * @return True if the ItemStack is a Crystal
	 */
	
	public static boolean isCrystal(ItemStack item){
		if(!item.hasItemMeta() || !item.getItemMeta().hasLore()) return false;
 		for(Enchant enchant : getAllEnchants()){
 			for(String str : item.getItemMeta().getLore()){
 				if(enchant.getCrystal().compareNames(str) && checkItemWatermark(item))
 					return true;
 			}
 		}
 		return false;
	}
	
	/**
	 * Returns the enchant(s) a crystal holds
	 * 
	 * @param item The ItemStack to scan
	 * @return The list of enchants embedded within the crystal
	 */
	
	public static Enchant getCrystalEnchant(ItemStack item){
		if(!isCrystal(item)) return null;
 		for(Enchant enchant : getAllEnchants())
			if(enchant.getCrystal().compareNames(item.getItemMeta().getDisplayName()))
				return enchant;
		return null;
	}
	
	/**
	 * Returns the crystal embedded in the item
	 * 
	 * @param item The ItemStack to scan into
	 * @return The Crystal
	 */
	
	public static Crystal getCrystal(ItemStack item){
		if(!isCrystal(item)) return null;
		for(Enchant enchant : getAllEnchants())
			if(enchant.getCrystal().compareNames(item.getItemMeta().getDisplayName()))
				return enchant.getCrystal();
		return null;
	}
	
	/**
	 * Adds an enchant to the specified ItemStack
	 * 
	 * @param item The ItemStack to add the enchant to
	 * @param enchant The enchant to add to the ItemStack
	 * @param level The level of the enchant
	 * @return The enchanted item
	 */
	
	public static ItemStack addEnchantToItem(ItemStack item, Enchant enchant, int level){
		if(enchant.isTypeAllowed(Utils.getCategory(item, false))){
			EnchantGlow.addGlow(item);
			ItemMeta im = item.getItemMeta();
			List<String> lore = new ArrayList<String>();
			if(im.hasLore()) lore = im.getLore();
			lore.add(enchant.getDisplayName(level));
			im.setLore(lore);
			item.setItemMeta(im);
		}
		return item;
	}
	
	/**
	 * Returns the enchant level of the enchant on the item
	 * 
	 * @param item The ItemStack to check for the level on
	 * @param enchant The Enchant to check for
	 * @return
	 */
	
	public static int getEnchantLevel(ItemStack item, Enchant enchant){
		ItemMeta im = item.getItemMeta();
		if(!im.hasLore()) return -1;
		for(String lorePiece : im.getLore())
			if(enchant.compareNames(lorePiece))
				return Utils.getRomanInInt(lorePiece.split(" ")[lorePiece.split(" ").length - 1]);
		return -1;
	}
	
	/**
	 * Returns the enchant level of the crystal
	 * 
	 * @param item The ItemStack to scan the level on
	 * @return The level of the enchant
	 */
	
	public static int getCrystalEnchantLevel(ItemStack item){
		if(!isCrystal(item)) return -1;
		Crystal crystal = getCrystal(item);
		ItemMeta im = item.getItemMeta();
		if(!im.hasDisplayName()) return -1;
		if(crystal.compareNames(im.getDisplayName()))
			return Utils.getRomanInInt(im.getDisplayName().split(" ")[im.getDisplayName().split(" ").length - 1]);
		return -1;
	}
	
	/**
	 * Calls the specific event to every concerned enchant
	 * 
	 * @param item The ItemStack that is triggering the event
	 * @param event The event to trigger
	 */
	
	public static void callEvent(ItemStack item, String event, Player player, Entity target, double value, Block block){
		if(player == target) return;
		for(Enchant enchant : getEnchants(item)){
			if(enchant.getEvent().equalsIgnoreCase(event) && enchant.isTypeAllowed(Utils.getCategory(item, false))){
				enchant.callEvent(item, player, target, value, block); 
			}	
		}
	}
	
	/**
	 * Returns all enchants existing in the plugin
	 * 
	 * @return All enchants
	 */
	
	public static ArrayList<Enchant> getAllEnchants(){
		ArrayList<Enchant> enchants = new ArrayList<Enchant>();
		enchants.add(new Blindness());
		enchants.add(new Confusion());
		enchants.add(new Flash());
		enchants.add(new Haste());
		enchants.add(new Headhunter());
		enchants.add(new Leaping());
		enchants.add(new Lifesteal());
		enchants.add(new Magnet());
		enchants.add(new Nightvision());
		enchants.add(new Poison());
		enchants.add(new Purify());
		enchants.add(new Replenish());
		enchants.add(new Slowness());
		enchants.add(new Starvation());
		enchants.add(new Unbreakable());
		return enchants;
	}
	
	/**
	 * Returns the matching enchant
	 * 
	 * @param enchantName The enchant to find
	 * @return The enchant found
	 */
	
	public static Enchant getEnchant(String enchantName){
		for(Enchant enchant : getAllEnchants())
			if(enchant.getName().equalsIgnoreCase(enchantName))
				return enchant;
		return null;
	}
	
	/**
	 * Returns the enchants' storage folder
	 * 
	 * @return The enchants' storage folder
	 */
	
	public static File getEnchantFolder(){
		File enchants;
		try{
			enchants = new File(Main.plugin.getDataFolder() + File.separator + "Enchants");
			if(!enchants.exists()) enchants.mkdirs();
		}catch(SecurityException e1){
			enchants = null;
		}
		return enchants;
	}
	
	public static boolean checkItemWatermark(ItemStack item){
		if(item.hasItemMeta() && item.getItemMeta().hasLore()){
			List<String> lore = item.getItemMeta().getLore();
			for(String str : lore)
				if(ChatColor.stripColor(str).equalsIgnoreCase(ChatColor.stripColor(item.getItemMeta().getDisplayName()))) return true;
		} 
		return false;
	}
	
	public static ItemStack setItemWatermark(ItemStack item){
		List<String> lore = new ArrayList<String>();
		if(item.hasItemMeta() && item.getItemMeta().hasLore()) lore = item.getItemMeta().getLore();
		lore.add(item.getItemMeta().getDisplayName());
		return Utils.addToIM(item, "", lore);
	}
	
}
