package me.Smc.eg.enchants;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.material.MaterialData;

import me.Smc.eg.listeners.MoveEvent;

public class Leaping extends Enchant{

	public static HashMap<UUID, Integer> jumpsDone = new HashMap<UUID, Integer>();
	public static HashMap<UUID, Integer> jumpLimit = new HashMap<UUID, Integer>();
	public static ArrayList<UUID> protectedFromFall = new ArrayList<UUID>();
	
	public Leaping(){
		super("leaping");
	}

	@SuppressWarnings("deprecation")
	@Override
	public void setDefaults(){
		typesAllowed.add("leggings");
		displayName = "&7Leaping {enchantlevel}";
		maxLevel = 20;
		event = "onFlight";
		permission = "eg.enchant.leaping.#";
		crystal = new Crystal(this);
		crystal.displayName = "&3Leaping {enchantlevel}";
		crystal.material = new MaterialData(Material.EMERALD, (byte) 0);
		setOption("extraJumps", "1");
		setOption("extraJumpsPerLevel", "0.2");
		setOption("baseJumpPower", "1");
		setOption("extraJumpPowerPerLevel", "0.2");
	}

	@Override
	public void callEvent(ItemStack item, final Player player, Entity target, double value, Block block){
		int level = EnchantManager.getEnchantLevel(item, this);
		UUID uuid = player.getUniqueId();
		if(!protectedFromFall.contains(uuid)) protectedFromFall.add(uuid);
		int jumpsDone = 0;
		int jumpLimit = (int) Math.floor(getIntOption("extraJumps") + level * getDoubleOption("extraJumpsPerLevel"));
		if(Leaping.jumpsDone.containsKey(uuid)) jumpsDone = Leaping.jumpsDone.get(uuid);
		if(Leaping.jumpLimit.containsKey(uuid)) jumpLimit = Leaping.jumpLimit.get(uuid);
		player.setFlying(false);
		double power = getIntOption("baseJumpPower") + (level - 1) * getDoubleOption("extraJumpPowerPerLevel");
		player.setVelocity(player.getLocation().getDirection().multiply(1 * power).setY(0.75 * power));
		jumpsDone++;
		Leaping.jumpsDone.put(uuid, jumpsDone);
		Leaping.jumpLimit.put(uuid, jumpLimit);
		if(jumpsDone >= jumpLimit){
			player.setAllowFlight(false);
			Leaping.jumpsDone.remove(uuid);
			Leaping.jumpLimit.remove(uuid);
			MoveEvent.readyToJump.remove(uuid);
		}
	}
	
}
