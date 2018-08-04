package me.auto.eg.listeners;

import java.util.List;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.AnvilInventory;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryView;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.Plugin;

import me.auto.eg.utils.InventoryUtils;
import me.auto.eg.utils.Settings;
import me.auto.eg.utils.Utils;
import me.smc.guiapi.GUI;

public class InvClickEvent implements Listener{

	Plugin plugin;
	static Settings settings = Settings.getInstance();
	
	public InvClickEvent(Plugin plugin){
		this.plugin = plugin;
		plugin.getServer().getPluginManager().registerEvents(this, plugin);
	}
	
	@EventHandler
	public void invClickEvent(InventoryClickEvent e){
		final Inventory inv = e.getInventory();
		if(inv.getHolder() instanceof GUI){
			final GUI gui = (GUI) inv.getHolder();
			if(e.getWhoClicked() instanceof Player){
				final Player player = (Player) e.getWhoClicked();
				final int index = e.getRawSlot();
				ItemStack clickedItem = e.getCurrentItem();
				List<Integer> inputSlots = settings.getCrystalGUI().getIntegerList("combinedInput-slots");
				int outputSlot = settings.getCrystalGUI().getInt("combinedOutput-slot");
				if(index < inv.getSize()){
					ItemStack toCheck = clickedItem;
					if(toCheck == null || toCheck.getType().equals(Material.AIR)) toCheck = player.getItemOnCursor();
					if(toCheck.getAmount() != 1){
						Inventory topInv = player.getOpenInventory().getTopInventory();
						for(int slot : inputSlots){
							if(topInv.getItem(slot) != null){
								player.getInventory().addItem(topInv.getItem(slot));
								topInv.remove(topInv.getItem(slot));
								gui.removeGUIItem(slot);		
							}
						}
						if(topInv.getItem(outputSlot) != null){
							ItemStack item = topInv.getItem(outputSlot);
							if(Utils.getCategory(item, true) == "other"){
								player.getInventory().addItem(item);
								topInv.remove(topInv.getItem(outputSlot));
								gui.removeGUIItem(outputSlot);
							}	
						}
						e.setCancelled(true);
						return;
					}
					if(inputSlots.contains(index)){
						InventoryUtils.handleCombinedItem(e, player, gui, toCheck, index, new String[]{"other", "barrier"}, false);
					}else if(index == outputSlot)
						InventoryUtils.handleCombinedItem(e, player, gui, toCheck, index, new String[]{"other", "barrier"}, true);
				}else if(e.isShiftClick()){
					if(index == outputSlot)
						InventoryUtils.handleCombinedItem(e, player, gui, clickedItem, index, new String[]{"other", "barrier"}, true);
					else
						InventoryUtils.handleCombinedItem(e, player, gui, clickedItem, index, new String[]{"other", "barrier"}, false);
				}
			}
		}
	}
	
	@EventHandler
	public static void onInventoryClick(InventoryClickEvent e){
		if(!e.isCancelled()){
			Inventory inv = e.getInventory();
			if(inv instanceof AnvilInventory){
				AnvilInventory anvil = (AnvilInventory)inv;
				InventoryView view = e.getView();
				int rawSlot = e.getRawSlot();
				if(rawSlot == view.convertSlot(rawSlot)){
					if(rawSlot == 2){
						ItemStack[] items = anvil.getContents();
						ItemStack item1 = items[0];
						ItemStack item2 = items[1];
						if(item1 != null && item2 != null){
							Material id2 = item2.getType();
							if(id2 == Material.ENCHANTED_BOOK || id2 == Material.BOOK){
								ItemStack item3 = e.getCurrentItem();
								if(item3 != null){
									ItemMeta meta = item3.getItemMeta();
									if(meta != null){
										
									}
								}
							}
						}
					}
				}
			}

		}
	}
	
	@EventHandler
	public void invCloseEvent(InventoryCloseEvent e){
		Inventory inv = e.getInventory();
		if(inv.getHolder() instanceof GUI){
			GUI gui = (GUI) inv.getHolder();
			if(e.getPlayer() instanceof Player){
				Player player = (Player) e.getPlayer();
				InventoryUtils.giveBackItems(e, player, gui);
			}
		}
	}
	
}
