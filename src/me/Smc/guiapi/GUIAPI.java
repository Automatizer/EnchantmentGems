package me.Smc.guiapi;

import org.bukkit.Bukkit;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryType.SlotType;
import org.bukkit.inventory.Inventory;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;

import me.Smc.eg.main.Main;

/**
 * GUIAPI
 * 
 * @author Smc
 */

public class GUIAPI implements Listener{
	
	/**
	 * Empty constructor to register the listeners.
	 * 
	 * @param plugin The current plugin
	 */
	
	public GUIAPI(Plugin plugin){
		Bukkit.getServer().getPluginManager().registerEvents(this, plugin);
	}
	
	/**
	 * Updates the inventory of the specified GUI
	 * 
	 * @param gui The GUI to update
	 */
	
	public static void updateGUI(GUI gui){
		gui.update();
	}
	
	/**
	 * Creates a new GUI
	 * 
	 * @param title The title of the GUI
	 * @param rows The amount of rows in the GUI
	 * @return GUI created
	 */
	
	public static GUI createGUI(String title, int rows){
		return new GUI(title, rows){
			@Override
			public void onClose(Player p){}
		};
	}
	
	/**
	 * Clones a GUI
	 * 
	 * @param gui GUI to clone
	 * @return The cloned GUI
	 */
	
	public static GUI cloneGUI(GUI gui){
		return gui.clone();
	}
	
	/**
	 * Destroys a GUI and closes it for any viewer
	 * 
	 * @param gui The GUI to destroy
	 */
	
	public static void removeGUI(GUI gui){
		for(HumanEntity human : gui.getInventory().getViewers()){
			if(human instanceof Player) gui.closeGUI((Player) human);
			else human.closeInventory();
		}
	}
	
	/**
	 * Switches from one GUI to another for specified player
	 * 
	 * @param p Player switching GUI
	 * @param from GUI player is currently viewing
	 * @param to GUI to switch to
	 */
	
	public static void switchGUI(final Player p, GUI from, final GUI to){
		from.closeGUI(p);
		new BukkitRunnable(){
			public void run(){
				if(p.isOnline()) to.openGUI(p);
			}
		}.runTaskLater(Main.plugin, 1);
	}
	
	@EventHandler
	public void onGUIItemClick(InventoryClickEvent e){
		Inventory inv = e.getInventory();
		if(inv.getHolder() instanceof GUI){
			GUI gui = (GUI) inv.getHolder();
			if((e.getWhoClicked() instanceof Player)){
				Player player = (Player) e.getWhoClicked();
				if(e.getSlotType().equals(SlotType.OUTSIDE) || (e.getClick() == ClickType.SHIFT_LEFT)){
					e.setCancelled(true);
					new BukkitRunnable() {
						public void run() {
							gui.closeGUI(player);
						}
					}.runTaskLater(Main.plugin, 1);
				}
				else{
					int index = e.getRawSlot();
					if(index < inv.getSize()){gui.clickItem(player, index); e.setCancelled(true);}
				}
			}
		}
	}
	
	@EventHandler
	public void onInventoryClose(InventoryCloseEvent e){
		Inventory inv = e.getInventory();
		if(inv.getHolder() instanceof GUI){
			GUI gui = (GUI) inv.getHolder();
			gui.onClose((Player) e.getPlayer());
		}
	}
	
}
