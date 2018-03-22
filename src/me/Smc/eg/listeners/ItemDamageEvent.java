package me.Smc.eg.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerItemDamageEvent;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.Plugin;

import me.Smc.eg.enchants.EnchantManager;

public class ItemDamageEvent implements Listener{

	Plugin plugin;
	
	public ItemDamageEvent(Plugin plugin){
		this.plugin = plugin;
		plugin.getServer().getPluginManager().registerEvents(this, plugin);
	}
	
	@EventHandler
	public void onDamage(PlayerItemDamageEvent e){
		ItemStack is = e.getItem();
		if(EnchantManager.hasEnchant(is, "unbreakable")){
			ItemMeta im = is.getItemMeta();
			if(!(im.isUnbreakable())){
				im.setUnbreakable(true);
				im.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
				is.setItemMeta(im);
			}
		}
	}
	
}
