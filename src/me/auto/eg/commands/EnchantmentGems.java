package me.auto.eg.commands;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

import me.auto.eg.enchants.EnchantManager;
import me.auto.eg.main.Main;
import me.auto.eg.utils.ChatUtils;
import me.auto.eg.utils.Settings;
import me.auto.eg.utils.Utils;

/**
 * This class handles /eg subcommand management
 * 
 * @author Smc
 */

public class EnchantmentGems{

	static Settings settings = Settings.getInstance();
	
	/**
	 * Runs the subcommand manager for /eg
	 * 
	 * @param player The player executing the command
	 * @param args The arguments passed along the command
	 */
	
	public static void runCommand(Player player, String[] args){
		if(args.length == 0){player.performCommand("eg help"); return;}
		if(args.length == 1 && args[0].equalsIgnoreCase("crystal")){
			String message = "false";
			if(player.getItemOnCursor() != null && EnchantManager.isCrystal(player.getItemOnCursor())) message = "true";
			player.sendMessage(ChatUtils.addPrefix(message));
			return;
		}
		if(player.hasPermission("eg.admin")) {
			switch(args[0].toLowerCase()){
			case "give": 
				if(args.length < 2 || args.length > 4) player.sendMessage(ChatUtils.addPrefix(settings.getMessage("Invalid-Arguments")));
				else EGGive.runCommand(player, args);
				break;
			case "fix":
				int i = 0;
				for(Block b : Utils.getNearbyBlocks(player.getLocation(), 15)) {
					if(b.getBlockData().getMaterial().equals(Material.MOVING_PISTON)) {
						b.setType(Material.AIR);
						i++;
					}
				}
				player.sendMessage(ChatUtils.addPrefix("Removed " + Integer.toString(i) + " corrupted blocks."));
				break;
			case "list":
				if(args.length != 1) player.sendMessage(ChatUtils.addPrefix(settings.getMessage("Invalid-Arguments")));
				else EGList.runCommand(player);
				break;
			case "reload":
				if(args.length != 1) player.sendMessage(ChatUtils.addPrefix(settings.getMessage("Invalid-Arguments")));
				else if(player.hasPermission(settings.getValue("Permission-To-Reload")) || player.isOp()){
					EnchantManager.startup();
					settings.setup(Main.plugin);
					player.sendMessage(ChatUtils.addPrefix(settings.getMessage("Reload-Message")));
				}else player.sendMessage(ChatUtils.addPrefix(settings.getMessage("Invalid-Perms")));
				break;
			case "help":
				if(args.length != 1) player.sendMessage(ChatUtils.addPrefix(settings.getMessage("Invalid-Arguments")));
				else EGHelp.runCommand(player);
				break;
			case "add":
				if(args.length < 2 || args.length > 3) player.sendMessage(ChatUtils.addPrefix(settings.getMessage("Invalid-Arguments")));
				else EGAdd.runCommand(player, args);
				break;
			case "enchant":
				if(args.length < 2 || args.length > 3) { player.sendMessage(ChatUtils.addPrefix(settings.getMessage("Invalid-Arguments"))); }
				else EGEnchant.execute(player.getInventory().getItemInMainHand(), EnchantManager.getEnchant(args[1]), Utils.stringToInt(args[2]));
				break;
			default: player.sendMessage(ChatUtils.addPrefix(settings.getMessage("Invalid-Arguments"))); break;
		}
		}
	}
	
}
