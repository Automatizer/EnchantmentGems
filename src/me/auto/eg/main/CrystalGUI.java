package me.auto.eg.main;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import me.auto.eg.utils.ChatUtils;
import me.auto.eg.utils.Settings;
import me.auto.eg.utils.Utils;
import me.smc.guiapi.GUI;
import me.smc.guiapi.GUIItem;

/**
 * This class handles the Crystal GUI setup.
 * 
 * @author Smc
 */

public class CrystalGUI{

	public static GUI crystalGUI;
	static Settings settings = Settings.getInstance();
	public static boolean combined = settings.getConfig().getBoolean("Combine-Enchanting-And-Crystal-Combining");
	
	/**
	 * Builds the crystal GUI for future uses
	 */
	
	public static void build(FileConfiguration fileConf){
		crystalGUI = null;
		crystalGUI = new GUI(ChatUtils.decodeMessage(fileConf.getString("GUI-Title")), fileConf.getInt("GUI-Rows")){
			@Override public void onClose(Player p){}
		};
		ArrayList<Integer> skipFilling = new ArrayList<Integer>();
		List<Integer> inputSlots = fileConf.getIntegerList("combinedInput-slots");
		for(int slot : inputSlots) if(slot >= 0) skipFilling.add(slot);
		if(fileConf.contains("Added-Item")){
			for(String index : fileConf.getConfigurationSection("Added-Item.").getKeys(false)){
				index = index.split("-")[0];
				for(int slot : fileConf.getIntegerList("Added-Item." + index + "-slots")){
					if(!skipFilling.contains(slot)) skipFilling.add(slot);
					if(crystalGUI.getItems().containsKey(slot)) crystalGUI.removeGUIItem(slot);
					crystalGUI.addGUIItem(new GUIItem(fileConf.getItemStack("Added-Item." + index + "-item")){
						@Override public void onClick(Player p){}
					}, slot);
				}
			}
		}
		int outputSlot = fileConf.getInt("combinedOutput-slot");
		if(outputSlot >= 0) skipFilling.add(outputSlot);
		if(fileConf.getBoolean("Fill-in-empty-slots")){
			GUIItem filler = new GUIItem(fileConf.getItemStack("Filler-Item")){
				@Override public void onClick(Player p){}
			};
			for(int index = 0; index < crystalGUI.getInventory().getSize(); index++)
				if(!skipFilling.contains(index)){
					ItemStack is = crystalGUI.getInventory().getItem(index);
					if(is == null) crystalGUI.addGUIItem(filler, index);	
				}
		}
		crystalGUI.update();
	}
	
	/**
	 * Opens a copy of the crystal GUI to the player
	 * 
	 * @param player The player to open it for
	 */
	
	public static void open(Player player){
		crystalGUI.clone().openGUI(player);
	}
	
	/**
	 * Sets the default file configuration for this GUI
	 * 
	 * @param fileConf The file configuration to save it in
	 */
	
	public static void setDefaults(File file, FileConfiguration fileConf){
		fileConf.options().header("# The Crystal GUI's configuration file. #");
		fileConf.set("GUI-Title", "&eEnchanting");
		fileConf.set("GUI-Rows", 3);
		fileConf.set("Fill-in-empty-slots", true);
		ItemStack filler = new ItemStack(Material.WHITE_STAINED_GLASS_PANE);
		fileConf.set("Filler-Item", Utils.addToIM(filler, " ", new ArrayList<String>()));
		ArrayList<ItemStack> items = new ArrayList<ItemStack>();
		int i = 0;
		for(ItemStack item : items){
			fileConf.set("Added-Item." + i + "-item", item);
			fileConf.set("Added-Item." + i + "-slots", Arrays.asList(i * 4));
			i++;
		}
		fileConf.set("combinedInput-slots", Arrays.asList(10, 12));
		fileConf.set("combinedOutput-slot", 15);
	}
	
}
