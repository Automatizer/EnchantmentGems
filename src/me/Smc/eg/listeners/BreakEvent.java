package me.Smc.eg.listeners;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;

import de.tr7zw.itemnbtapi.NBTItem;
import me.Smc.eg.enchants.EnchantManager;
import me.Smc.eg.utils.Cooldowns;
import me.Smc.eg.utils.Utils;

public class BreakEvent implements Listener{

	Plugin plugin;
	
	public BreakEvent(Plugin plugin){
		this.plugin = plugin;
		plugin.getServer().getPluginManager().registerEvents(this, plugin);
	}
	
	@EventHandler
	public void onBreak(BlockBreakEvent e){
		
		Block block = e.getBlock();
		Player player = e.getPlayer();
		Material type = block.getType();
		String enchant = "";
		int level = 0;
		ItemStack is = player.getInventory().getItemInMainHand();
		boolean b = is.containsEnchantment(Enchantment.SILK_TOUCH);
		NBTItem nbti = new NBTItem(is);
		if(nbti.hasKey("massbreaker")) {
			if(nbti.getBoolean("massbreaker")) {
				EnchantManager.callEvent(is, "blockBreak", player, null, 0, block);
			}
		}
		if(Utils.isOre(block)) {
			if(!Cooldowns.isEnchantOnCooldown("veinminer", player)) {
				if(EnchantManager.hasEnchant(is, "veinminer")) {
					EnchantManager.callEvent(is, "veinMine", player, null, 0, block);
					Cooldowns.addEnchantCooldown("veinminer", player);
					new BukkitRunnable() {
						public void run() {
							Cooldowns.removeEnchantCooldown("veinminer", player);
						}
					}.runTaskLater(plugin, 20);
				}
			}
		}
		switch(type){
			case DIAMOND_ORE: 
				int random = Utils.randomBetween(0, 10000);
				enchant = "unbreakable";
				if(random >= 10 && random < 101){ //1%
					level = 1;
				}
				else if(random >= 150 && random < 201){ //0.5%
					level = 2;
				}
				else if(random >= 250 && random < 261){ //0.1%
					level = 3;
				}
				break;
			case EMERALD_ORE: 
				int random1 = Utils.randomBetween(0, 10000);
				enchant = "unbreakable";
				if(random1 >= 10 && random1 < 101){ //1%
					level = 1;
				}
				else if(random1 >= 150 && random1 < 201){ //0.5%
					level = 2;
				}
				else if(random1 >= 250 && random1 < 261){ //0.1%
					level = 3;
				}
				break;
			case GLOWING_REDSTONE_ORE:
			case REDSTONE_ORE:
				int random2 = Utils.randomBetween(0, 10000);
				enchant = "magnet";
				if(random2 >= 10 && random2 < 51){ //0.5%
					level = 1;
				}
				else if(random2 >= 100 && random2 < 126){ //0.25%
					level = 2;
				}
				else if(random2 >= 150 && random2 < 161){ //0.1%
					level = 3;
				}
				else if(random2 >= 200 && random2 < 206){ //0.05%
					level = 4;
				}
				else if(random2 >= 210 && random2 < 212){ //0.02%
					level = 5;
				}
				break;
			case IRON_ORE:
				break;
			case GOLD_ORE:
				break;
			default: break;
		}
		if(enchant != "" && level != 0 && b != true){
			EnchantManager.dropCrystal(EnchantManager.getEnchant(enchant).getCrystal().getItem(level), block.getLocation(), EnchantManager.getEnchant(enchant), player, level);
		}
	}
	
}
