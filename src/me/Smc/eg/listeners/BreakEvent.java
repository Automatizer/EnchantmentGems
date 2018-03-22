package me.Smc.eg.listeners;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.plugin.Plugin;

import me.Smc.eg.enchants.EnchantManager;
import me.Smc.eg.utils.Utils;
import net.md_5.bungee.api.ChatColor;

public class BreakEvent implements Listener{

	Plugin plugin;
	
	public BreakEvent(Plugin plugin){
		this.plugin = plugin;
		plugin.getServer().getPluginManager().registerEvents(this, plugin);
	}
	
	@SuppressWarnings("deprecation")
	@EventHandler
	public void onBreak(BlockBreakEvent e){
		Block block = e.getBlock();
		Player player = e.getPlayer();
		Material type = block.getType();
		String enchant = "";
		int level = 0;
		boolean b = false;
		if(player.getItemInHand().getEnchantments().containsKey(Enchantment.SILK_TOUCH)) b = true;
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
			//player.getInventory().addItem(EnchantManager.getEnchant(enchant).getCrystal().getItem(level));
			player.getLocation().getWorld().dropItem(player.getLocation(), EnchantManager.getEnchant(enchant).getCrystal().getItem(level));
			String str = "";
			if((enchant.startsWith("a")) || (enchant.startsWith("e")) || (enchant.startsWith("i")) || (enchant.startsWith("o")) || (enchant.startsWith("u")) || (enchant.startsWith("y"))){
				str = "an";
			}else str = "a";
			player.sendMessage(ChatColor.GREEN + "You have found " + str + " " + ChatColor.LIGHT_PURPLE + enchant + " " + Utils.getIntInRoman(level) + ChatColor.GREEN + " gem!");
		}
		
	}
	
}
