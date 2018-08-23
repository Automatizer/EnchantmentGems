package me.auto.eg.enchants;

import java.io.File;
import java.util.HashMap;
import java.util.List;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.enchantments.EnchantmentTarget;
import org.bukkit.enchantments.EnchantmentWrapper;
import org.bukkit.inventory.ItemStack;

import me.auto.eg.utils.Settings;
import me.auto.eg.utils.Utils;

/**
 * The base class for all new enchants
 * 
 * @author Auto
 *
 */

public abstract class Enchant extends EnchantmentWrapper{
	
	protected static String name;
	protected static Enchantment enchant;
	protected int maxLevel;
	protected String event;
	protected List<EnchantmentTarget> typesAllowed;
	protected List<String> conflicts;
	protected HashMap<String, String> options = new HashMap<String, String>();
	protected boolean isTreasure;

	public Enchant() {
		super(name);
	}
	
	@Override
	public boolean canEnchantItem(ItemStack item){
		for(EnchantmentTarget et : typesAllowed) {
			if(et.includes(item)) return true;
		}
		return false;
	}

	@Override
	public boolean conflictsWith(Enchantment other){
		return conflicts.contains(other.getKey().getKey());
	}
	
	public boolean conflictsWith(Enchant e) {
		return conflicts.contains(e.getName());
	}

	@Override
	public EnchantmentTarget getItemTarget(){
		return null;
	}

	@Override
	public int getMaxLevel(){
		return maxLevel;
	}

	@Override
	public String getName(){
		return name;
	}

	@Override
	public int getStartLevel(){
		return 1;
	}
	
	@Override
	public boolean isTreasure() {
		return isTreasure;
	}
	
	/**
	 * Fetches an option with the string type
	 * 
	 * @param path The path to fetch the option from
	 * @return
	 */
	
	public String getOption(String path){
		return options.get(path);
	}
	
	/**
	 * Fetches an option with the integer type
	 * 
	 * @param path The path to fetch the option from
	 * @return The option as an integer
	 */
	
	public int getIntOption(String path){
		return Utils.stringToInt(getOption(path));
	}
	
	/**
	 * Fetches an option with the double type
	 * 
	 * @param path The path to fetch the option from
	 * @return The option as a double
	 */
	
	public double getDoubleOption(String path){
		return Utils.stringToDouble(getOption(path));
	}
	
	/**
	 * Fetches an option with the boolean type
	 * 
	 * @param path The path to fetch the option from
	 * @return The option as a boolean
	 */
	
	public boolean getBooleanOption(String path){
		return Utils.stringToBoolean(getOption(path));
	}
	
	/**
	 * Sets an option for the enchant
	 * 
	 * @param path The path to save the option from
	 * @param value The value to save
	 */
	
	public void setOption(String path, String value){
		File file = new File(EnchantManager.manager().getEnchantsFolder(), name + ".yml");
		FileConfiguration fileConf = YamlConfiguration.loadConfiguration(file);
		fileConf.set("options." + path, value);
		options.put(path, value);
		Settings.getInstance().saveConfig(file, fileConf);
	}
	
	/**
	 * Loads the enchant's configuration file
	 */
	
	public void load() {
		File file = new File(EnchantManager.manager().getEnchantsFolder(), name + ".yml");
		FileConfiguration fileConf = YamlConfiguration.loadConfiguration(file);
		if(!file.exists()){setDefaults(); save(); return;}
		event = fileConf.getString("event");
		maxLevel = fileConf.getInt("maxLevel");
		enchant = this;
		for(String s : fileConf.getStringList("types-allowed")){
			if(EnchantmentTarget.valueOf(s) != null) {
				typesAllowed.add(EnchantmentTarget.valueOf(s));
			}
		}
		if(fileConf.contains("options.")) {
			for(String option : fileConf.getConfigurationSection("options.").getKeys(false)) {
				options.put(option, fileConf.getString("options." + option));
			}	
		}
		Enchantment.registerEnchantment(enchant);
		EnchantManager.manager().registerEnchant(this);
	}
	
	/**
	 * Saves the enchant's configuration file
	 */
	
	public void save(){
		File file = new File(EnchantManager.manager().getEnchantsFolder(), name + ".yml");
		FileConfiguration fileConf = YamlConfiguration.loadConfiguration(file);
		fileConf.set("event", event);
		fileConf.set("maxLevel", maxLevel);
		fileConf.set("typesAllowed", typesAllowed);
		for(String option : options.keySet()) fileConf.set("options." + option, options.get(option));
		Settings.getInstance().saveConfig(file, fileConf);
	}
	
	/**
	 * Sets the default values in the configuration file
	 */
	
	public abstract void setDefaults();
	
	/**
	 * If the enchant needs to load things to memory it does it here
	 */
	
	public abstract void startup();
	
	/**
	 * The code that is to run when the enchant is used
	 */
	
	public abstract void execute();

}
