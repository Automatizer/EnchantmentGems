package me.auto.eg.listeners;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;

import me.auto.eg.commands.Executor;
import me.auto.eg.enchants.Enchant;
import me.auto.eg.enchants.EnchantManager;
import me.auto.eg.utils.Utils;

public class DeathEvent implements Listener{

	Plugin plugin;
	
	public DeathEvent(Plugin plugin){
		this.plugin = plugin;
		plugin.getServer().getPluginManager().registerEvents(this, plugin);
	}
	
	@EventHandler
	public void deathEvent(EntityDeathEvent e){
		 LivingEntity entity = e.getEntity();
		 Location loc = entity.getLocation();
		 Enchant enc = null;
		 ItemStack drop = new ItemStack(Material.AIR);
		 int one = 0;
		 int two = 0;
		 int rand = Utils.randomBetween(1, 100);
		 int lvl = 0;
		 if(rand >= 1 && rand <= 34) {
			 lvl = 1;
		 }else if(rand >= 35 && rand < 67) {
			 lvl = 2;
		 }else if(rand >= 67 && rand < 100) {
			 lvl = 3;
		 }
		 switch(entity.getType()) {
		 /*case ZOMBIE: 
		 case ZOMBIE_VILLAGER:
		 case SKELETON: enc = EnchantManager.getEnchant("purify"); two = 16; break;*/
		 case ENDER_DRAGON: enc = EnchantManager.getEnchant("replenish"); two = 1000; lvl = 1; break;
		 case WITHER: enc = EnchantManager.getEnchant("keepinventory"); two = 2500; lvl = 1; break;
		 case WITCH: enc = EnchantManager.getEnchant("lifesteal"); two = 16; break;
		 default: break;
		 }
		 if(enc != null) {
			 drop = enc.getCrystal().getItem(lvl);
		 }
		 if(drop.getType() != Material.AIR) {
			 int r = Utils.randomBetween(0, 10000);
			 if(r >= one && r <= two && entity.getKiller() instanceof Player) {
				 Player p = entity.getKiller();
				 EnchantManager.dropCrystal(drop, loc, enc, p, lvl);
			 }
		 }
		
		if(entity.getKiller() != null && entity.getKiller() instanceof Player){
			Player killer = e.getEntity().getKiller();
			if(killer.getInventory().getItemInMainHand() != null) EnchantManager.callEvent(killer.getInventory().getItemInMainHand(), "killedEntity", killer, e.getEntity(), 0.0, null);
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
