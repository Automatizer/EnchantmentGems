package me.Smc.eg.listeners;

import java.util.ArrayList;

import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;

import me.Smc.eg.enchants.Enchant;
import me.Smc.eg.enchants.EnchantManager;
import me.Smc.eg.main.CrystalGUI;
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
		if(e.getPlayer().getItemOnCursor() != null){
			ItemStack held = e.getPlayer().getItemOnCursor();
			ArrayList<Enchant> enchants = EnchantManager.getEnchants(held);
			if(!enchants.isEmpty())
				for(Enchant enchant : enchants)
					if(enchant.getName().toLowerCase().equalsIgnoreCase("unbreakable")){
						held.setDurability((short) 0);
						e.getPlayer().setItemOnCursor(held);	
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
	
}
