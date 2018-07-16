package me.smc.guiapi;

import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;

/**
 * GUI
 * 
 * @author Smc
 */

public abstract class GUI implements InventoryHolder{

	private HashMap<Integer, GUIItem> items = new HashMap<Integer, GUIItem>();
	private Inventory inventory;
	private String title;
	private int rows;
	
	/**
	 * Used to create a new GUI using the specified title
	 * and specified number of slot rows. 9 slots per row.
	 * 
	 * @param title Title of the GUI
	 * @param rows Number of rows in the GUI
	 */
	
	public GUI(String title, int rows){
		this.title = title;
		this.rows = rows;
	}
	
	/**
	 * Opens the GUI for the specified player
	 * 
	 * @param p Player opening GUI
	 * @return True if GUI is opened, false if player was already viewing it
	 */
	
	public boolean openGUI(Player p){
		if(getInventory().getViewers().contains(p)) return false;
		p.openInventory(getInventory());
		return true;
	}
	
	/**
	 * Closes GUI for specified player
	 * 
	 * @param p Player to close GUI for
	 * @return True if GUI closes, false if it was already closed.
	 */
	
	public boolean closeGUI(Player p){
		if(getInventory().getViewers().contains(p)){
			p.closeInventory();
			return true;
		}
		return false;
	}
	
	/**
	 * Switches from this GUI to another for specified player
	 * 
	 * @param p Player switching GUIs
	 * @param to GUI to switch to
	 */
	
	public void switchGUI(Player p, GUI to){
		GUIAPI.switchGUI(p, this, to);
	}
	
	/**
	 * Called whenever an item is clicked in this GUI
	 * 
	 * @param p Player who clicked
	 * @param index Slot number of item clicked
	 */
	
	public void clickItem(Player p, int index){
		if(items.containsKey(index)){
			GUIItem item = items.get(index);
			if(item.getGUIToOpen() != null) switchGUI(p, item.getGUIToOpen());
			else item.onClick(p);	
		}
	}
	
	/**
	 * Add a GUIItem to the GUI at specified location
	 * 
	 * @param item GUIItem to add
	 * @param x X coordinate to place item in
	 * @param y Y coordinate to place item in
	 * @return True if item was placed, false if an item was already there
	 */
	
	public boolean addGUIItem(GUIItem item, int x, int y){
		return addGUIItem(item, y * 9 + x);
	}
	
	/**
	 * Add a GUIItem to the GUI at specified location
	 * 
	 * @param item GUIItem to add
	 * @param index GUI slot number to put item in
	 * @return True if item was placed, false if an item was already there
	 */
	
	public boolean addGUIItem(GUIItem item, int index){
		ItemStack slot = getInventory().getItem(index);
		if(slot != null && slot.getType() != Material.AIR) return false;
		getInventory().setItem(index, item.getItem());
		items.put(index, item);
		item.addToGUI(this);
		return true;
	}
	
	/**
	 * Removes the GUIItem in the GUI at specified location
	 * 
	 * @param x X coordinate of the item
	 * @param y Y coordinate of the item
	 * @return True if removed, false if it was empty already
	 */
	
	public boolean removeGUIItem(int x, int y){
		return removeGUIItem(y * 9 + x);
	}
	
	/**
	 * Removes the GUIItem in the GUI at specified location
	 * 
	 * @param index GUI slot number
	 * @return True if removed, false if it it was already empty
	 */
	
	@SuppressWarnings("deprecation")
	public boolean removeGUIItem(int index) {
		ItemStack slot = getInventory().getItem(index);
		if(slot == null || slot.getTypeId() == 0) return false;
		getInventory().clear(index);
		items.remove(index).removeFromGUI(this);
		return true;
    }
	
	/**
	 * Get all items in the GUI
	 * 
	 * @return All items in the GUI
	 */
	
	public HashMap<Integer, GUIItem> getItems(){
		return items;
	}
	
	/**
	 * Clones the GUI and returns it
	 * 
	 * @return Cloned GUI
	 */
	
	public GUI clone(){
		GUI clone = new GUI(title, rows){
			@Override
			public void onClose(Player p){}
		};
		for(int index : items.keySet()) clone.addGUIItem(items.get(index), index);
		return clone;
	}
	
	/**
	 * Returns the GUI inventory
	 * 
	 * @return GUI inventory
	 */
	
	@Override
	public Inventory getInventory(){
		if(inventory == null) inventory = Bukkit.createInventory(this, rows * 9, title);
		return inventory;
	}
	
	/**
	 * Updates the GUI inventory for all viewers
	 */
	
	public void update(){
		for(int index : getItems().keySet()){
			GUIItem item = getItems().get(index);
			getInventory().setItem(index, item.getItem());
		}
		for(HumanEntity human : getInventory().getViewers())
			if(human instanceof Player)
				((Player) human).updateInventory();
	}
	
	/**
	 * Called when a player closes the GUI.
	 * 
	 * @param Player who closed the GUI.
	 */
	
	public abstract void onClose(Player p);
	
}
