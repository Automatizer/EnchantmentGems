package me.Smc.eg.listeners;

import java.util.ArrayList;
import java.util.UUID;

import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.plugin.Plugin;

import me.Smc.eg.enchants.EnchantManager;

public class MoveEvent implements Listener{

	Plugin plugin;
	int rateLimit = 100;
	long prevTime = 0;
	public static ArrayList<UUID> readyToJump = new ArrayList<UUID>();
	
	public MoveEvent(Plugin plugin){
		this.plugin = plugin;
		plugin.getServer().getPluginManager().registerEvents(this, plugin);
	}
	
	@EventHandler
	public void onMove(PlayerMoveEvent e){
		//if(System.currentTimeMillis() >= prevTime + rateLimit){
		//	prevTime = System.currentTimeMillis();
		//	if(e.getPlayer().getInventory().getChestplate() != null && EnchantManager.hasEnchant(e.getPlayer().getInventory().getChestplate(), "magnet")) {
				//EnchantManager.callEvent(e.getPlayer().getInventory().getChestplate(), "onMove", e.getPlayer(), null, 0.0, null);
		//	}
		//}	
		if(!e.isCancelled() && e.getFrom().getBlock().getLocation() != e.getTo().getBlock().getLocation()){
			final Player player = e.getPlayer();
			final UUID uuid = player.getUniqueId();
			if(player.getLocation().getBlock().getRelative(BlockFace.DOWN).getType() != Material.AIR){
				if(player.getInventory().getLeggings() != null && EnchantManager.hasEnchant(player.getInventory().getLeggings(), "leaping"))
					player.setAllowFlight(true);
				if(readyToJump.contains(uuid) || player.getGameMode().equals(GameMode.CREATIVE) || player.getGameMode().equals(GameMode.SPECTATOR)) return;
				player.setAllowFlight(false);
				if(player.getInventory().getLeggings() != null && EnchantManager.hasEnchant(player.getInventory().getLeggings(), "leaping")){
					player.setAllowFlight(true);
					readyToJump.add(uuid);	
				}	
			}
		}
	}
	
}
