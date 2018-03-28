package me.Smc.eg.enchants;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.material.MaterialData;

import com.gmail.nossr50.datatypes.player.McMMOPlayer;
import com.gmail.nossr50.datatypes.skills.XPGainReason;
import com.gmail.nossr50.skills.mining.Mining;
import com.gmail.nossr50.skills.mining.MiningManager;
import com.gmail.nossr50.util.player.UserManager;

import me.Smc.eg.main.Main;
import me.Smc.eg.utils.Utils;

public class Massbreaker extends Enchant{

	public Massbreaker() {
		super("massbreaker");
	}

	@SuppressWarnings("deprecation")
	@Override
	public void setDefaults() {
		typesAllowed.add("pickaxe");
		displayName = "&7Massbreaker {enchantlevel}";
		maxLevel = 1;
		event = "blockBreak";
		crystal = new Crystal(this);
		crystal.displayName = "&3Massbreaker {enchantlevel}";
		crystal.material = new MaterialData(Material.EMERALD, (byte) 0);
	}

	@Override
	public void callEvent(ItemStack item, Player player, Entity entity, double value, Block block) {
		if(EnchantManager.hasEnchant(item, "massbreaker")) {
			if(isEnabled(player)) {
				for(Block b : getBlocks(block.getLocation(), EnchantManager.getEnchantLevel(item, this))) {
					if(!((b.getType().equals(Material.AIR)) || (b.getType().equals(Material.BEDROCK)) || (b.getType().equals(Material.WATER)) || (b.getType().equals(Material.LAVA)))) {
						boolean bool = false;
						if(Bukkit.getPluginManager().getPlugin("mcMMO") != null && Bukkit.getPluginManager().getPlugin("mcMMO").isEnabled()) {
							McMMOPlayer mp = UserManager.getPlayer(player);
							MiningManager manager = mp.getMiningManager();
							if(isOre(b)) { 
								manager.miningBlockCheck(b.getState());
								bool = true;
							}else manager.applyXpGain(Mining.getBlockXp(b.getState()), XPGainReason.PVE);
						}
						if(item.getItemMeta().hasEnchant(Enchantment.SILK_TOUCH)) {
							BlockState bs = b.getState();
							ItemStack is = new ItemStack(bs.getData().toItemStack(1));
							if(!bool) b.getWorld().dropItem(b.getLocation(), is);
							b.setType(Material.AIR);
						}else {
							b.breakNaturally();
						}
					}
				}
			}
		}
	}
	
	private ArrayList<Block> getBlocks(Location loc, int radius){
		ArrayList<Block> blocks = Utils.getNearbyBlocks(loc, radius);
		return blocks;
	}
	
	private boolean isEnabled(Player p) {
		if(Main.massbreakers.contains(p)) {
			return true;
		}else return false;
	}
	
	private boolean isOre(Block b) {
		switch(b.getType()) {
		case COAL_ORE: return true;
		case IRON_ORE: return true;
		case GOLD_ORE: return true;
		case DIAMOND_ORE: return true;
		case EMERALD_ORE: return true;
		case GLOWING_REDSTONE_ORE: return true;
		case REDSTONE_ORE: return true;
		case LAPIS_ORE: return true;
		case QUARTZ_ORE: return true;
		default: return false;
		}
	}

}
