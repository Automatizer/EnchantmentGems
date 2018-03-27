package me.Smc.eg.listeners;

import org.bukkit.ChatColor;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;

import me.Smc.eg.enchants.EnchantManager;
import me.Smc.eg.main.CrystalGUI;
import me.Smc.eg.main.Main;
import me.Smc.eg.utils.Settings;

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
		Player player = e.getPlayer();
		if(player.isSneaking()) {
			if(e.getAction().equals(Action.RIGHT_CLICK_AIR)) {
				ItemStack is = player.getInventory().getItemInMainHand();
				if(is != null) {
					if(EnchantManager.hasEnchant(is, "massbreaker")) {
						if(!isListed(player)) {
							list(player);
						}else {
							unlist(player);
						}
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
	}
	
	private void list(Player p) {
		Main.massbreakers.add(p);
		p.sendMessage(ChatColor.GREEN + "You have enabled Massbreaker!");
	}
	
	private void unlist(Player p) {
		Main.massbreakers.remove(p);
		p.sendMessage(ChatColor.YELLOW + "You have disabled Massbreaker!");
	}
	
	private boolean isListed(Player p) {
		if(Main.massbreakers.contains(p)) {
			return true;
		}
		else return false;
	}
	
}
