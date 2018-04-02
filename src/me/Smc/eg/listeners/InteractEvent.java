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
import org.bukkit.scheduler.BukkitRunnable;

import de.tr7zw.itemnbtapi.NBTItem;
import me.Smc.eg.enchants.EnchantManager;
import me.Smc.eg.main.CrystalGUI;
import me.Smc.eg.utils.Cooldowns;
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
		Player p = e.getPlayer();
		if(p.isSneaking()) {
			if(e.getAction().equals(Action.RIGHT_CLICK_AIR)) {
				ItemStack is = p.getInventory().getItemInMainHand();
				if(is != null) {
					if(EnchantManager.hasEnchant(is, "massbreaker")) {
						NBTItem nbti = new NBTItem(is);
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
						p.getInventory().remove(is);
						p.getInventory().addItem(nbti.getItem());
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
