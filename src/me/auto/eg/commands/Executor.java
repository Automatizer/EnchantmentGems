package me.auto.eg.commands;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.auto.eg.utils.ChatUtils;
import me.auto.eg.utils.Settings;
import net.md_5.bungee.api.ChatColor;

/**
 * This class handles command execution.
 * It sends the commands to their subcommand manager.
 * 
 * @author Smc
 */

public class Executor implements CommandExecutor{

	static Settings settings = Settings.getInstance();
	
	public static HashMap<UUID, ArrayList<String>> sudoku = new HashMap<UUID, ArrayList<String>>();
	
	/**
	 * Empty constructor to be able to pass in Main
	 */
	
	public Executor(){}
	
	/**
	 * Handles command execution
	 */
	
	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args){
		if(!(sender instanceof Player)){
			if(cmd.getName().equalsIgnoreCase("eg") && args[0].equalsIgnoreCase("give") && args[1].equalsIgnoreCase("crystal")){
				EGGive.runCommand(sender, args);
				return true;
			}
			sender.sendMessage(ChatUtils.addPrefix(settings.getMessage("Console-Command-Error")));
			return true;
		}
		Player player = (Player) sender;
		if(cmd.getName().equalsIgnoreCase("eg")) EnchantmentGems.runCommand(player, args);
		if(cmd.getName().equalsIgnoreCase("trash")) Trash.execute(player);
		if(cmd.getName().equalsIgnoreCase("sudoku")){
			ArrayList<String> message = new ArrayList<String>();
			if(args.length == 0){
				message.add("committed");
				message.add("sudoku");
			}else
				for(String arg : args)
					message.add(arg);
			sudoku.put(player.getUniqueId(), message);
			player.setHealth(0.0);
		}
		return true;
	}
	
}
