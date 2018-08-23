package me.auto.eg.listeners;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;

import de.tr7zw.itemnbtapi.NBTItem;
import me.auto.eg.main.CrystalGUI;
import me.auto.eg.oldenchants.EnchantManager;
import me.auto.eg.utils.Cooldowns;
import me.auto.eg.utils.Settings;

public class InteractEvent implements Listener{

	Plugin plugin;
	Settings settings = Settings.getInstance();
	
	public InteractEvent(Plugin plugin){
		this.plugin = plugin;
		plugin.getServer().getPluginManager().registerEvents(this, plugin);
	}
	
	@EventHandler
	public void interactEvent(PlayerInteractEvent e){
		if(e.getHand() == EquipmentSlot.OFF_HAND) {
			return;
		}
		Player p = e.getPlayer();
		if(p.isSneaking()) {
			if(e.getAction().equals(Action.RIGHT_CLICK_AIR)) {
				ItemStack is = p.getInventory().getItemInMainHand();
				if(is != null) {
					NBTItem nbti = new NBTItem(is);
					if(EnchantManager.hasEnchant(is, "massbreaker")) {
						if(nbti.hasKey("massbreaker")) {
							if(!nbti.getBoolean("massbreaker")) {
								nbti.setBoolean("massbreaker", true);
								p.sendMessage(ChatColor.GREEN + "You have enabled Massbreaker!");
							}else {
								nbti.setBoolean("massbreaker", false);
								p.sendMessage(ChatColor.YELLOW + "You have disabled Massbreaker!");
							}
						}else {
							nbti.setBoolean("massbreaker", true);
							p.sendMessage(ChatColor.GREEN + "You have enabled Massbreaker!");
						}
						ItemStack newItem = nbti.getItem();
						ItemMeta im = newItem.getItemMeta();
						String str = "";
						ChatColor.stripColor(im.getDisplayName());
						if(nbti.getBoolean("massbreaker")) {
							str = ChatColor.GREEN + "Enabled";
						}else {
							str = ChatColor.RED + "Disabled";
						}
						ChatColor cc = ChatColor.GRAY;
						String append = cc + "[Massbreaker " + str + cc + "]";
						String name = im.getDisplayName();
						String newName;
						if((name != null) && (name.contains("[Massbreaker"))) {
							newName = name.replace(name.substring(name.indexOf("["), name.lastIndexOf("]")), "%placeholder%");
						}else {
							if(name == null) {
								name = is.getType().toString().replace("_", " ");
							}
							newName = name + "%placeholder%";
						}
						im.setDisplayName(newName.replace("%placeholder%", append));
						if(im.getDisplayName().contains(cc + "]]")) im.setDisplayName(im.getDisplayName().replace(cc + "]]", cc + "]"));
						newItem.setItemMeta(im);
						p.getInventory().remove(is);
						p.getInventory().addItem(newItem);
					}
					if((is.getItemMeta().hasLore()) && (is.getItemMeta().getLore().contains("Magnet Toggler"))) {
						ChatColor cc = null;
						String str = "";
						if(nbti.getBoolean("status") == false) {
							nbti.setBoolean("status", true);
							p.sendMessage(ChatColor.GREEN + "You have enabled Magnet!");
							cc = ChatColor.GREEN;
							str = "Enabled";
						}else if(nbti.getBoolean("status") == true) {
							nbti.setBoolean("status", false);
							p.sendMessage(ChatColor.YELLOW + "You have disabled Magnet!");
							cc = ChatColor.RED;
							str = "Disabled";
						}
						ItemStack newItem = nbti.getItem();
						ItemMeta im = newItem.getItemMeta();
						im.setDisplayName(ChatColor.GRAY + "Magnet " + cc + str);
						newItem.setItemMeta(im);
						newItem.setAmount(1);
						p.getInventory().removeItem(is);
						p.getInventory().addItem(newItem);
						e.setCancelled(true);
					}
				}
			}
		}
		
		if(e.getAction().equals(Action.RIGHT_CLICK_BLOCK)){
			ItemStack crystalGUIActivator = settings.getConfig().getItemStack("Crystal-GUI-Activator");
			Block block = e.getClickedBlock();
			if(block.getType().equals(crystalGUIActivator.getType())){
				CrystalGUI.open(e.getPlayer());
				e.setCancelled(true);
				return;
			}
		}
		
		if((e.getAction().equals(Action.RIGHT_CLICK_AIR)) || (e.getAction().equals(Action.RIGHT_CLICK_BLOCK))) {
			ItemStack is = p.getInventory().getItemInMainHand();
			if(is != null) {
				if(!Cooldowns.isEnchantOnCooldown("veinminer", e.getPlayer())) {
					if(EnchantManager.hasEnchant(is, "veinminer")) {
						if(EnchantManager.getEnchantLevel(is, EnchantManager.getEnchant("veinminer")) >= 5) {
							if(e.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
								ItemStack is2 = p.getInventory().getItemInOffHand();
								if(!is2.getType().equals(Material.AIR)) {
									return;
								}
							}
							EnchantManager.callEvent(is, "veinMine", p, null, 1.0, null);
							Cooldowns.addEnchantCooldown("veinminer", p);
							new BukkitRunnable() {
								public void run() {
									Cooldowns.removeEnchantCooldown("veinminer", p);
								}
							}.runTaskLater(plugin, 20);
						}
					}
				}
				
			}
		}
	}
	
}
