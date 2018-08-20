package me.auto.eg.utils;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import com.gmail.nossr50.datatypes.player.McMMOPlayer;
import com.gmail.nossr50.datatypes.skills.XPGainReason;
import com.gmail.nossr50.skills.mining.Mining;
import com.gmail.nossr50.skills.mining.MiningManager;
import com.gmail.nossr50.util.player.UserManager;

import me.auto.eg.enchants.EnchantManager;
import me.auto.eg.main.Main;

/**
 * This class handles utilitary methods.
 * 
 * @author Smc
 */

public class Utils{

	/**
	 * Fills in the ItemMeta of the ItemStack
	 * @param item The ItemStack to fill
	 * @param displayName The display name to set
	 * @param lore The lore to set
	 * @return The modified ItemStack
	 */
	
	public static ItemStack addToIM(ItemStack item, String displayName, List<String> lore){
		ItemMeta im = item.getItemMeta();
		if(displayName != null && displayName != "") im.setDisplayName(displayName);
		if(lore != null) im.setLore(lore);
		item.setItemMeta(im);
		return item;
	}
	
	/**
	 * Returns the number passed as a roman numeral
	 * 
	 * @param num The number to convert
	 * @return The roman numeral equivalent of the number
	 */
	
	public static String getIntInRoman(int num){
		switch(num){
			case 1: return "I";
			case 2: return "II";
			case 3: return "III";
			case 4: return "IV";
			case 5: return "V";
			case 6: return "VI";
			case 7: return "VII";
			case 8: return "VIII";
			case 9: return "IX";
			case 10: return "X";
			case 11: return "XI";
			case 12: return "XII";
			case 13: return "XIII";
			case 14: return "XIV";
			case 15: return "XV";
			case 16: return "XVI";
			case 17: return "XVII";
			case 18: return "XVIII";
			case 19: return "XIX";
			case 20: return "XX";
			default: return String.valueOf(num);
		}
	}
	
	/**
	 * Returns the roman numeral passed as a number
	 * 
	 * @param num The roman numeral to convert
	 * @return The number equivalent of the roman numeral
	 */
	
	public static int getRomanInInt(String roman){
		switch(roman.toUpperCase()){
			case "I": return 1;
			case "II": return 2;
			case "III": return 3;
			case "IV": return 4;
			case "V": return 5;
			case "VI": return 6;
			case "VII": return 7;
			case "VIII": return 8;
			case "IX": return 9;
			case "X": return 10;
			case "XI": return 11;
			case "XII": return 12;
			case "XIII": return 13;
			case "XIV": return 14;
			case "XV": return 15;
			case "XVI": return 16;
			case "XVII": return 17;
			case "XVIII": return 18;
			case "XIX": return 19;
			case "XX": return 20;
			default: return -1;
		}
	}
	
	/**
	 * Returns the item's category based on its material
	 * 
	 * @param item The ItemStack to check
	 * @return The category of the ItemStack
	 */
	
	public static String getCategory(ItemStack item, boolean verifySpecials){
		String matName = item.getType().toString().toLowerCase();
		if(InventoryUtils.compareItems(item, Recipes.getRepairGem()) && EnchantManager.checkItemWatermark(item)) return "repairgem";
		if(InventoryUtils.compareItems(item, Recipes.getSpeedGem()) && EnchantManager.checkItemWatermark(item)) return "speedgem";
		if(InventoryUtils.compareItems(item, Recipes.getLeapingGem()) && EnchantManager.checkItemWatermark(item)) return "leapinggem";
		if(EnchantManager.isCrystal(item) && verifySpecials) return "crystal";
		if(!EnchantManager.getEnchants(item).isEmpty() && verifySpecials) return "enchanted";
		if(matName.contains("hoe")) return "hoe";
		if(matName.contains("axe") && !matName.contains("pickaxe")) return "axe";
		if(matName.contains("sword")) return "sword";
		if(matName.contains("shovel")) return "shovel";
		if(matName.contains("pickaxe")) return "pickaxe";
		if(matName.contains("bow")) return "bow";
		if(matName.contains("helmet")) return "helmet";
		if(matName.contains("chestplate")) return "chestplate";
		if(matName.contains("leggings")) return "leggings";
		if(matName.contains("boots")) return "boots";
		if(matName.contains("barrier")) return "barrier";
		if(matName.contains("fishing")) return "fishing";
		return "other";
	}
	
	/**
	 * Converts a string to an integer
	 * 
	 * @param toConvert The string to convert
	 * @return The integer converted
	 */
	
	public static int stringToInt(String toConvert){
		int toReturn = -1;
		try{
			toReturn = Integer.parseInt(toConvert);
		}catch(Exception ex){
			toReturn = -1;
		}
		return toReturn;
	}
	
	/**
	 * Converts a string to a double
	 * 
	 * @param toConvert The string to convert
	 * @return The double converted
	 */
	
	public static double stringToDouble(String toConvert){
		double toReturn = -1;
		try{
			toReturn = Double.parseDouble(toConvert);
		}catch(Exception ex){
			toReturn = -1;
		}
		return toReturn;
	}
	
	/**
	 * Converts a string to a boolean
	 * 
	 * @param toConvert The string to convert
	 * @return The boolean converted
	 */
	
	public static boolean stringToBoolean(String toConvert){
		boolean toReturn = false;
		try{
			toReturn = Boolean.parseBoolean(toConvert);
		}catch(Exception ex){
			toReturn = false;
		}
		return toReturn;
	}
	
	/**
	 * Returns an integer value between the two extremes
	 * 
	 * @param min The minimum value that can be returned
	 * @param max The maximum value that can be returned
	 * @return The randomized value
	 */
	
	public static int randomBetween(int min, int max){
		return (int) (Math.random() * (max - min) + min);
	}
	
    public static Entity[] getNearbyEntities(Location l, int radius){
        int chunkRadius = radius < 16 ? 1 : (radius - (radius % 16))/16;
        HashSet<Entity> radiusEntities = new HashSet<Entity>();
            for (int chX = 0 -chunkRadius; chX <= chunkRadius; chX ++){
                for (int chZ = 0 -chunkRadius; chZ <= chunkRadius; chZ++){
                    int x = (int) l.getX(),y=(int) l.getY(),z=(int) l.getZ();
                    for (Entity e : new Location(l.getWorld(),x+(chX*16),y,z+(chZ*16)).getChunk().getEntities()){
                        if (e.getLocation().distance(l) <= radius && e.getLocation().getBlock() != l.getBlock()) radiusEntities.add(e);
                    }
                }
            }
        return radiusEntities.toArray(new Entity[radiusEntities.size()]);
    }
    
    public static ArrayList<Block> getNearbyBlocks(Location l, int radius){
    	ArrayList<Block> blocks = new ArrayList<Block>();
    	for (int x = radius; x >= -radius; x--) {
            for (int y = radius; y >= -radius; y--) {
                for (int z = radius; z >= -radius; z--) {
                    Block b = l.getBlock().getRelative(x, y, z);
                    blocks.add(b);
                }
            }
        }
    	return blocks;
    }
    
    public static ArrayList<Chunk> getNearbyChunks(Location l, int radius){
    	ArrayList<Chunk> chunks = new ArrayList<Chunk>();
    	List<Integer> range = IntStream.rangeClosed(-radius, radius).boxed().collect(Collectors.toList());
    	Chunk origin = l.getChunk();
    	World world = origin.getWorld();
    	int baseX = origin.getX();
    	int baseZ = origin.getZ();
    	for(int x : range) {
    		for(int z : range) {
    			Chunk c = world.getChunkAt(baseX + x, baseZ + z);
    			chunks.add(c);
    		}
    	}
    	return chunks;
    }
    
    public static ArrayList<Block> getAdjacentBlocks(Block b){
    	ArrayList<Block> blocks = new ArrayList<Block>();
    	for(BlockFace bf : BlockFace.values()) {
    		if((b.getRelative(bf) != null)) {
    			blocks.add(b.getRelative(bf));
    		}
    	}
    	return blocks;
    }
    
    public static boolean isOre(Block b) {
		switch(b.getType()) {
		case COAL_ORE: return true;
		case IRON_ORE: return true;
		case GOLD_ORE: return true;
		case DIAMOND_ORE: return true;
		case EMERALD_ORE: return true;
		case REDSTONE_ORE: return true;
		case LAPIS_ORE: return true;
		case NETHER_QUARTZ_ORE: return true;
		default: return false;
		}
	}
    
    public static void breakCheck(List<Block> blocks, Player p, ItemStack is, Location loc, List<Material> blacklist, boolean blacklistType) {
    	if(is.getItemMeta().hasEnchant(Enchantment.SILK_TOUCH)) {
			for(Block b : blocks) {
				if(blacklist.contains(b.getType()) == blacklistType) {
					if(Main.mcMMO) {
						McMMOPlayer mp = UserManager.getPlayer(p);
						MiningManager manager = mp.getMiningManager();
						manager.applyXpGain(Mining.getBlockXp(b.getState()) / 10, XPGainReason.PVE);
					}
					ItemStack item = new ItemStack(b.getType());
					b.getWorld().dropItem(loc, item);
					b.setType(Material.AIR);
				}
			}
		}else {
			for(Block b : blocks) {
				if(blacklist.contains(b.getType()) == blacklistType) {
					if(Main.mcMMO) {
						McMMOPlayer mp = UserManager.getPlayer(p);
						MiningManager manager = mp.getMiningManager();
						manager.applyXpGain(Mining.getBlockXp(b.getState()) / 10, XPGainReason.PVE);
					}
					p.giveExp(getBlockExp(b));
					b.breakNaturally();
				}
			}
		}
    }
    
    public static int getBlockExp(Block block) {
    	switch(block.getType()) {
    	case EMERALD_ORE: return 5;
    	case DIAMOND_ORE:
    	case LAPIS_ORE: return 3;
    	case REDSTONE_ORE:
    	case NETHER_QUARTZ_ORE: return 2;
    	case COAL_ORE: return 1;
    	case IRON_ORE: 
    	case GOLD_ORE:
    	default: return 0;
    	}
    }
	
}
