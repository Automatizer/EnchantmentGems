package me.auto.eg.commands;

import org.bukkit.entity.Player;

import me.auto.eg.utils.ChatUtils;
import me.auto.eg.utils.Settings;

/**
 * This class handles the /eg list command
 * 
 * @author Smc
 */

public class EGHelp{

	static Settings settings = Settings.getInstance();
	
	/**
	 * Runs the /eg help command
	 * 
	 * @param player The player executing the command
	 */
	
	public static void runCommand(Player player){
		player.sendMessage(ChatUtils.addPrefix(settings.getMessage("Help-Help-Command")));
		if(player.hasPermission(settings.getValue("Permission-For-Give-Command")) || player.isOp()){
			player.sendMessage(ChatUtils.addPrefix(settings.getMessage("Help-Give-CGUI-Command")));
			player.sendMessage(ChatUtils.addPrefix(settings.getMessage("Help-Give-Command")));
			player.sendMessage(ChatUtils.addPrefix(settings.getMessage("Help-Give-Crystal-Command")));
			player.sendMessage(ChatUtils.addPrefix(settings.getMessage("Help-Add-Enchant-Command")));
		}
		if(player.hasPermission(settings.getValue("Permission-For-Enchant-List-Command")) || player.isOp())
			player.sendMessage(ChatUtils.addPrefix(settings.getMessage("Help-List-Enchants-Command")));
	}
	
}
