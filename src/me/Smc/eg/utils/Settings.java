package me.Smc.eg.utils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;

import me.Smc.eg.main.CrystalGUI;

/**
 * This class handles the plugin's file storage
 * 
 * @author Smc
 */

public class Settings{

    private Settings(){}
    static Settings instance = new Settings();
    public static Settings getInstance(){
 	   return instance;
    }
	
    Plugin plugin;
    FileConfiguration fc;
    FileConfiguration mfc;
    FileConfiguration cguifc;
    File config;
    File messages;
    File crystalGUIFile;
	
    /**
     * Sets up the files and storage
     * 
     * @param p The plugin
     */
    
    public void setup(Plugin plugin){
        fc = plugin.getConfig();
        fc.options().copyDefaults(true);
        config = new File(plugin.getDataFolder(), "config.yml");
        messages = new File(plugin.getDataFolder(), "messages.yml");
        crystalGUIFile = new File(plugin.getDataFolder(), "crystalGUIConfig.yml");
        mfc = YamlConfiguration.loadConfiguration(messages);
        cguifc = YamlConfiguration.loadConfiguration(crystalGUIFile);
        if(!config.exists()) setConfig();
        if(!messages.exists()) setMessages();
        if(!crystalGUIFile.exists()) CrystalGUI.setDefaults(crystalGUIFile, cguifc);
        saveConfig(config, fc);
        saveConfig(messages, mfc);
        saveConfig(crystalGUIFile, cguifc);
        CrystalGUI.build(cguifc);
        this.plugin = plugin;
    }
    
    /**
     * Returns config.yml's file configuration
     * 
     * @return config.yml's file configuration
     */
    
    public FileConfiguration getConfig(){
        return fc;
    }
    
    /**
     * Returns crystalGUIConfig.yml's file configuration
     * 
     * @return crystalGUIConfig.yml's file configuration
     */
    
    public FileConfiguration getCrystalGUI(){
    	return cguifc;
    }
    
    /**
     * Returns the message under the specified field
     * 
     * @param message The field under which the message is saved ad
     * @return The message set
     */
    
    public String getMessage(String message){
    	return ChatUtils.decodeMessage(mfc.getString(message));
    }
    
    /**
     * Retrieves a string value from config.yml
     * 
     * @param index The field under which the string is saved as
     * @return The value
     */
    
    public String getValue(String index){
    	if(fc.contains(index)) return fc.getString(index);
    	else return "";
    }
    
    /**
     * Saves the provided file and file configuration
     * 
     * @param file File to save the configuration in
     * @param fileConf File configuration to save
     */
    
    public void saveConfig(File file, FileConfiguration fileConf){
    	try{fileConf.save(file);
    	}catch(IOException e){Bukkit.getConsoleSender().sendMessage(String.format(ChatColor.RED + "Could not save %s!", file.getName()));}
    }
	
    /**
     * Sets the default messages of the plugin
     */
    
	public void setMessages(){
		mfc.options().header("# All of the Enchantment Gems' messages are saved here. #");
		mfc.set("Plugin-Prefix", "&eEnchantment Gems &b||&r");
		mfc.set("Plugin-Enabled", "&aEnabled!");
		mfc.set("Plugin-Disabled", "&cDisabled!");
		mfc.set("Console-Command-Error", "&cThe console cannot use commands from this plugin!");
		mfc.set("Invalid-Arguments", "&cInvalid arguments! Use &2/eg help &cfor more information!");
		mfc.set("Invalid-Perms", "&cYou lack the required permissions!");
		mfc.set("Give-CGUI-Activator", "&eGiven Crystal GUI Activator!");
		mfc.set("Give-Enchanted-Item", "&eGiven enchanted item!");
		mfc.set("Give-Crystal", "&eGiven crystal!");
		mfc.set("List-Enchants-Format", "&8- &e%s");
		mfc.set("Help-Help-Command", "&2/eg help &8- &eShows this help menu.");
		mfc.set("Help-Give-CGUI-Command", "&2/eg give gui-activator &8- &eGives the Crystal GUI Activator block.");
		mfc.set("Help-Give-Command", "&2/eg give <item type/item id> <enchant name> <enchant level> &8- &eGives the specified item with the specified enchant on it.");
		mfc.set("Help-Give-Crystal-Command", "&2/eg give crystal <enchant name> <enchant level> &8- &eGives the crystal with the specified enchant on it.");
		mfc.set("Help-List-Enchants-Command", "&2/eg list &8- &eLists all enchants loaded in.");
		mfc.set("Player-Head-Title-Format", "&e%s");
		mfc.set("Headhunter-Head-Drop", "&6%s &emade &6%s&e's head drop!");
		mfc.set("Reload-Message", "&eReloaded all configs!");
		mfc.set("Help-Add-Enchant-Command", "&2/eg add crystal <enchant name> <enchant level> &8- &eAdds the crystal property for that enchant to the currently held item.");
	}
	
	/**
	 * Sets the default config of the plugin
	 */
	
	public void setConfig(){
		fc.options().header("# The main configuration file for Enchantment Gems.  #");
		fc.set("Show-Prefix-Before-Messages", true);
		fc.set("Permission-For-Enchant-Crystal-Slots", "eg.enchantslots.#"); //The # is replaced by the number of the slot (i.e first slot is 1)
		fc.set("Permission-For-Give-Command", "eg.give");
		fc.set("Permission-For-Enchant-List-Command", "eg.list");
		fc.set("Permission-To-Reload", "eg.reload");
		fc.set("Combine-Enchanting-And-Crystal-Combining", true);
		fc.set("Item-Cannot-Be-Enchanted", Utils.addToIM(new ItemStack(Material.BARRIER), ChatColor.RED + "This item cannot be enchanted", new ArrayList<String>()));
		fc.set("Item-Cannot-Be-Combined", Utils.addToIM(new ItemStack(Material.BARRIER), ChatColor.RED + "These items cannot be combined together", new ArrayList<String>()));
		fc.set("Crystal-GUI-Activator", 
			   Utils.addToIM(new ItemStack(Material.EMERALD_BLOCK), ChatColor.GOLD + "Crystal GUI Activator", Arrays.asList(ChatColor.AQUA + "Right-Clicking on this block will open the Crystal GUI")));
	}
	
}
