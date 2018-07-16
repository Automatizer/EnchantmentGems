package me.smc.guiapi;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.material.MaterialData;

/**
 * GUIItem
 * 
 * An ItemStack in a GUI. Behaves the same as an ItemStack except it triggers onClick whenever clicked
 * 
 * @author Smc
 */

public abstract class GUIItem implements Listener{

	private GUI gui;
	private GUI openonclick;
	private int amount;
	private MaterialData material;
	private String title;
	private HashMap<Enchantment, Integer> enchants = new HashMap<Enchantment, Integer>();
	private List<String> lore = new ArrayList<String>();
	private ItemStack item;
	
	/**
	 * Creates a GUIItem with the specified title
	 * 
	 * Defaults to a book
	 * 
	 * @param title Hover title on the item
	 */
	
	public GUIItem(String title){
		this(title, new MaterialData(Material.BOOK));
	}
	
	/**
	 * Creates a GUIItem with the specified title and material data
	 * 
	 * @param title Hover title on the item
	 * @param material MaterialData of the item
	 */
	
	public GUIItem(String title, MaterialData material){
		this(title, material, 1);
	}
	
	/**
	 * Creates a GUIItem with the specified title, material data and amount
	 * 
	 * @param title Hover title on the item
	 * @param material MaterialData of the item
	 * @param amount Custom stack size for the item
	 */
	
	public GUIItem(String title, MaterialData material, int amount){
		this.title = title;
		this.material = material;
		this.amount = amount;
	}
	
	/**
	 * Creates a GUIItem with the specified ItemStack
	 * 
	 * @param item The ItemStack to turn into a GUIItem
	 */
	
	public GUIItem(ItemStack item){
		this.item = item;
	}
	
	/**
	 * Gets the GUI containing the item
	 * 
	 * @return Returns the GUI the item resides in
	 */
	
	public GUI getGUI(){
		return gui;
	}
	
	/**
	 * Gets the MaterialData of the item
	 * 
	 * @return Returns the MaterialData of the item
	 */
	
	public MaterialData getMaterial(){
		return material;
	}
	
	/**
	 * Set the item's material data
	 * 
	 * @param material The MaterialData to put in place of the old one
	 */
	
	public void setData(MaterialData material){
		this.material = material;
	}
	
	/**
	 * Adds an enchantment to the item
	 * 
	 * @param enchant Enchantment to add
	 * @param level Level of the enchantment
	 */
	
	public void addEnchant(Enchantment enchant, int level){
		enchants.put(enchant, level);
	}
	
	/**
	 * Removes an enchantment from the item
	 * 
	 * @param enchant Enchantment to remove
	 */
	
	public void removeEnchant(Enchantment enchant){
		enchants.remove(enchant);
	}
	
	/**
	 * Sets the name of the item
	 * 
	 * @param title The item's new display name
	 */
	
	public void setName(String title){
		this.title = title;
	}
	
	/**
	 * Gets the hover title of the item
	 * 
	 * @return Returns the title of the item
	 */
	
	public String getTitle(){
		return title;
	}
	
	/**
	 * Sets the lore of the item
	 * 
	 * @param lore A list of all the lore to be set on the item
	 */
	
	public void setLore(List<String> lore){
		this.lore = lore;
	}
	
	/**
	 * Adds a single line of lore to the item
	 * 
	 * @param lore The line of lore to add
	 */
	
	public void addLore(String lore){
		this.lore.add(lore);
	}
	
	/**
	 * Gets the GUI which opens upon click
	 * 
	 * @return Returns the GUI which opens upon click
	 */
	
	public GUI getGUIToOpen(){
		return openonclick;
	}
	
	/**
	 * Sets the GUI which opens upon click
	 * 
	 * @param toopen The GUI to open upon item click
	 */
	
	public void setGUIToOpen(GUI toopen){
		this.openonclick = toopen;
	}
	
	/**
	 * Adds the item to specified GUI
	 * 
	 * @param gui The GUI to add the GUIItem in
	 */
	
	public void addToGUI(GUI gui){
		this.gui = gui;
		gui.update();
	}
	
	/**
	 * Removes the item from specified GUI if it contains the GUIItem
	 * 
	 * @param gui GUI to remove item from
	 */
	
	public void removeFromGUI(GUI gui){
		if(this.gui.equals(gui)){
			this.gui = null;
			gui.update();
		}
	}
	
	/**
	 * Sets the item's amount
	 * 
	 * @param amount The amount of the item
	 */
	
	public void setAmount(int amount){
		this.amount = amount;
		gui.update();
	}
	
	/**
	 * Gets the ItemStack representation of the item
	 * 
	 * @return Returns the ItemStack representation of the item
	 */
	
	@SuppressWarnings("deprecation")
	public ItemStack getItem(){
		ItemStack is = null;
		if(item != null) is = item;
		else new ItemStack(material.getItemType(), amount, material.getData());
		ItemMeta im = is.getItemMeta();
		if(title != null) im.setDisplayName(title);
		if(lore != null && !lore.isEmpty()) im.setLore(lore);
		if(!enchants.isEmpty()) for(Enchantment enchant : enchants.keySet()) is.addUnsafeEnchantment(enchant, enchants.get(enchant));
		is.setItemMeta(im);
		return is;
	}
	
	/**
	 * Method triggered upon item click
	 * 
	 * @param p Player who clicked the item
	 */
	
	public abstract void onClick(Player p);
	
}
