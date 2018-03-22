package me.Smc.eg.utils;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;

/**
 * This class handles utilitary methods related to chat messages.
 * 
 * @author Smc
 */

public class ChatUtils{

	public static Settings settings = Settings.getInstance();
	
	/**
	 * Returns the color code associated with the text
	 * 
	 * @param colorCode The text associated with a color code
	 * @return The color code associated with the text
	 */
	
	public static ChatColor getColor(String colorCode){
		switch(colorCode){
			case "0": return ChatColor.BLACK;
			case "1": return ChatColor.DARK_BLUE;
			case "2": return ChatColor.DARK_GREEN;
			case "3": return ChatColor.DARK_AQUA;
			case "4": return ChatColor.DARK_RED;
			case "5": return ChatColor.DARK_PURPLE;
			case "6": return ChatColor.GOLD;
			case "7": return ChatColor.GRAY;
			case "8": return ChatColor.DARK_GRAY;
			case "9": return ChatColor.BLUE;
			case "a": return ChatColor.GREEN;
			case "b": return ChatColor.AQUA;
			case "c": return ChatColor.RED;
			case "d": return ChatColor.LIGHT_PURPLE;
			case "e": return ChatColor.YELLOW;
			case "f": return ChatColor.WHITE;
			case "l": return ChatColor.BOLD;
			case "n": return ChatColor.UNDERLINE;
			case "o": return ChatColor.ITALIC;
			case "k": return ChatColor.MAGIC;
			case "m": return ChatColor.STRIKETHROUGH;
			case "r": return ChatColor.RESET;
			default: return null;
		}
	}
	
	/**
	 * Decodes a message's color codes
	 * 
	 * @param encoded A message with color codes in it
	 * @return The decrypted message with the color codes applied to it
	 */
	
	public static String decodeMessage(String encoded){
		String decoded = "";
		if(encoded.contains("&")){
			String[] words = encoded.split("&");
			for(String str : words){
				try{
					char color = str.charAt(0);
					String color1 = String.valueOf(color);
					ChatColor dcolor = getColor(color1);
					String str1 = str.substring(1, str.length());
					String decodedpart = dcolor + str1;
					decoded += decodedpart;	
				}catch(Exception e){continue;}
			}
		}else return encoded;
		return decoded;
	}
	
	/**
	 * Adds the plugin prefix to the message
	 * 
	 * @param message The message to add the prefix to
	 * @return The message with the prefix on it
	 */
	
	public static String addPrefix(String message){
		if(!settings.getConfig().getBoolean("Show-Prefix-Before-Messages")) return message;
		return settings.getMessage("Plugin-Prefix") + " " + message;
	}
	
	/**
	 * Sends a message to console
	 * @param message The message to send
	 */
	
	public static void messageConsole(String message){
		Bukkit.getConsoleSender().sendMessage(message);
	}
	
}
