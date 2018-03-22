package me.Smc.eg.commands;

import org.bukkit.entity.Player;

import me.Smc.eg.enchants.Enchant;
import me.Smc.eg.enchants.EnchantManager;
import me.Smc.eg.utils.ChatUtils;
import me.Smc.eg.utils.Settings;

/**
 * This class handles the /eg give command
 * 
 * @author Smc
 */

public class EGList{

	static Settings settings = Settings.getInstance();
	
	/**
	 * Runs the /eg list command
	 * 
	 * @param player The player executing the command
	 */
	
	public static void runCommand(Player player){
		if(player.hasPermission(settings.getValue("Permission-For-Enchant-List-Command")) || player.isOp())
			for(Enchant enchant : EnchantManager.getAllEnchants())
				player.sendMessage(ChatUtils.addPrefix(String.format(settings.getMessage("List-Enchants-Format"), 
						                               enchant.getName().substring(0, 1).toUpperCase() + enchant.getName().substring(1, enchant.getName().length()))));
		else player.sendMessage(ChatUtils.addPrefix(settings.getMessage("Invalid-Perms")));
	}
	
}
