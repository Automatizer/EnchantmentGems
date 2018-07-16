package me.auto.eg.listeners;

import java.util.Set;
import java.util.UUID;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.plugin.Plugin;
import org.bukkit.potion.PotionEffectType;

import com.google.common.collect.Sets;

import me.auto.eg.enchants.EnchantManager;

public class JumpEvent implements Listener{

	Plugin pl;
	
	public JumpEvent(Plugin plugin) {
		this.pl = plugin;
		plugin.getServer().getPluginManager().registerEvents(this, pl);
	}
	
	private Set<UUID> prevPlayersOnGround = Sets.newHashSet();
	 
    @EventHandler
    public void onMove(PlayerMoveEvent e) {
        Player player = e.getPlayer();
        if (player.getVelocity().getY() > 0) {
            double jumpVelocity = (double) 0.42F;
            if (player.hasPotionEffect(PotionEffectType.JUMP)) {
                jumpVelocity += (double) ((float) (player.getPotionEffect(PotionEffectType.JUMP).getAmplifier() + 1) * 0.1F);
            }
            if (e.getPlayer().getLocation().getBlock().getType() != Material.LADDER && prevPlayersOnGround.contains(player.getUniqueId())) {
                if (!player.isOnGround() && Double.compare(player.getVelocity().getY(), jumpVelocity) == 0 && player.isSneaking() && (player.getInventory().getLeggings() != null)) {
                	EnchantManager.callEvent(player.getInventory().getLeggings(), "onLeap", player, null, 0, null);
                }
            }
        }
        if (player.isOnGround()) {
            prevPlayersOnGround.add(player.getUniqueId());
        } else {
            prevPlayersOnGround.remove(player.getUniqueId());
        }
    }
	
}
