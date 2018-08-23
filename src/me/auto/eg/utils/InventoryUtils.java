package me.auto.eg.utils;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scheduler.BukkitRunnable;

import me.auto.eg.main.Main;
import me.auto.eg.oldenchants.Enchant;
import me.auto.eg.oldenchants.EnchantManager;
import me.smc.guiapi.GUI;
import me.smc.guiapi.GUIItem;
import net.md_5.bungee.api.ChatColor;

/**
 * This class handles inventory item handling
 * 
 * @author Smc
 */

public class InventoryUtils{

	static Settings settings = Settings.getInstance();
	
	/**
	 * This checks if the item's category is a denied type
	 * 
	 * @param item The ItemStack to check for
	 * @param deniedTypes The denied types
	 * @return True if it is denied
	 */
	
	public static boolean checkIfDenied(ItemStack item, String[] deniedTypes){
		if(deniedTypes.length == 0) return false;
		for(String denied : deniedTypes){
			if(denied == "!crystal" && EnchantManager.isCrystal(item)) return false;
			if(Utils.getCategory(item, true).equalsIgnoreCase(denied))
				return true;
		}
		return false;
	}
	
	/**
	 * This handles item placement in a combined gui
	 * 
	 * @param e The InventoryClickEvent called
	 * @param player The player involved
	 * @param gui The gui used by the player
	 * @param item The ItemStack to handle
	 * @param slot The slot involved
	 * @param deniedTypes Types of items that cannot be placed
	 * @param enchSlot If it is the enchant slot
	 */
	
	public static void handleCombinedItem(final InventoryClickEvent e, final Player player, final GUI gui, final ItemStack item, final int slot, String[] deniedTypes, boolean enchSlot){
		List<Integer> inputSlots = settings.getCrystalGUI().getIntegerList("combinedInput-slots");
		int outputSlot = settings.getCrystalGUI().getInt("combinedOutput-slot");
		if(e.isShiftClick()){
			if(gui.getItems().containsKey(slot)){
				if(inputSlots.contains(slot) && !checkIfDenied(item, deniedTypes)){
					gui.getItems().get(slot).addToGUI(gui);
					gui.removeGUIItem(slot);
					player.getInventory().addItem(item);
				}else if(enchSlot && slot == outputSlot && !checkIfDenied(item, deniedTypes)){
					gui.getItems().get(slot).addToGUI(gui);
					gui.removeGUIItem(slot);
					for(int input : inputSlots){if(gui.getItems().containsKey(input)){gui.getItems().get(input).addToGUI(gui); gui.removeGUIItem(input);}}
					player.getInventory().addItem(item);
				}
			}else if(item != null && !checkIfDenied(item, deniedTypes)){
				int firstInputAvailable = -1;
				for(int input : inputSlots)
					if(!gui.getItems().containsKey(input)){
						firstInputAvailable = input;
						break;
					}
				if(firstInputAvailable >= 0){
					player.getInventory().setItem(e.getSlot(), new ItemStack(Material.AIR));
					player.setItemOnCursor(new ItemStack(Material.AIR));
					gui.addGUIItem(new GUIItem(item){
						@Override public void onClick(Player p){}
					}, firstInputAvailable);
				}
			}
		}
		if(e.getAction().name().toLowerCase().contains("place") || e.getAction().name().toLowerCase().contains("pickup")){
			if(item != null && !checkIfDenied(item, deniedTypes)){
				boolean isRightClick = e.isRightClick();
				boolean pickup = e.getAction().name().toLowerCase().contains("pickup") ? true : false;
				if(gui.getItems().containsKey(slot)){
					if(inputSlots.contains(slot) && !isRightClick){
						if(player.getItemOnCursor() == null || player.getItemOnCursor().getType() == Material.AIR){
							gui.getItems().get(slot).addToGUI(gui);
							ItemStack storedIn = gui.getItems().get(slot).getItem();
							gui.removeGUIItem(slot);
							player.setItemOnCursor(storedIn);	
						}
					}else if(enchSlot && slot == outputSlot){
						gui.removeGUIItem(slot);
						for(int input : inputSlots) gui.removeGUIItem(input);
						player.setItemOnCursor(item);
					}
				}else if(inputSlots.contains(slot)){
					if(pickup){
						player.getOpenInventory().getTopInventory().remove(item);
						player.setItemOnCursor(item);
					}else{
						final ItemStack toAdd = item.clone();
						if(isRightClick) toAdd.setAmount(1);
						gui.addGUIItem(new GUIItem(toAdd){
							@Override public void onClick(Player p){}
						}, slot);
						ItemStack setToCursor = item.clone();
						if(isRightClick && setToCursor.getAmount() - 1 > 0){setToCursor.setAmount(setToCursor.getAmount() - 1); player.setItemOnCursor(setToCursor);}
						else player.setItemOnCursor(new ItemStack(Material.AIR));	
					}
				}
			}
		}
		new BukkitRunnable(){
			public void run(){
				if(removeCrap(player, player.getOpenInventory().getTopInventory(), gui)) return;
				displayCombinedResult(player, gui);
				e.setCancelled(true);
				gui.update();
			}
		}.runTaskLater(Main.plugin, 1);
	}
	
	/**
	 * Removes all of the junk in the GUI
	 * 
	 * @param player The player involved
	 * @param inventory The inventory to check in
	 * @param gui The GUI to check in
	 */
	
	public static boolean removeCrap(Player player, Inventory inventory, GUI gui){
		List<Integer> inputSlots = settings.getCrystalGUI().getIntegerList("combinedInput-slots");
		int outputSlot = settings.getCrystalGUI().getInt("combinedOutput-slot");
		List<Integer> slots = new ArrayList<Integer>(inputSlots);
		slots.add(outputSlot);
		boolean result = false;
		for(int slot : slots){
			if(inventory.getItem(slot) != null && Utils.getCategory(inventory.getItem(slot), true).equalsIgnoreCase("other")){
				player.getInventory().addItem(inventory.getItem(slot));
				inventory.remove(inventory.getItem(slot));
				gui.removeGUIItem(slot);
				result = true;
			}
			if(gui.getItems().containsKey(slot) && Utils.getCategory(gui.getItems().get(slot).getItem(), true).equalsIgnoreCase("other")){
				gui.removeGUIItem(slot);
				result = true;
			}
		}
		return result;
	}
	
	/**
	 * Displays the result of two inputs
	 * 
	 * @param player The player inputting
	 * @param gui The GUI involved
	 */
	
	public static void displayCombinedResult(Player player, GUI gui){
		List<Integer> inputSlots = settings.getCrystalGUI().getIntegerList("combinedInput-slots");
		int outputSlot = settings.getCrystalGUI().getInt("combinedOutput-slot");
		if(gui.getItems().containsKey(inputSlots.get(0)) && gui.getItems().containsKey(inputSlots.get(1))){
			ItemStack inputOne = gui.getItems().get(inputSlots.get(0)).getItem();
			ItemStack inputTwo = gui.getItems().get(inputSlots.get(1)).getItem();
			if(inputOne == null || inputTwo == null)
				if(gui.getItems().containsKey(outputSlot)) gui.removeGUIItem(outputSlot);
			ArrayList<String> types = new ArrayList<String>();
			String inputOneType = Utils.getCategory(inputOne, true);
			String inputTwoType = Utils.getCategory(inputTwo, true);
			types.add(inputOneType); types.add(inputTwoType);
			ItemStack result = null;
			String[] weapons = new String[]{"hoe", "axe", "pickaxe", "sword", "shovel", "bow", "helmet", "chestplate", "leggings", "boots", "enchanted"};
			if(getStringRepeats(types, new String[]{"repairgem"}) == 1){
				if(!inputOneType.equalsIgnoreCase("repairgem")){
					ItemStack clone = inputOne.clone();
					clone.setDurability((short) 0);
					result = clone;
				}else{
					ItemStack clone = inputTwo.clone();
					clone.setDurability((short) 0);
					result = clone;
				}
			}else if(getStringRepeats(types, new String[]{"speedgem"}) == 1 && getStringRepeats(types, new String[]{"leapinggem"}) == 1){
				result = EnchantManager.getEnchant("flash").getCrystal().getItem(1);
			}else{
				if(getStringRepeats(types, new String[]{"crystal"}) == 2)
					result = getResult(inputOne, inputTwo);
				else if(getStringRepeats(types, weapons) == 1 && getStringRepeats(types, new String[]{"crystal"}) == 1){
					if(checkIfDenied(inputOne, weapons)) result = getCombinedEnchantResult(player, inputOne, inputTwo, gui);
					else result = getCombinedEnchantResult(player, inputTwo, inputOne, gui);
				}//else if(getStringRepeats(types, weapons) == 2)
					//result = getItemCombineResult(player, inputOne, inputTwo, gui);	
			}
			if(result != null){
				if(gui.getItems().containsKey(outputSlot))
					gui.removeGUIItem(outputSlot);
				gui.addGUIItem(new GUIItem(result){
					@Override public void onClick(Player p){}
				}, outputSlot);
			}else gui.removeGUIItem(outputSlot);
		}else gui.removeGUIItem(outputSlot);
		gui.update();
	}
	
	/**
	 * Returns the result of a combination of an item and a crystal.
	 * 
	 * @param player The player combining
	 * @param item The ItemStack to enchant
	 * @param crystal The crystal to enchant with
	 * @param gui The GUI involved
	 * @return The result
	 */
	
	public static ItemStack getCombinedEnchantResult(Player player, ItemStack item, ItemStack crystal, GUI gui){
		ItemStack cannotBeEnchanted = settings.getConfig().getItemStack("Item-Cannot-Be-Enchanted");
		if(!EnchantManager.isCrystal(crystal)) return cannotBeEnchanted;
		ItemStack copy = item.clone();
		Enchant enchant = EnchantManager.getCrystalEnchant(crystal);
		int level = EnchantManager.getCrystalEnchantLevel(crystal);
		boolean equalLevel = false;
		if(!EnchantManager.getEnchants(copy).isEmpty())
			for(Enchant itemEnchant : EnchantManager.getEnchants(copy))
				if(itemEnchant.compareNames(enchant.getDisplayName(level))){
					if(EnchantManager.getEnchantLevel(item, itemEnchant) > level) return cannotBeEnchanted;
					else if(EnchantManager.getEnchantLevel(item, itemEnchant) == level){
						EnchantManager.removeEnchant(itemEnchant, copy);
						equalLevel = true;
					}else EnchantManager.removeEnchant(itemEnchant, copy);
				}
		if(enchant.getMaxLevel() == level && equalLevel) return cannotBeEnchanted;
		else if(equalLevel) level++;
		if(enchant.isTypeAllowed(Utils.getCategory(copy, false)))//player.hasPermission(enchant.getPermission(level)) && enchant.isTypeAllowed(Utils.getCategory(copy, false)))
			return EnchantManager.addEnchantToItem(copy, enchant, level);
		else return cannotBeEnchanted;
	}
	
	/**
	 * Returns the result of two items being combined
	 * 
	 * @param player The player combining
	 * @param one The first item to combine
	 * @param two The second item to combine
	 * @param gui The GUI involved
	 * @return The result
	 */
	
	public static ItemStack getItemCombineResult(Player player, ItemStack one, ItemStack two, GUI gui){
		ItemStack item = one.clone();
		ItemStack cannotBeEnchanted = settings.getConfig().getItemStack("Item-Cannot-Be-Combined");
		if(!item.getType().equals(two.getType())) return cannotBeEnchanted;
		if(!two.getEnchantments().isEmpty())
			for(Enchantment ench : two.getEnchantments().keySet()){
				if(item.containsEnchantment(ench) && item.getEnchantmentLevel(ench) < two.getEnchantmentLevel(ench)){
					item.removeEnchantment(ench);
					item.addUnsafeEnchantment(ench, two.getEnchantmentLevel(ench));
				}else if(item.containsEnchantment(ench) && item.getEnchantmentLevel(ench) == two.getEnchantmentLevel(ench)){
					if(ench.getMaxLevel() > two.getEnchantmentLevel(ench)){
						item.removeEnchantment(ench);
						item.addUnsafeEnchantment(ench, two.getEnchantmentLevel(ench) + 1);	
					}
				}else if(!item.containsEnchantment(ench))
					item.addUnsafeEnchantment(ench, two.getEnchantmentLevel(ench));
			}
		if(!EnchantManager.getEnchants(two).isEmpty())
			for(Enchant itemEnchant : EnchantManager.getEnchants(two)){
				boolean skip = false;
				if(!EnchantManager.getEnchants(item).isEmpty()){
					for(Enchant enchant : EnchantManager.getEnchants(item))
						if(itemEnchant.compareNames(enchant.getDisplayName(1)))
							if(EnchantManager.getEnchantLevel(item, enchant) > EnchantManager.getEnchantLevel(two, itemEnchant)){
								skip = true;
							}else EnchantManager.removeEnchant(enchant, item);
				}
				if(!skip) EnchantManager.addEnchantToItem(item, itemEnchant, EnchantManager.getEnchantLevel(two, itemEnchant));
			}
		return cannotBeEnchanted;
	}
	
	/**
	 * This counts the amount of strings in the string array that are present in the arraylist
	 * 
	 * @param toCheckIn The list to check in
	 * @param toCheckFor The list to compare to the other list
	 * @return The amount of repeats
	 */
	
	public static int getStringRepeats(ArrayList<String> toCheckIn, String[] toCheckFor){
		int amount = 0;
		for(String string : toCheckIn) 
			for(String check : toCheckFor) 
				if(string.equalsIgnoreCase(check)) 
					amount++;
		return amount;
	}
	
	/**
	 * Returns the result of a combination between two crystals
	 * 
	 * @param firstCC The first combine crystal
	 * @param secondCC The second combine crystal
	 * @return The combined crystal
	 */
	
	public static ItemStack getResult(ItemStack firstCC, ItemStack secondCC){
		if(EnchantManager.getCrystalEnchant(firstCC).compareNames(EnchantManager.getCrystalEnchant(secondCC).getDisplayName(EnchantManager.getCrystalEnchantLevel(firstCC))))
			return EnchantManager.getCrystalEnchant(firstCC).getCrystal().getItem(EnchantManager.getCrystalEnchantLevel(firstCC) + 1);
		return settings.getConfig().getItemStack("Item-Cannot-Be-Combined");
	}

	public static boolean compareItems(ItemStack item, ItemStack toCompare){
		if(item.getType().equals(toCompare.getType()))
			if(item.hasItemMeta() && toCompare.hasItemMeta()){
				ItemMeta im = item.getItemMeta(), tcm = toCompare.getItemMeta();
				if(im.hasDisplayName() && tcm.hasDisplayName()){
					if(ChatColor.stripColor(im.getDisplayName()).equalsIgnoreCase(ChatColor.stripColor(tcm.getDisplayName())))
						if(im.hasLore() && tcm.hasLore()){
							boolean loreValid = true;
							for(int i = 0; i < im.getLore().size(); i++)
								if(!ChatColor.stripColor(im.getLore().get(i)).equalsIgnoreCase(ChatColor.stripColor(tcm.getLore().get(i)))){
									loreValid = false;
									break;
								}
							if(loreValid) return true;
						}else if(!im.hasLore() && !tcm.hasLore()) return true;
				}else if(!im.hasDisplayName() && !tcm.hasDisplayName()) return true;
			}else if(!item.hasItemMeta() && !toCompare.hasItemMeta()) return true;
		return false;
	}
	
	/**
	 * This gives back the player's items if he left them in the gui when it closed
	 * 
	 * @param e The InventoryCloseEvent called
	 * @param player The player involved
	 * @param gui The gui the player is closing
	 */
	
	public static void giveBackItems(InventoryCloseEvent e, Player player, GUI gui){
		ArrayList<Integer> slots = new ArrayList<Integer>();
		slots.addAll(settings.getCrystalGUI().getIntegerList("combinedInput-slots"));	
		for(int recoverySlot : slots)
			if(gui.getItems().containsKey(recoverySlot)){
				player.getOpenInventory().getTopInventory().remove(player.getOpenInventory().getTopInventory().getItem(recoverySlot));
				gui.removeGUIItem(recoverySlot);
				player.getInventory().addItem(gui.getItems().get(recoverySlot).getItem());
			}
	}
	
}
