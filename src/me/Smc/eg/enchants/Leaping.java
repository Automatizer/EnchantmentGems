package me.Smc.eg.enchants;

import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;

import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Snowball;
import org.bukkit.inventory.ItemStack;
import org.bukkit.material.MaterialData;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

import com.comphenix.packetwrapper.WrapperPlayServerMapChunk;
import com.comphenix.protocol.ProtocolManager;

import me.Smc.eg.main.Main;
import me.Smc.eg.utils.EntityHider;
import me.Smc.eg.utils.Utils;

public class Leaping extends Enchant{
	
	public Leaping(){
		super("leaping");
	}
	
	public HashMap<String, Integer> charge = new HashMap<String, Integer>();
	EntityHider entityHider = Main.entityHider;
	ProtocolManager pm = Main.protocolManager;

	@Override
	public void setDefaults(){
		typesAllowed.add("leggings");
		displayName = "&7Leaping {enchantlevel}";
		maxLevel = 20;
		event = "onSneak";
		permission = "eg.enchant.leaping.#";
		crystal = new Crystal(this);
		crystal.displayName = "&3Leaping {enchantlevel}";
		crystal.material = new MaterialData(Material.EMERALD);
	}

	@Override
	public void callEvent(ItemStack item, Player player, Entity target, double value, Block block){
		if(EnchantManager.hasEnchant(item, this.name)) {
			int level = EnchantManager.getEnchantLevel(item, this);
			Timer timer = new Timer();
			if(value == 0.0) {
				timer.scheduleAtFixedRate(new TimerTask() {
					public void run() {
						if((!(getCharge(player) >= level)) && player.isSneaking()) {
							setCharge(player, getCharge(player) + 1);
							player.playSound(player.getLocation(), Sound.BLOCK_NOTE_CHIME, 1, 1);
						}else {
							jump(player, getCharge(player));
							cancel();
						}
					}
				}, 0, 1000);
			}
		}
	}
	
	public static void addEffect(Player p, ItemStack is) {
		int level = EnchantManager.getEnchantLevel(is, EnchantManager.getEnchant("leaping"));
		int i;
		switch(level) {
		case 1: 
		case 2:
		case 3:
		case 4:
		case 5: i = 1; break;
		case 6:
		case 7:
		case 8:
		case 9:
		case 10: i = 2; break;
		case 11:
		case 12:
		case 13:
		case 14:
		case 15: i = 3; break;
		case 16:
		case 17:
		case 18:
		case 19:
		case 20: i = 4; break;
		default: i = 1;
		}
		p.addPotionEffect(new PotionEffect(PotionEffectType.JUMP, Integer.MAX_VALUE, i - 1));
	}
	
	private void setCharge(Player p, int value) {
		charge.put(p.getName(), value);
	}
	
	private int getCharge(Player p) {
		if(!charge.containsKey(p.getName())) {setCharge(p, 0);}
		return charge.get(p.getName());
	}
	
	private void jump(Player p, int i) {
		Bukkit.getScheduler().scheduleSyncDelayedTask(Main.plugin, new SnowballSpawner(p));
		Thread t = new Thread(new Runnable() {
			public void run() {
				try {
					Thread.sleep(50);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		});
		t.start();
		try {
			t.join();
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		}
		Snowball sb = SnowballSpawner.getSnowball();
		sb.setVelocity(sb.getVelocity().multiply(i/3.5));
		entityHider.hideEntity(p, sb);
		
		new BukkitRunnable() {
			public void run() {
				sb.getLocation().setDirection(sb.getVelocity());
				p.teleport(sb.getLocation().add(0, 1, 0).setDirection(sb.getVelocity()));
				WrapperPlayServerMapChunk chunk = new WrapperPlayServerMapChunk();
				for(Chunk c : Utils.getNearbyChunks(p.getLocation(), 2)) {
					if(!c.isLoaded()) {
						c.load();
						chunk.setChunkMap(c);
						chunk.sendPacket(p);
					}
				}
				for(Block b : Utils.getNearbyBlocks(p.getLocation(), 1)) {
					if(!b.isEmpty()) cancel();
				}
				if(sb.isDead()) {
					cancel();
				}
			}
		}.runTaskTimer(Main.plugin, 1, 1);
		setCharge(p, 0);
	}
	
}

class SnowballSpawner implements Runnable{
	
	static Player p;
	
	SnowballSpawner(Player p){
		SnowballSpawner.p = p;
	}
	
	static Snowball sb;
	@Override
	public void run() {
		sb = p.launchProjectile(Snowball.class);
	}
	
	public static Snowball getSnowball() {
		return sb;
	}
	
}
