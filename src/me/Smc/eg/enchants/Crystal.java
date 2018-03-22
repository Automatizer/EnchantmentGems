package me.Smc.eg.enchants;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.material.MaterialData;

import me.Smc.eg.utils.ChatUtils;
import me.Smc.eg.utils.Settings;
import me.Smc.eg.utils.Utils;

/**
 * This class handles the crystal object
 * 
 * @author Smc
 */

public class Crystal{

	private Enchant enchant;
	protected String displayName;
	protected MaterialData material;
	protected List<String> lore;
	
	/**
	 * Creates a crystal object
	 * 
	 * @param enchant The enchant to assign to this crystal
	 */
	
	public Crystal(Enchant enchant){
		this.enchant = enchant;
		this.lore = new ArrayList<String>();
	}
	
	/**
	 * Returns the enchant linked to this crystal
	 * 
	 * @return The enchant linked to this crystal
	 */
	
	public Enchant getEnchant(){
		return enchant;
	}
	
	/**
	 * Returns the lore of this crystal
	 * 
	 * @return The lore of this crystal
	 */
	
	public ArrayList<String> getLore(){
		return new ArrayList<String>(lore);
	}
	
	/**
	 * Returns the display name of this crystal
	 * 
	 * @param level The level to display on the crystal
	 * @return The display name of this crystal
	 */
	
	public String getDisplayName(int level){
		return ChatUtils.decodeMessage(displayName.replace("{enchantlevel}", Utils.getIntInRoman(level)));
	}
	
	/**
	 * Compares two display names to see if they are equal
	 * 
	 * @param compareTo The display name to compare
	 * @return The result of the comparison
	 */
	
	public boolean compareNames(String compareTo){
		if(compareTo == null || compareTo == "") return false;
		String[] splitDisplayName = getDisplayName(1).split(" ");
		if(splitDisplayName.length != compareTo.split(" ").length) return false;
		for(int i = 0; i < splitDisplayName.length - 1; i++)
			if(!ChatColor.stripColor(splitDisplayName[i]).equalsIgnoreCase(ChatColor.stripColor(compareTo.split(" ")[i])))
				return false;
		return true;
	}
	
	/**
	 * Check is the display name is present inside of the lore.
	 * 
	 * @param lore The lore to scan
	 * @return If the display name is present
	 */
	
	public boolean findDisplayName(List<String> lore){
		if(lore.isEmpty()) return false;
		String[] splitDisplayName = getDisplayName(1).split(" ");
		for(String lorePiece : lore){
			if(splitDisplayName.length != lorePiece.split(" ").length) continue;
			boolean match = true;
			for(int i = 0; i < splitDisplayName.length - 1; i++)
				if(!ChatColor.stripColor(splitDisplayName[i]).equalsIgnoreCase(ChatColor.stripColor(lorePiece.split(" ")[i])))
					match = true;
			if(match) return true;
		}
		return false;
	}
	
	/**
	 * Returns the MaterialData of this crystal
	 * 
	 * @return Returns the MaterialData of the crystal
	 */
	
	public MaterialData getMaterial(){
		return material;
	}
	
	/**
	 * Returns the ItemStack this crystal forms
	 * 
	 * @return The ItemStack this crystal forms
	 */
	
	@SuppressWarnings("deprecation")
	public ItemStack getItem(int level){
		if(level > enchant.getMaxLevel()) return null;
		ItemStack is = new ItemStack(material.getItemType(), 1, material.getData());
		EnchantGlow.addGlow(is);
		ItemMeta im = is.getItemMeta();
		if(displayName != null) im.setDisplayName(getDisplayName(level));
		boolean added = false;
		if(lore != null && !lore.isEmpty()){
			List<String> convertedLore = new ArrayList<String>();
			for(String lorePiece : lore)
				convertedLore.add(ChatUtils.decodeMessage(lorePiece));
			convertedLore.add(getDisplayName(level));
			added = true;
			im.setLore(convertedLore);
		}
		if(!added){
			List<String> lore = new ArrayList<String>();
			lore.add(getDisplayName(level));
			im.setLore(lore);
		}
		is.setItemMeta(im);
		return is;
	}
	
	/**
	 * Loads this Crystal from file
	 */
	
	@SuppressWarnings("deprecation")
	public void load(){
		File file = new File(EnchantManager.getEnchantFolder(), enchant.getName() + ".yml");
		FileConfiguration fileConf = YamlConfiguration.loadConfiguration(file);
		material = new MaterialData(Material.getMaterial(fileConf.getString("crystal.material.name")), (byte) fileConf.getInt("crystal.material.data"));
		displayName = fileConf.getString("crystal.displayName");
		if(fileConf.contains("crystal.lore")) lore = fileConf.getStringList("crystal.lore");
	}
	
	/**
	 * Saves this crystal to file
	 * 
	 * @param file The file to save the configuration to
	 * @param fileConf The configurationt to save the crystal to
	 */
	
	@SuppressWarnings("deprecation")
	public void save(){
		File file = new File(EnchantManager.getEnchantFolder(), enchant.getName() + ".yml");
		FileConfiguration fileConf = YamlConfiguration.loadConfiguration(file);
		fileConf.set("crystal.material.name", material.getItemType().name());
		fileConf.set("crystal.material.data", material.getData());
		fileConf.set("crystal.displayName", displayName);
		if(lore != null && !lore.isEmpty()) fileConf.set("crystal.lore", lore);
		Settings.getInstance().saveConfig(file, fileConf);
	}
	
}
