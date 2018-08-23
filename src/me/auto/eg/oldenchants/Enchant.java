package me.auto.eg.oldenchants;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.block.Block;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import me.auto.eg.utils.ChatUtils;
import me.auto.eg.utils.Settings;
import me.auto.eg.utils.Utils;

/**
 * This class is an object representing an enchantment.
 * 
 * @author Smc
 */

public abstract class Enchant{
	
	protected String name;
	protected String crystalName;
	protected String displayName;
	protected Crystal crystal;
	protected String permission;
	protected int maxLevel = 1;
	protected List<String> typesAllowed; 
	protected String event;
	protected HashMap<String, String> options;
	
	/**
	 * The enchant object constructor
	 * 
	 * @param name The enchant's name, has to be the same as the file name
	 */
	
	public Enchant(String name){
		this.name = name;
		options = new HashMap<String, String>();
		typesAllowed = new ArrayList<String>();
		load();
	}
	
	/**
	 * Returns the enchant's name
	 * 
	 * @return The enchant's name
	 */
	
	public String getName(){
		return name;
	}
	
	/**
	 * Returns the display name of the enchant
	 * 
	 * @param The level to display on the display name
	 * @return The display name of the enchant
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
		String[] splitDisplayName = getDisplayName(1).split(" ");
		if(splitDisplayName.length != compareTo.split(" ").length) return false;
		for(int i = 0; i < splitDisplayName.length - 1; i++)
			if(!ChatColor.stripColor(splitDisplayName[i]).equalsIgnoreCase(ChatColor.stripColor(compareTo.split(" ")[i])))
				return false;
		return true;
	}
	
	/**
	 * Returns the maximum level of the enchant
	 * 
	 * @return The maximum level of the enchant
	 */
	
	public int getMaxLevel(){
		return maxLevel;
	}
	
	/**
	 * Returns the name of the event that needs to be called
	 * 
	 * @return The name of the event that needs to be called
	 */
	
	public String getEvent(){
		return event;
	}
	
	/**
	 * Returns the permission needed to enchant an item
	 * 
	 * @param level The level for the permission, to restrict enchanting
	 * @return The permission to enchant an item
	 */
	
	public String getPermission(int level){
		if(permission == null) return "";
		return permission.replace("#", String.valueOf(level));
	}
	
	/**
	 * Returns true if the type is allowed in the enchant
	 * 
	 * @param type The type to check for
	 * @return Returns true if the type is allowed in the enchant
	 */
	
	public boolean isTypeAllowed(String type){
		if(typesAllowed.contains(type.toLowerCase())) return true;
		return false;
	}
	
	/**
	 * Returns the crystal associated with this enchant
	 * 
	 * @return The crystal associated with this enchant
	 */
	
	public Crystal getCrystal(){
		return crystal;
	}
	
	/**
	 * Fetches an option as a string
	 * 
	 * @param path The path to fetch the option at
	 * @return The option as a string
	 */
	
	public String getOption(String path){
		return options.get(path);
	}
	
	/**
	 * Fetches an option with the integer type
	 * 
	 * @param path The path to fetch the option at
	 * @return The option as an integer
	 */
	
	public int getIntOption(String path){
		return Utils.stringToInt(getOption(path));
	}
	
	/**
	 * Fetches an option with the double type
	 * 
	 * @param path The path to fetch the option at
	 * @return The option as a double
	 */
	
	public double getDoubleOption(String path){
		return Utils.stringToDouble(getOption(path));
	}
	
	/**
	 * Fetches an option with the boolean type
	 * 
	 * @param path The path to fetch the option at
	 * @return The option as a boolean
	 */
	
	public boolean getBooleanOption(String path){
		return Utils.stringToBoolean(getOption(path));
	}
	
	/**
	 * Sets an option for the enchant
	 * 
	 * @param path The path to save the option as
	 * @param value The value to save
	 */
	
	public void setOption(String path, String value){
		File file = new File(EnchantManager.getEnchantFolder(), name + ".yml");
		FileConfiguration fileConf = YamlConfiguration.loadConfiguration(file);
		fileConf.set("options." + path, value);
		options.put(path, value);
		Settings.getInstance().saveConfig(file, fileConf);
	}
	
	/**
	 * Loads the enchant from file
	 */
	
	public void load(){
		File file = new File(EnchantManager.getEnchantFolder(), name + ".yml");
		FileConfiguration fileConf = YamlConfiguration.loadConfiguration(file);
		if(!file.exists()){setDefaults(); save(); return;}
		if(fileConf.contains("displayName")) displayName = fileConf.getString("displayName");
		event = fileConf.getString("event");
		maxLevel = fileConf.getInt("maxLevel");
		typesAllowed = fileConf.getStringList("typesAllowed");
		if(fileConf.contains("permission")) permission = fileConf.getString("permission");
		crystal = new Crystal(this);
		crystal.load();
		if(fileConf.contains("options."))
			for(String option : fileConf.getConfigurationSection("options.").getKeys(false))
				options.put(option, fileConf.getString("options." + option));	
		
	}
	
	/**
	 * Saves the enchant to file
	 */
	
	public void save(){
		File file = new File(EnchantManager.getEnchantFolder(), name + ".yml");
		FileConfiguration fileConf = YamlConfiguration.loadConfiguration(file);
		if(displayName != null) fileConf.set("displayName", displayName);
		fileConf.set("event", event);
		fileConf.set("maxLevel", maxLevel);
		fileConf.set("typesAllowed", typesAllowed);
		if(permission != null) fileConf.set("permission", permission);
		for(String option : options.keySet()) fileConf.set("options." + option, options.get(option));
		Settings.getInstance().saveConfig(file, fileConf);
		if(crystal != null) crystal.save();
	}
	
	/**
	 * Deletes the saved file for this enchant
	 */
	
	public void delete(){
		new File(EnchantManager.getEnchantFolder(), name + ".yml").delete();
	}
	
	/**
	 * Sets the default settings for this enchant
	 */
	
	public abstract void setDefaults();
	
	/**
	 * Calls the event set for this enchant
	 */
	
	public abstract void callEvent(ItemStack item, Player player, Entity entity, double value, Block block);
	
	public abstract void startup();

}
