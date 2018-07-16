package me.auto.eg.commands;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import me.auto.eg.enchants.Enchant;
import me.auto.eg.enchants.EnchantManager;
import me.auto.eg.utils.ChatUtils;
import me.auto.eg.utils.Settings;
import me.auto.eg.utils.Utils;

/**
 * This class handles the /eg give command
 * 
 * @author Smc
 */

public class EGGive{

	static Settings settings = Settings.getInstance();
	
	/**
	 * Runs the /eg give command
	 * 
	 * @param player The player executing the command
	 * @param args The arguments passed along the command
	 */
	
	@SuppressWarnings("deprecation")
	public static void runCommand(Player player, String[] args){
		if(player.hasPermission(settings.getValue("Permission-For-Give-Command")) || player.isOp()){
			if(args.length == 2){
				if(args[1].equalsIgnoreCase("gui-activator")){
					player.getInventory().addItem(settings.getConfig().getItemStack("Crystal-GUI-Activator"));
					player.sendMessage(ChatUtils.addPrefix(settings.getMessage("Give-CGUI-Activator")));
			    }else player.sendMessage(ChatUtils.addPrefix(settings.getMessage("Invalid-Arguments")));
				return;
			}else{
				if(args.length != 4){player.sendMessage(ChatUtils.addPrefix(settings.getMessage("Invalid-Arguments"))); return;}
				Enchant enchant = EnchantManager.getEnchant(args[2]);
				if(enchant == null){player.sendMessage(ChatUtils.addPrefix(settings.getMessage("Invalid-Arguments"))); return;}
				int level = Utils.stringToInt(args[3]);
				if(level == -1 || level > enchant.getMaxLevel()){player.sendMessage(ChatUtils.addPrefix(settings.getMessage("Invalid-Arguments"))); return;}
				Material toGive = null;
				boolean crystal = false;
				if(!args[1].equalsIgnoreCase("crystal")){
					if(Material.getMaterial(args[1].toUpperCase()) != null)
						toGive = Material.getMaterial(args[1].toUpperCase());
					else if(Material.getMaterial(Utils.stringToInt(args[1])) != null)
						toGive = Material.getMaterial(Utils.stringToInt(args[1]));
					if(toGive == null){player.sendMessage(ChatUtils.addPrefix(settings.getMessage("Invalid-Arguments"))); return;}	
				}else crystal = true;
				ItemStack itemToGive = null;
				if(crystal) itemToGive = enchant.getCrystal().getItem(level);
				else itemToGive = EnchantManager.addEnchantToItem(new ItemStack(toGive), enchant, level);
				player.getInventory().addItem(itemToGive);
				if(crystal) player.sendMessage(ChatUtils.addPrefix(settings.getMessage("Give-Crystal")));
				else player.sendMessage(ChatUtils.addPrefix(settings.getMessage("Give-Enchanted-Item")));
			}	
		}else player.sendMessage(ChatUtils.addPrefix(settings.getMessage("Invalid-Perms")));
	}
	
	/**
	 * Runs the /eg give command as console
	 * 
	 * @param sender The sender
	 * @param args The arguments
	 */
	
	public static void runCommand(CommandSender sender, String[] args){
		if(args.length != 5){sender.sendMessage(ChatUtils.addPrefix(settings.getMessage("Invalid-Arguments"))); return;}
		Enchant enchant = EnchantManager.getEnchant(args[2]);
		if(enchant == null){sender.sendMessage(ChatUtils.addPrefix(settings.getMessage("Invalid-Arguments"))); return;}
		int level = Utils.stringToInt(args[3]);
		if(level == -1 || level > enchant.getMaxLevel()){sender.sendMessage(ChatUtils.addPrefix(settings.getMessage("Invalid-Arguments"))); return;}
		ItemStack itemToGive = enchant.getCrystal().getItem(level);
		String targetName = args[4];
		Player target = null;
		for(Player online : Bukkit.getOnlinePlayers())
			if(online.getName().equalsIgnoreCase(targetName)){
				target = online;
				break;
			}
		if(target == null){sender.sendMessage(ChatUtils.addPrefix(settings.getMessage("Invalid-Arguments"))); return;}
		target.getInventory().addItem(itemToGive);
	}
	
}
