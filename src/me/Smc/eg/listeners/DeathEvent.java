package me.Smc.eg.listeners;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.entity.Skeleton;
import org.bukkit.entity.Witch;
import org.bukkit.entity.Zombie;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;

import me.Smc.eg.commands.Executor;
import me.Smc.eg.enchants.EnchantManager;
import me.Smc.eg.utils.Utils;

public class DeathEvent implements Listener{

	Plugin plugin;
	
	public DeathEvent(Plugin plugin){
		this.plugin = plugin;
		plugin.getServer().getPluginManager().registerEvents(this, plugin);
	}
	
	@EventHandler
	public void deathEvent(EntityDeathEvent e){
		if(e.getEntity() instanceof Zombie){
			ItemStack drop = new ItemStack(Material.AIR);
			int random = Utils.randomBetween(0, 10000);
			if(random >= 0 && random < 26) //0.25%
				drop = new ItemStack(Material.RABBIT_FOOT);
			else if(random >= 50 && random < 201) //1.5%
				drop = EnchantManager.getEnchant("purify").getCrystal().getItem(1);
			else if(random >= 250 && random < 301) //0.5%
				drop = EnchantManager.getEnchant("purify").getCrystal().getItem(2);
			else if(random >= 350 && random < 376) //0.25%
				drop = EnchantManager.getEnchant("purify").getCrystal().getItem(3);
			if(drop.getType() != Material.AIR) e.getEntity().getWorld().dropItemNaturally(e.getEntity().getLocation(), drop);
		}else if(e.getEntity() instanceof Witch){
			ItemStack drop = new ItemStack(Material.AIR);
			int random = Utils.randomBetween(0, 10000);
			if(random >= 0 && random < 501) //5%
				drop = EnchantManager.getEnchant("lifesteal").getCrystal().getItem(1);
			else if(random >= 1000 && random < 1201) //2%
				drop = EnchantManager.getEnchant("lifesteal").getCrystal().getItem(2);
			else if(random >= 1500 && random < 1551) //0.5%
				drop = EnchantManager.getEnchant("lifesteal").getCrystal().getItem(3);
			if(drop.getType() != Material.AIR) e.getEntity().getWorld().dropItemNaturally(e.getEntity().getLocation(), drop);
		}else if(e.getEntity() instanceof Skeleton){
			ItemStack drop = new ItemStack(Material.AIR);
			int random = Utils.randomBetween(0, 10000);
			if(random >= 50 && random < 201) //1.5%
				drop = EnchantManager.getEnchant("purify").getCrystal().getItem(1);
			else if(random >= 250 && random < 301) //0.5%
				drop = EnchantManager.getEnchant("purify").getCrystal().getItem(2);
			else if(random >= 350 && random < 376) //0.25%
				drop = EnchantManager.getEnchant("purify").getCrystal().getItem(3);
			if(drop.getType() != Material.AIR) e.getEntity().getWorld().dropItemNaturally(e.getEntity().getLocation(), drop);
		}
		if(e.getEntity().getKiller() != null){
			Player killer = e.getEntity().getKiller();
			if(killer.getItemOnCursor() != null) EnchantManager.callEvent(killer.getItemOnCursor(), "killedEntity", killer, e.getEntity(), 0.0, null);
		}
	}
	
	@EventHandler
	public void playerDeathEvent(PlayerDeathEvent e){
		Player player = e.getEntity();
		if(Executor.sudoku.containsKey(player.getUniqueId())){
			String message = player.getName();
			for(String arg : Executor.sudoku.get(player.getUniqueId()))
				message += " " + arg;
			e.setDeathMessage(message);
			Executor.sudoku.remove(player.getUniqueId());
		}
	}
	
}
