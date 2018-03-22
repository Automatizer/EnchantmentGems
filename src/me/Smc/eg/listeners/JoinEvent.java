package me.Smc.eg.listeners;

import java.io.File;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.Plugin;

import me.Smc.eg.utils.Configuration;

public class JoinEvent implements Listener{

	Plugin plugin;
	
	public JoinEvent(Plugin plugin){
		this.plugin = plugin;
		plugin.getServer().getPluginManager().registerEvents(this, plugin);
	}
	
	@EventHandler
	public void onJoin(PlayerJoinEvent e){
		@SuppressWarnings("unused")
		Configuration cfg = new Configuration(new File("players/" + e.getPlayer().getUniqueId().toString() + ".fuck"));
	}
	
}
